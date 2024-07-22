<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../layout/header.jsp"%>
<body>
	<div class="error--container" style="">
		<%
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
		%>
		<p
			style="color: red; font-size: 20px; font-weight: bold; text-align: center; padding: 250px 0;">
			<%=errorMessage%></p>
		<%
		}
		%>
	</div>
</body>
<%@ include file="../layout/footer.jsp"%>