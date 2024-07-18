package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Professor;
import com.university.repository.interfaces.ProfessorRepository;
import com.university.util.DBUtil;

public class ProfessorRepositoryImpl implements ProfessorRepository {

	private static final String INSERT_PROFESSOR_SQL = " INSERT INTO professor_tb (id, name, birth_date, gender,address, tel, email,dept_id,hire_date) VALUES(? ,?, ?, ?, ?, ?, ?, ?, ? )  ";
	private static final String DELETE_PROFESSOR_SQL = " DELETE FROM  professor_tb WHERE id  = ? ";
	private static final String SELECT_PROFESSOR_BY_PROFESSORNAME = " SELECT * FROM  professor_tb WHERE username = ? ";
	private static final String SELECT_PROFESSOR_BY_PROFESSORID_AND_NAME = " SELECT * FROM professor_tb WHERE id = ? AND name = ? ";
	private static final String SELECT_ALL_PROFESSOR = "  SELECT * FROM professor_tb";

	@Override
	public int addProfessor(Professor professor) {

		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_PROFESSOR_SQL)) {

				conn.setAutoCommit(false);

				pstmt.setInt(1, professor.getId());
				pstmt.setString(2, professor.getName());
				pstmt.setDate(3, professor.getBirthDate());
				pstmt.setString(4, professor.getGender());
				pstmt.setString(5, professor.getAddress());
				pstmt.setString(6, professor.getTel());
				pstmt.setString(7, professor.getEmail());
				pstmt.setInt(8, professor.getDeptId());
				pstmt.setDate(9, professor.getHireDate());

				rowCount = pstmt.executeUpdate();
				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowCount;
	}

	@Override
	public void deleteProfessor(int id) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_PROFESSOR_SQL)) {
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Professor getProfessorByprofessorname(String name) {
		Professor professor = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_PROFESSORNAME)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				professor = Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	@Override
	public Professor getProfessorByprofessorIdAndName(int id, String name) {
		Professor professor = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_PROFESSORID_AND_NAME)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				professor = Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	@Override
	public List<Professor> getAllProfessors() {
		List<Professor> professorList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_PROFESSOR)

		) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Professor professor = Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date")).build();
				professorList.add(professor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return professorList;
	}

}
