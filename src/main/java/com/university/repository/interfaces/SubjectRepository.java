package com.university.repository.interfaces;

import java.util.List;

import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;

public interface SubjectRepository {

	// TODO 내 강의 학기별 조회 (교수)
	Subject getProfessorSubjectbyYear(int professorID, int subYear);

	Subject getProfessorSubjectbyYearandSemester(int professorID, int subYear, int semester);

	// 강의별 학생리스트 전체 조회
	List<StuSubDetail> getAllStuSubDetails();

	// 강의별 학생리스트 학생 조회
	StuSubDetail getStuSubDetail(Student student, Subject subject); // 임시코드
	


}
