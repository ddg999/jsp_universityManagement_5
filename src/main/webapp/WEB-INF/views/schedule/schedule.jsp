<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/admin.css">
<style>
.room--table {
	width: 100%;
	text-align: center;
	margin-top: 20px;
	margin: 10px;
	text-align: center;
}

.room--table td {
	padding: 10px;
}

.first--tr {
	font-weight: bold;
}

.month {
	background-color: #f5f5f5;
	border-bottom: 1px solid #666;
}

.line {
	border-bottom: 1px solid #666;
}

.container {
	margin-top: 100px;
}

.schedule--delete {
	border: 1px solid;
	border-radius: 15px;
	color: white;
}

.schedule--delete a {
	color: #fff;
	background-color: #b35;
	display: block;
}

.schedule--delete a:hover {
	color: blue;
}
</style>
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td><a href="${pageContext.request.contextPath}/notice/list">공지사항</a></td>
					</tr>
					<tr>
						<td><a
							href="${pageContext.request.contextPath}/schedule/show"
							class="selected--menu">학사일정</a></td>
					</tr>
					<c:if test="${principal.userRole eq 'staff'}">
						<tr>
							<td><a
								href=${pageContext.request.contextPath}/schedule/write>학사일정
									등록</a></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>

	<main>
		<h1>학사일정</h1>
		<div class="container">
			<table class="room--table">
				<tbody>
					<c:forEach var="schedule" items="${scheduleList}">
						<tr>
							<td class="line"><fmt:formatDate
									value="${schedule.startDay}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate
									value="${schedule.endDay}" pattern="yyyy.MM.dd" /></td>
							<td class="line">&nbsp;${schedule.information}</td>
							<c:if test="${principal.userRole eq 'staff'}">
								<td class="schedule--delete"><a
									href="${pageContext.request.contextPath}/schedule/delete?id=${schedule.id}">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>