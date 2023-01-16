<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberJoin Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** MemberJoin Spring02_MVC2 **</h2>
<form action="mjoin" method="post">
<table>
	<tr height="40"><td bgcolor="LightBlue">I D</td>
		<td><input type="text" name="id" size="20" placeholder="영문과 숫자 10자 이내"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Password</td>
		<td><input type="password" name="password" size="20" placeholder="특수문자반드시포함 10자 이내"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Name</td>
		<td><input type="text" name="name" size="20" placeholder="영문과 한글 10자 이내"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Age</td>
		<td><input type="text" name="age" size="20" placeholder="정수"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Jno</td>
		<td><select name="jno">
			<option value="1">1조: 굉장해엄청나</option>
			<option value="2">2조: 구해조</option>
			<option value="3">3조: 백업은 기본이조</option>
			<option value="4">4조: 상호형 빵사조</option>
			<option value="5">5조: (주)식빵</option>
			<option value="7">7조: 칠면조</option>
		</select></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Info</td>
		<td><input type="text" name="info" placeholder="자기소개"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Point</td>
		<td><input type="text" name="point" size="20" placeholder="실수"></td>
	</tr>
	<tr height="40"><td bgcolor="LightBlue">Birthday</td>
		<td><input type="date" name="birthday" size="20"></td>
	</tr>
	<tr height="40"><td></td>
		<td><input type="submit" value="가입">&nbsp;&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>
</form>
<c:if test="${not empty requestScope.message}">
	<hr>
	=> ${requestScope.message}<br>
</c:if>
<hr>
&nbsp;<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
&nbsp;<a href="home">[Home]</a>&nbsp;
</body>
</html>