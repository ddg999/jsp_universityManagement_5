<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="../../resources/css/findUserInfo.css">
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
		<form action="/user/findPassword" method="post">
			<table class="search--table">
				<colgroup>
					<col class="col1">
					<col class="col2">
				</colgroup>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td colspan="2"><label for="student">학생</label> <input type="radio" name="userRole" value="student" id="student"> &nbsp;&nbsp; <label for="professor">교수</label> <input
						type="radio" name="userRole" value="professor" id="professor"> &nbsp;&nbsp; <label for="staff">직원</label> <input type="radio" name="userRole" value="staff" id="staff">
					</td>
				</tr>
			</table>
			<div class="button--container">
				<button type="submit" class="submit--button">임시 비밀번호 발급받기</button>
			</div>
		</form>
	</section>

</body>
</html>