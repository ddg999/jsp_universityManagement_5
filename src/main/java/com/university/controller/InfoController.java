package com.university.controller;

import java.io.IOException;

import com.university.model.Principal;
import com.university.model.Professor;
import com.university.model.ProfessorInfo;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.StudentInfo;
import com.university.repository.InfoRepositoryImpl;
import com.university.repository.interfaces.InfoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/info/*")
public class InfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	InfoRepository infoRepository;

	@Override
	public void init() throws ServletException {
		infoRepository = new InfoRepositoryImpl();

	}

	public InfoController() {
		super();
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
		// 내정보조회(학생)
		case "/student":
			getStudentInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/info/studentinfo.jsp").forward(request, response);
			break;

		// 내정보조회(직원)
		case "/staff":
			getStaffInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/info/staffinfo.jsp").forward(request, response);
			break;

		// 내정보조회(교수)
		case "/professor":
			getProfessorInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/info/professorinfo.jsp").forward(request, response);
			break;

		// 내정보 수정하기
		case "/update":
			System.out.println("혹시!!");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
			break;

		// 비밀번호 수정
		case "/password":
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
			break;

		// 휴학 신청
		case "/break/application":
			getbreakUserInfo(request, response, principal.getId());
			request.getRequestDispatcher("/WEB-INF/views/break/application.jsp").forward(request, response);
			break;

		// 휴학 내역 조회
		case "/break/list":
			request.getRequestDispatcher("/WEB-INF/views/break/appliststudent.jsp").forward(request, response);
			break;

		// 등록금 내역 조회
		case "/tuition/list":
			request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionlist.jsp").forward(request, response);
			break;

		// 등록금 납부 고지서
		case "/tuition/payment":
			request.getRequestDispatcher("/WEB-INF/views/tuition/tuitionpayment.jsp").forward(request, response);
			break;

		default:
			break;
		}
	}

	private void getbreakUserInfo(HttpServletRequest request, HttpServletResponse response, int principalId) {
		StudentInfo student = infoRepository.getStudentInfo(principalId);
		System.out.println(student);
		request.setAttribute("student", student);
	}

	private void getProfessorInfo(HttpServletRequest request, HttpServletResponse response, int principalId) {
		ProfessorInfo professor = infoRepository.getProfessorInfo(principalId);
		System.out.println(professor);
		request.setAttribute("professor", professor);
	}

	private void getStudentInfo(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws IOException {

		StudentInfo student = infoRepository.getStudentInfo(principalId);
		System.out.println(student);
		request.setAttribute("student", student);
	}

	private void getStaffInfo(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws IOException {

		Staff staff = infoRepository.getStaffInfo(principalId);
		System.out.println(staff);
		request.setAttribute("staff", staff);

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
		case "/password":
			handleUpdatePassword(request, response, principal.getId());
			break;

		// 휴학 신청
		case "/break/application":
			// 대기
			break;

		// 등록금 납부 고지서
		case "/tuition/payment":
			// 대기
			break;

		case "/update":
			handleUpdateStaffInfo(request, response, principal.getId());
			break;

		default:
			break;
		}
	}

	private void handleUpdateStaffInfo(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute("principal");
		String userRole = principal.getUserRole();
		request.setAttribute("principal", principal);

		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String checkPassword = request.getParameter("password");

		if (address == null || address.trim().isEmpty()) {
			request.setAttribute("message", "주소 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
			return;
		} else if (tel == null || tel.trim().isEmpty()) {
			request.setAttribute("message", "전화번호를 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
			return;
		} else if (email == null || email.trim().isEmpty()) {
			request.setAttribute("message", "이메일을 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
			return;
		} else if (checkPassword == null || tel.trim().isEmpty()) {
			request.setAttribute("message", "비밀번호를 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
			return;
		}
		
		if (checkPassword.equals(principal.getPassword())) {
			if (userRole.equals("student")) {
				Student student = Student.builder().address(address).tel(tel).email(email).build();
				infoRepository.updateStudentInfo(student, principalId);
				response.sendRedirect("/info/student");
				
			} else if (userRole.equals("staff")) {
				Staff staff = Staff.builder().address(address).tel(tel).email(email).build();
				infoRepository.updateStaffInfo(staff, principalId);
				response.sendRedirect("/info/staff");
				
			} else if (userRole.equals("professor")) {
				Professor professor = Professor.builder().address(address).tel(tel).email(email).build();
				infoRepository.updateProfessorInfo(professor, principalId);
				response.sendRedirect("/info/professor");
			}
		} else {
			request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
			request.getRequestDispatcher("/WEB-INF/views/info/updateInfo.jsp").forward(request, response);
		}

	}

	private void handleUpdatePassword(HttpServletRequest request, HttpServletResponse response, int principalId)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute("principal");

		String beforePassword = request.getParameter("beforePassword");
		String afterPassword = request.getParameter("afterPassword");
		String passwordCheck = request.getParameter("passwordCheck");

		// 방어적 코드 및 예외처리
		if (beforePassword == null || afterPassword == null || passwordCheck == null || beforePassword.trim().isEmpty()
				|| afterPassword.trim().isEmpty() || passwordCheck.trim().isEmpty()) {
			request.setAttribute("message", "비밀번호를 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
			return;
		}

		if (!principal.getPassword().equals(beforePassword)) {
			request.setAttribute("message", "현재 비밀번호가 일치하지 않습니다.");
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
			return;
		}

		if (!afterPassword.equals(passwordCheck)) {
			request.setAttribute("message", "변경할 비밀번호가 일치하지 않습니다.");
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
			return;
		}

		try {
			infoRepository.updateUserPassword(afterPassword, principalId);
			request.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다!");
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "ERROR");
			request.getRequestDispatcher("/WEB-INF/views/password/updatepassword.jsp").forward(request, response);
		}
	}
}
