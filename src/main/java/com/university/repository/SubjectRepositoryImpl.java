package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.model.Syllabus;
import com.university.repository.interfaces.SubjectRepository;
import com.university.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository {

	private static final String SELECT_SYLLABUS_BY_ID = " SELECT sy.subject_id, s.name, s.sub_year, s.semester, s.grades, s.type, s.sub_day, s.start_time, s.end_time, s.room_id, c.name college_name, p.name as professor_name, d.name as dept_name, p.tel, p.email, sy.overview, sy.objective, sy.textbook, sy.program FROM subject_tb s JOIN professor_tb p ON s.professor_id = p.id JOIN department_tb d ON p.dept_id = d.id JOIN syllabus_tb sy ON s.id = sy.subject_id JOIN room_tb r ON s.room_id = r.id JOIN college_tb c ON r.college_id = c.id WHERE subject_id = ? ";
	
	private static final String SELECT_ALL_SUBJECT_LIST = " SELECT sub.*, dept.name AS dept_name, coll.name AS coll_name, prof.name AS professor_name FROM subject_tb AS sub JOIN department_tb AS dept ON sub.dept_id = dept.id JOIN college_tb AS coll ON dept.college_id = coll.id JOIN professor_tb AS prof ON sub.professor_id = prof.id ORDER BY id ASC limit ? offset ? ";
	private static final String COUNT_ALL_SUBJECT = " SELECT count(*) as count FROM subject_tb ";

	private static final String SELECT_SUBJECT_BY_SEARCH = " select  sub.*, dept.name AS dept_name, coll.name AS coll_name, prof.name AS professor_name "
			+ " FROM subject_tb AS sub " + " JOIN department_tb AS dept " + " ON sub.dept_id = dept.id "
			+ " JOIN college_tb AS coll " + " ON dept.college_id = coll.id " + " JOIN professor_tb AS prof "
			+ " ON sub.professor_id = prof.id "
			+ " where sub.sub_year  = ? and sub.semester =  ? and sub.name like ?  and (dept.id = ? OR ? = -1) "
			+ " ORDER BY id asc " + " limit ? offset ? ";

	private static final String COUNT_SUBJECT_BY_SEARCH = " select count(*) as count " + " FROM subject_tb AS sub "
			+ " JOIN department_tb AS dept " + " ON sub.dept_id = dept.id " + " JOIN college_tb AS coll "
			+ " ON dept.college_id = coll.id " + " JOIN professor_tb AS prof " + " ON sub.professor_id = prof.id "
			+ " where sub.sub_year = ? and sub.semester = ? and sub.name like ? and (dept.id = ? OR ? = -1) "
			+ " ORDER BY sub.id asc ";

	private static final String UPDATE_SYLLABUS_BY_SUBJECT_ID = " UPDATE syllabus_tb SET " + " overview = ?, "
			+ "	objective = ?, " + "	textbook = ?, " + "	program = ? " + "	WHERE subject_id = ? ";

	

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

	@Override
	public List<Subject> getAllSubject(int limit, int offset) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUBJECT_LIST)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject subjectAll = Subject.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.professorId(rs.getInt("professor_id")).roomId(rs.getString("room_id"))
						.deptId(rs.getInt("dept_id")).type(rs.getString("type")).subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester")).subDay(rs.getString("sub_day"))
						.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time")).grades(rs.getInt("grades"))
						.capacity(rs.getInt("capacity")).numOfStudent(rs.getInt("num_of_student"))
						.deptName(rs.getString("dept_name")).collName(rs.getString("coll_name"))
						.professorName(rs.getString("professor_name")).build();
				subjectList.add(subjectAll);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subjectList;
	}

	@Override
	public int getTotalSubjectCount() {
		int count = 0;

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_SUBJECT)) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("totalCount : " + count);
		return count;
	}

	@Override
	public List<Subject> getSubjectBySearch(int subYear, int semester, String name, int deptId, int limit, int offset) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBJECT_BY_SEARCH)) {
			if (name == null) {
				name = "";
			}

			pstmt.setInt(1, subYear);
			pstmt.setInt(2, semester);
			pstmt.setString(3, "%" + name + "%");
			pstmt.setInt(4, deptId);
			pstmt.setInt(5, deptId);
			pstmt.setInt(6, limit);
			pstmt.setInt(7, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject subjectAll = Subject.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
						.collName(rs.getString("coll_name")).deptName(rs.getString("dept_name")).id(rs.getInt("id"))
						.type(rs.getString("type")).name(rs.getString("name"))
						.professorName(rs.getString("professor_name")).grades(rs.getInt("grades"))
						.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity")).build();

				subjectList.add(subjectAll);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subjectList;
	}

	@Override
	public int getTotalSubjectBySearch(int subYear, int semester, String name, int deptId) {
		int count = 0;

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SUBJECT_BY_SEARCH)) {
			pstmt.setInt(1, subYear);
			pstmt.setInt(2, semester);
			pstmt.setString(3, "%" + name + "%");
			pstmt.setInt(4, deptId);
			pstmt.setInt(5, deptId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public int updateSyllabus(String overview, String objective, String textbook, String program, int subjectId) {
		int count = 0;

		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SYLLABUS_BY_SUBJECT_ID)) {
				pstmt.setString(1, overview);
				pstmt.setString(2, objective);
				pstmt.setString(3, textbook);
				pstmt.setString(4, program);
				pstmt.setInt(5, subjectId);
				count = pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

}
