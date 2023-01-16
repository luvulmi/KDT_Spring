package model;

import java.sql.Connection;
import java.sql.DriverManager;

//** DB 연결
//=> Connection 객체가 DB 연결및 연결정보를 관리함
// 즉, Connection 객체를 생성해야함

public class DBConnection {
	
	// ** Connection 객체 생성
	// => 일반적인 생성문 
	//    Connection cn = new Connection_구현클래스() -> XXX
	// => DB 연결정보를이용해서 생성후 그 생성값을 전달받음   
	
	// ** Error Message
	// => 드라이버 오류 : java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver1
	// => portNO 오류 : Communications link failure
	// => DBName 오류 : Unknown database 'mydb1'
	// => 계정,PW 오류 : Access denied for user 'root1'@'localhost' (using password: YES)
	
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	        // => allowPublicKeyRetrieval=true : local DB open 하지 않아도 connection 허용
			System.out.println("===> JDBC Connection 성공  ===");
	        return DriverManager.getConnection(url,"root","mysql");
		} catch (Exception e) {
			System.out.println("*** JDBC Connection Exception => "+e.toString());
			return null;
		}
	} //getConnection 

} //class
