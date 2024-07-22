package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.GradeThisSemester;
import com.university.model.Principal;
import com.university.repository.GradeRepositoryImpl;
import com.university.repository.interfaces.GradeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/grade/*")
public class GradeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GradeRepository gradeRepository;

	@Override
	public void init() throws ServletException {
		gradeRepository = new GradeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String action = request.getPathInfo();
		switch (action) {
		// 금학기 성적 조회 페이지
		case "/thisSemester":
			showGradeThisSemester(request, response, session);
			break;
		// 학기별 성적 조회 페이지
		case "/semester":

			break;
		// 누계 성적 페이지
		case "/total":

			break;
		default:
			break;
		}
	}

	private void showGradeThisSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		List<GradeThisSemester> gradeList = gradeRepository.getGradeThisSemester(principal.getId(), 1, 2023);

		request.setAttribute("gradeList", gradeList);
		request.getRequestDispatcher("/WEB-INF/views/grade/thisgrade.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
