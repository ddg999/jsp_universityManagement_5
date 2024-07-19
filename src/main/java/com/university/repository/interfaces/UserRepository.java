package com.university.repository.interfaces;

import com.university.model.Principal;
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
	
	
	// 로그인?
	User getUserByIdAndPassword(int id, String password);
	
	// 임시 비밀번호 발급
	void updateUserPassword(String userPassword, int id);
	
	// 아이디로 이름찾기 로그인에 필요한거 ? 잠시 대기
	Principal getStudent(User user);
	Principal getProfessor(int id, String password);
	Principal getStaff(int id, String password);
	
	
	
}
