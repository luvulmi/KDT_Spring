<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>** Ajax Parameter Test View (Get/Post) **</title>
    </head>

    <body>
        <pre><h3>
** Ajax Parameter Test View (Get/Post) **

=> 전송된 Parameter Value
   I D : ${param.id}
   P W : ${param.password}
   Name: <%=request.getParameter("name")%>	
</h3></pre>
    </body>

    </html>