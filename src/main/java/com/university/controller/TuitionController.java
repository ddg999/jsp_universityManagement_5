package com.university.controller;

import java.io.IOException;

import com.university.model.Principal;
import com.university.model.StudentInfo;
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
	
	public TuitionController() {
		super();
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
			System.out.println("리스트");
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
//		
//			List<Integer> studentIdList = stuStatService.readIdList();
//
//			// 고지서 생성 개수 반환
//			int insertCount = 0;
//
//			// 모든 학생에 대해 일괄 생성 (고지서 생성 대상인지는 서비스에서 확인)
//			for (Integer studentId : studentIdList) {
//				// 생성될 때마다 +1됨
//				insertCount += tuitionService.createTuition(studentId);
//			}
//
//			// jsp로 생성 개수 보내기
//			model.addAttribute("insertCount", insertCount);
//			System.out.println(insertCount);
//
//			return "/tuition/createPayment";
//		}
	}

	private void getTuirionInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		TuitionInfo tuitionInfo = tuitionRepository.getTuitionInfoById(principal.getId());

		System.out.println(tuitionInfo+"dsadsasda");
		request.setAttribute("tuition", tuitionInfo);
		
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
		default:
			break;
		}
	}

	private void createTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Tuition tuition = tuitionRepository.getTuitionByIdAndSemester(2023000001, 1);
		tuitionRepository.createTuition(tuition);
		System.out.println("봏내짐");
		
	}

}
