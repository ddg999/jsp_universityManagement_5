<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../layout/header.jsp" %>
<link rel="stylesheet" href="../../resources/css/admin.css">
<style>
	.select--button {
    margin-left: 0 !important;
}
</style>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>등록</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody><tr>
					<td><a href="/college.jsp" class="selected--menu">단과대학</a></td>
				</tr>
				<tr>
					<td><a href="/department.jsp">학과</a></td>
				</tr>
				<tr>
					<td><a href="/admin/room">강의실</a></td>
				</tr>
				<tr>
					<td><a href="/admin/subject">강의</a></td>
				</tr>
				<tr>
					<td><a href="/admin/tuition">단대별 등록금</a></td>
				</tr>
			</tbody></table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>단과대학</h1>
		<div class="split--div"></div>
		<div class="select--button">
			<a href="/admin/college?crud=insert" class="button">등록</a> 
			<a href="/admin/college?crud=delete" class="button">삭제</a>
		</div>	

			<!-- 단과대학 조회 -->
			
			<div class="total--container">
				<table class="table--container">
					<tbody><tr class="first--tr">
						<td>ID</td>
						<td>이름</td>
					</tr>
					
						<tr>
							<td>1</td>
							<td>공과대학</td>
						</tr>
					
						<tr>
							<td>2</td>
							<td>인문대학</td>
						</tr>
					
						<tr>
							<td>3</td>
							<td>사회과학대학</td>
						</tr>
					
						<tr>
							<td>4</td>
							<td>상경대학</td>
						</tr>
					
				</tbody></table>
			</div>
			


			<!-- 단과대학 입력 -->
			

			<!-- 단과대학 삭제 -->
			
	</main>


</div>

<%@ include file="../layout/footer.jsp" %>
