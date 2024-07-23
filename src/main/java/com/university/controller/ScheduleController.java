package com.university.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.university.model.Principal;
import com.university.model.Schedule;
import com.university.repository.ScheduleRepositoryImpl;
import com.university.repository.interfaces.ScheduleRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/schedule/*")
public class ScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ScheduleRepository scheduleRepository;

	@Override
	public void init() throws ServletException {
		scheduleRepository = new ScheduleRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String action = request.getPathInfo();
		switch (action) {
		case "/show":
			showSchedule(request, response);
			break;
		case "/write":
			showCreateSchedule(request, response, session);
			break;
		case "/delete":
			deleteSchedule(request, response, session);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	// 학사 일정 페이지, 전체 학사일정 불러오기
	private void showSchedule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleList = scheduleRepository.getAllSchedules();

		request.setAttribute("scheduleList", scheduleList);
		request.getRequestDispatcher("/WEB-INF/views/schedule/schedule.jsp").forward(request, response);
	}

	// 학사 일정 등록 페이지
	private void showCreateSchedule(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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
		request.getRequestDispatcher("/WEB-INF/views/schedule/createSchedule.jsp").forward(request, response);
	}

	private void deleteSchedule(HttpServletRequest request, HttpServletResponse response, HttpSession session)
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

		try {
			scheduleRepository.deleteScheduleById(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect(request.getContextPath() + "/schedule/show");
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
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("staff")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		case "/write":
			createSchedule(request, response, session);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void createSchedule(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		try {
			if (!request.getParameter("startDay").startsWith("2024-")) {
				request.setAttribute("message", "현재는 2024년 일정만 추가할 수 있습니다.");
				request.getRequestDispatcher("/WEB-INF/views/schedule/createSchedule.jsp").forward(request, response);
				return;
			}

			Date startDay = Date.valueOf(request.getParameter("startDay"));
			Date endDay = Date.valueOf(request.getParameter("endDay"));
			String information = request.getParameter("information");

			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			if (Integer.parseInt(fmt.format(startDay)) > Integer.parseInt(fmt.format(endDay))) {
				request.setAttribute("message", "종료날짜가 시작날짜보다 빠릅니다");
				request.getRequestDispatcher("/WEB-INF/views/schedule/createSchedule.jsp").forward(request, response);
				return;
			}

			Schedule schedule = Schedule.builder().staffId(principal.getId()).startDay(startDay).endDay(endDay)
					.information(information).build();
			scheduleRepository.addSchedule(schedule);
			response.sendRedirect(request.getContextPath() + "/schedule/show");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}
}
