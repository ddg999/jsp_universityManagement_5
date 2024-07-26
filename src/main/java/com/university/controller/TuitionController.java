package com.university.controller;

import java.io.IOException;

import com.university.model.Principal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/tuition/*")
public class TuitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TuitionController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String action = request.getPathInfo();
		switch (action) {
		// 등록금 내역 조회
		case "/list":
			request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionlist.jsp").forward(request, response);
			break;
		// 등록금 납부 고지서
		case "/payment":
			request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionpayment.jsp").forward(request, response);
			break;
		// 등록금 고지서 발송
		case "/bill":
			showCreateTuition(request, response, session);

			break;
		default:
			break;
		}
	}

	private void showCreateTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
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
		request.getRequestDispatcher("/WEB-INF/views/tuition/createtuition.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
