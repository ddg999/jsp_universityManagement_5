<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">

	<div>
		<img alt="" src="/resources/images/main_photo.jpg" class="main--page--img">
		<div class="d-flex justify-content-center align-items-start">
			<div class="main--page--div">
				<div class="d-flex">
					<!-- 공지사항 -->
					<div class="main--page--notice">
						<h3>
							<a href="${pageContext.request.contextPath}/notice/list">공지사항</a>
						</h3>
						<div class="main--page--split"></div>
						<table>
							<c:forEach var="notice" items="${noticeList}">
								<tr class="second-trr" onclick="location.href='${pageContext.request.contextPath}/notice/read?id=${notice.id}';">

									<td>${notice.category}</td>
									<td>${notice.title}</td>
									<td><fmt:formatDate value="${notice.createdTime}" pattern="yyyy-MM-dd"/></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div class="main--page--calander">
						<h3>
							<a href="${pageContext.request.contextPath}/notice/schedule">학사일정</a>
						</h3>
						<div class="main--page--split"></div>
						<table>
							<tbody>
								<c:forEach items="${scheduleList}" var="schedule" varStatus="loop">
									<c:if test="${loop.index < 5}">
										<!-- 5개 항목까지만 출력 -->
										<tr>
											<td>${schedule.startDay}</td>
											<td>${schedule.endDay}</td>
											<td>${schedule.information}</td>
										</tr>
									</c:if>
								</c:forEach>
						</table>

					</div>
				</div>
			</div>
			<div>
				<!-- 사용자 간단한 프로필 -->
				<div class="main--page--profile">
					<ul class="d-flex align-items-start" style="margin: 0;">
						<li><span class="material-symbols-rounded" style="margin-top: 2px;"><img src="/resources/images/user.png" alt="user"></span>&nbsp;&nbsp;
						<li style="font-weight: 600; font-size: 18px;">${principal.name}님,&nbsp;환영합니다.
					</ul>
					<hr style="width: 100%;">
					<c:choose>
						<c:when test="${principal.getUserRole().equals(\"student\")}">
							<table>
								<tr>
									<td>이메일</td>
									<td>${principal.email}</td>
								</tr>
								<!--  tr>
									<td>소속</td>
									<td>${userInfo.deptName}</td>
								</tr>
								<tr>
									<td>학기</td>
									<td>${userInfo.grade}학년&nbsp;${userInfo.semester}학기</td>
								</tr>
								<tr>
									<td>학적상태</td>
									<td>${currentStatus}</td>
								</tr> -->
							</table>
							<div class="profile--button--div">
								<a href="${pageContext.request.contextPath}/info/student"><button>마이페이지</button></a> <a href="${pageContext.request.contextPath}/user/login"><button>로그아웃</button></a>
							</div>
						</c:when>
						<c:when test="${principal.getUserRole().equals(\"staff\")}">
							<table>
								<tr>
									<td>이메일</td>
									<td>${principal.email}</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>교직원</td>
								</tr>
							</table>
							<div class="profile--button--div">
								<a href="${pageContext.request.contextPath}/info/staff"><button>마이페이지</button></a> <a href="${pageContext.request.contextPath}/user/login"><button>로그아웃</button></a>
							</div>
						</c:when>
						<c:otherwise>
							<table>
								<tr>
									<td>이메일</td>
									<td>${principal.email}</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>${userInfo.deptName}교수</td>
								</tr>
							</table>
							<div class="profile--button--div">
								<a href="${pageContext.request.contextPath}/info/professor"><button>마이페이지</button></a> <a href="${pageContext.request.contextPath}/user/login"><button>로그아웃</button></a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<br>
				<c:if test="${principal.getUserRole().equals('staff')}">
				<c:choose>
					<c:when test="${breakAppSize > 0}">
						<div class="main--page--info">
							<ul class="d-flex align-items-start" style="margin: 0;">
								<li><span class="material-symbols-rounded" style="margin-top: 2px; color: #fccb03;"><img src="/resources/images/bell.png" alt="업무 알림"></span>&nbsp;&nbsp;
								<li style="font-weight: 600; font-size: 18px;">업무 알림
							</ul>
							<p>
								<a href="${pageContext.request.contextPath}/break/list/staff">처리되지 않은 휴학 신청이 존재합니다.</a>
							</p>
						</div>
					</c:when>
					<c:when test="${breakAppSize == 0}">
						<div class="main--page--info">
							<ul class="d-flex align-items-start" style="margin: 0;">
								<li><span class="material-symbols-rounded" style="margin-top: 2px;">notifications</span>&nbsp;&nbsp;
								<li style="font-weight: 600; font-size: 18px;">업무 알림
							</ul>
							<p>처리해야 할 업무가 없습니다.</p>
						</div>
					</c:when>
				</c:choose>
				</c:if>
			</div>
		</div>
	</div>
</div>
<%@ include file="WEB-INF/views/layout/footer.jsp"%>