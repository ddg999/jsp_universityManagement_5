package com.university.controller;

import java.io.IOException;

import com.university.model.BreakApp;
import com.university.model.Principal;
import com.university.model.Student;
import com.university.model.StudentInfo;
import com.university.repository.BreakAppRepositoryImpl;
import com.university.repository.interfaces.BreakAppRepository;
import com.university.repository.interfaces.InfoRepository;

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
	private InfoRepository infoRepository;
	

	@Override
	public void init() throws ServletException {
		breakAppRepository = new BreakAppRepositoryImpl();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute("principal");
		if (principal == null) {
			response.sendRedirect("/login.jsp");
			return;
		}
		
		String action = request.getPathInfo();
		switch (action) {
		// 휴학 신청 페이지
		case "/application":
			getAppStudentInfo(request, response, principal.getId());
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


	private void getAppStudentInfo(HttpServletRequest request, HttpServletResponse response, int principalId) {
		StudentInfo student = infoRepository.getStudentInfo(principalId);
		System.out.println(student);
		request.setAttribute("student", student);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute("principal");

		if (principal == null) {
			response.sendRedirect("/login.jsp");
			return;
		}
		
		String action = request.getPathInfo();
		switch (action) {
		case "/application":
			handleAddApplication(request, response, principal.getId());
			break;
		case "/list/staff":
			handleUpdateApplication(request, response, principal.getId());
			break;
		default:
			break;
		}
		
	}

	private void handleUpdateApplication(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		int appId = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
				
		try {
			
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

	private void handleAddApplication(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
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
