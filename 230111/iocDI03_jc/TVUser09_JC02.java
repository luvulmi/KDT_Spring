package iocDI03_jc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

//** Java Bean Configuration class를 이용한 DI
//=> Bean 컨테이너 : AnnotationConfigApplicationContext 사용
//=> Test02 : 스피커 1개 사용 TV
 
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
//----------------------------------------------------------
// 2) IOC/DI -> 생성자주입
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

public class TVUser09_JC02 {

	public static void main(String[] args) {
		// 1. 스프링 컨테이너 구동(생성)
		AbstractApplicationContext sc = new 
				AnnotationConfigApplicationContext(JavaConfig02.class);
		
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
