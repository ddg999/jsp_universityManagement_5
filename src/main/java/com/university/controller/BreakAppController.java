package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.BreakApp;
import com.university.model.Principal;
import com.university.model.Student;
import com.university.model.StudentInfo;
import com.university.repository.BreakAppRepositoryImpl;
import com.university.repository.InfoRepositoryImpl;
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
		infoRepository = new InfoRepositoryImpl();

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
			getAppListInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/break/appliststudent.jsp").forward(request, response);
			break;
		// 휴학 처리 페이지
		case "/list/staff":
			request.getRequestDispatcher("/WEB-INF/views/break/appliststaff.jsp").forward(request, response);
			break;
		case "/detail":
		// 휴학 신청서 확인 페이지
			getAppDetailInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/break/applicationdetail.jsp").forward(request, response);
			break;
		// 휴학 신청 취소
		case "/delete":
			handelDeleteApplication(request, response, principal.getId());
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void handelDeleteApplication(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		try {
			int breakAppId = Integer.parseInt(request.getParameter("id"));
			System.out.println("sdsd" + breakAppId);
			breakAppRepository.deleteAppById(breakAppId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/break/list");
		
	}

	private void getAppDetailInfo(HttpServletRequest request, HttpServletResponse response, int principalId) {
		int breakAppId = Integer.parseInt(request.getParameter("id"));
		System.out.println(breakAppId);
		BreakApp breakApp = breakAppRepository.selectAppById(breakAppId);
		StudentInfo student = infoRepository.getStudentInfo(principalId);
		System.out.println("student : " + student);
		System.out.println("breakApp : " + breakApp);
		
		request.setAttribute("student", student);
		request.setAttribute("breakApp", breakApp);

	}

	private void getAppListInfo(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws ServletException, IOException {
		List<BreakApp> breakAppList = breakAppRepository.selectAppByStudentId(principalId);
		request.setAttribute("breakAppList", breakAppList);
		System.out.println(request.getAttribute("breakAppList"));

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
		// 휴학 신청
		case "/application":
			handleAddApplication(request, response, principal.getId());
			break;
		// 휴학 신청관리 직원용
		case "/list/staff":
			handleUpdateApplication(request, response, principal.getId());
			break;
		default:
			break;
		}

	}
	

	private void handleUpdateApplication(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws IOException {
		int appId = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");

		try {

			BreakApp breakApp = BreakApp.builder().id(appId).status(status).build();

			breakAppRepository.updateAppById(appId, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/list/staff");

	}

	private void handleAddApplication(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws IOException {
		int studentId = Integer.parseInt(request.getParameter("student_id"));
		int studentGrade = Integer.parseInt(request.getParameter("student_grade"));
		int fromYear = Integer.parseInt(request.getParameter("from_year"));
		int fromSemester = Integer.parseInt(request.getParameter("from_semester"));
		int toYear = Integer.parseInt(request.getParameter("to_year"));
		int toSemester = Integer.parseInt(request.getParameter("to_semester"));
		String type = request.getParameter("type");

		BreakApp breakApp = BreakApp.builder().studentId(studentId).studentGrade(studentGrade).fromYear(fromYear)
				.fromSemester(fromSemester).toYear(toYear).toSemester(toSemester).type(type).build();
		System.out.println(breakApp);
		breakAppRepository.insertApp(breakApp);
		response.sendRedirect(request.getContextPath() + "/break/list");
	}

}
