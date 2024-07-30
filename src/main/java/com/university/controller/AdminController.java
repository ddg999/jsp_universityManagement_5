package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.CollTuit;
import com.university.model.College;
import com.university.model.Department;
import com.university.model.Principal;
import com.university.model.Room;
import com.university.model.Subject;
import com.university.repository.AdminRepositoryImpl;
import com.university.repository.interfaces.AdminRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminRepository adminRepository;

	@Override
	public void init() throws ServletException {
		adminRepository = new AdminRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
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
		String action = request.getPathInfo();
		switch (action) {
		// 단과 대학 등록 페이지
		case "/college":
			viewCollege(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp").forward(request, response);
			break;
		// 단과 대학 삭제
		case "/collegeDelete":
			deleteCollege(request, response, session);
			break;
		// 학과 등록 페이지
		case "/department":
			viewDepartment(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp").forward(request, response);
			break;
		// 학과 삭제
		case "/departmentDelete":
			deleteDepartment(request, response, session);
			break;
		// 강의실 등록 페이지
		case "/room":
			viewRoom(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/room.jsp").forward(request, response);
			break;
		// 강의실 삭제
		case "/roomDelete":
			deleteRoom(request, response, session);
			break;
		// 강의 등록 페이지
		case "/subject":
			viewSubject(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/subject.jsp").forward(request, response);
			break;
		// 강의 삭제
		case "/subjectDelete":
			deleteSubject(request, response, session);
			break;
		// 단대별 등록금 페이지
		case "/tuition":
			viewCollTuit(request, response, session);
			request.getRequestDispatcher("/WEB-INF/views/admin/colltuition.jsp").forward(request, response);
			break;
		// 단대별 등록금 삭제
		case "/tuitionDelete":
			deleteCollTuit(request, response, session);
			break;
		default:
			break;
		}
	}

	private void deleteCollTuit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int collegeId = Integer.parseInt(request.getParameter("collegeId"));
			String collegeName = request.getParameter("collegeName");
			CollTuit collTuit = CollTuit.builder().collegeId(collegeId).collegeName(collegeName).build();
			System.out.println(collTuit);
			adminRepository.deleteCollTuit(collegeId);
			response.sendRedirect(request.getContextPath() + "/admin/tuition?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void deleteSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int subjectId = Integer.parseInt(request.getParameter("id"));
			Subject subject = Subject.builder().id(subjectId).build();
			System.out.println(subject);
			adminRepository.deleteSubject(subjectId);
			response.sendRedirect(request.getContextPath() + "/admin/subject?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void deleteRoom(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			String roomId = request.getParameter("id");
			Room room = Room.builder().id(roomId).build();
			System.out.println(room);
			adminRepository.deleteRoom(roomId);
			response.sendRedirect(request.getContextPath() + "/admin/room?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void deleteDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int departmentId = Integer.parseInt(request.getParameter("id"));
			Department department = Department.builder().id(departmentId).build();
			System.out.println(department);
			adminRepository.deleteDepartment(departmentId);
			response.sendRedirect(request.getContextPath() + "/admin/department?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void deleteCollege(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int collegeId = Integer.parseInt(request.getParameter("id"));
			College college = College.builder().id(collegeId).build();
			System.out.println(college);
			adminRepository.deleteCollege(collegeId);
			response.sendRedirect(request.getContextPath() + "/admin/college?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void viewCollTuit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String crud = request.getParameter("crud");
			System.out.println("crud " + crud);
			request.setAttribute("crud", crud);

			List<CollTuit> selectAllCollTuits = adminRepository.getAllCollTuits();
			System.out.println(selectAllCollTuits);
			request.setAttribute("collTuitList", selectAllCollTuits);

			List<College> selectAllColleges = adminRepository.getAllColleges();
			System.out.println(selectAllColleges);
			request.setAttribute("collegeList", selectAllColleges);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void viewSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String crud = request.getParameter("crud");
			System.out.println("crud " + crud);
			request.setAttribute("crud", crud);

			List<Subject> selectAllSubjects = adminRepository.getAllSubjects();
			System.out.println(selectAllSubjects);
			request.setAttribute("subjectList", selectAllSubjects);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void viewRoom(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String crud = request.getParameter("crud");
			System.out.println("crud " + crud);
			request.setAttribute("crud", crud);

			List<Room> selectAllRooms = adminRepository.getAllRooms();
			System.out.println(selectAllRooms);
			request.setAttribute("roomList", selectAllRooms);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void viewDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String crud = request.getParameter("crud");
			System.out.println("crud " + crud);
			request.setAttribute("crud", crud);

			List<Department> selectAllDepartments = adminRepository.getAllDepartments();
			System.out.println(selectAllDepartments);
			request.setAttribute("departmentList", selectAllDepartments);

			List<College> selectAllColleges = adminRepository.getAllColleges();
			System.out.println(selectAllColleges);
			request.setAttribute("collegeList", selectAllColleges);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void viewCollege(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String crud = request.getParameter("crud");
			System.out.println("crud " + crud);
			request.setAttribute("crud", crud);

			List<College> selectAllColleges = adminRepository.getAllColleges();
			System.out.println(selectAllColleges);
			request.setAttribute("collegeList", selectAllColleges);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
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
		String action = request.getPathInfo();
		switch (action) {
		// 단과 대학 등록
		case "/college":
			addCollege(request, response, session);
			break;
		// 학과 등록
		case "/department":
			addDepartment(request, response, session);
			break;
		// 학과 수정
		case "/departmentUpdate":
			updateDepartment(request, response, session);
			break;
		// 강의실 등록
		case "/room":
			addRoom(request, response, session);
			break;
		// 강의 등록
		case "/subject":
			addSubject(request, response, session);
			break;
		// 강의 수정
		case "/subjectUpdate":
			updateSubject(request, response, session);
			break;
		// 단대별 등록금
		case "/tuition":
			addCollTuition(request, response, session);
			break;
		// 단대별 등록금 수정
		case "/tuitionUpdate":
			updateCollTutition(request, response, session);
			break;
		default:
			break;
		}
	}

	private void updateCollTutition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int collegeId = Integer.parseInt(request.getParameter("collegeId"));
			String collegeName = request.getParameter("name");
			int amount = Integer.parseInt(request.getParameter("amount"));

			CollTuit collTuit = CollTuit.builder().collegeId(collegeId).collegeName(collegeName).amount(amount).build();
			adminRepository.updateCollTuit(collTuit, collegeId);
			response.sendRedirect(request.getContextPath() + "/admin/tuition?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void updateSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			int subjectId = Integer.parseInt(request.getParameter("id"));
			String subDay = request.getParameter("subDay");
			String name = request.getParameter("name");
			String roomId = request.getParameter("roomId");
			int startTime = Integer.parseInt(request.getParameter("startTime"));
			int endTime = Integer.parseInt(request.getParameter("endTime"));
			int capacity = Integer.parseInt(request.getParameter("capacity"));

			try {
				Subject subject = Subject.builder().id(subjectId).subDay(subDay).name(name).roomId(roomId)
						.startTime(startTime).endTime(endTime).capacity(capacity).build();
				// 강의 중복 검사
				List<Subject> subjectList = adminRepository.getAllSubjects();
				for (int i = 0; i < subjectList.size(); i++) {
					boolean result = subject.subjectBoolean(subject, subjectList);
					if (result == false) {
						request.setAttribute("errorMessage", "해당 시간은 사용중인 강의실입니다");
						request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
						return;
					}
				}
				adminRepository.updateSubject(subject, subjectId);
				System.out.println(subject);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "강의실 입력 오류");
				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
				return;
			}
			response.sendRedirect(request.getContextPath() + "/admin/subject?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void updateDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			String departmentName = request.getParameter("name");
			int departmentId = Integer.parseInt(request.getParameter("id"));

			try {
				Department department = Department.builder().name(departmentName).id(departmentId).build();
				adminRepository.updateDepartment(department, departmentId);
				System.out.println(department);

				// 학과 이름 중복 검사
				List<Department> departmentList = adminRepository.getAllDepartments();
				for (int i = 0; i < departmentList.size(); i++) {
					if (departmentList.get(i).getName().equals(department.getName())) {
						request.setAttribute("errorMessage", "이미 존재하는 학과입니다");
						request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
						return;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "학과 입력 오류");
				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
				return;
			}

			response.sendRedirect(request.getContextPath() + "/admin/department?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void addCollTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {

			int collegeId = Integer.parseInt(request.getParameter("collegeId"));
			String collegeName = request.getParameter("collegeName");
			int amount = Integer.parseInt(request.getParameter("amount"));
			CollTuit collTuit = CollTuit.builder().collegeId(collegeId).collegeName(collegeName).amount(amount).build();
			adminRepository.addCollTuit(collTuit);
			response.sendRedirect(request.getContextPath() + "/admin/tuition?crud=select");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	private void addSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			String subjectName = request.getParameter("name");
			int professorId = Integer.parseInt(request.getParameter("professorId"));
			String roomId = request.getParameter("roomId");
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			String type = request.getParameter("type");
			int subYear = Integer.parseInt(request.getParameter("subYear"));
			int semester = Integer.parseInt(request.getParameter("semester"));
			String subDay = request.getParameter("subDay");
			int startTime = Integer.parseInt(request.getParameter("startTime"));
			int endTime = Integer.parseInt(request.getParameter("endTime"));
			int grades = Integer.parseInt(request.getParameter("grades"));
			int capacity = Integer.parseInt(request.getParameter("capacity"));

			try {
				Subject subject = Subject.builder().name(subjectName).professorId(professorId).roomId(roomId)
						.deptId(deptId).type(type).subYear(subYear).semester(semester).subDay(subDay)
						.startTime(startTime).endTime(endTime).grades(grades).capacity(capacity).build();

				// 강의 중복 검사
				List<Subject> subjectList = adminRepository.getAllSubjects();
				for (int i = 0; i < subjectList.size(); i++) {
					boolean result = subject.subjectBoolean(subject, subjectList);
					if (result == false) {
						request.setAttribute("errorMessage", "해당 시간은 사용중인 강의실입니다");
						request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
						return;
					}
				}

				adminRepository.addSubject(subject);
				response.sendRedirect(request.getContextPath() + "/admin/subject?crud=select");

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "강의실 입력 오류");
				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 입력입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}

	}

	private void addRoom(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			String roomId = request.getParameter("id");
			int collegeId = Integer.parseInt(request.getParameter("collegeId"));
			Room room = Room.builder().id(roomId).collegeId(collegeId).build();

			// 강의실 이름 중복 검사
			List<Room> roomList = adminRepository.getAllRooms();
			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getId().equals(room.getId())) {
					request.setAttribute("errorMessage", "이미 존재하는 강의실입니다");
					request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
					return;
				}
			}
			adminRepository.addRoom(room);
			System.out.println(room);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "강의실 입력 오류");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/admin/room?crud=select");
	}

	private void addDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		try {
			String departmentName = request.getParameter("name");
			int collegeId = Integer.parseInt(request.getParameter("collegeId"));

			List<College> collegeList = adminRepository.getAllColleges();
			request.setAttribute("collegeList", collegeList);
			Department department = Department.builder().name(departmentName).collegeId(collegeId).build();

			// 학과 이름 중복 검사
			List<Department> departmentList = adminRepository.getAllDepartments();
			for (int i = 0; i < departmentList.size(); i++) {
				if (departmentList.get(i).getName().equals(department.getName())) {
					request.setAttribute("errorMessage", "이미 존재하는 학과입니다");
					request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
					return;
				}
			}
			adminRepository.addDepartment(department);
			System.out.println(department);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "학과 입력 오류");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/admin/department?crud=select");
	}

	private void addCollege(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {

		try {
			String collegeName = request.getParameter("name");

			College college = College.builder().name(collegeName).build();

			// 단과 이름 중복 검사
			List<College> collegeList = adminRepository.getAllColleges();
			for (int i = 0; i < collegeList.size(); i++) {
				if (collegeList.get(i).getName().equals(college.getName())) {
					request.setAttribute("errorMessage", "이미 존재하는 단과입니다");
					request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
					return;
				}
			}
			adminRepository.addCollege(college);
			System.out.println(college);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "단과 입력 오류");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/admin/college?crud=select");
	}

}
