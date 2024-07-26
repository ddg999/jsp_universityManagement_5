package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Principal;
import com.university.model.Schedule;
import com.university.model.User;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.ScheduleRepositoryImpl;
import com.university.repository.UserRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;
import com.university.repository.interfaces.ScheduleRepository;
import com.university.repository.interfaces.UserRepository;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class homeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository userRepository;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		userRepository = new UserRepositoryImpl();
	}
	
	
	public homeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("여기로 왔는가????111111111111111111111111111111");
		HttpSession session = request.getSession();
		String userRole = (String) session.getAttribute("userRole");
		
		System.out.println("여기로 왔는가????22222222222222222222222222");
		

		NoticeRepository noticeRepository = new NoticeRepositoryImpl();
		ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl();
		
		List<Schedule> scheduleList = scheduleRepository.getAllSchedules();
        List<Notice> noticeList = noticeRepository.getAllNotices(5, 1);

        

        System.out.println("User Role: " + userRole);
        System.out.println("Break App Size: " + request.getAttribute("breakAppSize"));
		 request.setAttribute("breakAppSize", 2);
		request.setAttribute("scheduleList", scheduleList);
		request.setAttribute("noticeList", noticeList);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
		
	}


}
