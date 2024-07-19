<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<link rel="stylesheet" href="../../resources/css/findUserInfo.css">
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
		<form action="/user/findId" method="post">
			<table class="search--table">
				<colgroup>
					<col class="col1">
					<col class="col2">
				</colgroup>
				<tr>
					<td><label for="name">이름</label></td>
					<td><input type="text" name="name" id="name"></td>
				</tr>
				<tr>
					<td><label for="email">이메일</label></td>
					<td><input type="text" name="email" id="email"></td>
				</tr>
				<tr>
					<td colspan="2"><label for="student">학생</label> 
					<input type="radio" name="userRole" value="student" id="student"> &nbsp;&nbsp; <label for="professor">교수</label> <input
						type="radio" name="userRole" value="professor" id="professor"> &nbsp;&nbsp; <label for="staff">직원</label> <input type="radio" name="userRole" value="staff" id="staff"></td>
				</tr>

			</table>
			<div class="button--container">
				<button type="submit" class="submit--button">아이디 찾기</button>
			</div>
		</form>
	</section>

</body>
</html>