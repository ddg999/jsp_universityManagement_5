package com.university.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Random;

import com.university.filter.PasswordHashing;
import com.university.model.Principal;
import com.university.model.Professor;
import com.university.model.Staff;
import com.university.model.Student;
import com.university.model.User;
import com.university.repository.ProfessorRepositoryImpl;
import com.university.repository.StudentRepositoryImpl;
import com.university.repository.UserRepositoryImpl;
import com.university.repository.interfaces.ProfessorRepository;
import com.university.repository.interfaces.StudentRepository;
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
	private StudentRepository studentRepository;
	private ProfessorRepository professorRepository;

	@Override
	public void init() throws ServletException {
		userRepository = new UserRepositoryImpl();
		studentRepository = new StudentRepositoryImpl();
		professorRepository = new ProfessorRepositoryImpl();
	}

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String action = request.getPathInfo();
		System.out.println(action);
		switch (action) {
		case "/findId":
			request.getRequestDispatcher("/WEB-INF/views/find/findid.jsp").forward(request, response);
			break;
		case "/findPassword":
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
			break;
		case "/login":
			if (session != null) {
				session.invalidate();
			}
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			break;
		case "/studentList":
			studentAllView(request, response, session);
			break;
		case "/professorList":
			professorAllView(request, response, session);
			break;
		case "/student":
			request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
			break;
		case "/studentList/search":
			studentSearch(request, response);
			break;
		case "/professor":
			request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
			break;
		case "/professorList/search":
			professorSearch(request, response);
			break;
		case "/staff":
			request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
			break;
		case "/logout":
			logout(request, response, session);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/user/login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();

		switch (action) {
		// 로그인 기능 처리
		case "/login":
			handleSignin(request, response);
			break;
		case "/home":
			request.getRequestDispatcher("/home.jsp").forward(request, response);
			break;
		case "/findId":
			handleFindId(request, response);
			break;

		case "/findPassword":
			handleFindPassword(request, response);
			break;

		case "/student":
			addStudent(request, response);
			break;

		case "/professor":
			addProfessor(request, response);
			break;

		case "/staff":
			addStaff(request, response);
			break;

		default:
			break;
		}
	}

	/**
	 * 교수 이름 검색 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void professorSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("됨요?????");
		List<Professor> professorList = null;
		// int totalStudent = 0;
		String professorName = null;

		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산( offset 값 계산)

		try {
			professorName = request.getParameter("professorName");

			professorList = professorRepository.getProfessorName(professorName, pageSize, offset);
			int totalProfessor = professorRepository.getTotalgetProfessorNameCount(professorName);
			System.out.println("totalStudent1 : " + totalProfessor);
			int totalPages = (int) Math.ceil((double) totalProfessor / pageSize);

			request.setAttribute("keyword", professorName);
			request.setAttribute("professorList", professorList);
			request.setAttribute("listCount", totalPages);
			request.setAttribute("totalStudent", totalProfessor);
			request.setAttribute("index", page);

			request.getRequestDispatcher("/WEB-INF/views/user/professorlist.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 학생 이름 검색 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void studentSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Student> studentList = null;
		// int totalStudent = 0;
		String studentName = null;

		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산( offset 값 계산)

		try {
			studentName = request.getParameter("studentName");

			studentList = studentRepository.getStudentId(studentName, pageSize, offset);
			int totalStudent1 = studentRepository.getTotalStudentNameCount(studentName);
			System.out.println("totalStudent1 : " + totalStudent1);
			int totalPages = (int) Math.ceil((double) totalStudent1 / pageSize);

			request.setAttribute("keyword", studentName);
			request.setAttribute("studentList", studentList);
			request.setAttribute("listCount", totalPages);
			request.setAttribute("totalStudent", totalStudent1);
			request.setAttribute("index", page);

			request.getRequestDispatcher("/WEB-INF/views/user/studentlist.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 교수 명단 조회
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void professorAllView(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
			e.printStackTrace();
		}

		int offset = (page - 1) * pageSize;
		List<Professor> professorList = userRepository.getAllProfessor(pageSize, offset);

		int totalBoards = userRepository.getTotalProfessorCount();
		int totalPage = (int) Math.ceil((double) totalBoards / pageSize);

		request.setAttribute("professorList", professorList);
		request.setAttribute("listCount", totalPage);
		request.setAttribute("currentPage", page);

		request.getRequestDispatcher("/WEB-INF/views/user/professorlist.jsp").forward(request, response);
	}

	/**
	 * 학생 명단 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void studentAllView(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
			e.printStackTrace();
		}

		int offset = (page - 1) * pageSize;
		List<Student> studentList = userRepository.getAllBoards(pageSize, offset);

		int totalBoards = userRepository.getTotalBoardCount();
		int totalPage = (int) Math.ceil((double) totalBoards / pageSize);

		request.setAttribute("studentList", studentList);
		request.setAttribute("listCount", totalPage);
		request.setAttribute("currentPage", page);

//		if(session != null) {
//			Principal principal = (Principal)session.getAttribute("principal");
//			if(principal != null) {
//				request.setAttribute("id", principal.getId());
//			}
//		}
		request.getRequestDispatcher("/WEB-INF/views/user/studentlist.jsp").forward(request, response);
	}

	/**
	 * 직원 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void addStaff(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String name = request.getParameter("name");
			Date birthDate = Date.valueOf(request.getParameter("birthDate"));
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");

			// 방어적 코드 작성 및 예외처리
			if (name == null || name.trim().isEmpty()) {
				request.setAttribute("message", "이름을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			} else if (birthDate == null) {
				request.setAttribute("message", "생년월일을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			} else if (gender == null || gender.trim().isEmpty()) {
				request.setAttribute("message", "성별을 선택해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			} else if (address == null || address.trim().isEmpty()) {
				request.setAttribute("message", "주소를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			} else if (tel == null || tel.trim().isEmpty()) {
				request.setAttribute("message", "전화번호를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			} else if (email == null || email.trim().isEmpty()) {
				request.setAttribute("message", "이메일을 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
				return;
			}

			Staff staff = Staff.builder().name(name).birthDate(birthDate).gender(gender).address(address).tel(tel)
					.email(email).build();

			userRepository.addStaff(staff);
			int id = userRepository.getStaffIdByNameAndEmailForUser(name, email);
			String password = request.getParameter("birthDate").replaceAll("-", "");
			userRepository.addUser(User.builder().id(id).password(password).userRole("staff").build());
			request.setAttribute("message", "등록 완료 ㅡ 아이디 : " + id + ", 초기비밀번호 : 생년월일 8자리");
			request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "입력된 정보가 잘못되었습니다.");
			request.getRequestDispatcher("/WEB-INF/views/user/createstaff.jsp").forward(request, response);
		}
	}

	/**
	 * 교수 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void addProfessor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String name = request.getParameter("name");
			Date birthDate = Date.valueOf(request.getParameter("birthDate"));
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			String deptIdStr = request.getParameter("deptId");

			// 방어적 코드 작성 및 예외처리
			if (name == null || name.trim().isEmpty()) {
				request.setAttribute("message", "이름을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (birthDate == null) {
				request.setAttribute("message", "생년월일을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (gender == null || gender.trim().isEmpty()) {
				request.setAttribute("message", "성별을 선택해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (address == null || address.trim().isEmpty()) {
				request.setAttribute("message", "주소를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (tel == null || tel.trim().isEmpty()) {
				request.setAttribute("message", "전화번호를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (email == null || email.trim().isEmpty()) {
				request.setAttribute("message", "이메일을 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			} else if (deptIdStr == null || deptIdStr.trim().isEmpty()) {
				request.setAttribute("message", "과 ID를 선택해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
				return;
			}

			Professor professor = Professor.builder().name(name).birthDate(birthDate).gender(gender).address(address)
					.tel(tel).email(email).deptId(deptId).build();

			userRepository.addProfessor(professor);
			int id = userRepository.getProfessorIdByNameAndEmailForUser(name, email);
			String password = request.getParameter("birthDate").replaceAll("-", "");
			userRepository.addUser(User.builder().id(id).password(password).userRole("professor").build());
			request.setAttribute("message", "등록 완료 ㅡ 아이디 : " + id + ", 초기비밀번호 : 생년월일 8자리");
			request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "입력된 정보가 잘못되었습니다.");
			request.getRequestDispatcher("/WEB-INF/views/user/createprofessor.jsp").forward(request, response);
		}
	}

	/**
	 * 학생 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void addStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {

			String name = request.getParameter("name");
			Date birthDate = Date.valueOf(request.getParameter("birthDate"));
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			String deptIdStr = request.getParameter("deptId");
			Date entranceDate = Date.valueOf(request.getParameter("entranceDate"));
			// 방어적 코드 작성 및 예외처리
			if (name == null || name.trim().isEmpty()) {
				request.setAttribute("message", "이름을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/student.jsp").forward(request, response);
				return;
			} else if (birthDate == null) {
				request.setAttribute("message", "생년월일을 입력해주세요.");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (gender == null || gender.trim().isEmpty()) {
				request.setAttribute("message", "성별을 선택해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (address == null || address.trim().isEmpty()) {
				request.setAttribute("message", "주소를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (tel == null || tel.trim().isEmpty()) {
				request.setAttribute("message", "전화번호를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (email == null || email.trim().isEmpty()) {
				request.setAttribute("message", "이메일을 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (deptIdStr == null || deptIdStr.trim().isEmpty()) {
				request.setAttribute("message", "과 ID를 입력해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			} else if (entranceDate == null) {
				request.setAttribute("message", "입학날짜를 선택해주세요");
				request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
				return;
			}

			Student student = Student.builder().name(name).birthDate(birthDate).gender(gender).address(address).tel(tel)
					.email(email).deptId(deptId).entranceDate(entranceDate).build();

			userRepository.addStudent(student);
			int id = userRepository.getStudentIdByNameAndEmailForUser(name, email);
			String password = request.getParameter("birthDate").replaceAll("-", "");
			userRepository.addUser(User.builder().id(id).password(password).userRole("student").build());
			request.setAttribute("message", "등록 완료 ㅡ 아이디 : " + id + ", 초기비밀번호 : 생년월일 8자리");
			request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "입력된 정보가 잘못되었습니다.");
			request.getRequestDispatcher("/WEB-INF/views/user/createstudent.jsp").forward(request, response);
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

	// 비밀번호 찾기
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

		try {
			int id = Integer.parseInt(request.getParameter("id"));
			if (userRole.equals("student")) {
				userPassword = userRepository.getStudentPasswordByNameAndIdAndEmail(name, id, email);
			} else if (userRole.equals("staff")) {
				userPassword = userRepository.getStaffPasswordByNameAndIdAndEmail(name, id, email);
			} else if (userRole.equals("professor")) {
				userPassword = userRepository.getProfessorPasswordByNameAndIdAndEmail(name, id, email);
			}
			userPassword = randomPassword(6);
			String hashedPassword = PasswordHashing.hashPassword(userPassword);
			userRepository.updateUserPassword(hashedPassword, id);
			request.setAttribute("userPassword", userPassword);
			request.setAttribute("name", name);
			request.getRequestDispatcher("/WEB-INF/views/find/findpasswordresult.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "입력된 정보가 틀렸습니다.");
			request.getRequestDispatcher("/WEB-INF/views/find/findpassword.jsp").forward(request, response);
		}
	}

	// 아이디 찾기
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

	// 로그인
	private void handleSignin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {

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
			String password = request.getParameter("password");
			String getHashedPassword = userRepository.getPasswordById(id);
			if (PasswordHashing.checkPassword(password, getHashedPassword)) {
				User user = userRepository.getUserByIdAndPassword(id, getHashedPassword);
				System.out.println("user : " + user);
				Principal principal = null;
				if (user != null) {
					if (user.getUserRole().equals("student")) {
						principal = userRepository.getStudent(user);
					} else if (user.getUserRole().equals("staff")) {
						principal = userRepository.getStaff(user);
					} else if (user.getUserRole().equals("professor")) {
						principal = userRepository.getProfessor(user);
					}
					System.out.println("principal : " + principal);
					if (principal != null && principal.getPassword().equals(getHashedPassword)) {
						String checkbox = request.getParameter("rememberId");
						System.out.println("checkbox : " + checkbox);
//				String id1 = request.getParameter("userId");
//				System.out.println("id1 : " + id1);
						response.setCharacterEncoding("UTF-8");
						PrintWriter out = response.getWriter();
						Cookie cookie = new Cookie("userId", request.getParameter("id"));
						System.out.println(checkbox);
						System.out.println(cookie.getName());
						if (checkbox != null) {
							cookie.setMaxAge(86400);
							response.addCookie(cookie);
							System.out.println("실행" + cookie);
						} else {
							cookie.setMaxAge(0);
							response.addCookie(cookie);
						}
						HttpSession session = request.getSession();
						session.setAttribute("principal", principal);
						System.out.println(principal.toString());
						response.sendRedirect("/home");
						// List<Schedule> scheduleList = scheduleRepository.getAllSchedules();
						// List<Notice> noticeList = noticeRepository.getAllNotices(5 , 1);

//				request.setAttribute("breakAppSize", 2);
//				request.setAttribute("scheduleList", scheduleList);
//				request.setAttribute("noticeList", noticeList);

						// request.getRequestDispatcher("/home.jsp").forward(request, response);
						// request.getRequestDispatcher("/login.jsp").forward(request, response);
					}
				} else {
					String id1 = request.getParameter("id");
					Cookie cookie = new Cookie("userId", id1);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					System.out.println(cookie.getName() + "dsasdasadsda");
					request.setAttribute("errorMessage", "아이디 혹은 비밀번호가 틀렸습니다.");
					request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
				}
			} else {
				String id1 = request.getParameter("id");
				Cookie cookie = new Cookie("userId", id1);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				System.out.println(cookie.getName() + "dsasdasadsda");
				request.setAttribute("errorMessage", "아이디 혹은 비밀번호가 틀렸습니다.");
				request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String id1 = request.getParameter("id");
			Cookie cookie = new Cookie("userId", id1);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			System.out.println(cookie.getName() + "dsasdasadsda");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
