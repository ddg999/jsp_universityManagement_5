package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Department;
import com.university.model.Principal;
import com.university.model.SubjectList;
import com.university.repository.AdminRepositoryImpl;
import com.university.repository.ProfessorRepositoryImpl;
import com.university.repository.interfaces.AdminRepository;
import com.university.repository.interfaces.ProfessorRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/professor/*")
public class ProfessorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorRepository professorRepository;
	private AdminRepository adminRepository;

	@Override
	public void init() throws ServletException {
		professorRepository = new ProfessorRepositoryImpl();
		adminRepository = new AdminRepositoryImpl();
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
		// 내 강의 조회 페이지
		case "/subject":
			showListProfessor(request, response, session);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private void showListProfessor(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Principal principal = (Principal) session.getAttribute("principal");

		int page = 1;
		int pageSize = 20; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;

		List<Department> departmentList = adminRepository.getAllDepartments();
		List<SubjectList> subjectList = professorRepository.getProfessorSubject(principal.getName(), pageSize, offset);

		int totalSubject = professorRepository.getTotalProfessorSubject(principal.getId());
		int totalPage = (int) Math.ceil((double) totalSubject / pageSize);

		request.setAttribute("departmentList", departmentList);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("pageCount", totalPage);
		request.setAttribute("subjectCount", totalSubject);
		request.setAttribute("currentPage", page);

		System.out.println("현재 로그인 된 교수님 : " + principal.getName());
		request.getRequestDispatcher("/WEB-INF/views/professor/professorsublist.jsp").forward(request, response);

	}

}
