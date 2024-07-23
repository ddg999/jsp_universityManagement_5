package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Schedule;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/schedule/*")
public class ScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeRepository noticeRepository;

	@Override
	public void init() throws ServletException {
		noticeRepository = new NoticeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/show":
			showSchedule(request, response);
			break;
		case "/list":
			showScheduleList(request, response);
			break;
		default:
			break;
		}
	}

	// 학사 일정 페이지, 전체 학사일정 불러오기
	private void showSchedule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleList = noticeRepository.getAllSchedule();

		request.setAttribute("scheduleList", scheduleList);
		request.getRequestDispatcher("/WEB-INF/views/schedule/schedule.jsp").forward(request, response);
	}

	// 학사 일정 등록 페이지
	private void showScheduleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleList = noticeRepository.getAllSchedule();

		request.setAttribute("scheduleList", scheduleList);
		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
