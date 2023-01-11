package iocDI03_jc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//** Java Bean Configuration class를 이용한 DI
//=> Test03 : 스피커 2개중 선택 
//=> 생성자 를 이용한 주입.. & JC에서 @, xml 병행사용

//*** JC 와 @ 병행사용
//*** JC , @, xml 병행사용
//=> JC 내에서 xml 로 생성된 객체를 직접 사용하는것은 허용 되지 않음. 
//   단, User 클래스에서는 사용가능

//** 실습
//=> SsTVsi , Speaker 는 xml 로 생성
//=> LgTVsi, AiTVsi 는 JC 의 @Bean 으로 생성
//=> LgTVsi (Speaker 생성자주입) 
//   AiTVsi (Speaker @Autowired)   

@ImportResource("iocDI03_jc/app10.xml")
@Configuration
public class JavaConfig03 {
	// 1) xml 과 병행사용 Test
	// => SsTVsi 는 xml 에서 생성
	
	// 2) LgTVsi : 생성자주입
	// => Speaker 를 xml로 생성하면 그인스턴스는 JC로 전달되지않음
	// => JC 내에서 생성자 주입하려면 JC 내에서 생성해야됨. 
	@Bean
	public TV tvl() {
		return new LgTVsi(spb(), "Blue", 7788000);
	}
	
	//** Speaker : JC , xml 생성 모두 Test 
	//@Bean -> xml 생성후 @Autowired 확인 
	//public Speakeri spa() { return new SpeakerA(); }
	@Bean
	public Speakeri spb() { return new SpeakerB(); }
	 
	
	// 3) AiTVsi : @ 와 병행사용 Test 
	// => Speaker 에 @Autowired 적용 Test
	// => AiTVsi 생성 : JC , xml 생성 모두 Test
	//@Bean
	//public TV tva() { return new AiTVsi(); }

} //class
