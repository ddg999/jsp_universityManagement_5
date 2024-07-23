<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layout/header.jsp"%>
<style>
.select--button {
	margin-left: 350px;
}
</style>
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
						<td><a href="${pageContext.request.contextPath}/schedule/show">학사일정</a></td>
					</tr>
					<c:if test="${principal.userRole eq 'staff'}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/schedule/list">학사일정 등록</a></td>
					</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>
			<div class="container">
				<table class="table">
					<tr class="title">
						<td class="type">제목</td>
						<td>${notice.title}</td>
					</tr>
					<tr class="content--container">
						<td class="type">내용</td>
						<td>${notice.content}</td>
					</tr>
				</table>
				<div class="select--button">
					<a href="/notice/list" class="button">목 록</a>
					<c:if test="${principal.userRole eq 'staff'}">
						<a href="/notice/update?id=${notice.id}" class="button">수 정</a> 
						<a href="/notice/delete?id=${notice.id}" class="button">삭 제</a>
					</c:if>
				</div>
			</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>
