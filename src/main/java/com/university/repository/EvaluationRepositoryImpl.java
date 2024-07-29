package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.university.model.Evaluation;
import com.university.repository.interfaces.EvaluationRepository;
import com.university.util.DBUtil;

public class EvaluationRepositoryImpl implements EvaluationRepository{
	
	private static final String ADD_EVALUATION = " INSERT INTO evaluation_tb (student_id,subject_id, answer1, answer2, answer3, answer4, answer5, answer6, answer7, improvements) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
	
	private EvaluationRepository evaluationRepository;

	@Override
	public void insertEvaluation(Evaluation evaluation) {
		System.out.println("됐나 여기111111111111");
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_EVALUATION)){
				pstmt.setInt(1, evaluation.getStudentId());
				pstmt.setInt(2, evaluation.getSubjectId());
				pstmt.setInt(3, evaluation.getAnswer1());
				pstmt.setInt(4, evaluation.getAnswer2());
				pstmt.setInt(5, evaluation.getAnswer3());
				pstmt.setInt(6, evaluation.getAnswer4());
				pstmt.setInt(7, evaluation.getAnswer5());
				pstmt.setInt(8, evaluation.getAnswer6());
				pstmt.setInt(9, evaluation.getAnswer7());
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("됐나 여기2222222222222");
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Evaluation selectEvaluation(int subjectId) {
		return null;
	}

	@Override
	public List<Evaluation> selectMyEvaluationByProfessorId(int professorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
