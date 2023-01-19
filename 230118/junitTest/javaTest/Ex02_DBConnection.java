package javaTest;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

//** @ 종류
//=> @Before - @Test - @After
//	-> 하나의 클래스에서 @ 들을 반복사용하면 오류는 안나지만, 마지막 @만 실행됨
//=> @ 적용 테스트 메서드 : non static, void 로 정의 해야 함.

public class Ex02_DBConnection {

	// 1) static, return 값 있는 경우 Test
	// => Test 메서드를 작성해서 Test
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
	
	public void connectionTest() {
		System.out.println("** Connection => "+ getConnection());
		// => 연결 성공 -> 주소 return -> not null
		// => 연결 실패 -> null
		assertNotNull(getConnection());
	}
	@Test
	// 2) non static, void 로 정의
	// => 아래처럼 항상 Green_Line , console 의 메시지로 확인
	public void getConnectionVoid() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	        // => allowPublicKeyRetrieval=true : local DB open 하지 않아도 connection 허용
			System.out.println("===> JDBC Connection 성공  ===");
			Connection cn = DriverManager.getConnection(url,"root","mysql");
			System.out.println("*** JDBC Connection cn => "+cn);
		} catch (Exception e) {
			System.out.println("*** JDBC Connection Exception => "+e.toString());
		}
	} //getConnectionVoid
	 
} //class
