package com.university.repository.interfaces;

import java.util.List;

import com.university.model.GradeSemester;

public interface GradeRepository {

	List<GradeSemester> getGradeThisSemester(int studentId, int semester, int year);

	List<GradeSemester> getGradeYear(int studentId);

	int getTotalCountGrade(int studentId);
}
