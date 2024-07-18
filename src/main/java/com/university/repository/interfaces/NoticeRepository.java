package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Notice;

public interface NoticeRepository {
	Notice getNoticeById(int noticeId);

	List<Notice> getAllNotices();
}
