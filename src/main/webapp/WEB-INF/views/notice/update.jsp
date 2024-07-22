<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="/css/notice.css">
<style>
.select--button {
	margin-left: 350px;
}
</style>
<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td><a href="${pageContext.request.contextPath}/notice/list"
							class="selected--menu">공지사항</a></td>
					</tr>
					<tr>
						<td><a
							href="${pageContext.request.contextPath}/notice/schedule">학사일정</a></td>
					</tr>
					<c:if test="${principal.userRole eq 'staff'}">
						<tr>
							<td><a
								href="${pageContext.request.contextPath}/scheduleList.jsp">학사일정
									등록</a></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>
		<div class="write--div">
			<form action="/notice/update" method="post">
				<input type="hidden" name="noticeId" value="${notice.id}">
				<div class="title--container">
					<div class="category">
						<select name="category" class="input--box">
							<option value="[일반]">[일반]</option>
							<option value="[학사]">[학사]</option>
							<option value="[장학]">[장학]</option>
						</select>
					</div>
					<div class="title">
						<input type="text" class="form-control form-control-sm"
							name="title" required="required" value="${notice.title}"
							style="width: 900px;">
					</div>
				</div>
				<div class="content--container">
					<textarea name="content" class="form-control" cols="100" rows="20"
						placeholder="내용을 입력하세요" required="required">${notice.content}</textarea>
				</div>
				<a href="/notice/list" class="button">목록</a> <input type="submit"
					class="button" value="수정">
			</form>
		</div>
	</main>
</div>
<%@ include file="../layout/footer.jsp"%>