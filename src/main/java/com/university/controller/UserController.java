package com.university.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.university.model.Principal;
import com.university.model.User;
import com.university.repository.UserRepositoryImpl;
import com.university.repository.interfaces.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
		case "/findId":
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
			break;
		case "/findPassword":
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
			break;
		case "/login":
			request.getRequestDispatcher("/login.jsp").forward(request, response);
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

	// 임시 비밀번호 발급
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
		String idStr = request.getParameter("id");
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		String userPassword = null;

		// 방어적 코드 작성 및 예외처리
		if (name == null || name.trim().isEmpty()) {
			request.setAttribute("errorMessage", "이름을 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
			return;
		} else if (idStr == null || idStr.trim().isEmpty()) {
			request.setAttribute("errorMessage", "아이디를 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
			return;
		} else if (email == null || email.trim().isEmpty()) {
			request.setAttribute("errorMessage", "이메일을 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
			return;
		} else if (userRole == null) {
			request.setAttribute("errorMessage", "직위를 선택해주세요");
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
		}

		int id = Integer.parseInt(request.getParameter("id"));
		try {
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
			request.getRequestDispatcher("/WEB-INF/views/find/findpasswordresult.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "입력된 정보가 틀렸습니다.");
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
		}
	}

	private void handleFindId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		int userId = 0;

		// 방어적 코드 및 예외처리
		if (name == null || name.trim().isEmpty()) {
			request.setAttribute("errorMessage", "이름을 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
			return;
		} else if (email == null || email.trim().isEmpty()) {
			request.setAttribute("errorMessage", "이메일을 입력해주세요.");
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
			return;
		} else if (userRole == null) {
			request.setAttribute("errorMessage", "직위를 선택해주세요");
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
		}

		try {
			if (userRole.equals("student")) {
				userId = userRepository.getStudentIdByNameAndEmail(name, email);
			} else if (userRole.equals("staff")) {
				userId = userRepository.getStaffIdByNameAndEmail(name, email);
			} else if (userRole.equals("professor")) {
				userId = userRepository.getProfessorIdByNameAndEmail(name, email);
			}
			request.setAttribute("userId", userId);
			request.setAttribute("name", name);
			request.getRequestDispatcher("/WEB-INF/views/find/findidresult.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "입력된 정보가 틀렸습니다.");
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
		}

	}

	private void handleSignin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String password = request.getParameter("password");

		// 방어적 코드 및 예외 처리
		// JSP에 required 때문에 필요없을지도? (일단 대기)
//		String idStr = request.getParameter("id");
//		if(password == null || password.trim().isEmpty()) {
//			request.setAttribute("errorMessage", "비밀번호을 입력해주세요.");
//			request.getRequestDispatcher("/login.jsp").forward(request, response);
//			return;
//		} else if (idStr == null || idStr.trim().isEmpty()){
//			request.setAttribute("errorMessage", "아이디를 입력해주세요.");
//			request.getRequestDispatcher("/login.jsp").forward(request, response);
//			return;
//		}

		int id = Integer.parseInt(request.getParameter("id"));
		User user = userRepository.getUserByIdAndPassword(id, password);
		Principal principal = null;

		if (user != null) {
			if (user.getUserRole().equals("student")) {
				principal = userRepository.getStudent(user);
			} else if (user.getUserRole().equals("staff")) {
				principal = userRepository.getStaff(user);
			} else if (user.getUserRole().equals("professor")) {
				principal = userRepository.getProfessor(user);
			}
			if (principal != null && principal.getPassword().equals(password)) {
				String checkbox = request.getParameter("rememberId");
				String id1 = request.getParameter("id");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				Cookie cookie = new Cookie("userId", id1);
				System.out.println(checkbox);
				System.out.println(cookie.getName());
				if (checkbox != null) {
					cookie.setMaxAge(86400);
					response.addCookie(cookie);
					System.out.println("실행" + cookie);
				} else {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					System.out.println("실행xxx");
				}
				HttpSession session = request.getSession();
				session.setAttribute("principal", principal);
				// response.sendRedirect("/home.jsp");
				request.getRequestDispatcher("/home.jsp").forward(request, response);
				// request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} else {
			String id1 = request.getParameter("id");
			Cookie cookie = new Cookie("userId", id1);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			request.setAttribute("errorMessage", "아이디 비밀번호가 틀렸습니다.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
