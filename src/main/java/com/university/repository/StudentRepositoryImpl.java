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
	
	private static final String SEARCH_STUDENT_ID = " SELECT * FROM student_tb WHERE id LIKE ? OR name ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SEARCH_STUDENT_ID_AND_DEPT_ID = " SELECT * FROM student_tb WHERE id LIKE ? OR dept_id LIKE ? ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SEARCH_STUDNET_NAME = " select * from student_tb where name like ? ";
	
	
	
	@Override
	public List<Student> getStudentId(String keyword, int pageSize, int offset) {
		List<Student> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SEARCH_STUDNET_NAME)) {
			pstmt.setString(1, "%" + keyword + "%");
//			pstmt.setInt(2, pageSize);
//			pstmt.setInt(3, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("11");
				studentList.add(Student.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date"))
						.gender(rs.getString("gender"))
						.address(rs.getString("address"))
						.tel(rs.getString("tel"))
						.email(rs.getString("email"))
						.deptId(rs.getInt("dept_id"))
						.grade(rs.getInt("grade"))
						.semester(rs.getInt("semester"))
						.entranceDate(rs.getDate("entrance_date"))
						.graduationDate(rs.getDate("graduation_date"))
						.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public List<Student> getStudentDeptId(String keyword, int pageSize, int offset) {
		return null;
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
						.birthDate(rs.getDate("birthDate")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("deptId")).grade(rs.getInt("grade")).semester(rs.getInt("semester"))
						.entranceDate(rs.getDate("entranceDate")).graduationDate(rs.getDate("graduationDate")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public int getTotalStudentCountByTitle(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalStudentCountByTitleOrContent(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

}
