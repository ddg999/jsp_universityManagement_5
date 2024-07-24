package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Professor;
import com.university.model.Subject;
import com.university.model.Syllabus;
import com.university.repository.interfaces.ProfessorRepository;
import com.university.util.DBUtil;

public class ProfessorRepositoryImpl implements ProfessorRepository {

	private static final String SELECT_PROFESSOR_NAME = " SELECT * FROM professor_tb WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?  ";
	private static final String COUNT_SEARCH_PROFESSOR = " SELECT COUNT(*) as count FROM professor_tb ORDER BY name ASC WHERE name LIKE ? ";

	@Override
	public void professorgetInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public Syllabus getsyllabus(Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Professor> getProfessorName(String keyword, int pageSize, int offset) {
		List<Professor> professorList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_NAME)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				professorList.add(Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date"))
						.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return professorList;
	}

	@Override
	public int getTotalgetProfessorNameCount(String processName) {
		int count = 0;

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_PROFESSOR)) {
			pstmt.setString(1, "%" + processName + "%");
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
