package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Department;
import com.university.model.Principal;
import com.university.model.SubTime;
import com.university.model.SugangSubject;
import com.university.repository.SugangRepositoryImpl;
import com.university.repository.interfaces.SugangRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SugangRepository sugangRepository;

	@Override
	public void init() throws ServletException {
		sugangRepository = new SugangRepositoryImpl();
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
		// 강의 시간표 조회 페이지
		case "/subject":
			showSugangSubject(request, response, session);
			break;
		// 강의 시간표 검색 페이지
		case "/subject/search":
			showSugangSubjectSearch(request, response, session);
			break;
		// 예비 수강 신청 페이지
		case "/preRegist":
			showPreRegist(request, response, session);
			break;
		// 예비 수강 신청 검색 페이지
		case "/preRegist/search":
			showPreRegistSearch(request, response, session);
			break;
		// 예비 수강 신청 내역 페이지
		case "/preRegist/result":
			showPreRegistResult(request, response, session);
			break;
		// 수강 신청 페이지
		case "/regist":
			showRegist(request, response, session);
			break;
		// 수강 신청 내역 조회 페이지
		case "/registResult":
			request.getRequestDispatcher("/WEB-INF/views/sugang/registResult.jsp").forward(request, response);
			break;
		// 수강 신청 기간 설정 페이지
		case "/period":
			request.getRequestDispatcher("/WEB-INF/views/sugang/updateperiod.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	// 수강 신청
	private void showRegist(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
		List<SugangSubject> subjectList = sugangRepository.getAllSugangSubject(principal.getId(), pageSize, offset);
		int totalSubjects = sugangRepository.getTotalSubjectsCount();
		List<Department> deptList = sugangRepository.getAllDepartment();

		int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);
		request.setAttribute("subjectCount", totalSubjects);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/regist.jsp").forward(request, response);
	}

	// 예비 수강 신청
	private void showPreRegist(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
		List<SugangSubject> subjectList = sugangRepository.getAllSugangSubject(principal.getId(), pageSize, offset);
		int totalSubjects = sugangRepository.getTotalSubjectsCount();
		List<Department> deptList = sugangRepository.getAllDepartment();
		int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);

		request.setAttribute("subjectCount", totalSubjects);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/preRegist.jsp").forward(request, response);
	}

	// 예비 수강 신청 검색 TODO 신청,취소 했을 때 페이징처리
	private void showPreRegistSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String deptName = request.getParameter("deptName");

			int searchTotalSubjects = sugangRepository.getSearchSugangSubjectCount(name, type, deptName);
			List<Department> deptList = sugangRepository.getAllDepartment();
			List<SugangSubject> subjectList = sugangRepository.getSearchSugangSubjects(principal.getId(), name, type,
					deptName, pageSize, offset);
			int totalPages = (int) Math.ceil((double) searchTotalSubjects / pageSize);

			request.setAttribute("subjectCount", searchTotalSubjects);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("deptList", deptList);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("selectedName", name);
			request.setAttribute("selectedType", type);
			request.setAttribute("selectedDeptName", deptName);
			request.getRequestDispatcher("/WEB-INF/views/sugang/preRegist.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	// 예비 수강 신청 내역
	private void showPreRegistResult(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
		List<SugangSubject> subjectList = sugangRepository.getPreSugangResult(principal.getId(), pageSize, offset);
		int totalSubjects = sugangRepository.getTotalSubjectsCount();
		List<Department> deptList = sugangRepository.getAllDepartment();
		int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);

		request.setAttribute("subjectCount", totalSubjects);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/preRegistResult.jsp").forward(request, response);

	}

	// 수강신청 - 강의시간표 조회
	private void showSugangSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
		List<SugangSubject> subjectList = sugangRepository.getAllSugangSubject(principal.getId(), pageSize, offset);
		List<Department> deptList = sugangRepository.getAllDepartment();
		int totalSubjects = sugangRepository.getTotalSubjectsCount();
		int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);

		request.setAttribute("subjectCount", totalSubjects);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/sugangSubject.jsp").forward(request, response);
	}

	// 수강신청 - 강의시간표 검색
	private void showSugangSubjectSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("student")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		int page = 1;
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
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String deptName = request.getParameter("deptName");

			int searchTotalSubjects = sugangRepository.getSearchSugangSubjectCount(name, type, deptName);
			List<Department> deptList = sugangRepository.getAllDepartment();
			List<SugangSubject> subjectList = sugangRepository.getSearchSugangSubjects(principal.getId(), name, type,
					deptName, pageSize, offset);
			int totalPages = (int) Math.ceil((double) searchTotalSubjects / pageSize);

			request.setAttribute("subjectCount", searchTotalSubjects);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("deptList", deptList);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("selectedName", name);
			request.setAttribute("selectedType", type);
			request.setAttribute("selectedDeptName", deptName);
			request.getRequestDispatcher("/WEB-INF/views/sugang/sugangSubject.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
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
		String action = request.getPathInfo();
		switch (action) {
		case "/pre/add":
			addPreStuSub(request, response, session);
			break;
		case "/pre/delete":
			deletePreStuSub(request, response, session);
			break;
		default:
			break;
		}
	}

	// 수강 신청 추가
	private void addPreStuSub(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Principal principal = (Principal) session.getAttribute("principal");
			if (!(principal.getId() == studentId)) {
				request.setAttribute("errorMessage", "권한이 없습니다");
				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
				return;
			}

			// 시간표 중복방지 로직
			List<SubTime> stuSubTimeList = sugangRepository.getStuSubTime(studentId);
			SubTime subTime = sugangRepository.getSubTime(subjectId);
			for (SubTime stuSubTime : stuSubTimeList) {
				if (subTime.getSubDay().equals(stuSubTime.getSubDay())
						&& (subTime.getStartTime() < stuSubTime.getEndTime())
						&& subTime.getEndTime() > stuSubTime.getStartTime()) {
					request.setAttribute("message", "이미 해당 시간에 수강 중인 강의가 있습니다. 새로운 시간대를 선택해주세요!<br>선택한 강의 : "
							+ subTime.getName() + "<br> 중복된 강의 : " + stuSubTime.getName());
					showPreRegist(request, response, session);
					return;
				}
			}

			sugangRepository.addRegist(studentId, subjectId);
			response.sendRedirect(request.getContextPath() + "/sugang/preRegist");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	// 수강 신청 삭제
	private void deletePreStuSub(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Principal principal = (Principal) session.getAttribute("principal");
			if (!(principal.getId() == studentId)) {
				request.setAttribute("errorMessage", "권한이 없습니다");
				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
				return;
			}
			sugangRepository.deleteRegist(studentId, subjectId);
			if (request.getParameter("call").equals("list")) {
				response.sendRedirect(request.getContextPath() + "/sugang/preRegist");
			} else {
				response.sendRedirect(request.getContextPath() + "/sugang/preRegist/result");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

}
