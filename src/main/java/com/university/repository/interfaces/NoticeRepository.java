package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;

public interface NoticeRepository {
	Notice getNoticeById(int noticeId);

	List<Notice> getAllNotices();
	
	List<Schedule> getAllSchedule();
}
