package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Schedule;

public interface ScheduleRepository {

	// 학사일정 등록
	int insertSchedule(Schedule schedule);
	
	// 학사일정 조회
	List<Schedule> selectSchedule();
	
	// 학사일정 업데이트
	int updateSchedule(Schedule schedule);
	
	// 학사일정 삭제
	int deleteSchedule(int scheduleId);
	
	// 학사일정 상세 조회
	Schedule selectScheduleById(int scheduleId);
	
}
