package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.university.model.Professor;
import com.university.model.ProfessorInfo;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.StudentInfo;
import com.university.model.User;
import com.university.repository.interfaces.InfoRepository;
import com.university.util.DBUtil;

public class InfoRepositoryImpl implements InfoRepository{

	private static final String SELECT_STUDENT_BY_ID_SQL = " select c.name as collegeName ,d.name as deptName, s.* from college_tb as c join department_tb as d on c.id = d.college_id join student_tb as s on d.id = s.dept_id where s.id = ?; ";
	private static final String SELECT_STAFF_BY_ID_SQL = " select * from user_tb as u join staff_tb as sf on u.id = sf.id where u.id = ? ";
	private static final String SELECT_PROFESSOR_BY_ID_SQL = " select c.name as collegeName , d.name as deptName, p.* from college_tb as c join department_tb as d on c.id = d.college_id join professor_tb as p on d.id = p.dept_id where p.id = ? ";
	
	// 수정 쿼리 작성하기
	private static final String UPDATE_STUNDET_INFO_SQL = "  " ;
	
	private static final String UPDATE_STAFF_INFO_SQL = " UPDATE staff_tb SET address = ?, tel = ?, email = ? WHERE id = ? ";
	private static final String SELECT_USER_INFO_CHECKPW = " SELECT password FROM user_tb WHERE id = ? ";
	
	private static final String UPDATE_PROFESSOR_INFO_SQL = "  " ;
	private static final String UPDATE_USER_PASSWORD_SQL = " update user_tb set password = ? where id = ? ";
	
	
	@Override
	public StudentInfo getStudentInfo(int principalId) {
		StudentInfo student = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_BY_ID_SQL)) {
			pstmt.setInt(1, principalId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					student = StudentInfo.builder()
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
							.deptName(rs.getString("deptName"))
							.collegeName(rs.getString("collegeName"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
		
	}

	@Override
	public Staff getStaffInfo(int principalId) {
		Staff staff = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_BY_ID_SQL)) {
			pstmt.setInt(1, principalId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					staff = Staff.builder()
							.id(rs.getInt("id"))
							.hireDate(rs.getDate("hire_date"))
							.name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date"))
							.address(rs.getString("address"))
							.tel(rs.getString("tel"))
							.email(rs.getString("email"))
							.gender(rs.getString("gender"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff;
	}

	@Override
	public ProfessorInfo getProfessorInfo(int principalId) {
		ProfessorInfo professor = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_ID_SQL)) {
			pstmt.setInt(1, principalId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					professor = ProfessorInfo.builder()
							.id(rs.getInt("id"))
							.hireDate(rs.getDate("hire_date"))
							.name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date"))
							.address(rs.getString("address"))
							.tel(rs.getString("tel"))
							.email(rs.getString("email"))
							.gender(rs.getString("gender"))
							.deptId(rs.getInt("dept_id"))
							.collegeName(rs.getString("collegeName"))
							.deptName(rs.getString("deptName"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	@Override
	public int updateStudentInfo(Student student, int principalId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STUNDET_INFO_SQL)){
				pstmt.setInt(1, principalId);
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
	public int updateStaffInfo(Staff staff, int principalId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STAFF_INFO_SQL)){
				pstmt.setString(1, staff.getAddress());
				pstmt.setString(2, staff.getTel());
				pstmt.setString(3, staff.getEmail());
				pstmt.setInt(4, principalId);
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

	
	// 일단 대기
	@Override
	public String getUserPasswordById(int principal) {
		User user = null;
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_INFO_CHECKPW)) {
//			pstmt.setInt(1, principal);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				user = User.builder().password(rs.getString("password")).build();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return user.getPassword();
	}

	@Override
	public int updateProfessorInfo(Professor professor, int principalId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PROFESSOR_INFO_SQL)){
				pstmt.setInt(1, principalId);
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
	public int updateUserPassword(String password, int principalId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER_PASSWORD_SQL)){
				pstmt.setString(1, password);
				pstmt.setInt(2, principalId);
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


}
