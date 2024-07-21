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
						<td><a href="${pageContext.request.contextPath}/notice/list" class="selected--menu">공지사항</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/notice/schedule">학사일정</a></td>
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
		<h1>공지사항</h1>
		<!-- 공지검색기능 -->
		<form action="/notice/search" method="get" class="form--container">
			<select class="input--box" name="type">
				<option value="title" ${type eq 'title' ? 'selected' : ''}>제목</option>
				<option value="keyword" ${type eq 'keyword' ? 'selected' : ''}>제목+내용</option>
			</select><input type="text" name="keyword" class="input--box" placeholder="검색어를 입력하세요" value="${keyword}"> <input type="submit" class="button" value="검색">
		</form>
		<div class="split--div"></div>
		<table class="table">
			<tbody>
				<tr class="first--tr">
					<td>번호</td>
					<td>말머리</td>
					<td>제목</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
				<c:forEach var="notice" items="${noticeList}">
					<tr class="second--tr" onclick="location.href='${pageContext.request.contextPath}/notice/read?id=${notice.id}';">
						<td>${notice.id}</td>
						<td>${notice.category}</td>
						<td>${notice.title}</td>
						<td><fmt:formatDate value="${notice.createdTime}" pattern="yyyy-MM-dd"/></td>
						<td>${notice.views}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination">
		<c:forEach begin="1" end="${totalPages}"  var="i" >
			<c:choose>
				<c:when test="${i == currentPage}">
					<span class="current-page" >${i}</span>
				</c:when>
				<c:otherwise>
					 <c:choose>
						<c:when test="${not empty keyword}">
							<c:if test="${type eq 'title'}">
								<span><a href="${pageContext.request.contextPath}/notice/search?type=title&keyword=${keyword}&page=${i}">${i}</a></span>
							</c:if>	
							<c:if test="${type eq 'keyword'}">
								<span><a href="${pageContext.request.contextPath}/notice/search?type=keyword&keyword=${keyword}&page=${i}">${i}</a></span>
							</c:if>
						</c:when>
						<c:otherwise>
							<span><a href="${pageContext.request.contextPath}/notice/list?page=${i}">${i}</a></span>	
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>