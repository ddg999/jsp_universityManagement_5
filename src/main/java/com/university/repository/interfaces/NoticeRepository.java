package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;

public interface NoticeRepository {
	Notice getNoticeById(int noticeId);

	List<Notice> getAllNotices(int pageSize, int offset);

	List<Notice> getNoticesByTitle(String keyword, int pageSize, int offset);

	List<Notice> getNoticesByTitleOrContent(String keyword, int pageSize, int offset);

	void createNotice(Notice notice);

	void updateNotice(Notice notice);

	void deleteNotice(int noticeId);

	int getTotalNoticesCount();

	int getTotalNoticesCountByTitle(String keyword);

	int getTotalNoticesCountByTitleOrContent(String keyword);

	void updateNoticeView(int noticeId);
}
