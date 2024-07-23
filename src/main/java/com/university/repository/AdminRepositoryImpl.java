package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.CollTuit;
import com.university.model.College;
import com.university.model.Department;
import com.university.model.Room;
import com.university.model.Subject;
import com.university.repository.interfaces.AdminRepository;
import com.university.util.DBUtil;

public class AdminRepositoryImpl implements AdminRepository {
	private static final String INSERT_COLLEGE_SQL = " INSERT INTO college_tb (name) VALUES (?) ";
	private static final String SELECT_ALL_COLLEGES = " SELECT * FROM college_tb order by id asc ";
	private static final String UPDATE_COLLEGE_SQL = " UPDATE college_tb SET name = ? WHERE id = ? ";
	private static final String DELETE_COLLEGE_SQL = " DELETE FROM college_tb WHERE name = ? ";

	private static final String INSERT_DEPARTMENT_SQL = " INSERT INTO department_tb (name, college_id) VALUES (?, ?) ";
	private static final String SELECT_ALL_DEPARTMENTS = " SELECT * FROM department_tb order by id asc ";
	private static final String UPDATE_DEPARTMENT_SQL = " UPDATE department_tb SET name = ?, collegeId = ? WHERE id = ? ";
	private static final String DELETE_DEPARTMENT_SQL = " DELETE FROM department_tb WHERE id = ? ";

	private static final String INSERT_ROOM_SQL = " INSERT INTO room_tb (id, college_id) VALUES (?, ?) ";
	private static final String SELECT_ALL_ROOMS = " SELECT * FROM room_tb order by college_id asc ";
	private static final String UPDATE_ROOM_SQL = " UPDATE room_tb SET collegeId = ? WHERE id = ? ";
	private static final String DELETE_ROOM_SQL = " DELETE FROM room_tb WHERE id = ? ";

	private static final String INSERT_SUBJECT_SQL = " INSERT INTO subject_tb (name, professor_id, room_id, dept_id, type, sub_year, semester, sub_day, start_time, end_time, grades, capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_ALL_SUBJECTS = " SELECT * FROM subject_tb order by id asc ";
	private static final String UPDATE_SUBJECT_SQL = " UPDATE subject_tb SET name = ?, professor_id = ?, room_id = ?, sub_day = ?, start_time = ?, end_time = ?, capacity = ?  WHERE id = ? ";
	private static final String DELETE_SUBJECT_SQL = " DELETE FROM subject_tb WHERE id = ? ";

	private static final String INSERT_COLLTUIT_SQL = " INSERT INTO coll_tuit_tb (college_id, amount) VALUES (?, ?) ";
	private static final String SELECT_ALL_COLLTUIT = " SELECT ct.college_id, c.name, ct.amount FROM coll_tuit_tb AS ct LEFT JOIN college_tb AS c ON ct.college_id = c.id ";
	private static final String UPDATE_COLLTUIT_SQL = " UPDATE coll_tuit_tb SET amount = ? WHERE college_id = ? ";
	private static final String DELETE_COLLTUIT_SQL = " DELETE FROM coll_tuit_tb WHERE college_id = ? ";

