package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Tuition;
import com.university.model.TuitionInfo;

public interface TuitionRepository {
	//
	List<Tuition> getAllTuition();
	
	TuitionInfo getTuitionInfoById(int principalId);
	
	void createTuition(Tuition tuition);
	
	Tuition getTuitionByIdAndSemester(int studentId,int semester);
}
