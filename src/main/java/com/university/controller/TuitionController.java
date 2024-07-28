package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.AvgGrade;
import com.university.model.Principal;
import com.university.model.Tuition;
import com.university.model.TuitionInfo;
import com.university.repository.TuitionRepositoryImpl;
import com.university.repository.interfaces.TuitionRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/tuition/*")
public class TuitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TuitionRepository tuitionRepository;

	@Override
	public void init() throws ServletException {
		tuitionRepository = new TuitionRepositoryImpl();
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
		// 등록금 내역 조회
		case "/list":
			viewTuitionInfo(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionlist.jsp").forward(request, response);
			break;
		// 등록금 납부 고지서
		case "/payment":
			getTuirionInfo(request, response, session);
			break;
		// 등록금 고지서 발송
		case "/bill":
			showCreateTuition(request, response, session);
			break;

		case "/create":
			createTuition(request, response, session);
			break;
		default:
			break;
		}
	}

	private void viewTuitionInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		Tuition tuition = tuitionRepository.getTuitionBYId(principal.getId());
		request.setAttribute("tuition", tuition);

		request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionlist.jsp").forward(request, response);
		
	}

	private void getTuirionInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		TuitionInfo tuitionInfo = tuitionRepository.getTuitionInfoById(principal.getId());
		Tuition tuition1 = tuitionRepository.getTuitionBYId(principal.getId());
		System.out.println(tuition1);
		request.setAttribute("tuition", tuitionInfo);
		request.setAttribute("tuition1", tuition1);

		request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionpayment.jsp").forward(request, response);
	}

	private void showCreateTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
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
		request.getRequestDispatcher("/WEB-INF/views/tuition/createtuition.jsp").forward(request, response);
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
		// 등록금 납부 고지서
		case "/payment":
			updateStatus(request, response, session);
			break;
		default:
			break;
		}
	}

	private void updateStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		tuitionRepository.updateStatus(principal.getId());
		request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionpayment.jsp").forward(request, response);
	}

	private void createTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

		// 1. 학생아이디, 학생 평균값 구하기
		try {
			List<AvgGrade> avgGradeList = tuitionRepository.getAvgGrade();
			int rowCount = 0;
			for (AvgGrade avgGrade : avgGradeList) {
				int id = avgGrade.getStudentId();
				int sch;
				if (avgGrade.getAvgGrade() >= 4) {
					sch = 1;
				} else {
					sch = 2;
				}
				tuitionRepository.createStuSch(id, sch);
				
				// 등록금 고지서 
				Tuition tuition = tuitionRepository.getTuitionByIdAndSemester(id, 1);
				// 등록금 고지서 생성 학생
				rowCount = tuitionRepository.createTuition(tuition);
			}
			request.setAttribute("insertCount", rowCount);
			request.getRequestDispatcher("/WEB-INF/views/tuition/createtuition.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	

	}
}
