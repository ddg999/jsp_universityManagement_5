package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Department;
import com.university.model.Subject;
import com.university.model.SugangSubject;

public interface SugangRepository {

	// TODO 수강 신청 기간 설정
	void updatePeriod();

	// 수강신청 탭 강의시간표 조회
	List<SugangSubject> getAllSugangSubject(int pageSize, int offset);

	List<SugangSubject> getSearchSugangSubjects(String name, String type, String deptName, int pageSize, int offset);

	// 수강신청 탭 강의수
	int getTotalSubjectsCount();
	
	// 검색한 수강신청 탭 강의수
	int getSearchSugangSubjectCount(String name, String type, String deptName);

	// 모든 학과 조회
	List<Department> getAllDepartment();

	// 학생 수강 신청
	void addSubStudent(int studentId, int subjectId);

	// 학생 수강 신청 내역 조회
	List<Subject> selectSubStudent(int limit, int offset);

}
