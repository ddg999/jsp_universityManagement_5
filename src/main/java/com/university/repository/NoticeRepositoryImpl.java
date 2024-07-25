package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Notice;
import com.university.repository.interfaces.NoticeRepository;
import com.university.util.DBUtil;

public class NoticeRepositoryImpl implements NoticeRepository {
	private static final String SELECT_NOTICE_BY_ID = " SELECT * FROM notice_tb WHERE id = ? ";
	private static final String SELECT_ALL_NOTICES = " SELECT * FROM notice_tb ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SELECT_NOTICES_BY_TITLE = " SELECT * FROM notice_tb WHERE title LIKE ? ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String SELECT_NOTICES_BY_TITLE_OR_CONTENT = " SELECT * FROM notice_tb WHERE title LIKE ? OR content LIKE ? ORDER BY id DESC LIMIT ? OFFSET ? ";
	private static final String COUNT_ALL_NOTICES = " SELECT count(*) count from notice_tb ";
	private static final String COUNT_NOTICES_BY_TITLE = " SELECT count(*) count from notice_tb WHERE title LIKE ? ";
	private static final String COUNT_NOTICES_BY_TITLE_OR_CONTENT = " SELECT count(*) count from notice_tb WHERE title LIKE ? OR content LIKE ? ";
	private static final String UPDATE_NOTICE_VIEW = " UPDATE notice_tb SET views = views + 1 WHERE id = ? ";

	private static final String INSERT_NOTICE_SQL = " INSERT INTO notice_tb (category, title, content) VALUES (?, ?, ?) ";
	private static final String UPDATE_NOTICE_SQL = " UPDATE notice_tb SET category = ?, title = ?, content = ? WHERE id = ? ";
	private static final String DELETE_NOTICE_SQL = " DELETE FROM notice_tb WHERE id = ? ";

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
	public void addNotice(Notice notice) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_NOTICE_SQL)) {
				pstmt.setString(1, notice.getCategory());
				pstmt.setString(2, notice.getTitle());
				pstmt.setString(3, notice.getContent());
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
	public void updateNotice(Notice notice) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_NOTICE_SQL)) {
				pstmt.setString(1, notice.getCategory());
				pstmt.setString(2, notice.getTitle());
				pstmt.setString(3, notice.getContent());
				pstmt.setInt(4, notice.getId());
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
	public void deleteNotice(int noticeId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_NOTICE_SQL)) {
				pstmt.setInt(1, noticeId);
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

	@Override
	public void updateNoticeView(int noticeId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_NOTICE_VIEW)) {
				pstmt.setInt(1, noticeId);
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
