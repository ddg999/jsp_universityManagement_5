package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Student;

public interface StudentRepository {
	
	// 학번 조회
	List<Student> getStudentId(String keyword, int pageSize, int offset);
	int getTotalStudentNameCount(String studentName);
	
	// 학과 조회
	List<Student> getStudentIdDeptId(String keyword, int pageSize, int offset);
	int getTotalStudentCountByTitle(String keyword, int pageSize, int offset);
	
}
