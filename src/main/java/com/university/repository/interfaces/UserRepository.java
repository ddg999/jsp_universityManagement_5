package com.university.repository.interfaces;

import com.university.model.Professor;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.User;

public interface UserRepository {
	
	// 학생 아이디 조회
	int getStudentIdByNameAndEmail (String name, String email);
	// 교수 아이디 조회
	int getProfessorIdByNameAndEmail (String name, String email);
	// 직원 아이디 조회
	int getStaffIdByNameAndEmail (String name, String email);
	
	
	// 학생 비밀번호 조회
	String getStudentPasswordByNameAndIdAndEmail(String name, int id, String email);
	
	// 교수 비밀번호 조회
	String getProfessorPasswordByNameAndIdAndEmail(String name, int id, String email);
	
	// 직원 비밀번호 조회
	String getStaffPasswordByNameAndIdAndEmail(String name, int id, String email);
	
	
	// 학생 회원가입
	void addStudentUser(User user, Student student);
	
	// 교수 회원가입
	void addProfessorUser(User user, Professor professor);
	
	// 직원 회원가입
	void addStaffUser(User user, Staff staff);
	
	
	// 로그인?
	User getUserByIdAndPassword(int id, String password);
	
	
}
