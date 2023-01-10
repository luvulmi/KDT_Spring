package iocDI01_xml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

// ** TV 의 의존성 처리
// => TV 는 여러개의 Speaker 를 사용할 수 있음 
// => Speaker interface 적용 
// => 생성자 주입, setter 주입

interface Speakeri {
	void volumeUp();
	void volumeDown();
} //Speakeri
class SpeakerA implements Speakeri {
	public SpeakerA() {System.out.println("~~ SpeakerA Default 생성자 ~~");}
	@Override
	public void volumeUp() {System.out.println("~~ SpeakerA volumeUp ~~");}
	@Override
	public void volumeDown() {System.out.println("~~ SpeakerA volumeDown ~~");}
} //SpeakerA
class SpeakerB implements Speakeri {
	public SpeakerB() {System.out.println("~~ SpeakerB Default 생성자 ~~");}
	@Override
	public void volumeUp() {System.out.println("~~ SpeakerB volumeUp ~~");}
	@Override
	public void volumeDown() {System.out.println("~~ SpeakerB volumeDown ~~");}
} //SpeakerB

//1) 고전적 방법 (직접 new : 소스 재컴파일)
// => SpeakerA, B 교체 : 직접코드에서
class SsTVsi implements TV {
	private Speakeri speaker = new SpeakerA();
	
	public SsTVsi() {System.out.println("~~ SsTVsi Default 생성자 ~~");}
	@Override
	public void powerOn() {System.out.println("~~ SsTVsi powerOn ~~");}
	@Override
	public void powerOff() {System.out.println("~~ SsTVsi powerOff ~~");}
	@Override
	public void volumeUp() { speaker.volumeUp(); }
	@Override
	public void volumeDown() { speaker.volumeDown(); }
} //SsTVsi

//2) IOC/DI -> 생성자 주입 
//=> SpeakerA, B 교체 : ~xml 에서 설정시에
class LgTVsi implements TV {
	private Speakeri speaker;
	private String color;
	private int price;
	
	public LgTVsi() {System.out.println("~~ LgTVsi Default 생성자 ~~");}
	public LgTVsi(Speakeri speaker, String color, int price) {
		this.speaker=speaker;
		this.color=color;
		this.price=price;
		System.out.println("~~ LgTVsi 초기화 생성자 color, price => "+color+price);
	}
	@Override 
	public void powerOn() {System.out.println("~~ LgTVsi powerOn ~~");}
	@Override
	public void powerOff() {System.out.println("~~ LgTVsi powerOff ~~");}
	@Override
	public void volumeUp() {speaker.volumeUp();}
	@Override
	public void volumeDown() {speaker.volumeDown();}
} //LgTVs

//3) IOC/DI -> setter 주입
//=> SpeakerA, B 교체 : ~xml 에서 설정시에
class AiTVsi implements TV {
	private Speakeri speaker;
	private String color;
	private int price;
	
	public AiTVsi() {System.out.println("~~ AiTVsi Default 생성자 ~~");}
	
	public void setSpeaker(Speakeri speaker) { this.speaker = speaker; }
	public void setColor(String color) { this.color = color; }
	public void setPrice(int price) { this.price = price; }

	@Override 
	public void powerOn() {
		System.out.println("~~ AiTVsi powerOn ~~ color, price => "+color+price);
	}
	@Override
	public void powerOff() {System.out.println("~~ AiTVsi powerOff ~~");}
	@Override
	public void volumeUp() {speaker.volumeUp();}
	@Override
	public void volumeDown() {speaker.volumeDown();}
} //AiTVs

public class TVUser04_Speakeri {

	public static void main(String[] args) {
		// 1. 스프링 컨테이너 구동(생성)
		AbstractApplicationContext sc = new 
				GenericXmlApplicationContext("iocDI01_xml/app04.xml");
		
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
