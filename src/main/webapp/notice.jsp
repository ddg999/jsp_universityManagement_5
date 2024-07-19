<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ include file="WEB-INF/views/header.jsp" %>

<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table">
				<tbody><tr>
					<td><a href="/notice" class="selected--menu">공지사항</a></td>
				</tr>
				<tr>
					<td><a href="/schedule">학사일정</a></td>				
				</tr>
				
					<tr>
						<td><a href="/schedule/list"> 학사일정 등록</a></td>
					</tr>
				
			</tbody></table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>


		<!-- 공지 검색 -->
		


		<!-- 공지 조회 -->
		
			<form action="/notice/search" method="get" class="form--container">
				<select class="input--box" name="type">
					<option value="title">제목</option>
					<option value="keyword">제목+내용</option>
				</select>
				<input type="text" name="keyword" class="input--box" placeholder="검색어를 입력하세요"> 
				<input type="submit" class="button" value="검색">
			</form>
			<table class="table">
				
					
						<tbody><tr class="first--tr">
							<td>번호</td>
							<td>말머리</td>
							<td>제목</td>
							<td>작성일</td>
							<td>조회수</td>
						</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=6';">
								<td>6</td>
								<td>[장학]</td>
								<td>2023학년도 교외장학재단 신규장학생 선발 결과 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=5';">
								<td>5</td>
								<td>[일반]</td>
								<td>2023학년도 1학기 코로나 19 특별휴학 시행 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=4';">
								<td>4</td>
								<td>[일반]</td>
								<td>2023학년도 장애 인식개선 교육(법정의무교육) 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=3';">
								<td>3</td>
								<td>[일반]</td>
								<td>Gartner Research(IT 분야 시장분석) 서비스 지원 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=2';">
								<td>2</td>
								<td>[장학]</td>
								<td>2023학년도 보건장학회 연구지원장학생 선발 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
							<tr class="second--tr" onclick="location.href='/notice/read?id=1';">
								<td>1</td>
								<td>[학사]</td>
								<td>2023학년도 1학기 학생예비군 전입 신고 안내</td>
								<td>2024-07-19 11:46:54</td>
								<td>0</td>
							</tr>
						
					
					
				
			</tbody></table>
			<div class="paging--container">
			
				<a href="/notice/list/1"> 1</a> &nbsp;&nbsp;
			
				
					<a href="/notice?crud=write" class="button">등록</a>
				
			</div>
		



		<!-- 공지 상세 조회 -->
		


		<!-- 공지 수정 -->
		


		<!-- 공지 등록 -->
		
	</main>

	

  		<footer>
			<!-- 필요 시 -->
		</footer>

</div>


        
        <%@ include file="WEB-INF/views/footer.jsp" %>