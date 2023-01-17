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
<!--  ** FileUpLoad Form **
=> form 과 table Tag 사용시 주의사항 : form 내부에 table 사용해야함
   -> form 단위작업시 인식안됨
   -> JQ 의 serialize, FormData 의 append all 등 
   
=> method="post" : 255 byte 이상 대용량 전송 가능 하므로 
=> enctype="multipart/form-data" : 화일 upload 를 가능하게 해줌 
	** multipart/form-data는 파일업로드가 있는 입력양식요소에 사용되는 enctype 속성의 값중 하나이고, 
       multipart는 폼데이터가 여러 부분으로 나뉘어 서버로 전송되는 것을 의미
       이 폼이 제출될 때 이 형식을 서버에 알려주며, 
       multipart/form-data로 지정이 되어 있어야 서버에서 정상적으로 데이터를 처리할 수 있다.     
-->
<h2>** MemberJoin Spring02_MVC2 **</h2>
<form action="mjoin" method="post" enctype="multipart/form-data">
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
	
	<tr height="40"><td bgcolor="LightBlue">추천인_ID</td>
		<td><input type="text" name="rid" size="20"></td>
	</tr>
	
	<tr height="40"><td bgcolor="LightBlue">Image</td>
		<td><input type="file" name="uploadfilef" size="20"></td>
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