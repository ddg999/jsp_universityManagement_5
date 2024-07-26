<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/subject.css">


<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
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
					<td><a href="${pageContext.request.contextPath}/sugang/subject">강의 시간표 조회</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/preRegist" class="selected--menu">예비 수강 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/regist">수강 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/registResult">수강 신청 내역</a></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>예비 수강 신청 내역</h1>
		<div class="split--div"></div>
		<div class="d-flex justify-content-between align-items-start">
			<!-- 예비 수강 신청 내역으로 가기 -->
			<a href="/sugang/preRegist"><button class="preStuSubList--button" style="margin-bottom: 30px; margin-top: 0;">예비 수강 신청 하기</button></a>
		</div>
		<c:choose>
			<c:when test="${subjectList.isEmpty() == false}">
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
							<th>수강신청</th>
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
								<td class="sub--list--button--row"><c:choose>
										<%-- 신청된 상태라면 --%>
										<c:when test="${subject.status == true}">
											<form action="/sugang/pre/delete?subjectId=${subject.id}&call=result" method="post">
												<input type="hidden" name="studentId" value="${principal.id}"> 
												<button type="submit" onclick="return confirm('수강신청을 취소하시겠습니까?');" style="background-color: #a7a7a7;">취소</button>
											</form>
										</c:when>
										<c:when test="${subject.numOfStudent == subject.capacity}">
											<button type="button" style="background-color: #c5b; color: white" disabled>마감</button>
										</c:when>
										<%-- 신청되지 않은 상태라면 --%>
										<c:otherwise>
											<form action="/sugang/pre/add?subjectId=${subject.id}&call=result" method="post">
												<input type="hidden" name="studentId" value="${principal.id}"> 
												<button type="submit" onclick="return confirm('해당 강의를 수강신청하시겠습니까?');" style="background-color: #548AC2;">신청</button>
											</form>
										</c:otherwise>

									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p class="no--list--p">수강 신청 내역이 없습니다</p>
			</c:otherwise>
		</c:choose>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>