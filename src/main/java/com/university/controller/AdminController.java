package com.university.controller;

import java.io.IOException;

import com.university.model.Principal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("staff")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		// 단과 대학 등록 페이지
		case "/college":
			request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp").forward(request, response);
			break;
		// 학과 등록 페이지
		case "/department":
			request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp").forward(request, response);
			break;
		// 강의실 등록 페이지
		case "/room":
			request.getRequestDispatcher("/WEB-INF/views/admin/room.jsp").forward(request, response);
			break;
		// 강의 등록 페이지
		case "/subject":
			request.getRequestDispatcher("/WEB-INF/views/admin/subject.jsp").forward(request, response);
			break;
		// 단대별 등록금 페이지
		case "/tuition":
			request.getRequestDispatcher("/WEB-INF/views/admin/colltuition.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
