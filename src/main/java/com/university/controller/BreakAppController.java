package com.university.controller;

import java.io.IOException;

import com.university.repository.BreakAppRepositoryImpl;
import com.university.repository.interfaces.BreakAppRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/break/*")
public class BreakAppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BreakAppRepository breakAppRepository;

	@Override
	public void init() throws ServletException {
		breakAppRepository = new BreakAppRepositoryImpl();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 휴학 신청 페이지
		case "/application":
			request.getRequestDispatcher("/WEB-INF/views/break/application.jsp").forward(request, response);
			break;
		// 휴학 내역 조회 페이지
		case "/list":
			request.getRequestDispatcher("/WEB-INF/views/break/appliststudent.jsp").forward(request, response);
			break;
		// 휴학 처리 페이지
		case "/list/staff":
			request.getRequestDispatcher("/WEB-INF/views/break/appliststaff.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
