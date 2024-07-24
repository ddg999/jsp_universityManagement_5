package com.university.repository.interfaces;

import java.util.List;

import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.model.Syllabus;

public interface SubjectRepository {

	// TODO 내 강의 학기별 조회 (교수)
	List<SubjectList> getProfessorSubjectbyYear(int professorID, int subYear);

	List<SubjectList> getProfessorSubjectbyYearandSemester(int professorID, int subYear, int semester);
	
	List<SubjectList> getProfessorSubjectAll(int pageSize, int offset);

	int getTotalProfessorSubject();
	
	// 강의별 학생리스트 전체 조회
	List<StuSubDetail> getAllStuSubDetails();

	// 강의별 학생리스트 학생 조회
	StuSubDetail getStuSubDetail(Student student, Subject subject); 
	
	// 강의계획서 조회
	Syllabus getSyllabusById(int subjectId);
	


}
