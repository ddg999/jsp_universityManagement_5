package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.GradeSemester;
import com.university.model.Principal;
import com.university.repository.EvaluationRepositoryImpl;
import com.university.repository.GradeRepositoryImpl;
import com.university.repository.interfaces.EvaluationRepository;
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
	private EvaluationRepository evaluationRepository;

	@Override
	public void init() throws ServletException {
		gradeRepository = new GradeRepositoryImpl();
		evaluationRepository = new EvaluationRepositoryImpl();
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
			showGradeSemester(request, response, session);
			break;
		// 누계 성적 페이지
		case "/total":
			showGradeTotal(request, response, session);
			break;
		case "/search":
			showGradeSearch(request, response, session);
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
		List<GradeSemester> gradeList = gradeRepository.getGradeThisSemester(principal.getId(), 1, 2023);
		
		request.setAttribute("gradeList", gradeList);
		request.getRequestDispatcher("/WEB-INF/views/grade/thisgrade.jsp").forward(request, response);
	}

	private void showGradeSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
		List<GradeSemester> gradeList = gradeRepository.getGradeThisSemester(principal.getId(), 1, 2023);
		List<GradeSemester> yearList = gradeRepository.getGradeYear(principal.getId());
		request.setAttribute("yearList", yearList);
		request.setAttribute("gradeList", gradeList);
		request.getRequestDispatcher("/WEB-INF/views/grade/semester.jsp").forward(request, response);
	}

	private void showGradeSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			int subYear = Integer.parseInt(request.getParameter("subYear"));
			int semester = Integer.parseInt(request.getParameter("semester"));
			String type = request.getParameter("type");
			System.out.println(studentId + " " + subYear + " " + semester + " " + type);

			List<GradeSemester> gradeList = gradeRepository.getGradeSemesterSearch(studentId, subYear, semester, type);
			List<GradeSemester> yearList = gradeRepository.getGradeYear(studentId);

			request.setAttribute("selectedSubYear", subYear);
			request.setAttribute("selectedSemester", semester);
			request.setAttribute("selectedType", type);
			request.setAttribute("yearList", yearList);
			request.setAttribute("gradeList", gradeList);
			request.getRequestDispatcher("/WEB-INF/views/grade/semester.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void showGradeTotal(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/grade/totalgrade.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String action = request.getPathInfo();
		switch (action) {
		case "":
			break;
		default:
			break;
		}
	}

}
