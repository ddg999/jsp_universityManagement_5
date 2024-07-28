package com.university.repository.interfaces;

import java.util.List;

import com.university.model.AvgGrade;
import com.university.model.StuSch;
import com.university.model.Tuition;
import com.university.model.TuitionInfo;

public interface TuitionRepository {
	//
	List<Tuition> getAllTuition();
	
	TuitionInfo getTuitionInfoById(int principalId);
	
	int createTuition(Tuition tuition);
	
	Tuition getTuitionByIdAndSemester(int studentId,int semester);
	
	List<AvgGrade> getAvgGrade();
	
	List<StuSch> createStuSch(int studentId, int schType);
	
	//status 1로 바꾸는
	void updateStatus(int studentId);
	
	Tuition getTuitionBYId(int studentId);
}
