<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** BoardList Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** BoardList Spring02_MVC2 **</h2>
<br>
<c:if test="${not empty requestScope.message}">
	${requestScope.message}<br>
</c:if>
<hr>
<table width=100%> 
	<tr bgcolor="Gold" height="30">
		<th>Seq</th><th>Title</th><th>I D</th><th>RegDate</th><th>조회수</th>
	</tr>
	<c:if test="${not empty banana}">
		<c:forEach  var="board" items="${banana}" >
		<tr height="30">
			<td>${board.seq}</td>
			<td>
				<!-- 댓글 등록후 indent 에 따른 들여쓰기 
					=> 댓글의 경우에만 적용 -->
				<c:if test="${board.indent>0}">
					<c:forEach begin="1" end="${board.indent}">
						<span>&nbsp;&nbsp;</span>
					</c:forEach>
					<span style="color:blue;">re..</span>
				</c:if>	
				
				<!-- 로그인 한 경우에만 title을 클릭하면 content를 볼 수 있도록 함
						=> bdetail 을 실행함 -->
				<c:if test="${not empty loginID}">
					<a href="bdetail?seq=${board.seq}">${board.title}</a>
				</c:if> 
				<c:if test="${empty loginID}">
				    ${board.title}
				</c:if>      
			</td>
			
			<td>  <!-- 관리자인 경우에는 글쓴이의 detail을 볼 수 있도록 -->
				<c:if test="${loginID=='admin'}">
					<a href="mdetail?id=${board.id}">${board.id}</a>
				</c:if> 
				<c:if test="${loginID!='admin'}">
				    ${board.id}
				</c:if>
			</td> 
			
			<td>${board.regdate}</td><td>${board.cnt}</td>
		</tr>	
		</c:forEach>
	</c:if>
	<c:if test="${empty banana}">
	<tr><td colspan="5">** 출력할 자료가 1건도 없습니다. **</td></tr>
  </c:if>
</table>
<hr>
<hr>
<c:if test="${not empty loginID}">
&nbsp;&nbsp;<a href="binsert">새글등록_F</a>
</c:if>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>
</body>
</html>