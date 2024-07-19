package com.university.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SugangController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 강의 시간표 조회 페이지
		case "/subjectList":

			break;
		// 예비 수강 신청 페이지
		case "/pre":

			break;
		// 수강 신청 페이지
		case "/preAppList":

			break;
		// 수강 신청 내역 조회 페이지
		case "/list":

			break;
		// 수강 신청 기간 설정 페이지
		case "/period":

			break;
		default:

			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
