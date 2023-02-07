<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Ajax_BoardList Spring02_Mybatis **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<!-- ** 반복문에 이벤트 적용 
   => 매개변수처리에 varStatus 활용, ajax, json Test  
   => Login 여부에 무관하게 처리함.
// Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  -> aTag, JS, jsBDetail1(  ) 
// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기 -> aTag, JS, jsBDetail2( , ) 
// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 
//         -> jQuery : id, class, this
//          -> seq 의 <td> 에 마우스오버 이벤트 적용
//         -> content 를 표시할 div (table 바깥쪽에) : 표시/사라짐  
//         -> 마우스 포인터의 위치를 이용해서 div의 표시위치 지정
-->
<h2>** Ajax_BoardList Spring02_Mybatis **</h2>
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
		<c:forEach  var="board" items="${banana}" varStatus="vs" >
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
				<!-- Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  
               	=> aTag, JS, jsBDetail1(???)  
               	
               	Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기
               => aTag, JS, jsBDetail2( , )  
               => <tr> </tr> 추가후 <span> 에 content 표시 
               => 이 <span> 의 id 속성의 값으로 반복문의 index 또는 count 이용하기   
               => scroll 정지 : "javascript:;" , "javascript:void(0);" 동일효과
               => Toggle 방식으로 없을때 클릭하면 표시되고, 있을때 클릭하면 사라짐
               => 새로운 글을 클릭하면 이전글의 컨텐츠는 사라짐
               => 만약 출력할 content의 내용이 없으면 아무것도 나타나지 않는다 (공백의 span 은 표시 되지않음.)   
               
               ** function 에 이벤트객체 전달
               => 이벤트핸들러의 첫번째 매개변수에 event 라는 이름으로 전달함.
               
               ** 1) <a href="#resultArea02" onclick="jsBDetail1(${board.seq})" >
               	-->
				<a href="javascript:;" onclick="jsBDetail2(event, ${board.seq},${vs.count})">${board.title}</a>
			</td>
			<td>${board.id}</td> 
			<td>${board.regdate}</td><td>${board.cnt}</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4"><span class="content" id="${vs.count}"></span></td>
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