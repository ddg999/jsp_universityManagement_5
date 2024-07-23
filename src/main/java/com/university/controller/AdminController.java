package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.CollTuit;
import com.university.model.College;
import com.university.model.Department;
import com.university.model.Principal;
import com.university.model.Room;
import com.university.model.Subject;
import com.university.repository.AdminRepositoryImpl;
import com.university.repository.interfaces.AdminRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminRepository adminRepository;

	@Override
	public void init() throws ServletException {
		adminRepository = new AdminRepositoryImpl();
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
			viewCollege(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp").forward(request, response);
			break;
		// 학과 등록 페이지
		case "/department":
			viewDepartment(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp").forward(request, response);
			break;
		// 강의실 등록 페이지
		case "/room":
			viewRoom(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/room.jsp").forward(request, response);
			break;
		// 강의 등록 페이지
		case "/subject":
			viewSubject(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/subject.jsp").forward(request, response);
			break;
		// 단대별 등록금 페이지
		case "/tuition":
			viewCollTuit(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/colltuition.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}

	private void viewCollTuit(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<CollTuit> selectAllCollTuits = adminRepository.getAllCollTuits();
		System.out.println(selectAllCollTuits);
		request.setAttribute("collTuitList", selectAllCollTuits);
	}

	private void viewSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Subject> selectAllSubjects = adminRepository.getAllSubjects();
		System.out.println(selectAllSubjects);
		request.setAttribute("subjectList", selectAllSubjects);
	}

	private void viewRoom(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Room> selectAllRooms = adminRepository.getAllRooms();
		System.out.println(selectAllRooms);
		request.setAttribute("roomList", selectAllRooms);
	}

	private void viewDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String crud = request.getParameter("crud");
		System.out.println("crud " + crud);
		request.setAttribute("crud", crud);
		
		List<Department> selectAllDepartments = adminRepository.getAllDepartments();
		System.out.println(selectAllDepartments);
		request.setAttribute("departmentList", selectAllDepartments);
	}

	private void viewCollege(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String crud = request.getParameter("crud");
		System.out.println("crud " + crud);
		request.setAttribute("crud", crud);
		
		List<College> selectAllColleges = adminRepository.getAllColleges();
		System.out.println(selectAllColleges);
		request.setAttribute("collegeList", selectAllColleges);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		// 단과 대학 등록
		case "/college":
			addCollege(request, response, session);
			break;
		// 학과 등록
		case "/department":
			break;
		// 강의실 등록
		case "/room":
			break;
		// 강의 등록
		case "/subject":
			break;
		// 단대별 등록금
		case "/tuition":
			break;
		default:
			break;
		}
	}

	private void addCollege(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		String collegeName = request.getParameter("name");
		College college = College.builder()
				.name(collegeName)
				.build();
		System.out.println(college);
		adminRepository.addCollege(college);
		response.sendRedirect(request.getContextPath() + "/admin/college?crud=select");
	}

}
