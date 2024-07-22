package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.SubjectList;
import com.university.repository.interfaces.SubjectRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/professor/*")
public class ProfessorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectRepository subjectRepository;
	
	public ProfessorController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		int subjectId;
		switch (action) {
		// 내 강의 조회 페이지
		case "/subject":
			showListProfessor(request, response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

}
