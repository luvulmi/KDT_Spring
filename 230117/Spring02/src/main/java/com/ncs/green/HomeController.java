package com.ncs.green;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//** Bean 생성하는 @
//* Java : @Component
//* Spring 세분화 됨
//=> @Controller,  @Service,  @Repository
//	스프링 프레임웤 이 역할별로 객체를 인식할 수 있도록
//=> @Controller 
//	-> interface 없이도 Controller 로 인식
//	-> 오버라이딩 의무가 없어짐 (메서드명, 매개변수, 리턴값 자유) 
//	-> 하나의 컨트롤러에 여러개의 메서드를 작성할수있고,
//		메서드 단위로 매핑 ( @RequestMapping )
//	-> 일반적으로 Table 단위로 컨트롤러를 구현

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	} //home
	
} //class
