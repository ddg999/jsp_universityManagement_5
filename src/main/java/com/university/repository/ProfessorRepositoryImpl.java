//package com.university.repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.university.model.Evaluation;
//import com.university.model.Professor;
//import com.university.model.StuSubDetail;
//import com.university.model.Student;
//import com.university.model.Subject;
//import com.university.model.Syllabus;
//import com.university.repository.interfaces.ProfessorRepository;
//import com.university.util.DBUtil;
//
//public class ProfessorRepositoryImpl implements ProfessorRepository {
//
//	private static final String SELECT_PROFESSOR_SUBJECT_YEAR = " SELECT id, name, type, sub_year, semester, sub_day, num_of_student FROM subject where professor_id = ? AND sub_year = ? ";
//	private static final String SELECT_PROFESSOR_SUBJECT_YEAR_AND_SEMESTER = " SELECT id, name, type, sub_year, semester, sub_day, num_of_student FROM subject where professor_id = ? AND sub_year = ? AND semester = ? ";
//	private static final String SELECT_ALL_SUBSTUDENT = " SELECT * FROM stu_sub_detail_tb ";
//	private static final String SELECT_SUBSTUDENT = " SELECT * FROM stu_sub_detail_tb WHERE name = ? ";
//
//	// TODO 내 강의 학기별 조회(년도만)
//	@Override
//	public Subject getProfessorSubjectbyYear(int professorID, int subYear) {
//		Subject subject = null;
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_SUBJECT_YEAR)) {
//			pstmt.setInt(1, professorID);
//			pstmt.setInt(2, subYear);
//			ResultSet rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				subject = Subject.builder().id(rs.getInt("id")).name(rs.getString("name")).type(rs.getString("type"))
//						.subYear(rs.getInt("sub_year")).semester(rs.getInt("semester")).subDay(rs.getString("sub_day"))
//						.numOfStudent(rs.getInt("num_of_student")).build();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return subject;
//	}
//
//	// TODO 내 강의 학기별 조회(년도 ,학기)
//	@Override
//	public Subject getProfessorSubjectbyYearandSemester(int professorID, int subYear, int semester) {
//		Subject subject = null;
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_SUBJECT_YEAR_AND_SEMESTER)) {
//			pstmt.setInt(1, professorID);
//			pstmt.setInt(2, subYear);
//			pstmt.setInt(3, semester);
//			ResultSet rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				subject = Subject.builder().id(rs.getInt("id")).name(rs.getString("name")).type(rs.getString("type"))
//						.subYear(rs.getInt("sub_year")).semester(rs.getInt("semester")).subDay(rs.getString("sub_day"))
//						.numOfStudent(rs.getInt("num_of_student")).build();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return subject;
//	}	
//
//	// TODO 강의별 학생리스트 조회, 출결 및 성적 기입
//
//	// 전체일경우
//	@Override
//	public List<StuSubDetail> getAllStuSubDetails() {
//		List<StuSubDetail> studentList = new ArrayList<>();
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUBSTUDENT)) {
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				StuSubDetail stuSubDetail = StuSubDetail.builder().id(rs.getInt("id"))
//						.studentId(rs.getInt("student_id")).subjectId(rs.getInt("subject_id"))
//						.absent(rs.getInt("absent")).lateness(rs.getInt("lateness")).homework(rs.getInt("homework"))
//						.midExam(rs.getInt("mid_exam")).finalExam(rs.getInt("final_exam"))
//						.convertedMark(rs.getInt("coverted_mark")).build();
//				studentList.add(stuSubDetail);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return studentList;
//	}
//
//	// TODO 특정 학생을 검색할경우
//	@Override
//	public StuSubDetail getStuSubDetail(Student student, Subject subject) {
//		StuSubDetail stuSubDetail = null;
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBSTUDENT)) {
//			pstmt.setString(1, "name");
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				stuSubDetail = StuSubDetail.builder().id(rs.getInt("id")).studentId(rs.getInt("student_id"))
//						.subjectId(rs.getInt("subject_id")).absent(rs.getInt("absent")).lateness(rs.getInt("lateness"))
//						.homework(rs.getInt("homework")).midExam(rs.getInt("mid_exam"))
//						.finalExam(rs.getInt("final_exam")).convertedMark(rs.getInt("coverted_mark")).build();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return stuSubDetail;
//	}
//
//	@Override
//	public Syllabus getsyllabus(Subject subject) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	// TODO 강의평가 확인
//	@Override
//	public Evaluation getEvaluation(Subject subject) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
