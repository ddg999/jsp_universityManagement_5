package com.university.controller;

import java.io.IOException;

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
		case "/signin":
			// 로그인 기능 처리
			handleSignin(request, response);
			break;

		default:
			break;
		}
	}

	private void handleSignin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		User principal = userRepository.getUserByIdAndPassword(id, password);
		
		if(principal != null && principal.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("principal", principal);
			response.sendRedirect(request.getContextPath() + "/home.jsp");
		} else {
			request.setAttribute("errorMessage", "잘못된 요청입니다.");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}

}
