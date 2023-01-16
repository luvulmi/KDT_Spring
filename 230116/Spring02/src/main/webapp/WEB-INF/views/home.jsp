<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h1>Hello Spring02 !!!</h1>
<P>  The time on the server is ${serverTime}. </P>
<hr>
<c:if test="${not empty sessionScope.loginID}">
=> ${sessionScope.loginID}, ${sessionScope.loginName} 님 안녕하세요 !!!<br>
</c:if>
<c:if test="${not empty requestScope.message}">
=> ${requestScope.message}
</c:if>
<hr>
<img alt="home" src="resources/images/tulips.png" width="400" height="300">
<hr>
<!-- Login 전 -->
<c:if test="${empty sessionScope.loginID}">
	&nbsp;<a href="mlogin">Login</a>&nbsp;
	&nbsp;<a href="mjoin">Join</a>&nbsp;
</c:if>
<!-- Login 후 -->
<c:if test="${not empty sessionScope.loginID}">
	&nbsp;<a href="mdetail">MyInfo</a>&nbsp;
	&nbsp;<a href="mlogout">Logout</a>&nbsp;
	&nbsp;<a href="mdelete">탈퇴</a>&nbsp;
</c:if>
<hr>
&nbsp;<a href="mlist">MemberList</a>&nbsp;
&nbsp;<a href="blist">BoardList</a>&nbsp;
</body>
</html>
