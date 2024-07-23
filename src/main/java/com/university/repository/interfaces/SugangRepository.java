package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Subject;

public interface SugangRepository {

	// TODO 수강 신청 기간 설정
	void updatePeriod();
	
	// TODO 학생 강의 시간표 조회
	List<Subject> selectAllSub(int limit, int offset);
	
	// 학생 수강 신청
	void addSubStudent(int studentId, int subjectId);
	
	// 학생 수강 신청 내역 조회
	List<Subject> selectSubStudent(int limit, int offset);
	
}
