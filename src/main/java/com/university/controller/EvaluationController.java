package com.university.controller;

import java.io.IOException;

import com.university.model.Evaluation;
import com.university.repository.EvaluationRepositoryImpl;
import com.university.repository.interfaces.EvaluationRepository;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/evaluation/*")
public class EvaluationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	EvaluationRepository evaluationRepository;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		evaluationRepository = new EvaluationRepositoryImpl();
	}

	public EvaluationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String subjectId = request.getParameter("subjectId");
	        if (subjectId != null) {
	            request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
	        } else {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "subjectId가 누락되었습니다.");
	        }
	    }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 강의 평가 조회(학생)
		case "/write":
			getEvaluation(request, response);
			break;

			
		default:
			break;
		}
	}

	private void getEvaluation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int studentId = Integer.parseInt(request.getParameter("student_id"));
			int subjectId = Integer.parseInt(request.getParameter("subject_id"));
			int answer1 = Integer.parseInt(request.getParameter("answer1"));
			int answer2 = Integer.parseInt(request.getParameter("answer2"));
			int answer3 = Integer.parseInt(request.getParameter("answer3"));
			int answer4 = Integer.parseInt(request.getParameter("answer4"));
			int answer5 = Integer.parseInt(request.getParameter("answer5"));
			int answer6 = Integer.parseInt(request.getParameter("answer6"));
			int answer7 = Integer.parseInt(request.getParameter("answer7"));
			String improvements = request.getParameter("improvements");
			
			Evaluation evaluation = Evaluation.builder().studentId(studentId).subjectId(subjectId).answer1(answer1)
					.answer2(answer2).answer3(answer3).answer4(answer4).answer5(answer5).answer6(answer6).answer7(answer7)
					.improvements(improvements).build();
			evaluationRepository.insertEvaluation(evaluation);
			System.out.println("ㅇㅁㄴㅇㄴㅁㅇㄴㅁㄴㅇㅁㄴㅇㅁㄴㅁㅇㄴㅁㅇ");
			
			request.setAttribute("message", "등록 완료");
			request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "입력된 정보가 잘못되었습니다.");
			request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
		}
		
	}

}
