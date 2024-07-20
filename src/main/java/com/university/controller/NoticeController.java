package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
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
		case "/search":
			showNoticeSearch(request, response);
			break;
		case "/read":
			showNoticeDetail(request, response);
			break;
		case "/schedule":
			showSchedule(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	// 공지사항 페이지, 전체 공지사항 불러오기
	private void showNoticeBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Notice> noticeList = noticeRepository.getAllNotices();
		request.setAttribute("noticeList", noticeList);
		request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
	}

	// 공지사항 검색
	// TODO 페이징
	private void showNoticeSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			String keyword = request.getParameter("keyword");
			if (type.equals("title")) {
				List<Notice> noticeList = noticeRepository.getNoticesByTitle(keyword);
				request.setAttribute("noticeList", noticeList);
				request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
			} else if (type.equals("keyword")) {
				List<Notice> noticeList = noticeRepository.getNoticesByTitleOrContent(keyword);
				request.setAttribute("noticeList", noticeList);
				request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
			} else {
				// TODO type이 잘못쓰였을때 에러처리
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 공지사항 상세보기
	private void showNoticeDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeId = Integer.parseInt(request.getParameter("id"));
		Notice notice = noticeRepository.getNoticeById(noticeId);

		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/read.jsp").forward(request, response);
	}

	private void showSchedule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleList = noticeRepository.getAllSchedule();

		request.setAttribute("scheduleList", scheduleList);
		request.getRequestDispatcher("/WEB-INF/views/schedule/schedule.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
