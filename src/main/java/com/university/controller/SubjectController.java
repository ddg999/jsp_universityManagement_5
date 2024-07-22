package com.university.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.university.model.SubjectList;
import com.university.repository.SubjectRepositoryImpl;
import com.university.repository.interfaces.SubjectRepository;

@WebServlet("/subject/*")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectRepository subjectRepository;

	public SubjectController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		// 전체 강의 조회
		case "/list":
			showListProfessor(request, response);
			break;
		// 강의 계획서 조회
		case "/syllabus":
			
			break;
		default:
			break;
		}
	}

	private void showListProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int pageSize = 20;
		
		try {
			String pageStr = request.getParameter("page");
			if(pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page -1) * pageSize;
		List<SubjectList> subjectList = subjectRepository.getProfessorSubjectAll(pageSize, offset);
		request.setAttribute("subjectList", subjectList);
		request.getRequestDispatcher("/WEB-INF/views/professor/professorsublist.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
