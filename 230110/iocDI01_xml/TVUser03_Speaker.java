package iocDI01_xml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

// TV 의 의존성 처리
// => TV 는 Speaker 를 사용
// => 생성자 주입, setter 주입

// ** 의존성 해결 Test
// 1) 고전적 방법 (직접 new)
// 2) IOC/DI -> 생성자 주입 
// 3) IOC/DI -> setter 주입
// => setter 보다는 생성자주입을 권장함 (최초 1회 적용후 변경 불가능 하기때문)

// ** IOC: 제어의 역전 (외부에서 Control)
// ** DI : 주입 받음으로 해결

class Speaker {
	public Speaker() { System.out.println("~~ Speaker Default 생성자 ~~"); }
	
	public void volumeUp() {System.out.println("~~ Speaker volumeUp ~~"); }
	public void volumeDown() {System.out.println("~~ Speaker volumeDown ~~");}
}
// 1) 고전적 방법 (직접 new : 소스 재컴파일)
//=> Speaker 생성 : 직접코드에서
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

// 2) IOC/DI -> 생성자 주입 
//=> Speaker 생성 : ~xml 에서 설정
class LgTVs implements TV {
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
//=> Speaker 생성 : ~xml 에서 설정
class AiTVs implements TV {
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

public class TVUser03_Speaker {

	public static void main(String[] args) {
		// 1. 스프링 컨테이너 구동(생성)
		AbstractApplicationContext sc = new 
				GenericXmlApplicationContext("iocDI01_xml/app03.xml");
		
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
