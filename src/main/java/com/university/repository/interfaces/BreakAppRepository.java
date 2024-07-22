package com.university.repository.interfaces;

import java.util.List;

import com.university.model.BreakApp;

public interface BreakAppRepository {

	// 휴학 신청
	void insertApp(BreakApp breakApp);
	
	// 학생 휴학 신청 조회
	List<BreakApp> selectAppByStudentId(int studentId);
	
	// 직원 휴학 신청 조회
	List<BreakApp> selectAppByStatus(String status);
	
	// 학생 휴학 신청 취소
	void deleteAppById(int id);
	
	// 직원 휴학 신청 처리
	void updateAppById(int id, String status);
	
}
