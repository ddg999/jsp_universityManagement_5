package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Department;
import com.university.model.Subject;
import com.university.model.SubjectList;
import com.university.model.Syllabus;
import com.university.repository.AdminRepositoryImpl;
import com.university.repository.SubjectRepositoryImpl;
import com.university.repository.interfaces.AdminRepository;
import com.university.repository.interfaces.SubjectRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/subject/*")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectRepository subjectRepository;
	private AdminRepository adminRepository;

	@Override
	public void init() throws ServletException {
		subjectRepository = new SubjectRepositoryImpl();
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
		// 전체 강의 조회 페이지
		case "/list":
			showAllSubjectList(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/subject/allSubList.jsp").forward(request, response);
//			showListProfessor(request, response);
			break;
		// 강의 검색
		case "/list/search":
			subjectSearch(request, response, session);
			
			break;
		// 강의 계획서 조회
		case "/syllabus":
			showSyllabus(request, response);
			break;
		case "/student":
			break;
		default:
			break;
		}
	}

	private void subjectSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		
		try {
			int year = Integer.parseInt(request.getParameter("subYear"));
			String name = request.getParameter("name");
			int semester = Integer.parseInt(request.getParameter("semester"));
			int deptId = Integer.parseInt(request.getParameter("deptId"));

			int page = 1;
			int pageSize = 20; // 한 페이지당 보여질 게시글 수
			try {
				String pageStr = request.getParameter("page");
				if (pageStr != null) {
					page = Integer.parseInt(pageStr);
				}
			} catch (Exception e) {
				page = 1;
				e.printStackTrace();
			}
			int offset = (page - 1) * pageSize; // 시작 위치 계산 (offset 값 계산)
			try {
				List<Subject> subjectList = subjectRepository.getSubjectBySearch(year, semester, name, deptId, pageSize,
						offset);
				int totalPageSize = subjectRepository.getTotalSubjectBySearch(year, semester, name, deptId);
				int totalPage = (int) Math.ceil((double) totalPageSize / pageSize);
				List<Department> departmentList = adminRepository.getAllDepartments();

				request.setAttribute("departmentList", departmentList);
				request.setAttribute("subjectList", subjectList);
				request.setAttribute("pageCount", totalPage);
				request.setAttribute("i", page);
				System.out.println("totalPage : " + totalPageSize);
				request.getRequestDispatcher("/WEB-INF/views/subject/allSubList.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/subject/list");
			return;
		}
	}

	private void showAllSubjectList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
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
		int offset = (page - 1) * pageSize; // 시작 위치 계산 (offset 값 계산)
		List<Subject> subjectList = subjectRepository.getAllSubject(pageSize, offset);
		int totalSubject = subjectRepository.getTotalSubjectCount();
		int totalPage = (int) Math.ceil((double) totalSubject / pageSize);
		List<Department> departmentList = adminRepository.getAllDepartments();

		request.setAttribute("departmentList", departmentList);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("pageCount", totalPage);
		request.setAttribute("subjectCount", totalSubject);
		request.setAttribute("i", page);

		System.out.println(departmentList);
		System.out.println(subjectList);
		
	}

	private void showSyllabus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Syllabus syllabus = subjectRepository.getSyllabusById(subjectId);
			request.setAttribute("syllabus", syllabus);
			request.getRequestDispatcher("/WEB-INF/views/subject/readSyllabus.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void showListProfessor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		int pageSize = 20;

		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;
		List<SubjectList> subjectList = subjectRepository.getProfessorSubjectAll(pageSize, offset);
		request.setAttribute("subjectList", subjectList);
		request.getRequestDispatcher("/WEB-INF/views/professor/professorsublist.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
