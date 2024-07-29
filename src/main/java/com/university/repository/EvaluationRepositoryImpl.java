package com.university.repository;

import java.util.List;

import com.university.model.Evaluation;
import com.university.repository.interfaces.EvaluationRepository;

public class EvaluationRepositoryImpl implements EvaluationRepository{
	
	private EvaluationRepository evaluationRepository;

	@Override
	public int insertEvaluation(Evaluation evaluation) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Evaluation selectEvaluation(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Evaluation> selectMyEvaluationByProfessorId(int professorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
