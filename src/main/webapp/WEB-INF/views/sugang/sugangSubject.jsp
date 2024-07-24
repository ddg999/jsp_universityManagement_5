<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/subject.css">

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>수강신청</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/sugang/subject"
						class="selected--menu">강의 시간표 조회</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/sugang/preRegist">예비
							수강 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/regist">수강
							신청</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/sugang/registResult">수강
							신청 내역</a></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>강의 시간표 조회</h1>
		<div class="split--div"></div>
		<!-- 필터 및 검색 -->
		<div class="sub--filter">
			<form action="/sugang/subject/search" method="get">
				<div>
					<!-- 강의구분 콤보박스 -->
					<label for="type">강의구분</label> <select name="type" id="type">
						<option value="">전체</option>
						<option value="전공" ${selectedType eq '전공' ? 'selected' : ''}>전공</option>
						<option value="교양" ${selectedType eq '교양' ? 'selected' : ''}>교양</option>
					</select>
					<!-- 대상학과 콤보박스 -->
					<label for="deptName">개설학과</label> <select name="deptName"
						id="deptName">
						<option value="">전체</option>
						<c:forEach var="dept" items="${deptList}">
							<option value="${dept.name}"
								${selectedDeptName eq dept.name ? 'selected' : ''}>${dept.name}</option>
						</c:forEach>
					</select>
					<!-- 강의 검색 -->
					<label for="subName">강의명</label> <input type="text" name="name"
						list="subName"
						value="${not empty selectedName ? selectedName : ''}">
					<!-- 검색 버튼 -->
					<button type="submit">
						<ul class="d-flex justify-content-center" style="margin: 0;">
							<li style="height: 24px; margin-right: 2px;">조회 <li style="height: 24px;"><span
								class="material-symbols-outlined"
								style="font-size: 18px; padding-top: 4px;">search</span>
						
						</ul>
					</button>
				</div>
			</form>
		</div>
		<c:choose>
			<c:when test="${subjectList.isEmpty() == false}">
				<h4>
					<span style="font-weight: 600;">강의 목록</span>&nbsp; <span
						style="color: gray; font-size: 18px;">[총 ${subjectCount}건]</span>
				</h4>
				<table border="1" class="sub--list--table">
					<thead>
						<tr>
							<th>단과대학</th>
							<th>개설학과</th>
							<th>학수번호</th>
							<th>강의구분</th>
							<th style="width: 200px;">강의명</th>
							<th>담당교수</th>
							<th>학점</th>
							<th>요일시간 (강의실)</th>
							<th>현재인원</th>
							<th>정원</th>
							<th>강의계획서</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="subject" items="${subjectList}">
							<tr>
								<td>${subject.collName}</td>
								<td>${subject.deptName}</td>
								<td>${subject.id}</td>
								<td>${subject.type}</td>
								<td class="sub--list--name">${subject.name}</td>
								<td>${subject.professorName}</td>
								<td>${subject.grades}</td>
								<td><c:choose>
										<c:when test="${subject.startTime < 10}">
											${subject.subDay} 0${subject.startTime}:00-${subject.endTime}:00&nbsp;(${subject.roomId})								
										</c:when>
										<c:otherwise>
											${subject.subDay} ${subject.startTime}:00-${subject.endTime}:00&nbsp;(${subject.roomId})							
										</c:otherwise>
									</c:choose></td>
								<td>${subject.numOfStudent}</td>
								<td>${subject.capacity}</td>
								<td>
									<ul class="d-flex justify-content-center sub--plan--view"
										style="margin: 0;">
										<li style="height: 24px;"><a
											href="/subject/syllabus/${subject.id}"
											onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;">조회</a>
										<li style="height: 24px;"><a
											href="/subject/syllabus/${subject.id}"
											onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;"><span
												class="material-symbols-outlined">content_paste_search</span></a>
									
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination">
					<c:forEach begin="1" end="${totalPages}" var="i">
						<c:choose>
							<c:when test="${i == currentPage}">
								<span class="current-page">${i}</span>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${not empty keyword}">
										<c:if test="${type eq 'title'}">
											<a
												href="${pageContext.request.contextPath}/sugang/search?type=title&keyword=${keyword}&page=${i}">${i}</a>
										</c:if>
										<c:if test="${type eq 'keyword'}">
											<a
												href="${pageContext.request.contextPath}/sugang/search?type=keyword&keyword=${keyword}&page=${i}">${i}</a>
										</c:if>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/sugang/subject?page=${i}">${i}
			</a> </c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				
							</div>
			
										</c:when>
			<c:otherwise>
				<p class="no--list--p">검색 결과가 없습니다.</p>
			</c:otherwise>
		</c:choose>

	</main>
</div>
<%@ include file="../layout/footer.jsp"%>