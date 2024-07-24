package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Department;
import com.university.model.Subject;
import com.university.model.SugangSubject;
import com.university.repository.interfaces.SugangRepository;
import com.university.util.DBUtil;

public class SugangRepositoryImpl implements SugangRepository {

	private static final String SELECT_ALL_SUB = " select * from subject_tb ";
	private static final String INSERT_SUB = " insert into stu_sub_tb values(?, ?) ";
	private static final String SELECT_SUB_BY_STUDENT_ID_AND_SEMESTER = " SELECT student_id, subject_id, su.name AS subject_name, p.name AS professor_name, grades, sub_day, start_time, end_time, num_of_student, capacity, room_id FROM stu_sub_tb AS ss LEFT JOIN subject_tb AS su ON ss.subject_id = su.id LEFT JOIN professor_tb AS p ON su.professor_id = p.id WHERE student_id = ? AND sub_year = ? AND semester = ? ";
	private static final String SELECT_ALL_SUGANG_SUBJECT = " SELECT c.name coll_name, d.name dept_name, su.id, su.type, su.name, p.name professor_name, su.grades, su.sub_day, su.start_time, su.end_time, su.room_id, su.num_of_student, su.capacity FROM subject_tb su JOIN department_tb d ON su.dept_id = d.id JOIN college_tb c ON d.college_id = c.id JOIN professor_tb p ON su.professor_id = p.id ORDER BY id ASC LIMIT ? OFFSET ? ";
	private static final String SELECT_SEARCH_SUGANG_SUBJECT = " SELECT c.name coll_name, d.name dept_name, su.id, su.type, su.name, p.name professor_name, su.grades, su.sub_day, su.start_time, su.end_time, su.room_id, su.num_of_student, su.capacity FROM subject_tb su JOIN department_tb d ON su.dept_id = d.id JOIN college_tb c ON d.college_id = c.id JOIN professor_tb p ON su.professor_id = p.id WHERE su.name LIKE CONCAT('%', COALESCE(NULLIF(?, ''), su.name), '%') AND su.type like CONCAT('%', COALESCE(NULLIF(?, ''), su.type), '%') AND d.name like CONCAT('%', COALESCE(NULLIF(?, ''), d.name), '%') ORDER BY id ASC LIMIT ? OFFSET ? ";
	private static final String COUNT_ALL_SUGANG_SUBJECTS = " SELECT count(*) count from subject_tb ";
	private static final String COUNT_SEARCH_SUGANG_SUBJECT = " SELECT count(*) count FROM subject_tb su JOIN department_tb d ON su.dept_id = d.id JOIN college_tb c ON d.college_id = c.id JOIN professor_tb p ON su.professor_id = p.id WHERE su.name LIKE CONCAT('%', COALESCE(NULLIF(?, ''), su.name), '%') AND su.type like CONCAT('%', COALESCE(NULLIF(?, ''), su.type), '%') AND d.name like CONCAT('%', COALESCE(NULLIF(?, ''), d.name), '%') ";
	private static final String SELECT_ALL_DEPARTMENTS = " SELECT * FROM department_tb ";

	@Override
	public void updatePeriod() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSubStudent(int studentId, int subjectId) {

	}

	@Override
	public List<Subject> selectSubStudent(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SugangSubject> getAllSugangSubject(int pageSize, int offset) {
		List<SugangSubject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUGANG_SUBJECT)) {
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(SugangSubject.builder().collName(rs.getString("coll_name"))
						.deptName(rs.getString("dept_name")).id(rs.getInt("id")).type(rs.getString("type"))
						.name(rs.getString("name")).professorName(rs.getString("professor_name"))
						.grades(rs.getInt("grades")).subDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
						.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
						.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public int getTotalSubjectsCount() {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_SUGANG_SUBJECTS)) {
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
	public List<Department> getAllDepartment() {
		List<Department> deptList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_DEPARTMENTS)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				deptList.add(Department.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.collegeId(rs.getInt("college_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptList;
	}

	@Override
	public List<SugangSubject> getSearchSugangSubjects(String name, String type, String deptName, int pageSize,
			int offset) {
		// 과목명, 강의구분, 학과번호 순서
		List<SugangSubject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SEARCH_SUGANG_SUBJECT)) {
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setString(3, deptName);
			pstmt.setInt(4, pageSize);
			pstmt.setInt(5, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(SugangSubject.builder().collName(rs.getString("coll_name"))
						.deptName(rs.getString("dept_name")).id(rs.getInt("id")).type(rs.getString("type"))
						.name(rs.getString("name")).professorName(rs.getString("professor_name"))
						.grades(rs.getInt("grades")).subDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
						.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
						.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public int getSearchSugangSubjectCount(String name, String type, String deptName) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_SUGANG_SUBJECT)) {
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setString(3, deptName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
