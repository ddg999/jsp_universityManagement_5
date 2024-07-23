package com.university.controller;

import java.io.IOException;
import java.util.List;

import com.university.model.Notice;
import com.university.model.Principal;
import com.university.repository.NoticeRepositoryImpl;
import com.university.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
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
		case "/write":
			showCreateNotice(request, response, session);
			break;
		case "/update":
			showUpdateNotice(request, response, session);
			break;
		case "/delete":
			deleteNotice(request, response, session);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void showCreateNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("staff")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/views/notice/write.jsp").forward(request, response);
	}

	// 공지사항 삭제
	private void deleteNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("staff")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		try {
			noticeRepository.deleteNotice(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	// 공지사항 수정 페이지로 이동
	private void showUpdateNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Principal principal = (Principal) session.getAttribute("principal");
		if (!principal.getUserRole().equals("staff")) {
			request.setAttribute("errorMessage", "권한이 없습니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
		try {
			Notice notice = noticeRepository.getNoticeById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("notice", notice);
			request.getRequestDispatcher("/WEB-INF/views/notice/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
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
		int noticeId = 0;
		try {
			noticeId = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
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
			createNotice(request, response);
			break;
		case "/update":
			updateNotice(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	// 공지사항 수정
	private void updateNotice(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			String category = request.getParameter("category");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Notice notice = Notice.builder().id(noticeId).category(category).title(title).content(content).build();

			noticeRepository.updateNotice(notice);
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}

	// 공지사항 작성
	private void createNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String category = request.getParameter("category");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Notice notice = Notice.builder().category(category).title(title).content(content).build();

			noticeRepository.createNotice(notice);
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근입니다");
			request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			return;
		}
	}
}
