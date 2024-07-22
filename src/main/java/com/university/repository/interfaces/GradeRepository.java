package com.university.repository.interfaces;

import java.util.List;

import com.university.model.GradeThisSemester;

public interface GradeRepository {

	List<GradeThisSemester> getGradeThisSemester(int studentId, int semester, int year);
}
