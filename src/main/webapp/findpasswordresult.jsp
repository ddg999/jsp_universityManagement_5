<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="/resources/css/findUserInfo.css">
</head>

<body>
	<header>
		<div class="header--top"></div>
	</header>
	<section>
		<div class="section--header">
			<h2>비밀번호 찾기</h2>
			<br>
		</div>
		<div class="section--content">
			${name}님의 임시 비밀번호<br> 
			<span style="font-weight: bold;">${userPassword}</span><br><br>
			보안을 위해 로그인 후 비밀번호를 변경해주세요.
		</div>
	</section>

</body>
</html>