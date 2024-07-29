package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Evaluation;
import com.university.model.Principal;
import com.university.repository.EvaluationRepositoryImpl;
import com.university.repository.interfaces.EvaluationRepository;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/evaluation/*")
public class EvaluationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EvaluationRepository evaluationRepository;

	@Override
	public void init(ServletConfig config) throws ServletException {
		evaluationRepository = new EvaluationRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		case "/add":
			showAddEvaluation(request, response, session);
			break;
		case "/read":
			showMyEvaluation(request, response, session);
			break;
		default:
			break;
		}

	}

	private void showMyEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("professor")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}

		List<Evaluation> evaluationList = evaluationRepository.getEvaluationByProfessorId(principal.getId());
		request.setAttribute("evaluationList", evaluationList);
		request.getRequestDispatcher("/WEB-INF/views/evaluation/myevaluation.jsp").forward(request, response);
	}

	private void showAddEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		String subjectId = request.getParameter("subjectId");
		request.setAttribute("subjectId", subjectId);
		request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		// 강의 평가 조회(학생)
		case "/write":
			addEvaluation(request, response, session);
			break;

		default:
			break;
		}
	}

	private void addEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			int answer1 = Integer.parseInt(request.getParameter("answer1"));
			int answer2 = Integer.parseInt(request.getParameter("answer2"));
			int answer3 = Integer.parseInt(request.getParameter("answer3"));
			int answer4 = Integer.parseInt(request.getParameter("answer4"));
			int answer5 = Integer.parseInt(request.getParameter("answer5"));
			int answer6 = Integer.parseInt(request.getParameter("answer6"));
			int answer7 = Integer.parseInt(request.getParameter("answer7"));
			String improvements = request.getParameter("improvements");

			Evaluation evaluation = Evaluation.builder().studentId(studentId).subjectId(subjectId).answer1(answer1)
					.answer2(answer2).answer3(answer3).answer4(answer4).answer5(answer5).answer6(answer6)
					.answer7(answer7).improvements(improvements).build();
			evaluationRepository.insertEvaluation(evaluation);
			response.sendRedirect(request.getContextPath() + "/grade/thisSemester");
		} catch (Exception e) {
			request.getRequestDispatcher("/WEB-INF/views/evaluation/evaluation.jsp").forward(request, response);
		}

	}

}
