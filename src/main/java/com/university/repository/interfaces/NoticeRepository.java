package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;

public interface NoticeRepository {
	Notice getNoticeById(int noticeId);

	List<Notice> getAllNotices(int pageSize, int offset);

	List<Notice> getNoticesByTitle(String keyword, int pageSize, int offset);

	List<Notice> getNoticesByTitleOrContent(String keyword, int pageSize, int offset);

	List<Schedule> getAllSchedule();

	int getTotalNoticesCount();

	int getTotalNoticesCountByTitle(String keyword);

	int getTotalNoticesCountByTitleOrContent(String keyword);
}
