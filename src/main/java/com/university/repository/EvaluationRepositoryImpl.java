package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Evaluation;
import com.university.model.Subject;
import com.university.repository.interfaces.EvaluationRepository;
import com.university.util.DBUtil;

public class EvaluationRepositoryImpl implements EvaluationRepository {

	private static final String ADD_EVALUATION = " INSERT INTO evaluation_tb (student_id, subject_id, answer1, answer2, answer3, answer4, answer5, answer6, answer7, improvements) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_EVALUATION_BY_PRO_ID = " SELECT * FROM evaluation_tb e JOIN subject_tb s ON e.subject_id = s.id WHERE s.professor_id = ? AND s.name like CONCAT('%', COALESCE(NULLIF(?, ''), s.name), '%') ";
	private static final String SELECT_EVALUATION_BY_ID = " SELECT * FROM evluation_tb WHERE subject_id = ? AND student_id = ? ";
	private static final String SELECT_EVALUATION_SUBJECTS = " SELECT name FROM evaluation_tb e JOIN subject_tb s ON e.subject_id = s.id WHERE professor_id = ? GROUP BY name; ";
	
	@Override
	public void insertEvaluation(Evaluation evaluation) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_EVALUATION)) {
				pstmt.setInt(1, evaluation.getStudentId());
				pstmt.setInt(2, evaluation.getSubjectId());
				pstmt.setInt(3, evaluation.getAnswer1());
				pstmt.setInt(4, evaluation.getAnswer2());
				pstmt.setInt(5, evaluation.getAnswer3());
				pstmt.setInt(6, evaluation.getAnswer4());
				pstmt.setInt(7, evaluation.getAnswer5());
				pstmt.setInt(8, evaluation.getAnswer6());
				pstmt.setInt(9, evaluation.getAnswer7());
				pstmt.setString(10, evaluation.getImprovements());
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Evaluation> getEvaluationByProfessorId(int professorId, String subjectName) {
		List<Evaluation> evaluationList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_EVALUATION_BY_PRO_ID)) {
			pstmt.setInt(1, professorId);
			pstmt.setString(2, subjectName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluationList.add(Evaluation.builder().subjectName(rs.getString("name")).answer1(rs.getInt("answer1"))
						.answer2(rs.getInt("answer2")).answer3(rs.getInt("answer3")).answer4(rs.getInt("answer4"))
						.answer5(rs.getInt("answer5")).answer6(rs.getInt("answer6")).answer7(rs.getInt("answer7"))
						.improvements(rs.getString("improvements")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationList;
	}

	@Override
	public int isEvaluation(int subjectId, int studentId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_EVALUATION_BY_ID)) {
			pstmt.setInt(1, subjectId);
			pstmt.setInt(2, studentId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				rowCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	@Override
	public List<Subject> getEvaluationSubjects(int professorId) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_EVALUATION_SUBJECTS)) {
			pstmt.setInt(1, professorId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(Subject.builder().name(rs.getString("name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

}
