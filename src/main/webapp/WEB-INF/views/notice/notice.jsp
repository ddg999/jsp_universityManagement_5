<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');
@charset "UTF-8";

.material-symbols-outlined {
  font-variation-settings:
  'FILL' 0,
  'wght' 300,
  'GRAD' 0,
  'opsz' 48;
}

.material--li {
	display: flex; 
	align-items: center;
	margin-right: 5px;
}

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Noto Sans KR', sans-serif;
}

a {
	text-decoration: none;
	color: black;
}

a:hover {
	text-decoration: none;
}

li {
	list-style: none;
}

header {
	margin-bottom: 40px;
}

.logo {
	width: 320px;
	height: 80px;
	margin: 5px 50px 5px 10px;
	
}

.header--top {
	min-width: 100em;
	height: 40px; 
	background-color: #031734;
	display: flex;
	justify-content: right;
}

.header--top ul {
	display: flex;
	align-items: center;
	margin: 0 30px;
}

.header--top ul li {
	color: white;
}

.header--top ul li a {
	color: white;
}

.main--menu {
	padding: 10px 5px;
	min-width: 100em;
	height: 100px; 
	background-color: white; /* #031734 */
	display: flex;
	align-items: center;
	border-bottom: 1px solid #e5e5e5;
}

.main--menu ul {
	display: flex;
	align-items: center;
	margin: 0;
}

.main--menu ul li {
	margin: 0 20px;
}

.main--menu ul li a {
	padding: 27px 50px;
	font-size: 27px;
	font-weight: 600;
}

.main--menu ul li a:hover {
	border-bottom: 4px solid #075AA4;
	color: #075AA4;
}

.sub--menu {
	min-width: 230px; 
	margin-right: 80px;
}

.sub--menu--top {
	text-align: center;
	padding: 25px 0;
	border-bottom: 3px solid #33688F;
}

.sub--menu--top h2 {
	margin: 0;
	font-weight: 600;
	color: #133c63;
}

.sub--menu--mid {
	display: flex;
	justify-content: center;
}

.sub--menu--table {
	border-color: #D1D5D4;
	
}

.sub--menu--table td {
	border-left: hidden;
	border-right: hidden;
	width: 200px;
	height: 71px;
	padding: 20px 0;
	font-size: 20px;
}

.sub--menu--table td a{
	padding: 20px 10px;
}

.sub--menu--table tr:nth-last-of-type(1) td {
	border-bottom: hidden;
	height: 72px !important;
}

.sub--menu--table tr:nth-of-type(1) td {
	border-top: hidden;
}

main {
	min-width: 1150px; 
	padding: 20px;
	margin-bottom: 50px;
}

main h1:first-of-type {
	font-weight: 600;
}

.split--div {
	min-width: 1100px;
	height: 2px;
	background-color: gray;
	margin: 20px 0 50px;
}

.selected--menu,
.sub--menu--table td a:hover {
	color: #075AA4;
	font-weight: 600;
}

.no--list--p {
	font-size: 20px;
	color: #6c6a6a;
	font-weight: 600;
}

.page--list {
	display: flex;
	justify-content: center;
}

.page--list li {
	margin: 30px 20px;
}

.selected--page {
	font-weight: 700; 
	color: #007bff;
}

.write--div {
	background-color: #f7f6f6;
	padding: 20px;
}
.title--container {
	display: flex;
	margin-bottom: 20px;
}

.content--container {
	width: 1000px;
	margin-bottom: 20px;
}

.custom-file {
	width: 500px;
}

.update--box {
	width: 500px;
	border: hidden;
}
.textarea {
	border: hidden;
}
.table {
	width: 1000px;
	margin-bottom: 20px;
}

.first--tr {
	background-color: #f7f6f6;
	font-weight: bolder;
	overflow: hidden;
	text-overflow: ellipsis;
}

.second--tr {
	border-bottom: 1px solid #031734;
}

.type {
	width: 70px;
}
.read--container {
	display: flex;
	flex-direction: column;
}

.table-active {
	width: 80px;
	font-size: 15px;
	font-weight: bold;
}

.second--tr:hover {
	font-weight: bold;
	cursor: pointer;
}

.button {
	border: 1px solid #031734;
	border-radius: 3px;
	background-color: #031734;
	color: #fff;
	text-decoration: none;
	padding: 3px;
	height: 31px;
	margin-left: 10px;
}

.button:hover {
	color: #fff;

}
.select--button {
	margin-left: 200px;
}
.input--box {
	border: 1px solid #D2D1D1;
	border-radius: 3px;
	height: 31px;
	margin-right: 20px;
}
.form--container {
	display: flex;
	justify-content: flex-end;
	margin: 0 90px 20px 0;
}
.paging--container {
	display: flex;
	justify-content: center;
}
.split--div {
	min-width: 1100px;
	height: 2px;
	background-color: gray;
	margin: 20px 0 50px;
}

</style>
</head>
<body>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
<div class="sub--menu">
	<div class="sub--menu--top">
		<h2>학사정보</h2>
	</div>
	<div class="sub--menu--mid">
		<table class="sub--menu--table" border="1">
			<tbody>
				<tr>
					<td>
						<a href="/notice/list" class="selected--menu">공지사항</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="/notice/schedule">학사일정</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<main>
<h1>공지사항</h1>
<!-- 공지검색기능 -->
<form action="/notice/search" method="get" class="form--container">
	<select class="input--box" name="type">
		<option value="title">제목</option>
		<option value="keyword">제목+내용</option>
	</select>
	<input type="text" name="keyword" class="input--box" placeholder="검색어를 입력하세요">
	<input type="submit" class="button" value="검색">
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
				<td>${notice.createdTime}</td>
				<td>${notice.views}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</main>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>