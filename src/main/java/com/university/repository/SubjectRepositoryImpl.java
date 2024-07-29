package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.model.Syllabus;
import com.university.repository.interfaces.SubjectRepository;
import com.university.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository {

	private static final String SELECT_SYLLABUS_BY_ID = " SELECT sy.subject_id, s.name, s.sub_year, s.semester, s.grades, s.type, s.sub_day, s.start_time, s.end_time, s.room_id, c.name college_name, p.name as professor_name, d.name as dept_name, p.tel, p.email, sy.overview, sy.objective, sy.textbook, sy.program FROM subject_tb s JOIN professor_tb p ON s.professor_id = p.id JOIN department_tb d ON p.dept_id = d.id JOIN syllabus_tb sy ON s.id = sy.subject_id JOIN room_tb r ON s.room_id = r.id JOIN college_tb c ON r.college_id = c.id WHERE subject_id = ? ";

	

	@Override
	public List<StuSubDetail> getAllStuSubDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StuSubDetail getStuSubDetail(Student student, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Syllabus getSyllabusById(int subjectId) {
		Syllabus syllabus = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SYLLABUS_BY_ID)) {
			pstmt.setInt(1, subjectId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				syllabus = Syllabus.builder().subjectId(subjectId).name(rs.getString("name"))
						.subYear(rs.getInt("sub_year")).semester(rs.getInt("semester")).grades(rs.getInt("grades"))
						.type(rs.getString("type")).subDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
						.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
						.collName(rs.getString("college_name")).professorName(rs.getString("professor_name"))
						.deptName(rs.getString("dept_name")).tel(rs.getString("tel")).email(rs.getString("email"))
						.overview(rs.getString("overview")).objective(rs.getString("objective"))
						.textbook(rs.getString("textbook")).program(rs.getString("program")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return syllabus;
	}

}
