<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Oh University 학사관리시스템</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../../resources/css/main.css">
<link rel="stylesheet" href="../../resources/css/notice.css">
<link rel="stylesheet" href="../../resources/css/schedule.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
	<header class="d-flex flex-column">
		<div class="header--top">
			<ul>
				<li class="material--li"><span class="material-symbols-outlined">account_circle</span>
				<li>${principal.name}님 (${principal.id})
				<li style="margin: 0 15px;">ㅣ
				<li class="material--li"><span style="color: white;" class="material-symbols-outlined">logout</span>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a>
			</ul>
		</div>

		<nav class="main--menu">
			<a href="${pageContext.request.contextPath}/home"><img class="logo" alt="" src="/resources/images/logo.png"></a>
			<!-- userRole에 따라 메뉴 다르게 표시 -->
			<c:choose>
				<c:when test="${principal.userRole.equals(\"student\")}">
					<ul>
						<li><a href="${pageContext.request.contextPath}/home">홈</a>
						<li><a href="${pageContext.request.contextPath}/info/student">MY</a>
						<li><a href="${pageContext.request.contextPath}/subject/list">수업</a>
						<li><a href="${pageContext.request.contextPath}/sugang/subject">수강신청</a>
						<li><a href="${pageContext.request.contextPath}/grade/thisSemester">성적</a>
						<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a>
					</ul>
				</c:when>

				<c:when test="${principal.userRole.equals(\"professor\")}">
					<ul>
						<li><a href="${pageContext.request.contextPath}/home">홈</a>
						<li><a href="${pageContext.request.contextPath}/info/professor">MY</a>
						<li><a href="${pageContext.request.contextPath}/subject/list">수업</a>
						<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<li><a href="${pageContext.request.contextPath}/home">홈</a>
						<li><a href="${pageContext.request.contextPath}/info/staff">MY</a>
						<li><a href="${pageContext.request.contextPath}/user/studentList">학사관리</a>
						<li><a href="${pageContext.request.contextPath}/admin/college?crud=select">등록</a>
						<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a>
					</ul>
				</c:otherwise>
			</c:choose>

		</nav>
	</header>