package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Subject;
import com.university.repository.interfaces.SugangRepository;
import com.university.util.DBUtil;

public class SugangRepositoryImpl implements SugangRepository {

	private static final String SELECT_ALL_SUB = " select * from subject_tb ";
	private static final String INSERT_SUB = " insert into stu_sub_tb values(?, ?) ";
	private static final String SELECT_SUB_BY_STUDENT_ID_AND_SEMESTER = " SELECT student_id, subject_id, su.name AS subject_name, p.name AS professor_name, grades, sub_day, start_time, end_time, num_of_student, capacity, room_id FROM stu_sub_tb AS ss LEFT JOIN subject_tb AS su ON ss.subject_id = su.id LEFT JOIN professor_tb AS p ON su.professor_id = p.id WHERE student_id = ? AND sub_year = ? AND semester = ? ";
	
	@Override
	public void updatePeriod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Subject> selectAllSub(int limit, int offset) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUB)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return subjectList;
	}

	@Override
	public void addSubStudent(int studentId, int subjectId) {
		
	}

	@Override
	public List<Subject> selectSubStudent(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
