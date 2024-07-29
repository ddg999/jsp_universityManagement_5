package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Evaluation;

public interface EvaluationRepository {

	// 학생 강의 평가 제출
	void insertEvaluation(Evaluation evaluation);

	// 학생 강의 평가 여부
	int isEvaluation(int subjectId, int studentId);
	
	// 교수 강의평가 조회
	List<Evaluation> getEvaluationByProfessorId(int professorId);
	
}
