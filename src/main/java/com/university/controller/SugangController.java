package com.university.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
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
		case "/subjectList":
			request.getRequestDispatcher("/WEB-INF/views/sugang/stuSubList.jsp").forward(request, response);
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

			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
