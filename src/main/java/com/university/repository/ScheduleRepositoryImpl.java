package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Schedule;
import com.university.repository.interfaces.ScheduleRepository;
import com.university.util.DBUtil;

public class ScheduleRepositoryImpl implements ScheduleRepository {

	private static final String INSERT_SCHEDULE_SQL = " INSERT INTO schedule_tb (staff_id, start_day, end_day, information) VALUES (?, ?, ?, ?) ";
	private static final String SELECT_ALL_SCHEDULES = " SELECT * FROM schedule_tb ORDER BY start_day ASC ";
	private static final String DELETE_SCHEDULE_SQL = " DELETE FROM schedule_tb WHERE id = ? ";

	@Override
	public void addSchedule(Schedule schedule) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SCHEDULE_SQL)) {
				pstmt.setInt(1, schedule.getStaffId());
				pstmt.setDate(2, schedule.getStartDay());
				pstmt.setDate(3, schedule.getEndDay());
				pstmt.setString(4, schedule.getInformation());
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
	public List<Schedule> getAllSchedules() {
		List<Schedule> scheduleList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SCHEDULES)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				scheduleList.add(Schedule.builder().id(rs.getInt("id")).staffId(rs.getInt("staff_id"))
						.startDay(rs.getDate("start_day")).endDay(rs.getDate("end_day"))
						.information(rs.getString("information")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleList;
	}

	@Override
	public void deleteScheduleById(int scheduleId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_SCHEDULE_SQL)) {
				pstmt.setInt(1, scheduleId);
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
