<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberList Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** MemberList Spring02_MVC2 **</h2>
<table width=100%>
	<tr bgcolor="YellowGreen">
		<th>I D</th><th>Password</th><th>Name</th><th>Age</th>
		<th>Jno</th><th>Info</th><th>Point</th><th>Birthday</th>
		<th>추천인</th><th>Image</th>
	</tr>
  <c:if test="${not empty banana}">
  	<c:forEach var="s" items="${banana}">
  	<tr>
		<td>
			<c:if test="${sessionScope.loginID=='admin'}">
				<a href="mdetail?id=${s.id}">${s.id}</a>
			</c:if>
			<c:if test="${sessionScope.loginID!='admin'}">
				${s.id}
			</c:if>   	
		</td>
		<td>${s.password}</td><td>${s.name}</td><td>${s.age}</td><td>${s.jno}</td>
		<td>${s.info}</td><td>${s.point}</td><td>${s.birthday}</td><td>${s.rid}</td>
		<td><img alt="memberImage" src="${s.uploadfile}" width=50 height=60></td>
	</tr>
  	</c:forEach>
  </c:if>	
  <c:if test="${empty banana}">
	<tr><td colspan="7">** 출력할 자료가 1건도 없습니다. **</td></tr>
  </c:if>
</table>
<hr>
&nbsp;<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
&nbsp;<a href="home">[Home]</a>&nbsp;
</body>
</html>