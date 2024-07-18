package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.university.model.Professor;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.User;
import com.university.repository.interfaces.UserRepository;
import com.university.util.DBUtil;

public class UserRepositoryImpl implements UserRepository{

	private static final String SELECT_STUDENT_ID_SQL = " select u.id from user_tb as u join student_tb as s on u.id = s.id where s.name = ? and s.email = ? ";
	private static final String SELECT_STAFF_ID_SQL = " select u.id from user_tb as u join staff_tb as sf on u.id = sf.id where sf.name = ? and sf.email = ? ";
	private static final String SELECT_PROFESSOR_ID_SQL = " select u.id from user_tb as u join professor_tb as p on u.id = p.id where p.name = ? and p.email = ? ";
	private static final String SELECT_STUDENT_PASSWORD_SQL = " select u.password from user_tb as u join student_tb as s on u.id = s.id where s.name = ? and s.id = ? and s.email = ? ";
	private static final String SELECT_PROFESSOR_PASSWORD_SQL = " select u.password from user_tb as u join professor_tb as p on u.id = p.id where p.name = ? and p.id = ? and p.email = ? ";
	private static final String SELECT_STAFF_PASSWORD_SQL = " select u.password from user_tb as u join staff_tb as sf on u.id = sf.id where sf.name = ? and sf.id = ? and sf.email = ? ";
	private static final String INSERT_STUDENT_SQL = "  ";
	private static final String INSERT_PROFESSOR_SQL = "  ";
	private static final String INSERT_STAFF_SQL = "  ";
	private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = " select * from user_tb where id = ? and password = ? ";
	
	@Override
	public int getStudentIdByNameAndEmail(String name, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_ID_SQL)) {
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().id(rs.getInt("id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getId();
	}

	@Override
	public int getProfessorIdByNameAndEmail(String name, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_ID_SQL)) {
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().id(rs.getInt("id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getId();
	}

	@Override
	public int getStaffIdByNameAndEmail(String name, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_ID_SQL)) {
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().id(rs.getInt("id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getId();
	}

	@Override
	public String getStudentPasswordByNameAndIdAndEmail(String name, int id, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_PASSWORD_SQL)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.setString(3, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().password(rs.getString("password")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getPassword();
	}

	@Override
	public String getProfessorPasswordByNameAndIdAndEmail(String name, int id, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_PASSWORD_SQL)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.setString(3, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().password(rs.getString("password")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getPassword();
	}

	@Override
	public String getStaffPasswordByNameAndIdAndEmail(String name, int id, String email) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_PASSWORD_SQL)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.setString(3, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = User.builder().password(rs.getString("password")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getPassword();
	}

	@Override
	public void addStudentUser(User user, Student student) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProfessorUser(User user, Professor professor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStaffUser(User user, Staff staff) {
		// TODO Auto-generated method stub
		
	}

	// 로그인
	@Override
	public User getUserByIdAndPassword(int id, String password) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
//				user = User.builder()
//						.id(rs.getInt("id"))
//						.username(rs.getString("username"))
//						.password(rs.getString("password"))
//						.email(rs.getString("email"))
//						.createdAt(rs.getTimestamp("created_at"))
//						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
