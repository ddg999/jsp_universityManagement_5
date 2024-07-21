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
	private static final String SELECT_ALL_NOTICES = " SELECT * FROM notice_tb ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SELECT_NOTICES_BY_TITLE = " SELECT * FROM notice_tb WHERE title LIKE ? ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SELECT_NOTICES_BY_TITLE_OR_CONTENT = " SELECT * FROM notice_tb WHERE title LIKE ? OR content LIKE ? ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SELECT_ALL_SCHEDULES = " SELECT * FROM schedule_tb ";
	private static final String COUNT_ALL_NOTICES = " SELECT count(*) count from notice_tb ";
	private static final String COUNT_NOTICES_BY_TITLE = " SELECT count(*) count from notice_tb WHERE title LIKE ? ";
	private static final String COUNT_NOTICES_BY_TITLE_OR_CONTENT = " SELECT count(*) count from notice_tb WHERE title LIKE ? OR content LIKE ? ";

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
	public List<Notice> getAllNotices(int pageSize, int offset) {
		List<Notice> noticeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_NOTICES)) {
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, offset);
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
	public List<Notice> getNoticesByTitle(String keyword, int pageSize, int offset) {
		List<Notice> noticeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_NOTICES_BY_TITLE)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
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
	public List<Notice> getNoticesByTitleOrContent(String keyword, int pageSize, int offset) {
		List<Notice> noticeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_NOTICES_BY_TITLE_OR_CONTENT)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, pageSize);
			pstmt.setInt(4, offset);
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

	@Override
	public int getTotalNoticesCount() {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_NOTICES)) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getTotalNoticesCountByTitle(String keyword) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_NOTICES_BY_TITLE)) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getTotalNoticesCountByTitleOrContent(String keyword) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_NOTICES_BY_TITLE_OR_CONTENT)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
