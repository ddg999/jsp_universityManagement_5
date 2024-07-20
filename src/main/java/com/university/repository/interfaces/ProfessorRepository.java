package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Evaluation;
import com.university.model.Professor;
import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.Syllabus;



public interface ProfessorRepository {


	// TODO 강의 계획서 수정
	Syllabus getsyllabus(Subject subject); // 임시코드
	// TODO 강의별 학생리스트, 조회, 출결 및 성적기입
	
	
	
}
