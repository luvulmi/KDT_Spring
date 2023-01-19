package javaTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import model.MemberDAO;
import vo.MemberVO;

// ** DAO Test 시나리오
// => Detail 정확성 
//	-> Test Data
//	-> 정확한 id 를 사용하면 not null : Green_Line
//	-> 없는 id 를 사용하면 null : Red_Line

// => Insert 정확성
//	-> 입력 가능한 Data 적용 : 1 return : Green_Line
// 	-> 입력 불가능한 Data 적용 : 0 return : Red_Line

public class Ex03_DAOTest {
	
	MemberDAO dao = new MemberDAO();
	MemberVO vo = new MemberVO();
	
	// 1) Detail 정확성
	public void detailTest() {
		//vo.setId("banana");  // -> 있는 Data
		vo.setId("yellow");    // -> 없는 Data
		assertNotNull(dao.selectOne(vo));   
		System.out.println("** vo => "+vo);
	} //detailTest
	
	@Test
	// 2) Insert  정확성
	// => 2회 실행 ( 1회는 green, 2회부터는 red -> ID 중복되므로 )	
	public void insertTest() {
		vo.setId("junit");
		vo.setPassword("12345!");
		vo.setName("유니트");
		vo.setAge(20);
		vo.setInfo("성공이냐 실패냐 ~~~");
		vo.setJno(7);
		vo.setPoint(99.88);
		vo.setBirthday("1999-09-09");
		// => 성공:1 , 실패:0
		assertEquals(dao.insert(vo), 1);         
	} //insertTest

} //class
