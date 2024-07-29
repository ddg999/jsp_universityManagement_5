<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../css/admin.css">

<style>
	input[type="submit"] {
	background-color: gray;
	border-radius: 5px;
	border: hidden;
	color: #fff;
	text-decoration: none;
	margin: 5px;
	padding: 3px;
}
</style>
<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>MY</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">

			<table class="sub--menu--table" border="1">
				<c:choose>
					<c:when test="${principal.userRole.equals(\"student\")}">
						<tr>
							<td><a href="${pageContext.request.contextPath}/info/student">내 정보 조회</a></td>
						</tr>
					</c:when>
					<c:when test="${principal.userRole.equals(\"professor\")}">
						<tr>
							<td><a href="${pageContext.request.contextPath}/info/professor">내 정보 조회</a></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><a href="${pageContext.request.contextPath}/info/staff">내 정보 조회</a></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td><a href="${pageContext.request.contextPath}/info/password" class="selected--menu">비밀번호 변경</a></td>
				</tr>
				<c:if test="${principal.userRole.equals(\"student\")}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/break/list">휴학 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/tuition/list">등록금 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>비밀번호 변경</h1>
		<div class="split--div"></div>
		<form action="/info/password" method="post" class="form--container">
			<input type="hidden" name="_method" value="put" />
			<table class="table--container">
				<tr>
					<td><label for="beforePassword">현재 비밀번호</label></td>
					<td><input type="password" name="beforePassword"
						class="input--box" id="beforePassword"></td>
				</tr>
				<tr>
					<td><label for="afterPassword">변경할 비밀번호</label></td>
					<td><input type="password" name="afterPassword"
						class="input--box" id="afterPassword"></td>
				</tr>
				<tr>
					<td><label for="passwordCheck">변경할 비밀번호 확인</label></td>
					<td><input type="password" name="passwordCheck"
						class="input--box" id="passwordCheck"></td>
				</tr>
				<tr>
					<td>
						<%
						String message = (String) request.getAttribute("message");
						if (message != null) {
						%>
						<p style="color: red">
							<%=message%></p> <%
 }
 %>
					
					<td>
				</tr>
			</table>
			<c:choose>
				<c:when test="${principal.getUserRole().equals(\"staff\")}">
					<div class="button--container">
						<input type="submit" value="입력">
					</div>
				</c:when>
				<c:otherwise>
					<br>
					<button type="submit" class="btn btn-dark update--button">수정하기</button>
				</c:otherwise>
			</c:choose>
		</form>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>