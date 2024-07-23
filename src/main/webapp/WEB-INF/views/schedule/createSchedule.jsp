<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>

<style>
.room--table {
	text-align: center;
	margin-top: 20px;
	margin: 10px;
}

.room--table td {
	padding: 10px;
	width: 300px;
}

.first--tr {
	font-weight: bold;
}

.mouth {
	background-color: #f5f5f5;
}

.line {
	
}

.container {
	margin-top: 100px;
}

.table {
	width: 800px;
}

.first--tr {
	background-color: #f7f6f6;
	font-weight: bolder;
	overflow: hidden;
	text-overflow: ellipsis;
}

.button {
	padding: 5px;
	border: 1px solid #031734;
	border-radius: 3px;
	background-color: #031734;
	color: #ccc;
	text-decoration: none;
	margin-left: 770px;
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
							href="${pageContext.request.contextPath}/schedule/show">학사일정</a></td>
					</tr>
					<c:if test="${principal.userRole eq 'staff'}">
						<tr>
							<td><a href="${pageContext.request.contextPath}/schedule/write" class="selected--menu">학사일정 등록</a></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>학사일정</h1>
		<br>
		<form action="/schedule/write" method="post">
			<table class="table">
				<thead>
					<tr class="first--tr">
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>시작날짜</td>
						<td><input type="date" name="startDay" required="required"></td>
					</tr>
					<tr>
						<td>종료날짜</td>
						<td><input type="date" name="endDay" required="required"></td>
					</tr>
					<tr>
						<td class="td">내용</td>
						<td class="info"><input type="text" name="information"
							class="textbox" required="required"></td>
					</tr>
				</tbody>
			</table>
			<%
			String message = (String) request.getAttribute("message");
			if (message != null) {
			%>
			<p style="color: red">
				<%=message%></p>
			<%
			}
			%>
			<button class="button">등록</button>
		</form>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>