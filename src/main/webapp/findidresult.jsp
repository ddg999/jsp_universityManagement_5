<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<link rel="stylesheet" href="/resources/css/findUserInfo.css">
</head>

<body>
	<header>
		<div class="header--top"></div>
	</header>
	<section>
		<div class="section--header">
			<h2>아이디 찾기</h2>
			<br>
		</div>
		<div class="section--content">
			${name}님의 아이디는<br> 
			<span style="font-weight: bold;">${userId}</span>입니다.
		</div>
	</section>

</body>
</html>