package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Schedule;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
		int page = 1; // 기본 페이지 번호
		int pageSize = 10; // 한 페이지당 보여질 게시글 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산( offset 값 계산)
		List<Notice> noticeList = noticeRepository.getAllNotices(pageSize, offset);
		// 전체 게시글 수 조회
		int totalNotices = noticeRepository.getTotalNoticesCount();
		// 총 페이지 수 계산 --> [1][2][3][...]
		int totalPages = (int) Math.ceil((double) totalNotices / pageSize);

		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("noticeList", noticeList);
		request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
	}

	// 공지사항 검색
	private void showNoticeSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Notice> noticeList = null;
		int totalNotices = 0;
		String type = null;
		String keyword = null;

		int page = 1; // 기본 페이지 번호
		int pageSize = 10; // 한 페이지당 보여질 게시글 수
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
			type = request.getParameter("type");
			keyword = request.getParameter("keyword");
			if (type.equals("title")) {
				noticeList = noticeRepository.getNoticesByTitle(keyword, pageSize, offset);
				totalNotices = noticeRepository.getTotalNoticesCountByTitle(keyword);
			} else if (type.equals("keyword")) {
				noticeList = noticeRepository.getNoticesByTitleOrContent(keyword, pageSize, offset);
				totalNotices = noticeRepository.getTotalNoticesCountByTitleOrContent(keyword);
			}
			int totalPages = (int) Math.ceil((double) totalNotices / pageSize);
			request.setAttribute("type", type);
			request.setAttribute("keyword", keyword);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("noticeList", noticeList);
			request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 공지사항 상세보기
	private void showNoticeDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int noticeId = Integer.parseInt(request.getParameter("id"));
		Notice notice = noticeRepository.getNoticeById(noticeId);

		Cookie viewCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getValue().equals("notice:" + noticeId)) {
					viewCookie = cookies[i];
				}
			}
		}
		if (viewCookie == null) {
			try {
				Cookie newCookie = new Cookie("viewCookie", "notice:" + noticeId);
				newCookie.setMaxAge(1800);
				response.addCookie(newCookie);

				noticeRepository.updateNoticeView(noticeId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!viewCookie.getValue().equals("notice:" + noticeId)) {
			noticeRepository.updateNoticeView(noticeId);
		}

		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/read.jsp").forward(request, response);
	}

	// 학사 일정 페이지, 전체 학사일정 불러오기
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
