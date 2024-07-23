package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Schedule;

public interface ScheduleRepository {

	// 학사일정 등록
	void addSchedule(Schedule schedule);

	// 전체 학사일정 조회
	List<Schedule> getAllSchedules();

	// 학사일정 삭제
	void deleteScheduleById(int scheduleId);

}
