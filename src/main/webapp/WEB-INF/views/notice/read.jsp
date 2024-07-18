<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>
		<div class="container">
			<table class="table">
			<tbody>
				<tr class="title">
					<td>제목</td>
					<td>${notice.title}</td>				
				</tr>
				<tr class="content--container">
					<td class="type">내용</td>
					<td>${notice.content}</td>				
				</tr>
			</tbody>
			</table>
		</div>
	</main>
</body>
</html>