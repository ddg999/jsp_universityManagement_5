<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/document.css">

<style>
.document--layout h3 {
	font-weight: 600;
}

.document--layout>p {
	font-weight: 600;
	margin-bottom: 30px;
}
</style>

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>MY</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/info/student" >내 정보 조회</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/info/password">비밀번호 변경</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/break/list">휴학 내역 조회</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/tuition/payment" class="selected--menu">등록금 납부 고지서</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/tuition/list">등록금 납부 내역</a></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>등록금 납부 고지서</h1>
		<div class="split--div"></div>

		<div class="d-flex flex-column align-items-center" style="width: 100%">
	<c:choose>
	<c:when test="${tuition.studentName == null}">
		<p class="no--list--p">등록금 납부 고지서가 없습니다.</p>
	</c:when>
	<c:otherwise>
			<div class="document--layout">
				<h3>등록금 고지서</h3>
				<p>${"2024"}년도${tuition.semester}학기</p>
				<table class="tuition--payment--table" border="1">
					<thead>
						<tr>
							<th>단 과 대</th>
							<td>${tuition.collegeName}</td>
							<th>학 과</th>
							<td>${tuition.deptName}</td>
						</tr>
						<tr>
							<th>학 번</th>
							<td>${tuition.studentId}</td>
							<th>성 명</th>
							<td>${tuition.studentName}</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th colspan="2">장 학 유 형</th>
							<td colspan="2">${tuition.scholarshipId}유형</td>
						</tr>
						<tr>
							<th colspan="2">등 록 금</th>
							<td colspan="2">${tuition.tuitionAmount}</td>
						</tr>
						<tr>
							<th colspan="2">장 학 금</th>
							<td colspan="2">${tuition.scholarshipAmount}</td>
						</tr>
						<tr>
							<th colspan="2">납 부 금</th>
							<td colspan="2">${tuition.payment}</td>
						</tr>
						<tr>
							<th colspan="2">납 부 계 좌</th>
							<td colspan="2">그린은행 483-531345-536</td>
						</tr>
						<tr>
							<th colspan="2">납 부 기 간</th>
							<td colspan="2">~ 2023.05.02</td>
						</tr>

					</tbody>
				</table>
			</div>

			<c:choose>
				<c:when test="${tuition1.status == 1}">
					<p class="no--list--p">이번 학기 등록금 납부가 완료되었습니다.</p>
				</c:when>
				<c:otherwise>
					<form action="/tuition/payment" method="post">
						<button type="submit" class="btn btn-dark" onclick="return confirm('등록금을 납부하시겠습니까?')">납부하기</button>
					</form>
				</c:otherwise>
			</c:choose>

	</c:otherwise>
	</c:choose>
		</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>