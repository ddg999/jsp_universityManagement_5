package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Professor;
import com.university.model.Subject;
import com.university.model.Syllabus;

public interface ProfessorRepository {

	void professorgetInfo();
	
	// 교수 이름 조회
	List<Professor> getProfessorName(String keyword, int pageSize, int offset);
	int getTotalgetProfessorNameCount(String professorName);

	// TODO 강의 계획서 수정
	Syllabus getsyllabus(Subject subject); // 임시코드

	
}
