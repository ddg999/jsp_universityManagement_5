<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/subject.css">

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>수업</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/subject/list" >전체 강의 조회</a></td>
				</tr>
				<c:if test="${principal.userRole.equals(\"professor\") }">
					<tr>
						<td><a href="${pageContext.request.contextPath}/professor/subject" class="selected--menu">내 강의 조회</a></td>
					</tr>
				</c:if>
				<c:if test="${principal.userRole.equals(\"professor\") }">
					<tr>
						<td><a href="${pageContext.request.contextPath}/evaluation/read"> 내 강의 평가</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>내 강의 조회</h1>
		<div class="split--div"></div>
		<!-- 여기에 내용 넣기 -->

		<!-- 필터 및 검색 -->
		<div class="sub--filter">
			<form action="/professor/subject" method="get">
				<div>
					<!-- 개설연도 숫자 -->
					<label for="subYear">연도 </label> <input type="number" value="2023" name="subYear" id="subYear" min="2023" max="2024">
					<!-- 개설학기 콤보박스-->
					<label for="subSemester">학기 </label> <select name="semester" id="subSemester">
						<option value="1">1학기</option>
						<option value="2">2학기</option>
					</select>
					<!-- 대상학과 콤보박스 -->
					<label for="deptId">개설학과</label> <select name="deptId" id="deptId">
						<option value="-1">전체</option>
						<c:forEach var="department" items="${departmentList}">
							<option value="${department.id}">${department.name}</option>
						</c:forEach>
					</select>
					<!-- 강의 검색 -->
					<label for="subName">강의명</label> <input type="text" name="name" list="subName">
					<datalist id="subName">
						<c:forEach var="subName" items="${subjectList}">
							<option value="${subject.name}">
						</c:forEach>
					</datalist>
					<!-- 검색 버튼 -->
					<button type="submit">
						<ul class="d-flex justify-content-center" style="margin: 0;">
							<li style="height: 24px; margin-right: 2px;">조회
							<li style="height: 24px;"><span class="material-symbols-outlined" style="font-size: 18px; padding-top: 4px;">search</span>
						</ul>
					</button>
				</div>
			</form>
		</div>
		<c:choose>
			<c:when test="${subjectList.isEmpty() == false}">
				<h4>
					<span style="font-weight: 600;">강의 목록</span>&nbsp; <span style="color: gray; font-size: 18px;">[총 ${subjectCount}건]</span>
				</h4>
				<table border="1" class="sub--list--table">
					<thead>
						<tr>
							<th>연도/학기</th>
							<th>단과대학</th>
							<th>개설학과</th>
							<th>학수번호</th>
							<th>강의구분</th>
							<th style="width: 200px;">강의명</th>
							<th>담당교수</th>
							<th>학점</th>
							<th>수강인원</th>
							<th>정원</th>
							<th>강의계획서</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="subject" items="${subjectList}">
							<tr>
								<td>${subject.year}-${subject.semester}학기</td>
								<td>${subject.university}</td>
								<td>${subject.department}</td>
								<td>${subject.subjectNum}</td>
								<td>${subject.type}</td>
								<td class="sub--list--name">${subject.subject}</td>
								<td>${subject.teacher}</td>
								<td>${subject.grade}</td>
								<td>${subject.stu_Num}</td>
								<td>${subject.capacity}</td>
								<td>
									<ul class="d-flex justify-content-center sub--plan--view" style="margin: 0;">
										<li style="height: 24px;"><a href="${pageContext.request.contextPath}/subject/syllabus?subjectId=${subject.subjectNum}"
											onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;">조회</a>
										<li style="height: 24px;"><a href="${pageContext.request.contextPath}/subject/syllabus?subjectId=${subject.subjectNum}"
											onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;"><span class="material-symbols-outlined">content_paste_search</span></a>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination">
				<c:if test="${pageCount != null}">
					<ul class="page--list">
						<c:forEach var="i" begin="1" end="${pageCount}" step="1">
							<c:choose>
								<c:when test="${not empty year or not empty semester or not empty subjectNum or not empty subject}">
									<li><a href="/subject/list/search?subYear=${year}&semester=${semester}&deptId=${subjectNum}&name=${subject}">${i}</a></li>
								</c:when>
							<c:when test="${i == currentPage}">
								<span class="current-page">${i}</span>
							</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/subject/list?page=${i}">${i}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<p class="no--list--p">검색 결과가 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>