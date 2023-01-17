<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Detail Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** Board Detail Spring02_MVC2 **</h2>
<hr>
<c:if test="${not empty apple}"> 
	<table>
		<tr height="40"><td bgcolor="Khaki">Seq</td><td>${apple.seq}</td></tr>
		<tr height="40"><td bgcolor="Khaki">I D</td><td>${apple.id}</td></tr>
		<tr height="40"><td bgcolor="Khaki">Title</td><td>${apple.title}</td></tr>
		<tr height="40"><td bgcolor="Khaki">Content</td>
						<td><textarea rows="5" cols="50" readonly>${apple.content}</textarea></td>
		</tr>
		<tr height="40"><td bgcolor="Khaki">RegDate</td><td>${apple.regdate}</td></tr>
		<tr height="40"><td bgcolor="Khaki">조회수</td><td>${apple.cnt}</td></tr>
	</table>
</c:if>
<c:if test="${not empty requestScope.message}">
	<hr>
	${requestScope.message}<br>
</c:if>
<hr>
<c:if test="${loginID==apple.id || loginID=='admin'}">
	&nbsp;&nbsp;<a href="bdetail?jCode=U&seq=${apple.seq}">[글수정]</a>
	&nbsp;&nbsp;<a href="bdelete?seq=${apple.seq}">[글삭제]</a>
</c:if>
<c:if test="${not empty loginID}">
	&nbsp;&nbsp;<a href="rinsert?root=${apple.root}&step=${apple.step}&indent=${apple.indent}">댓글</a>
	<!-- 댓글 입력시에는 부모글의 root, step, indent 가 필요하기때문에 같이 전송 --> 
</c:if>
<hr>
&nbsp;&nbsp;<a href="blist">BoardList</a>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>
</body>
</html>