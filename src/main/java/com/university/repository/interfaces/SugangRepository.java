package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Department;
import com.university.model.SubTime;
import com.university.model.Subject;
import com.university.model.SugangSubject;

public interface SugangRepository {

	// TODO 수강 신청 기간 설정
	void updatePeriod();

	// 수강신청-강의시간표 조회
	List<SugangSubject> getAllSugangSubject(int principalId, int pageSize, int offset);

	// 수강신청-강의시간표 조회 강의수
	int getTotalSubjectsCount();

	// 수강신청-강의시간표 검색
	List<SugangSubject> getSearchSugangSubjects(String name, String type, String deptName, int pageSize, int offset);
	
	// 수강신청-강의시간표 검색 강의수
	int getSearchSugangSubjectCount(String name, String type, String deptName);

	// 수강신청 내역 조회
	List<SugangSubject> getSugangSubjectResult(int principalId, int pageSize, int offset);


	// 모든 학과 조회
	List<Department> getAllDepartment();

	// 학생 수강 신청 내역 조회
	List<Subject> selectSubStudent(int limit, int offset);

	// 학생 수강 신청 추가
	int addRegist(int studentId, int subjectId);

	// 학생 수강 시간 조회
	List<SubTime> getStuSubTime(int studentId);

	// 해당 과목의 시간 조회
	SubTime getSubTime(int subjectId);

	// 학생 수강 신청 삭제
	void deleteRegist(int studentId, int subjectId);

}
