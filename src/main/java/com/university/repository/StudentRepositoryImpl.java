package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Student;
import com.university.repository.interfaces.StudentRepository;
import com.university.util.DBUtil;

public class StudentRepositoryImpl implements StudentRepository {

	private static final String SEARCH_STUDENT_ID_AND_DEPT_ID = " SELECT * FROM student_tb WHERE id LIKE ? OR dept_id LIKE ? ORDER BY id ASC LIMIT ? OFFSET ? ";
	private static final String SEARCH_STUDNET_NAME = " SELECT * FROM student_tb WHERE name LIKE ? ORDER BY id ASC LIMIT ? OFFSET ? ";
	private static final String SELECT_STUDENT_BY_NAME = " SELECT * FROM student_tb WHERE name LIKE ? ORDER BY id ASC LIMIT ? OFFSET ? ";
	private static final String COUNT_SEARCH_STUDENT = " SELECT COUNT(*) as count FROM student_tb WHERE name LIKE ? ";	
	
	@Override
	public List<Student> getStudentId(String keyword, int pageSize, int offset) {
		List<Student> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SEARCH_STUDNET_NAME)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				studentList.add(Student.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).grade(rs.getInt("grade")).semester(rs.getInt("semester"))
						.entranceDate(rs.getDate("entrance_date")).graduationDate(rs.getDate("graduation_date"))
						.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}


	@Override
	public int getTotalStudentCountByTitle(String keyword, int pageSize, int offset) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_BY_NAME)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
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
	public List<Student> getStudentIdDeptId(String keyword, int pageSize, int offset) {
		List<Student> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SEARCH_STUDENT_ID_AND_DEPT_ID)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, pageSize);
			pstmt.setInt(4, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				studentList.add(Student.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).grade(rs.getInt("grade")).semester(rs.getInt("semester"))
						.entranceDate(rs.getDate("entrance_date")).graduationDate(rs.getDate("graduation_date")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public int getTotalStudentNameCount(String studentName) {
		int count = 0;
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_STUDENT)) {
			pstmt.setString(1, "%" + studentName + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("searchStudent totalCount : " + count);
		return count;
	}

	
	
}
