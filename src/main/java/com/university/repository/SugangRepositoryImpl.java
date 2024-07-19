package com.university.repository;

import java.util.List;

import com.university.model.Subject;
import com.university.repository.interfaces.SugangRepository;

public class SugangRepositoryImpl implements SugangRepository {

	private static final String SELECT_ALL_SUB = "  ";
	
	@Override
	public void updatePeriod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Subject> selectAllSub(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPresubStudent(int studentId, int subjectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSubStudent(int studentId, int subjectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Subject> selectSubStudent(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
