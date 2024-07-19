package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.university.model.Principal;
import com.university.model.Student;
import com.university.model.User;
import com.university.repository.interfaces.UserRepository;
import com.university.util.DBUtil;

public class UserRepositoryImpl implements UserRepository {

	private static final String SELECT_STUDENT_ID_SQL = " select u.id from user_tb as u join student_tb as s on u.id = s.id where s.name = ? and s.email = ? ";
	private static final String SELECT_STAFF_ID_SQL = " select u.id from user_tb as u join staff_tb as sf on u.id = sf.id where sf.name = ? and sf.email = ? ";
	private static final String SELECT_PROFESSOR_ID_SQL = " select u.id from user_tb as u join professor_tb as p on u.id = p.id where p.name = ? and p.email = ? ";
	private static final String SELECT_STUDENT_PASSWORD_SQL = " select u.password from user_tb as u join student_tb as s on u.id = s.id where s.name = ? and s.id = ? and s.email = ? ";
	private static final String SELECT_PROFESSOR_PASSWORD_SQL = " select u.password from user_tb as u join professor_tb as p on u.id = p.id where p.name = ? and p.id = ? and p.email = ? ";
	private static final String SELECT_STAFF_PASSWORD_SQL = " select u.password from user_tb as u join staff_tb as sf on u.id = sf.id where sf.name = ? and sf.id = ? and sf.email = ? ";

	private static final String SELECT_STUDENT = " select * from user_tb as u join student_tb as s on u.id = s.id where u.id = ? and u.password = ? ";
	private static final String SELECT_PROFESSOR = " select * from user_tb as u join professor_tb as p on u.id = p.id where u.id = ? and u.password = ? ";
	private static final String SELECT_STAFF = " select * from user_tb as u join staff_tb as sf on u.id = sf.id where u.id = ? and u.password = ? ";

//	private static final String SELECT_USER_BY_USERID_AND_PASSWORD = " select * from user_tb "
	private static final String UPDATE_USER_PASSWORD = " update user_tb set password = ? where id = ?; ";

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
				} else {
					return 0;
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

	// 로그인
	@Override
	public User getUserByIdAndPassword(int id, String password) {
		User user = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(null)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				user = User.builder()
						.id(rs.getInt("id"))
						.password(rs.getString("password"))
						.userRole(rs.getString("user_role"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Principal getStudent(User user) {
		Principal principal = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT)) {
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				principal = Principal.builder().id(rs.getInt("id")).password(rs.getString("password"))
						.userRole(rs.getString("user_role")).name(rs.getString("name")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return principal;
	}

	@Override
	public Principal getProfessor(int id, String password) {
		Principal principal = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				principal = Principal.builder().id(rs.getInt("id")).password(rs.getString("password"))
						.userRole(rs.getString("user_role")).name(rs.getString("name")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return principal;	}

	@Override
	public Principal getStaff(int id, String password) {
		Principal principal = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				principal = Principal.builder().id(rs.getInt("id")).password(rs.getString("password"))
						.userRole(rs.getString("user_role")).name(rs.getString("name")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return principal;	}

	//
	@Override
	public void updateUserPassword(String userPassword, int id) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER_PASSWORD)) {
				pstmt.setString(1, userPassword);
				pstmt.setInt(2, id);
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

}
