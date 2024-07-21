<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layout/header.jsp"%>

<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td><a href="/notice/list">공지사항</a></td>
					</tr>
					<tr>
						<td><a href="/notice/schedule" class="selected--menu">학사일정</a></td>
					</tr>
					<c:if test="${principal.userRole eq staff}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/scheduleList.jsp">학사일정 등록</a></td>
					</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>

	<main>
		<h1>학사일정</h1>
		<div class="container">
			<table>
				<tbody>
					<c:forEach var="schedule" items="${scheduleList}">
						<tr>
							<td class="line"><fmt:formatDate value="${schedule.startDay}" pattern="MM-dd" />~<fmt:formatDate value="${schedule.endDay}" pattern="MM-dd" /></td>
							<td class="line">&nbsp;${schedule.information}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>