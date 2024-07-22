package com.university.repository.interfaces;

import com.university.model.Subject;
import com.university.model.Syllabus;

public interface ProfessorRepository {

	void professorgetInfo();
	
	
	

	// TODO 강의 계획서 수정
	Syllabus getsyllabus(Subject subject); // 임시코드

	
}
