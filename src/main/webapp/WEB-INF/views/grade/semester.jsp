<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="../../resources/css/subject.css">
<%@ include file="../layout/header.jsp"%>

<style>
.button {
	margin-left: 20px;
}

.sub--filter form div {
	padding: 13px 10px;
}

.sub--list--table th {
	padding: 1px 25px;
}
</style>
<!-- 학기별 성적 조회 -->

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>성적</h2>
		</div>
		<!-- 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/grade/thisSemester">금학기 성적 조회</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/grade/semester" class="selected--menu">학기별 성적 조회</a></td>
				</tr>
				<!--<tr>
					<td><a href="${pageContext.request.contextPath}/grade/total">누계 성적</a></td>
				</tr>-->
			</table>
		</div>
	</div>
	<!-- 메인 div -->
	<main>
		<h1>학기별 성적 조회</h1>
		<div class="split--div"></div>
		<div class="sub--filter">
					<form action="/grade/search" method="get">
						<div>
							<input type="hidden" name="studentId" value="${principal.id}">
							<select name="subYear">
								<c:forEach var="grade" items="${yearList}">
									<option value="${grade.subYear}" ${selectedSubYear eq grade.subYear ? 'selected' : ''}>${grade.subYear}년</option>
								</c:forEach>
							</select> <select name="semester">
								<option value="1" ${selectedSemester == 1 ? 'selected' : ''}>1학기</option>
								<option value="2" ${selectedSemester == 2 ? 'selected' : ''}>2학기</option>
							</select> <select name="type">
								<option value="">전체</option>
								<option value="전공" ${selectedType eq '전공' ? 'selected' : ''}>전공</option>
								<option value="교양" ${selectedType eq '교양' ? 'selected' : ''}>교양</option>
							</select>
							<!-- 검색 버튼 -->
							<button type="submit">
								<ul class="d-flex justify-content-center" style="margin: 0;">
									<li style="height: 24px; margin-right: 2px;">조회
									<li style="height: 24px;"><span
										class="material-symbols-outlined"
										style="font-size: 18px; padding-top: 4px;">search</span>
								</ul>
							</button>
						</div>
					</form>
				</div>
		<c:choose>
			<c:when test="${gradeList.isEmpty() == false}">
				<table border="1" class="sub--list--table">
					<thead>
						<tr>
							<th>연도</th>
							<th>학기</th>
							<th>과목번호</th>
							<th>과목명</th>
							<th>강의구분</th>
							<th>이수학점</th>
							<th>성적</th>
						</tr>
					</thead>
					<tbody>
						<%-- 조회한 값 --%>
						<c:forEach var="grade" items="${gradeList}">
							<tr>
								<td>${grade.subYear}년</td>
								<td>${grade.semester}학기</td>
								<td>${grade.subjectId}</td>
								<td>${grade.subjectName}</td>
								<td>${grade.type}</td>
								<td>${grade.grades}</td>
								<td>${grade.grade}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p class="no--list--p">강의 신청 및 수강 이력 확인 바랍니다.</p>
			</c:otherwise>
		</c:choose>
	</main>
</div>

<%@ include file="../layout/footer.jsp"%>