package com.university.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

import com.university.model.Principal;
import com.university.model.User;
import com.university.repository.UserRepositoryImpl;
import com.university.repository.interfaces.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepository;

	@Override
	public void init() throws ServletException {
		userRepository = new UserRepositoryImpl();
	}

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		switch (action) {
		case "/signin":
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		switch (action) {
		case "/login":
			// 로그인 기능 처리
			handleSignin(request, response);
			break;

		case "/findId":
			handleFindId(request, response);
			break;

		case "/findPassword":
			handleFindPassword(request, response);

		default:
			break;
		}
	}

	public static String randomPassword(int leng) {
		int index = 0;
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer password = new StringBuffer();
		Random random = new Random();

		for (int i = 0; i < leng; i++) {
			double rd = random.nextDouble();
			index = (int) (charSet.length * rd);
			password.append(charSet[index]);
		}
		return password.toString();
	}

	private void handleFindPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		String userPassword = null;

		if (userRole.equals("student")) {
			userPassword = userRepository.getStudentPasswordByNameAndIdAndEmail(name, id, email);
		} else if (userRole.equals("staff")) {
			userPassword = userRepository.getStaffPasswordByNameAndIdAndEmail(name, id, email);
		} else if (userRole.equals("professor")) {
			userPassword = userRepository.getProfessorPasswordByNameAndIdAndEmail(name, id, email);
		}
		userPassword = randomPassword(6);
		userRepository.updateUserPassword(userPassword, id);
		request.setAttribute("userPassword", userPassword);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/findpasswordresult.jsp").forward(request, response);
	}

	private void handleFindId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		int userId = 0;
		if (userRole.equals("student")) {
			userId = userRepository.getStudentIdByNameAndEmail(name, email);
		} else if (userRole.equals("staff")) {
			userId = userRepository.getStaffIdByNameAndEmail(name, email);
		} else if (userRole.equals("professor")) {
			userId = userRepository.getProfessorIdByNameAndEmail(name, email);
		}

		System.out.println(userId);
		request.setAttribute("userId", userId);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/findidresult.jsp").forward(request, response);

	}

	private void handleSignin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		User user = userRepository.getUserByIdAndPassword(id, password);
		Principal principal = null;
		try {
			if (user != null) {
				if (user.getUserRole().equals("student")) {
					principal = userRepository.getStudent(user);
				} else if (user.getUserRole().equals("staff")) {
					principal = userRepository.getStaff(user);
				} else if (user.getUserRole().equals("professor")) {
					principal = userRepository.getProfessor(user);
				}
				if (principal != null && principal.getPassword().equals(password)) {
					HttpSession session = request.getSession();
					session.setAttribute("principal", principal);
					response.sendRedirect(request.getContextPath() + "/home.jsp");
				}
			} else {
				request.setAttribute("errorMessage", "아이디 비밀번호가 틀렸습니다.");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}

}
