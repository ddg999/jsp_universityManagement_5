package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Professor;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.model.Syllabus;
import com.university.repository.interfaces.ProfessorRepository;
import com.university.util.DBUtil;

public class ProfessorRepositoryImpl implements ProfessorRepository {

	private static final String SELECT_PROFESSOR_NAME = " SELECT * FROM professor_tb WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?  ";
	private static final String COUNT_SEARCH_PROFESSOR = " SELECT COUNT(*) as count FROM professor_tb ORDER BY name ASC WHERE name LIKE ? ";

	private static final String GETTING_PROFESSOR_SUBJECT =
			" select subject_tb.sub_year as 'year', subject_tb.semester as 'semester', college_tb.name as 'university' , department_tb.name as 'department', subject_tb.id as 'subjectNum', subject_tb.type as 'type', subject_tb.name as 'subject', professor_tb.name as 'teacher', subject_tb.grades as 'grade', subject_tb.num_of_student as 'stu_Num', subject_tb.capacity as 'capacity' from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id ORDER BY id DESC LIMIT ? OFFSET ? " ;
	 
	private static final String GETTING_PROFESSOR_SEMESTER_SUBJECT =
			" select subject_tb.sub_year, subject_tb.semester, college_tb.name, department_tb.name, subject_tb.id, subject_tb.type, subject_tb.name, professor_tb.name, subject_tb.grades, subject_tb.num_of_student, subject_tb.capacity from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id where subject_tb.sub_year = ? and subject_tb.semester = ? and professor_tb.id = ?" ;
	
	private static final String GETTING_PROFESSOR_SUBJECT_ALL = " select subject_tb.sub_year, subject_tb.semester, college_tb.name, department_tb.name, subject_tb.id, subject_tb.type, subject_tb.name, professor_tb.name, subject_tb.grades, subject_tb.num_of_student, subject_tb.capacity from subject_tb join professor_tb on subject_tb.professor_id = professor_tb.id join department_tb on department_tb.id = subject_tb.dept_id join college_tb on department_tb.college_id = college_tb.id " ;
	


	private static final String COUNT_ALL_PROFESSOR_SUBJECT_ALL = " SELECT count(*) from subject_tb ";
	
	ProfessorRepository professorRepository;

	
	@Override
	public void professorgetInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public Syllabus getsyllabus(Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Professor> getProfessorName(String keyword, int pageSize, int offset) {
		List<Professor> professorList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_NAME)) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				professorList.add(Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date"))
						.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return professorList;
	}

	@Override
	public int getTotalgetProfessorNameCount(String processName) {
		int count = 0;

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_PROFESSOR)) {
			pstmt.setString(1, "%" + processName + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("searchStudent totalCount : " + count);
		return count;
	}
	
	@Override
	public List<SubjectList> getProfessorSubjectbyYear(int professorID, int subYear) {
		List<SubjectList> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
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
		try (Connection conn = DBUtil.getConnection();
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
	public List<SubjectList> getProfessorSubjectAll() {
		List<SubjectList> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GETTING_PROFESSOR_SUBJECT_ALL)) {

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				subjectList.add(SubjectList.builder()
						.year(rs.getInt("sub_year"))
						.semester(rs.getInt("semester"))
						.university(rs.getString("coll_name"))
						.department(rs.getString("dept_name"))
						.subjectNum(rs.getInt("id"))
						.type(rs.getString("type"))
						.subject(rs.getString("name"))
						.teacher(rs.getString("professor_name"))
						.grade(rs.getInt(".grades"))
						.stu_Num(rs.getInt("num_of_student"))
						.capacity(rs.getInt("capacity"))
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
	

}
