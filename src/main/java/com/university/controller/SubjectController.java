package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.SubjectList;
import com.university.model.Syllabus;
import com.university.repository.SubjectRepositoryImpl;
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

	@Override
	public void init() throws ServletException {
		subjectRepository = new SubjectRepositoryImpl();
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
		// 전체 강의 조회
		case "/list":
			showAllSubjectList(request, response);
//			showListProfessor(request, response);
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

	private void showAllSubjectList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/subject/allSubList.jsp").forward(request, response);
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
