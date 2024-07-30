<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>

<style>
li {
	list-style: none;
}

.button--row {
	margin-top: 30px;
	text-align: center;
	width: 100%;
}

.title--row {
	text-align: center;
	width: 100%;
	margin: 20px 0px;
}

.title--row h1 {
	font-weight: 600;
}

hr {
	margin-bottom: 30px;
}

.radio--check li:first-of-type {
	font-size: 20px;
	margin-bottom: 10px;
	color: maroon;
	font-weight: bolder;
}

.etc--row li:first-of-type {
	margin-bottom: 10px;
}

.etc--row span {
	font-weight: 600;
	font-size: 20px;
}

</style>
<body>
	<c:if test="${type == 1}">
		<script>
			window.close();
		</script>
	</c:if>

	<main>
		<div class="title--row">
			<h1>강의 평가</h1>
		</div>
		<hr>
		<form action="/evaluation/write?subjectId=${subjectId}" method="post">
			<input type="hidden" name="studentId" value="${principal.id}">
			<ul class="radio--check">
				<li>1.&nbsp;수업은 강의계획서에 따라 체계적으로 진행되었다.</li>
				<li>&nbsp;<input type="radio" name="answer1" value="5" id="a11" required> <label for="a11"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer1" value="4" id="a12"> <label for="a12"> 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer1" value="3" id="a13"> <label for="a13"> 보통</label></li>
				<li>&nbsp;<input type="radio" name="answer1" value="2" id="a14"> <label for="a14"> 그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer1" value="1" id="a15"> <label for="a15"> 전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>2.&nbsp;수업내용이 명확하고 효과적으로 제시되었다.</li>
				<li>&nbsp;<input type="radio" name="answer2" value="5" id="a21" required> <label for="a21"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer2" value="4" id="a22"> <label for="a22"> 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer2" value="3" id="a23"> <label for="a23"> 보통</label></li>
				<li>&nbsp;<input type="radio" name="answer2" value="2" id="a24"> <label for="a24"> 그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer2" value="1" id="a25"> <label for="a25"> 전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>3.&nbsp;수업은 학생의 학습동기와 흥미를 유발시켰다.</li>
				<li>&nbsp;<input type="radio" name="answer3" value="5" id="a31" required> <label for="a31"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer3" value="4" id="a32"> <label for="a32"> 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer3" value="3" id="a33"> <label for="a33"> 보통</label></li>
				<li>&nbsp;<input type="radio" name="answer3" value="2" id="a34"> <label for="a34"> 그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer3" value="1" id="a35"> <label for="a35"> 전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>4.&nbsp;수업시간이 전반적으로 잘 준수되었다.</li>
				<li>&nbsp;<input type="radio" name="answer4" value="5" id="a41" required> <label for="a41"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer4" value="4" id="a42"> <label for="a42"> 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer4" value="3" id="a43"> <label for="a43"> 보통</label></li>
				<li>&nbsp;<input type="radio" name="answer4" value="2" id="a44"> <label for="a44"> 그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer4" value="1" id="a45"> <label for="a45"> 전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>5.&nbsp;성적평가의 기준과 방법이 분명하게 제시되었다.</li>
				<li>&nbsp;<input type="radio" name="answer5" value="5" id="a51" required> <label for="a51"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer5" value="4" id="a52"> <label for="a52"> 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer5" value="3" id="a53"> <label for="a53"> 보통</label></li>
				<li>&nbsp;<input type="radio" name="answer5" value="2" id="a54"> <label for="a54"> 그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer5" value="1" id="a55"> <label for="a55"> 전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>6.&nbsp;학습과제에 대하여 평가와 지도가 있었다.</li>
				<li>&nbsp;<input type="radio" name="answer6" value="5" id="a61" required> <label for="a61"> 매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer6" value="4" id="a62"> <label for="a62">그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer6" value="3" id="a63"> <label for="a63">보통</label></li>
				<li>&nbsp;<input type="radio" name="answer6" value="2" id="a64"> <label for="a64">그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer6" value="1" id="a65"> <label for="a65">전혀 그렇지 않다</label></li>
			</ul>
			<ul class="radio--check">
				<li>7.&nbsp;강의를 전체적으로 평가할 때 우수하다고 생각한다.</li>
				<li><input type="radio" name="answer7" value="5" id="a71" required> <label for="a71">매우 그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer7" value="4" id="a72"> <label for="a72">그렇다</label></li>
				<li>&nbsp;<input type="radio" name="answer7" value="3" id="a73"> <label for="a73">보통</label></li>
				<li>&nbsp;<input type="radio" name="answer7" value="2" id="a74"> <label for="a74">그렇지 않다</label></li>
				<li>&nbsp;<input type="radio" name="answer7" value="1" id="a75"> <label for="a75">전혀 그렇지 않다</label></li>
			</ul> 
			<ul class="etc--row">
				<li><span>기타 사항</span></li>
				<li><textarea cols="80" rows="5" name="improvements"> </textarea></li>
			</ul>

			<div class="button--row">
				<button type="submit" class="btn btn-dark update--button">제출하기</button>
			</div>
		</form>
	</main>
<%@ include file="../layout/footer.jsp"%>