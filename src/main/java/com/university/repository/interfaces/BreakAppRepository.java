package com.university.repository.interfaces;

import java.util.List;

import com.university.model.BreakApp;

public interface BreakAppRepository {

	// 휴학 신청
	int insertApp(BreakApp breakApp);
	
	// 학생 휴학 신청 조회
	List<BreakApp> selectAppByStudentId(int studentId);
	
	// 직원 휴학 신청 조회
	List<BreakApp> selectAppByStatus(String status);
	
	// 특정 휴학 신청 조회
	BreakApp selectAppById(int id);
	
	// 학생 휴학 신청 취소
	int deleteAppById(int id);
	
	// 직원 휴학 신청 처리
	int updateAppById(int id, String status);
	
}
