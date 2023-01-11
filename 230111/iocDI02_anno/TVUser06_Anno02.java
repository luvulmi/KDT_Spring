package iocDI02_anno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

// TV 의 의존성 처리
// => TV 는 Speaker 를 사용
// => 생성자 주입, setter 주입

// ** 의존성 해결 Test
// 1) 고전적 방법 (직접 new)
// 2) IOC/DI -> 생성자 주입 
// 3) IOC/DI -> setter 주입

//** @Autowired
//=> 맴버변수에 생성된(Type에해당하는) 주소값을 찾아 있으면 전달
//   그러므로 Speaker 는 반드시 이미 생성되어 있어야 함.
//=> 자동주입: 있으면 주입 , 없으면 Exception 발생
//=> 적용순위
//1) 주로 멤버변수 위에 선언하며,
//	  스프링 컨테이너는 해당변수의 타입을 체크하고,
//	  그 타입의 객체가 메모리에 존재하는지 확인하여
//	  해당 변수에 대입 해준다.
//2) 동일타입의 객체가 둘이상이면  @Qualifier 에 명시된 객체 주입
//3) 동일타입의 객체가 둘이상이고  @Qualifier 없으면 
//   id가 같은 객체 찾아 있으면 주입, 없으면 오류

//** Test : Speaker 생성 안되어있는 경우 오류메시지 확인
//		=> ...Injection of autowired dependencies failed;.....

//** required 속성
//	=> true : 해당 타입의 등록된 빈이 없을때 주입시 익셉션이 발생
//	=> false: 해당 타입의 등록된 빈이 없을때 익셉션이 발생하지 않음 
//			( 있으면 주입 , 없으면 null ) 	
//	
//1) @Autowired(required=true) : default 
//		=> BeanCreationException <- NoSuchBeanDefinitionException: .... as autowire...  
//2) @Autowired(required=false) 
//		=> 실행문에서 접근시 NullPointerException 발생

//@Component
class Speaker {
	public Speaker() { System.out.println("~~ Speaker Default 생성자 ~~"); }
	
	public void volumeUp() {System.out.println("~~ Speaker volumeUp ~~"); }
	public void volumeDown() {System.out.println("~~ Speaker volumeDown ~~");}
}

// 1) 고전적 방법 (직접 new : 소스 재컴파일)
//=> Speaker 생성 : 직접코드에서
//@Component("tvs")
class SsTVs implements TV {
	private Speaker speaker = new Speaker();
	
	public SsTVs() {System.out.println("~~ SsTVs Default 생성자 ~~");}
	@Override
	public void powerOn() {System.out.println("~~ SsTVs powerOn ~~");}
	@Override
	public void powerOff() {System.out.println("~~ SsTVs powerOff ~~");}
	@Override
	public void volumeUp() { speaker.volumeUp(); }
	@Override
	public void volumeDown() { speaker.volumeDown(); }
} //SsTVs
//----------------------------------------------------------
// 2) IOC/DI  
//=> Speaker 주입 : @Autowired -> 전역변수에 적용

//** @Autowired(required = false) 
// => 자동주입
// => Speaker는 반드시 생성되어 있어야함 
//    Speaker speaker = new Speaker(); 구문의 "=" 역할 
// => 메모리에서 타입이 일치하는 객체를 찾아 있으면 제공
// => 없으면 오류 : UnsatisfiedDependencyException: Error creating bean wit....
// => required = false
//    못찾으면 Exception 을 발생하지않고 null return 
//    -> 그러므로 사용구문( volumeUp, volumeDown) 에서 NullPointExceptiopn 발생

// ** Speaker 생성 : Speaker 에서 처리
//	-> @Component
//  -> new Speaker() 처리되었음, 그러므로 생성된 주소를 전달만 받겠다는 표시만 해주면 됨. 
//       Speaker speaker = new Speaker()

//@Component("tvl")
class LgTVs implements TV {
	
	//@Autowired(required = false)
	// private Speaker speaker = new Speaker() 구문의 "=" 즉 주입만 함
	private Speaker speaker; 
	
	private String color;
	private int price;
	
	public LgTVs() {System.out.println("~~ LgTVs Default 생성자 ~~");}
	public LgTVs(Speaker speaker, String color, int price) {
		this.speaker=speaker;
		this.color=color;
		this.price=price;
		System.out.println("~~ LgTVs 초기화 생성자 color, price => "+color+price);
	}
	@Override 
	public void powerOn() {System.out.println("~~ LgTVs powerOn ~~");}
	@Override
	public void powerOff() {System.out.println("~~ LgTVs powerOff ~~");}
	@Override
	public void volumeUp() {speaker.volumeUp();}
	@Override
	public void volumeDown() {speaker.volumeDown();}
} //LgTVs

// 3) IOC/DI -> setter 주입
//=> Speaker 생성 : @Autowired
//@Component("tva")
class AiTVs implements TV {
	
	//@Autowired(required = false)
	private Speaker speaker;
	
	private String color;
	private int price;
	
	public AiTVs() {System.out.println("~~ AiTVs Default 생성자 ~~");}
	
	public void setSpeaker(Speaker speaker) { this.speaker = speaker; }
	public void setColor(String color) { this.color = color; }
	public void setPrice(int price) { this.price = price; }

	@Override 
	public void powerOn() {
		System.out.println("~~ AiTVs powerOn ~~ color, price => "+color+price);
	}
	@Override
	public void powerOff() {System.out.println("~~ AiTVs powerOff ~~");}
	@Override
	public void volumeUp() {speaker.volumeUp();}
	@Override
	public void volumeDown() {speaker.volumeDown();}
} //AiTVs

public class TVUser06_Anno02 {

	public static void main(String[] args) {
		// 1. 스프링 컨테이너 구동(생성)
		AbstractApplicationContext sc = new 
				GenericXmlApplicationContext("iocDI02_anno/app05.xml");
		
		// 2. 필요한 객체를 전달받고 서비스 실행
		System.out.println("** Test1. 고전적 방법 (직접 new) **");
		TV tvs = (TV)sc.getBean("tvs");
		tvs.powerOn();
		tvs.volumeUp();
		tvs.volumeDown();
		tvs.powerOff();
		
		System.out.println("** Test2. IOC/DI -> 생성자 주입 **");
		TV tvl = (TV)sc.getBean("tvl");
		tvl.powerOn();
		tvl.volumeUp();
		tvl.volumeDown();
		tvl.powerOff();
		
		System.out.println("** Test3. IOC/DI -> setter 주입 **");
		TV tva = (TV)sc.getBean("tva");
		tva.powerOn();
		tva.volumeUp();
		tva.volumeDown();
		tva.powerOff();

		System.out.println("** Program 종료 **");
		sc.close();
	} //main

} //class
