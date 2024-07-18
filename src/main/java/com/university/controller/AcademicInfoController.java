package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/*")
public class AcademicInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeRepository noticeRepository;

	@Override
	public void init() throws ServletException {
		noticeRepository = new NoticeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		HttpSession session = request.getSession(false);
//		if (session == null || session.getAttribute("principal") == null) {
//			response.sendRedirect(request.getContextPath() + "/user/signin");
//			return;
//		}
		String action = request.getPathInfo();
		switch (action) {
		case "/list":
			showNoticeBoard(request, response);
			break;
		case "/read":
			showNoticeDetail(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void showNoticeBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Notice> noitceList = noticeRepository.getAllNotices();

		request.setAttribute("noticeList", noitceList);
		request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
	}

	private void showNoticeDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeId = Integer.parseInt(request.getParameter("id"));
		Notice notice = noticeRepository.getNoticeById(noticeId);

		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/read.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
