package com.university.controller;

import java.io.IOException;

import com.university.model.BreakApp;
import com.university.model.Student;
import com.university.repository.BreakAppRepositoryImpl;
import com.university.repository.interfaces.BreakAppRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/break/*")
public class BreakAppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BreakAppRepository breakAppRepository;
	

	@Override
	public void init() throws ServletException {
		breakAppRepository = new BreakAppRepositoryImpl();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/application":
			handleAddApplication(request, response, session);
			break;
		case "/list/staff":
			handleUpdateApplication(request, response, session);
			break;
		default:
			break;
		}
		
	}

	private void handleUpdateApplication(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		int appId = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
				
		try {
			Student student = (Student) session.getAttribute("principal");
			
			BreakApp breakApp = BreakApp.builder()
					.id(appId)
					.status(status)
					.build();
			
			breakAppRepository.updateAppById(appId, status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/list/staff");
		
	}

	private void handleAddApplication(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Student student = (Student) session.getAttribute("principal"); 
		int studentId = Integer.parseInt(request.getParameter("student_id"));
		int studentGrade = Integer.parseInt(request.getParameter("student_grade"));
		int fromYear = Integer.parseInt(request.getParameter("from_year"));
		int fromSemester = Integer.parseInt(request.getParameter("from_semester"));
		int toYear = Integer.parseInt(request.getParameter("to_year"));
		int toSemester = Integer.parseInt(request.getParameter("to_semester"));
		String type = request.getParameter("type");
		
		BreakApp breakApp = BreakApp.builder()
				.studentId(studentId)
				.studentGrade(studentGrade)
				.fromYear(fromYear)
				.fromSemester(fromSemester)
				.toYear(toYear)
				.toSemester(toSemester)
				.type(type)
				.build();
		
		breakAppRepository.insertApp(breakApp);
		response.sendRedirect(request.getContextPath() + "/application");
	}
	
}
