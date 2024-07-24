package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Department;
import com.university.model.SugangSubject;
import com.university.repository.SugangRepositoryImpl;
import com.university.repository.interfaces.SugangRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SugangRepository sugangRepository;

	@Override
	public void init() throws ServletException {
		sugangRepository = new SugangRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		// 강의 시간표 조회 페이지
		case "/subject":
			showSugangSubject(request, response);
			break;
		case "/subject/search":
			showSugangSubjectSearch(request, response);
			break;
		// 예비 수강 신청 페이지
		case "/preRegist":
			request.getRequestDispatcher("/WEB-INF/views/sugang/preRegist.jsp").forward(request, response);
			break;
		// 수강 신청 페이지
		case "/regist":
			request.getRequestDispatcher("/WEB-INF/views/sugang/regist.jsp").forward(request, response);
			break;
		// 수강 신청 내역 조회 페이지
		case "/registResult":
			request.getRequestDispatcher("/WEB-INF/views/sugang/registResult.jsp").forward(request, response);
			break;
		// 수강 신청 기간 설정 페이지
		case "/period":
			request.getRequestDispatcher("/WEB-INF/views/sugang/updateperiod.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void showSugangSubject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		int pageSize = 20; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산( offset 값 계산)
		List<SugangSubject> subjectList = sugangRepository.getAllSugangSubject(pageSize, offset);
		int totalSubjects = sugangRepository.getTotalSubjectsCount();
		List<Department> deptList = sugangRepository.getAllDepartment();

		int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);
		request.setAttribute("subjectCount", totalSubjects);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/sugangSubject.jsp").forward(request, response);
	}

	private void showSugangSubjectSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		int pageSize = 20; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산( offset 값 계산)
		try {
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String deptName = request.getParameter("deptName");

			int totalSubjects = sugangRepository.getSearchSugangSubjectCount(name, type, deptName);
			List<Department> deptList = sugangRepository.getAllDepartment();
			List<SugangSubject> subjectList = sugangRepository.getSearchSugangSubjects(name, type, deptName, pageSize,
					offset);
			int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);
			request.setAttribute("subjectCount", totalSubjects);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("deptList", deptList);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("selectedName", name);
			request.setAttribute("selectedType", type);
			request.setAttribute("selectedDeptName", deptName);
			request.getRequestDispatcher("/WEB-INF/views/sugang/sugangSubject.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
