<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/login.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<style type="text/css">
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type=number] {
  -moz-appearance: textfield;
}

</style>
</head>
<body>

	<div class="login--div">
		<div class="main--logo">
			<a href="#"><img class="logo" alt="" src="/images/logo.png"></a>
		</div>

		<form action="/login" method="post" class="main--container">
			<div class="login--container">
				<div class="id--container">
					<div class="login--id">
						<label for="userId"><span class="material-symbols-outlined">person</span></label> <input type="number" max="2147483647" name="id" id="userId" placeholder="아이디를 입력하세요" required value="">
						
							
								<div class="checkbox--id">
									<input type="checkbox" name="rememberId">&nbsp;ID 저장
								</div>
							
							
						
					</div>
				</div>
				<div class="pwd--container">
					<div class="login--pwd">
						<label for="password"><span class="material-symbols-outlined">lock</span></label> <input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요" required>
					</div>

				</div>
			</div>
			<div>
				<input type="submit" value="로그인" id="input--submit">
			</div>
			<ul class="login--info">
				<li><a href="/findid.jsp" onclick="window.open(this.href, '_blank', 'width=500, height=300'); return false;"> ID 찾기 </a></li>
				<li><a href="/findpassword.jsp" onclick="window.open(this.href, '_blank', 'width=500, height=350'); return false;"> 비밀번호 찾기 </a></li>
			</ul>
		</form>
	</div>
	<div>
		<pre>
			학생 2023000001
			교직원 230001
			교수 23000001
			비밀번호 123123
		</pre>
	</div>
</body>
</html>