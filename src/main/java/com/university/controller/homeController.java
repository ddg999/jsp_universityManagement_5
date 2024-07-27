package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.ScheduleRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;
import com.university.repository.interfaces.ScheduleRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class homeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public homeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NoticeRepository noticeRepository = new NoticeRepositoryImpl();
		ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl();
		
		List<Schedule> scheduleList = scheduleRepository.getAllSchedules();
        List<Notice> noticeList = noticeRepository.getAllNotices(5, 1);

		 request.setAttribute("breakAppSize", 2);
		request.setAttribute("scheduleList", scheduleList);
		request.setAttribute("noticeList", noticeList);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
		
	}


}
