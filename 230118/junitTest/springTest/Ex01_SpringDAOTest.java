package springTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.MemberDAO;
import vo.MemberVO;

//** DAOTest Spring Version
//=> 설정화일(~.xml) 을  사용
//	-> 테스트코드 실행시에 설정파일을 이용해서 스프링이 로딩 되도록 해줌
//	-> @RunWith(스프링 로딩) , @ContextConfiguration (설정파일 등록)

//=> IOC/DI Test
//=> 공통적으로 사용하는 MemberDAO dao 인스턴스를 전역으로 정의
//=> 자동 주입 받기 ( xml_root-context.xml , @ )

//** SpringJUnit4ClassRunner.class 자동 import 안되면 직접 복.붙 해본다.  

//** import 제대로 안되고 오류발생시 Alt+f5 눌러 Maven Update 한다.
//=> 메뉴 : Project 우클릭 - Maven - Update Project .. 
//  ( 하기전 주의사항은 pom.xml 의  <plugin> <configuration> 의 
//  <source>1.8</source> 와 <target> Java 버전 확인 )

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class Ex01_SpringDAOTest {
	
	// ** 자동주입 : 생성은 root~~~.xml 로 설정
	@Autowired
	MemberDAO dao;
	@Autowired
	MemberVO vo;
	
	// 1) 자동주입 과 Detail 정확성
	public void detailTest() {
		// ** 자동주입 확인
		System.out.println("** DAO 자동주입 확인 => "+dao);
		System.out.println("** VO 자동주입 확인 => "+vo);
		assertNotNull(dao); 
		
		vo.setId("banana");  // -> 있는 Data
		//vo.setId("yellow");    // -> 없는 Data
		assertNotNull(dao.selectOne(vo));   
		System.out.println("** vo => "+vo);
	} //detailTest

	@Test
	// 2) Insert  정확성
	// => 2회 실행 ( 1회는 green, 2회부터는 red -> ID 중복되므로 )	
	public void insertTest() {
		vo.setId("spring");
		vo.setPassword("12345!");
		vo.setName("스프링");
		vo.setAge(20);
		vo.setInfo("성공이냐 실패냐 ~~~");
		vo.setJno(7);
		vo.setPoint(99.88);
		vo.setBirthday("1999-09-09");
		// => 성공:1 , 실패:0
		assertEquals(dao.insert(vo), 1);         
	} //insertTest

} //class
