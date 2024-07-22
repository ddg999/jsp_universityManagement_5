package com.university.repository.interfaces;

import com.university.model.Professor;
import com.university.model.ProfessorInfo;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.StudentInfo;

public interface InfoRepository {
	
	// 정보 조회
	StudentInfo getStudentInfo(int principalId);
	Staff getStaffInfo(int principalId);
	ProfessorInfo getProfessorInfo(int principalId);
	
	// 정보 수정
	int updateStudentInfo(Student student, int principalId); 
	int updateStaffInfo(Staff staff, int principalId); 
	int updateProfessorInfo(Professor professor, int principalId); 
	
	// 비밀번호 수정
	int updateUserPassword(String password, int principalId);
	
	// 비밀번호 체크
	String getUserPasswordById(int principal);
	
}
