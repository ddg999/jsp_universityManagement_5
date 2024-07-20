<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>


<style>
.room--table {
	text-align: center;
	margin-top: 20px;
	margin: 10px;
}

.room--table td {
	padding: 10px;
	width: 300px;
}

.first--tr {
	font-weight: bold;
}

.mouth {
	background-color: #f5f5f5;
}

.line {
	
}

.container {
	margin-top: 100px;
}

.table {
	width: 800px;
}

.first--tr {
	background-color: #f7f6f6;
	font-weight: bolder;
	overflow: hidden;
	text-overflow: ellipsis;
}

.button {
	padding: 5px;
	border: 1px solid #031734;
	border-radius: 3px;
	background-color: #031734;
	color: #ccc;
	text-decoration: none;
	margin-left: 770px;
}
</style>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="../notice.jsp">공지사항</a></td>
				</tr>
				<tr>
					<td><a href="../schedule.jsp">학사일정</a></td>
				</tr>
				<tr>
					<td><a href="../scheduleList.jsp" class="selected--menu">학사일정 등록</a></td>
				</tr>

			</table>
		</div>
	</div>

	<main>
		<h1>학사일정</h1>


		<div class="container">
			<table class="table">
				<thead>
					<tr class="first--tr">
						<th>ID</th>
						<th>날짜</th>
						<th>내용</th>
					</tr>
				</thead>
				<tbody>

					<tr onclick="location.href='/schedule/detail?id=1';">
						<td>1</td>
						<td>01-27~02-01</td>
						<td>2023-1학기 예비수강신청</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=2';">
						<td>2</td>
						<td>02-13~02-17</td>
						<td>2023-1학기 수강신청</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=3';">
						<td>3</td>
						<td>02-17~02-23</td>
						<td>2023-1학기 등록</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=4';">
						<td>4</td>
						<td>02-22~02-22</td>
						<td>복학 접수 마감</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=5';">
						<td>5</td>
						<td>02-26~02-26</td>
						<td>졸업예배</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=6';">
						<td>6</td>
						<td>02-27~02-27</td>
						<td>학위수여식</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=7';">
						<td>7</td>
						<td>03-01~03-01</td>
						<td>삼일절</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=8';">
						<td>8</td>
						<td>03-02~03-02</td>
						<td>개강/교무위원회</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=9';">
						<td>9</td>
						<td>03-06~03-08</td>
						<td>수강신청 확인 및 변경</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=10';">
						<td>10</td>
						<td>03-10~03-13</td>
						<td>2023-1학기 추가등록</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=11';">
						<td>11</td>
						<td>03-13~03-17</td>
						<td>조기졸업 신청</td>
					</tr>

					<tr onclick="location.href='/schedule/detail?id=12';">
						<td>12</td>
						<td>03-15~03-15</td>
						<td>미등록자 일반 휴학 접수 마감/ 등록금 전액반환 마감</td>
					</tr>

				</tbody>
			</table>
		</div>
		<a href="/schedule/list?crud=insert" class="button">등록</a>


	</main>
</div>
</div>
<%@ include file="../layout/footer.jsp"%>