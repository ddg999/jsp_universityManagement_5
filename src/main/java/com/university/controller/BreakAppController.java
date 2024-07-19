package com.university.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/break/*")
public class BreakAppController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BreakAppController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 휴학 신청 페이지
		case "/application":

			break;
		// 휴학 내역 조회 페이지
		case "/list":

			break;
		// 휴학 처리 페이지
		case "/list/staff":

			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
