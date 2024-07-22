package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.BreakApp;
import com.university.repository.interfaces.BreakAppRepository;
import com.university.util.DBUtil;

public class BreakAppRepositoryImpl implements BreakAppRepository {
	private BreakAppRepository breakAppRepository;
	
	private static final String INSERT_APP = " insert into break_app_tb(student_id, student_grade, from_year, from_semester, to_year, to_semester, type) values(?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_APP_BY_STUDENT_ID = " select * from break_app_tb where student_id = ? ";
	private static final String SELECT_APP_BY_STATUS = " select * from break_app_tb where status = ? ";
	private static final String DELETE_APP_BY_ID = " delete from break_app_tb where id = ? ";
	private static final String UPDATE_APP_BY_ID = " update break_app_tb set status = ? where id = ? ";
	
	@Override
	public void insertApp(BreakApp breakApp) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_APP)) {
				pstmt.setInt(1, breakApp.getStudentId());
				pstmt.setInt(2, breakApp.getStudentGrade());
				pstmt.setInt(3, breakApp.getFromYear());
				pstmt.setInt(4, breakApp.getFromSemester());
				pstmt.setInt(5, breakApp.getToYear());
				pstmt.setInt(6, breakApp.getToSemester());
				pstmt.setString(7, breakApp.getType());
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
	public List<BreakApp> selectAppByStudentId(int studentId) {
		List<BreakApp> breakAppList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_APP_BY_STATUS)) {
			pstmt.setInt(1, studentId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				breakAppList.add(BreakApp.builder()
						.studentId(studentId)
						.studentGrade(rs.getInt("student_grade"))
						.fromYear(rs.getInt("from_year"))
						.fromSemester(rs.getInt("from_semester"))
						.toYear(rs.getInt("to_year"))
						.toSemester(rs.getInt("to_semester"))
						.type(rs.getString("type"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return breakAppList;
	}

	@Override
	public List<BreakApp> selectAppByStatus(String status) {
		List<BreakApp> breakAppList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_APP_BY_STUDENT_ID)) {
			pstmt.setString(1, status);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				breakAppList.add(BreakApp.builder()
						.studentId(rs.getInt("student_id"))
						.studentGrade(rs.getInt("student_grade"))
						.fromYear(rs.getInt("from_year"))
						.fromSemester(rs.getInt("from_semester"))
						.toYear(rs.getInt("to_year"))
						.toSemester(rs.getInt("to_semester"))
						.type(rs.getString("type"))
						.status(rs.getString("status"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return breakAppList;
	}

	@Override
	public void deleteAppById(int id) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_APP_BY_ID)) {
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
	public void updateAppById(int id, String status) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_APP_BY_ID)) {
				pstmt.setString(1, status);
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
