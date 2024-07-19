package com.university.controller;

import java.io.IOException;

import com.university.model.Staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/info/*")
public class InfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InfoController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 내정보조회(학생)
		case "/student":

			break;
		case "/password":
			request.getRequestDispatcher("/WEB-INF/views/info/staffinfo.jsp").forward(request, response);
			break;
		// 내정보조회(학생)
		case "/staff":
			Staff staff = Staff.builder()
			.id(1)
			.name("형정쓰")
			// .birthDate(request.getParameter("birthDate"))
			.gender("여자")
			.gender("부산광역시")
			.tel("010-1111-1111")
			.email("e@naver.com")
			//.hireDate()
			.build();
			request.setAttribute("staff", staff);
			request.getRequestDispatcher("/WEB-INF/views/info/staffinfo.jsp").forward(request, response);
			break;
		// 내정보조회(교수)
		case "/professor":

			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
	}

}
