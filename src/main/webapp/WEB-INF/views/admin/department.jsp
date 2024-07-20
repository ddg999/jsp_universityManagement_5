<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="../../resources/css/admin.css">
<style>
.select--button {
	margin-left: 0 !important;
}
</style>
<!-- 세부 메뉴 + 메인 -->
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
				<tr>
					<td><a href="/college.jsp">단과대학</a></td>
				</tr>
				<tr>
					<td><a href="/department.jsp" class="selected--menu">학과</a></td>
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
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>학과</h1>
		<div class="split--div"></div>
		<div class="select--button">
			<a href="/admin/department?crud=insert" class="button">등록</a> <a href="/admin/department?crud=update" class="button">수정</a> <a href="/admin/department?crud=delete"
				class="button">삭제</a>
		</div>

		<!-- 학과 입력 -->

		<div class="total--container">
			<table class="table--container">
				<tr class="first--tr">
					<td>ID</td>
					<td>학과명</td>
					<td>단과대ID</td>
				</tr>

				<tr>
					<td>101</td>
					<td>컴퓨터공학과</td>
					<td>1</td>
				</tr>

				<tr>
					<td>102</td>
					<td>전자공학과</td>
					<td>1</td>
				</tr>

				<tr>
					<td>103</td>
					<td>화학공학과</td>
					<td>1</td>
				</tr>

				<tr>
					<td>104</td>
					<td>기계공학과</td>
					<td>1</td>
				</tr>

				<tr>
					<td>105</td>
					<td>신소재공학과</td>
					<td>1</td>
				</tr>

				<tr>
					<td>106</td>
					<td>철학과</td>
					<td>2</td>
				</tr>

				<tr>
					<td>107</td>
					<td>국사학과</td>
					<td>2</td>
				</tr>

				<tr>
					<td>108</td>
					<td>언어학과</td>
					<td>2</td>
				</tr>

				<tr>
					<td>109</td>
					<td>국어국문학과</td>
					<td>2</td>
				</tr>

				<tr>
					<td>110</td>
					<td>영어영문학과</td>
					<td>2</td>
				</tr>

				<tr>
					<td>111</td>
					<td>심리학과</td>
					<td>3</td>
				</tr>

				<tr>
					<td>112</td>
					<td>정치외교학과</td>
					<td>3</td>
				</tr>

				<tr>
					<td>113</td>
					<td>사회복지학과</td>
					<td>3</td>
				</tr>

				<tr>
					<td>114</td>
					<td>언론정보학과</td>
					<td>3</td>
				</tr>

				<tr>
					<td>115</td>
					<td>인류학과</td>
					<td>3</td>
				</tr>

				<tr>
					<td>116</td>
					<td>경영학과</td>
					<td>4</td>
				</tr>

				<tr>
					<td>117</td>
					<td>경제학과</td>
					<td>4</td>
				</tr>

				<tr>
					<td>118</td>
					<td>회계학과</td>
					<td>4</td>
				</tr>

				<tr>
					<td>119</td>
					<td>농업경영학과</td>
					<td>4</td>
				</tr>

				<tr>
					<td>120</td>
					<td>무역학과</td>
					<td>4</td>
				</tr>

			</table>
		</div>
	</main>

</div>

<%@ include file="../layout/footer.jsp"%>