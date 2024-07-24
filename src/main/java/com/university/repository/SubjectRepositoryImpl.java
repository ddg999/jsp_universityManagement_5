package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.university.model.StuSubDetail;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.repository.interfaces.SubjectRepository;
import com.university.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository{

	private static final String GETTING_PROFESSOR_SUBJECT = " select concat(subject_tb.sub_year , \"-\" , subject_tb.semester, \"학기\") as \"연도/학기\", college_tb.name as \"단과대학\" ,department_tb.name as \"개설학과\", subject_tb.id as \"학수번호\", subject_tb.type as \"강의구분\", subject_tb.name as \"강의명\", professor_tb.name as \"담당교수\", subject_tb.grades as 학점, subject_tb.num_of_student as 수강인원, subject_tb.capacity as 정원\r\n"
			+ "from subject_tb\r\n"
			+ "join professor_tb\r\n"
			+ "on subject_tb.professor_id = professor_tb.id\r\n"
			+ "join department_tb\r\n"
			+ "on department_tb.id = subject_tb.dept_id\r\n"
			+ "join college_tb\r\n"
			+ "on department_tb.college_id = college_tb.id\r\n"
			+ "where subject_tb.sub_year = ? and professor_tb.name = ? "; 
	private static final String GETTING_PROFESSOR_SEMESTER_SUBJECT = " select concat(subject_tb.sub_year , \"-\" , subject_tb.semester, \"학기\") as \"연도/학기\", college_tb.name as \"단과대학\" ,department_tb.name as \"개설학과\", subject_tb.id as \"학수번호\", subject_tb.type as \"강의구분\", subject_tb.name as \"강의명\", professor_tb.name as \"담당교수\", subject_tb.grades as 학점, subject_tb.num_of_student as 수강인원, subject_tb.capacity as 정원\r\n"
			+ "from subject_tb\r\n"
			+ "join professor_tb\r\n"
			+ "on subject_tb.professor_id = professor_tb.id\r\n"
			+ "join department_tb\r\n"
			+ "on department_tb.id = subject_tb.dept_id\r\n"
			+ "join college_tb\r\n"
			+ "on department_tb.college_id = college_tb.id\r\n"
			+ "where subject_tb.sub_year = ? and subject_tb.sub_semester = ? and professor_tb.name = ? "; 
	private static final String GETTING_PROFESSOR_SUBJECT_ALL = " select concat(subject_tb.sub_year , \"-\" , subject_tb.semester, \"학기\") as \"연도/학기\", college_tb.name as \"단과대학\" ,department_tb.name as \"개설학과\", subject_tb.id as \"학수번호\", subject_tb.type as \"강의구분\", subject_tb.name as \"강의명\", professor_tb.name as \"담당교수\", subject_tb.grades as 학점, subject_tb.num_of_student as 수강인원, subject_tb.capacity as 정원\r\n"
			+ "from subject_tb\r\n"
			+ "join professor_tb\r\n"
			+ "on subject_tb.professor_id = professor_tb.id\r\n"
			+ "join department_tb\r\n"
			+ "on department_tb.id = subject_tb.dept_id\r\n"
			+ "join college_tb\r\n"
			+ "on department_tb.college_id = college_tb.id\r\n ORDER BY id DESC LIMIT ? OFFSET ? " ;
	
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
						.year(rs.getString("연도/학기"))
						.university(rs.getString("단과대학"))
						.department(rs.getString("개설학과"))
						.subjectNum(rs.getInt("학수번호"))
						.type(rs.getString("강의구분"))
						.subject(rs.getString("강의명"))
						.teacher(rs.getString("담당교수"))
						.grade(rs.getInt("학점"))
						.stu_Num(rs.getInt("수강인원"))
						.capacity(rs.getInt("정원"))
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
						.year(rs.getString("연도/학기"))
						.university(rs.getString("단과대학"))
						.department(rs.getString("개설학과"))
						.subjectNum(rs.getInt("학수번호"))
						.type(rs.getString("강의구분"))
						.subject(rs.getString("강의명"))
						.teacher(rs.getString("담당교수"))
						.grade(rs.getInt("학점"))
						.stu_Num(rs.getInt("수강인원"))
						.capacity(rs.getInt("정원"))
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
						.year(rs.getString("연도/학기"))
						.university(rs.getString("단과대학"))
						.department(rs.getString("개설학과"))
						.subjectNum(rs.getInt("학수번호"))
						.type(rs.getString("강의구분"))
						.subject(rs.getString("강의명"))
						.teacher(rs.getString("담당교수"))
						.grade(rs.getInt("학점"))
						.stu_Num(rs.getInt("수강인원"))
						.capacity(rs.getInt("정원"))
						.build());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return subjectList;
	}
	
//	@Override
//	public int getTotalProfessorSubject() {
//		List<SubjectList> subjectList = new ArrayList<>();
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_PROFESSOR_SUBJECT_ALL)){
//			Resultset rs = pstmt.executeQuery();
//			while(rs.next()) {
//				subjectList.add(SubjectList.builder()
//						.year(rs.getString("연도/학기"))
//						.university(rs.getString("단과대학"))
//						.department(rs.getString("개설학과"))
//						.subjectNum(rs.getInt("학수번호"))
//						.type(rs.getString("강의구분"))
//						.subject(rs.getString("강의명"))
//						.teacher(rs.getString("담당교수"))
//						.grade(rs.getInt("학점"))
//						.stu_Num(rs.getInt("수강인원"))
//						.capacity(rs.getInt("정원"))
//						.build());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
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

	@Override
	public int getTotalProfessorSubject() {
		// TODO Auto-generated method stub
		return 0;
	}
	





}