	@Override
	public void addCollege(College college) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_COLLEGE_SQL)) {
				pstmt.setString(1, college.getName());
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
	public List<College> getAllColleges() {
		List<College> collegeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_COLLEGES)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				collegeList.add(College.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collegeList;
	}

	@Override
	public void updateCollege(College college, int collegeId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_COLLEGE_SQL)) {
				pstmt.setString(1, college.getName());
				pstmt.setInt(2, collegeId);
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
	public void deleteCollege(String collegeName) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_COLLEGE_SQL)) {
				pstmt.setString(1, collegeName);
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
	public void addDepartment(Department department) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_DEPARTMENT_SQL)) {
				pstmt.setString(1, department.getName());
				pstmt.setInt(2, department.getCollegeId());
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
	public List<Department> getAllDepartments() {
		List<Department> departmentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_DEPARTMENTS)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				departmentList.add(Department.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.collegeId(rs.getInt("college_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentList;
	}

	@Override
	public void updateDepartment(Department department, int departmentId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_DEPARTMENT_SQL)) {
				pstmt.setString(1, department.getName());
				pstmt.setInt(2, department.getCollegeId());
				pstmt.setInt(3, departmentId);
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
	public void deleteDepartment(int departmentId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_DEPARTMENT_SQL)) {
				pstmt.setInt(1, departmentId);
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
	public void addRoom(Room room) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_ROOM_SQL)) {
				pstmt.setString(1, room.getId());
				pstmt.setInt(2, room.getCollegeId());
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
	public List<Room> getAllRooms() {
		List<Room> roomList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_ROOMS)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(Room.builder().id(rs.getString("id")).collegeId(rs.getInt("college_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}

	@Override
	public void updateRoom(Room room, int roomId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_ROOM_SQL)) {
				pstmt.setInt(1, room.getCollegeId());
				pstmt.setString(2, room.getId());
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
	public void deleteRoom(String roomId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_ROOM_SQL)) {
				pstmt.setString(1, roomId);
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
	public void addSubject(Subject subject) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SUBJECT_SQL)) {
				pstmt.setString(1, subject.getName());
				pstmt.setInt(2, subject.getProfessorId());
				pstmt.setString(3, subject.getRoomId());
				pstmt.setInt(4, subject.getDeptId());
				pstmt.setString(5, subject.getType());
				pstmt.setInt(6, subject.getSubYear());
				pstmt.setInt(7, subject.getSemester());
				pstmt.setString(8, subject.getSubDay());
				pstmt.setInt(9, subject.getStartTime());
				pstmt.setInt(10, subject.getEndTime());
				pstmt.setInt(11, subject.getGrades());
				pstmt.setInt(12, subject.getCapacity());
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
	public List<Subject> getAllSubjects() {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUBJECTS)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(Subject.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.professorId(rs.getInt("professor_id")).roomId(rs.getString("room_id"))
						.deptId(rs.getInt("dept_id")).type(rs.getString("type")).subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester")).subDay(rs.getString("sub_day"))
						.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time")).grades(rs.getInt("grades"))
						.capacity(rs.getInt("capacity")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public void updateSubject(Subject subject, int subjectId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SUBJECT_SQL)) {
				pstmt.setString(1, subject.getName());
				pstmt.setInt(2, subject.getProfessorId());
				pstmt.setString(3, subject.getRoomId());
				pstmt.setString(4, subject.getSubDay());
				pstmt.setInt(5, subject.getStartTime());
				pstmt.setInt(6, subject.getEndTime());
				pstmt.setInt(7, subject.getCapacity());
				pstmt.setInt(8, subject.getId());
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
	public void deleteSubject(int subjectId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_SUBJECT_SQL)) {
				pstmt.setInt(1, subjectId);
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
	public void addCollTuit(CollTuit collTuit) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_COLLTUIT_SQL)) {
				pstmt.setInt(1, collTuit.getCollegeId());
				pstmt.setInt(2, collTuit.getAmount());
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
	public List<CollTuit> getAllCollTuits() {
		List<CollTuit> collTuitList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_COLLTUIT)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				collTuitList
						.add(CollTuit.builder().collegeId(rs.getInt("college_id")).collegeName(rs.getString("name")).amount(rs.getInt("amount")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collTuitList;
	}

	@Override
	public void updateCollTuit(CollTuit collTuit, int collegeId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_COLLTUIT_SQL)) {
				pstmt.setInt(1, collTuit.getCollegeId());
				pstmt.setInt(2, collegeId);
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
	public void deleteCollTuit(int collegeId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_COLLTUIT_SQL)) {
				pstmt.setInt(1, collegeId);
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
