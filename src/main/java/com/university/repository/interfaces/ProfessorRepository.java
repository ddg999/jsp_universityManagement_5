package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Evaluation;
import com.university.model.Professor;
import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.Syllabus;



public interface ProfessorRepository {

	//TODO 내 강의 학기별 조회
	Subject getProfessorSubjectbyYear(int professorID, int subYear);
	Subject getProfessorSubjectbyYearandSemester(int professorID, int subYear,int semester);
	// TODO 강의 계획서 수정
	Syllabus getsyllabus(Subject subject); // 임시코드
	// TODO 강의별 학생리스트, 조회, 출결 및 성적기입
	
	// 강의별 학생리스트 전체 조회
	List<StuSubDetail> getAllStuSubDetails();
	
	StuSubDetail getStuSubDetail(Student student, Subject subject); // 임시코드
	// TODO 강의평가 확인
	Evaluation getEvaluation(Subject subject); //임시코드
	
}
