package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.repository.interfaces.SubjectRepository;
import com.university.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository{

	private static final String GETTING_PROFESSOR_SUBJECT =
			" select subject_tb.sub_year as 'year', subject_tb.semester as 'semester', college_tb.name as 'university' , department_tb.name as 'department', subject_tb.id as 'subjectNum', subject_tb.type as 'type', subject_tb.name as 'subject', professor_tb.name as 'teacher', subject_tb.grades as 'grade', subject_tb.num_of_student as 'stu_Num', subject_tb.capacity as 'capacity' from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id ORDER BY id DESC LIMIT ? OFFSET ? " ;
	 
	private static final String GETTING_PROFESSOR_SEMESTER_SUBJECT =
			" select subject_tb.sub_year as 'year', subject_tb.semester as 'semester', college_tb.name as 'university' , department_tb.name as 'department', subject_tb.id as 'subjectNum', subject_tb.type as 'type', subject_tb.name as 'subject', professor_tb.name as 'teacher', subject_tb.grades as 'grade', subject_tb.num_of_student as 'stu_Num', subject_tb.capacity as 'capacity' from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id ORDER BY id DESC LIMIT ? OFFSET ? " ;
	
	private static final String GETTING_PROFESSOR_SUBJECT_ALL = 
			" select subject_tb.sub_year, subject_tb.semester, college_tb.name, department_tb.name, subject_tb.id, subject_tb.type, subject_tb.name, professor_tb.name, subject_tb.grades, subject_tb.num_of_student, subject_tb.capacity from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id ORDER BY id DESC LIMIT ? OFFSET ? " ;
	
	private static final String COUNT_ALL_PROFESSOR_SUBJECT_ALL = " SELECT count(*) from subject_tb ";
	
	@Override
	public List<SubjectList> getProfessorSubjectbyYear(int professorID, int subYear) {
		List<SubjectList> subjectList = new ArrayList<>();
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETTING_PROFESSOR_SUBJECT)) {
			pstmt.setInt(1, subYear);
			pstmt.setInt(2, professorID);
			
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				subjectList.add(SubjectList.builder()
						.year(rs.getInt("subject_tb.sub_year"))
						.semester(rs.getInt("subject_tb.semester"))
						.university(rs.getString("college_tb.name"))
						.department(rs.getString("department_tb.name"))
						.subjectNum(rs.getInt("subject_tb.id"))
						.type(rs.getString("subject_tb.type"))
						.subject(rs.getString("subject_tb.name"))
						.teacher(rs.getString("professor_tb.name"))
						.grade(rs.getInt("subject_tb.grades"))
						.stu_Num(rs.getInt("subject_tb.num_of_student"))
						.capacity(rs.getInt("subject_tb.capacity"))
						.build());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return subjectList;
		
	}

	@Override
	public List<SubjectList> getProfessorSubjectbyYearandSemester(int professorID, int subYear, int semester) {
		List<SubjectList> subjectList = new ArrayList<>();
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETTING_PROFESSOR_SEMESTER_SUBJECT)) {
			pstmt.setInt(1, subYear);
			pstmt.setInt(2, semester);
			pstmt.setInt(3, professorID);
			
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				subjectList.add(SubjectList.builder()
						.year(rs.getInt("subject_tb.sub_year"))
						.semester(rs.getInt("subject_tb.semester"))
						.university(rs.getString("college_tb.name"))
						.department(rs.getString("department_tb.name"))
						.subjectNum(rs.getInt("subject_tb.id"))
						.type(rs.getString("subject_tb.type"))
						.subject(rs.getString("subject_tb.name"))
						.teacher(rs.getString("professor_tb.name"))
						.grade(rs.getInt("subject_tb.grades"))
						.stu_Num(rs.getInt("subject_tb.num_of_student"))
						.capacity(rs.getInt("subject_tb.capacity"))
						.build());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return subjectList;
	}

		
	@Override
	public List<SubjectList> getProfessorSubjectAll(int pageSize, int offset) {
		List<SubjectList> subjectList = new ArrayList<>();
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETTING_PROFESSOR_SUBJECT_ALL)) {
			
			ResultSet rs = pstmt.executeQuery();
		pstmt.setInt(1, pageSize);
		pstmt.setInt(2, offset);
		
			
			while(rs.next()) {
				subjectList.add(SubjectList.builder()
						.year(rs.getInt("subject_tb.sub_year"))
						.semester(rs.getInt("subject_tb.semester"))
						.university(rs.getString("college_tb.name"))
						.department(rs.getString("department_tb.name"))
						.subjectNum(rs.getInt("subject_tb.id"))
						.type(rs.getString("subject_tb.type"))
						.subject(rs.getString("subject_tb.name"))
						.teacher(rs.getString("professor_tb.name"))
						.grade(rs.getInt("subject_tb.grades"))
						.stu_Num(rs.getInt("subject_tb.num_of_student"))
						.capacity(rs.getInt("subject_tb.capacity"))
						.build());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return subjectList;
	}
	
	@Override
	public int getTotalProfessorSubject() {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_PROFESSOR_SUBJECT_ALL)){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<StuSubDetail> getAllStuSubDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StuSubDetail getStuSubDetail(Student student, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	




}
