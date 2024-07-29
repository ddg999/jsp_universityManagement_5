package com.university.controller;

import java.io.IOException;

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
		// 내정보조회(학생)
		case "/":
			System.out.println("조회 들어왔나?111111111111");
			getEvaluation(request, response);
			System.out.println("조회 들어왔나?222222222222");
			request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
			System.out.println("조회 들어왔나?333333333333");
			break;

			
		default:
			break;
		}
	}

	private void getEvaluation(HttpServletRequest request, HttpServletResponse response) {
//		Evaluation evaluation = evaluationRepository./();
//		request.setAttribute("write", evaluation);
	}

}
