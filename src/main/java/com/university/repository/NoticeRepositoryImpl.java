package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;
import com.university.repository.interfaces.NoticeRepository;
import com.university.util.DBUtil;

public class NoticeRepositoryImpl implements NoticeRepository {
	private static final String SELECT_NOTICE_BY_ID = " SELECT * FROM notice_tb WHERE id = ? ";
	private static final String SELECT_ALL_NOTICES = " SELECT * FROM notice_tb ORDER BY id DESC ";
	private static final String SELECT_ALL_SCHEDULES = " SELECT * FROM schedule_tb ";

	@Override
	public Notice getNoticeById(int noticeId) {
		Notice notice = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_NOTICE_BY_ID)) {
			pstmt.setInt(1, noticeId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				notice = Notice.builder().id(noticeId).category(rs.getString("category")).title(rs.getString("title"))
						.content(rs.getString("content")).createdTime(rs.getTimestamp("created_time"))
						.views(rs.getInt("views")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}

	@Override
	public List<Notice> getAllNotices() {
		List<Notice> noticeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_NOTICES)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				noticeList.add(Notice.builder().id(rs.getInt("id")).category(rs.getString("category"))
						.title(rs.getString("title")).content(rs.getString("content"))
						.createdTime(rs.getTimestamp("created_time")).views(rs.getInt("views")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	@Override
	public List<Schedule> getAllSchedule() {
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
}
