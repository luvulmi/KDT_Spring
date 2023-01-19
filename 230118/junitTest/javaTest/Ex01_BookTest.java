package javaTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//** Book class
//=> 맴버필드 3개 정의, 맴버필드 3개를 초기화하는 생성자를 만드세요 ~
//=> 접근범위는 default

class Book {
	String author;
	String title;
	int price;
	
	Book(String author, String title, int price) {
		this.author=author;
		this.title=title;
		this.price=price;
	} //생성자
	
	public boolean isBook(boolean b) { return b; }
	
} //Book

// ** 테스트 주도 개발 (Test-Driven Development , TDD)
// => JUnit 활용
//	Java 개발시 가장 많이 이용되는 단위테스트 프레임
//	오픈 소스 형태로 개발되며 계속 업그레이드 되고 있음.
//	JUnit4 부터 에너테이션 적용 ( Java 가 5 부터 언어적 개선이 이루어짐에 따른 변화임 )

// ** @ 종류
// => @Before - @Test - @After
//	-> 하나의 클래스에서 @ 들을 반복사용하면 오류는 안나지만, 앞쪽 @이 실행됨
// => @ 적용 테스트 메서드 : non static, void 로 정의 해야 함.

// ** org.junit.Assert 가 지원하는 다양한 Test용 Method 
// 1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
// 2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
// 3) assertTrue(a) : a가 참인지 확인
// 4) assertNotNull(a) : a객체가 Null 이 아님을 확인
// 5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

// => 자동 import 가 안되는경우
//	-> 프로젝트 우클릭 -> Build Path -> Configure Build Path.. 
//			-> Libraries -> Add Library  -> JUnit5
//	-> @Test: import org.junit.Test 확인

// => pom.xml
//	-> junit version : 4.12 로 수정
//	-> dependency 추가 ( Spring MVC Mybatis Test )

public class Ex01_BookTest {
	
	// 1) assertEquals(a,b) 
	public void equalsTest() {
		Book b1 = new Book("엄미현", "Spring 정복", 9900);
		//assertEquals(b1.author, "톨스토이");  // false:레드라인
		assertEquals(b1.author, "엄미현");  // true:그린라인  
	}
	
	// 2) assertSame(a,b)
	public void sameTest() {
		Book b1 = new Book("엄미현", "Spring 정복", 9900);
		Book b2 = new Book("톨스토이", "죄와벌", 19900);
		Book b3 = new Book("톨스토이", "죄와벌", 19900);
		//assertSame(b1, b2);  // false  
		//assertSame(b2, b3);  // false  
		b1=b3;
		assertSame(b1, b3);  // true
	}
	
	// 3) assertTrue(a)
	public void trueTest() {
		Book b1 = new Book("엄미현", "Spring 정복", 9900);
		assertTrue(b1.isBook(true));  // true:그린라인  
	}
	 
	// 4) assertNotNull(a)
	public void notNullTest() {
		Book b1 = new Book("엄미현", "Spring 정복", 9900);
		System.out.println("** b1 => "+b1);
		//assertNotNull(b1);  // true:그린라인  
		b1=null;
		assertNotNull(b1);   // false:레드라인
	}
	@Test
	// 5) assertArrayEquals(a,b)
	public void arrayEqualsTest() {
		String[] a1 = new String[] {"가","나","다"}; 
		String[] a2 = new String[] {"가","나","다"};   
		String[] a3 = new String[] {"가","다","나"};   
		String[] a4 = new String[] {"가","나","라"};   
		// 5.1) 두배열의 순서, 값 모두 동일, 주소값은 다름 (a1, a2)
		//assertArrayEquals(a1, a2); // Green : 주소비교는 아님
		
		// 5.2) 두배열의 순서는 다르고, 값 모두 동일 (a1, a3)
		//assertArrayEquals(a1, a2); // RED_Line
		
		// 5.3) 모두 다른경우 (a1, a4)
		//assertArrayEquals(a1, a4); // RED_Line
		
		// 5.4) Book Type 의 배열 2개 만들고 비교하기
		Book b1 = new Book("엄미현", "Spring 정복", 9900);
		Book b2 = new Book("톨스토이", "죄와벌", 19900);
		Book b3 = new Book("톨스토이", "죄와벌", 19900);
		
		Book[] books1 = new Book[] { b1, b2, b3 } ;
		Book[] books2 = new Book[] { b1, b2, b3 } ;
		Book[] books3 = new Book[] { b1, b2, new Book("톨스토이", "죄와벌", 19900) } ;
		//assertArrayEquals(books1, books2); // Green
		assertArrayEquals(books1, books3);   // Red
	}
	
} //class
