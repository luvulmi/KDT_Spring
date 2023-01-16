package com.ncs.green;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.MemberService;
import vo.MemberVO;

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	@RequestMapping(value="/mlist")
	public ModelAndView mlist(ModelAndView mv) {
		mv.addObject("banana", service.selectList());   
		mv.setViewName("/member/memberList");
		return mv;
	} //mlist
	
	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		// => 매핑메서드의 매개변수로 VO를 정의하면 
		//	  Parameter name 이 일치하는 컬럼의 값을 자동으로 VO에 할당(set)해줌
		//	  즉, vo.setId(request.getParameter("id")) 자동처리	
		
		// 1) 요청분석 
		// => 요청1) 관리자로그인의 경우 memberList 에서 : 요청id Parameter로 전달 
		// => 요청2) login 후 내정보 : session 에서 loginID 를 get 
		// => 요청3) Update 를 위한 요청 ( parameter jCode=U 추가됨 )
		
		String uri="/member/memberDetail";
		// => 처리순서 : parameter확인: 없으면 -> session 확인 -> Update요청여부 확인
		if ( vo.getId()==null || vo.getId().length()<1 ) {
			// => session 확인 
			if ( request.getSession().getAttribute("loginID")!=null ) {
				vo.setId((String)request.getSession().getAttribute("loginID"));
			}else {
				mv.addObject("message", "~~ session loginID 없음, 로그인 후 이용하세요 ~~");
				mv.setViewName("/member/loginForm");
				return mv;
			} // session확인
		} // vo확인
		
		// 2) Service
		vo=service.selectOne(vo);
		mv.addObject("apple", vo);
		
		// 3) View
		if ( "U".equals(request.getParameter("jCode")) ) {
			uri = "member/updateForm";
		}
		mv.setViewName(uri);
		return mv;
	} //mdetail
	
	// ** Login & Logout **
	@RequestMapping(value="/mlogin", method = RequestMethod.GET)
	public ModelAndView mloginf(ModelAndView mv) {
		mv.setViewName("/member/loginForm");
		return mv;
	} //mloginf
	
	@RequestMapping(value="/mlogin", method = RequestMethod.POST)
	public ModelAndView mlogin(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		// 1) 요청분석
		// => 한글처리 (web.xml 에서 Filter 설정), 
		// => request 처리 ( 매핑메서드의 매개변수로 VO를 정의하면 자동처리 )
		// => 단, Service 로 id 만 확인후, Password 는 메서드에서 확인 
		//    그러므로 입력받은 Password 는 보관
		String password=vo.getPassword();
		
		// 2) Service 실행
		// => 성공 -> 로그인정보 보관후, home
		// => 실패 -> loginForm 재로그인 유도
		String uri="/member/loginForm" ;
		vo=service.selectOne(vo);
		if ( vo!=null ) {
				// id 일치 -> Password 확인 
			if (vo.getPassword().equals(password)) {
				// 로그인 성공 -> session 에 로그인정보 보관
				request.getSession().setAttribute("loginID", vo.getId());
				request.getSession().setAttribute("loginName", vo.getName());
				uri="redirect:home"; // * 주의 : 반드시 요청명사용
			}else {
				// password 오류
				mv.addObject("message", "~~ password 오류 !! 다시 하세요 ~~");
			}
		}else { // id 오류
			mv.addObject("message", "~~ ID 오류 !! 다시 하세요 ~~");
		}
		// 3) View 처리
		// => Spring 에서는 Forward 가 Default 
		//    그러므로 redirect 는 "redirect:home" 
		mv.setViewName(uri);
		return mv;
	} //mlogin
	
	@RequestMapping(value="/mlogout", method = RequestMethod.GET)
	public ModelAndView mlogout(HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
		// 1) 요청분석 & 해당하는 Service 실행
		// => Logout: session 무효화, home
		
		HttpSession session = request.getSession(false);
		// => false : session이 없을때는 null return
		//			 사용전에 반드시 null 확인해야함 (NullPointerException 예방)
		if ( session !=null ) {
			session.invalidate(); // session 무효화
			rttr.addFlashAttribute("message", " ~~ Logout 성공 ~~ ");
		}
		mv.setViewName("redirect:home");
		return mv;
	} //mlogout
	
	// ** Member Join **
	@RequestMapping(value="/mjoin", method = RequestMethod.GET )
	public ModelAndView mjoinf(ModelAndView mv) {
		mv.setViewName("/member/joinForm");
		return mv;
	} //mjoin
	
	@RequestMapping(value="/mjoin", method = RequestMethod.POST )
	public ModelAndView mjoin(ModelAndView mv, MemberVO vo) {
		// 1) 요청분석
		// => 한글처리 (web.xml 에서 Filter 설정), 
		// => request 처리 ( 매핑메서드의 매개변수로 VO를 정의하면 자동처리 )
		
		String uri = "/member/loginForm";
		// 2) Service 실행
		// => 성공 -> 로그인 유도, loginForm
		// => 실패 -> joinForm 재가입 유도
		if ( service.insert(vo)>0 ) {
			mv.addObject("message", "~~ 회원가입 성공, 로그인 후 이용하세요 ~~");
		}else {
			mv.addObject("message", "~~ 회원가입 실패, Data 오류! 다시 하세요 ~~");
			uri="/member/joinForm" ;
		}
		
		// 3) View 처리
		mv.setViewName(uri);
		return mv;
	} //mjoin

	// ** Member Update **
	@RequestMapping(value="/mupdate", method = RequestMethod.POST )
	public ModelAndView mupdate(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		// 1) 요청분석
		// => 한글처리 (web.xml 에서 Filter 설정), 
		// => request 처리 ( 매핑메서드의 매개변수로 VO를 정의하면 자동처리 )
		
		// 2) Service 실행
		// => 성공 -> 내정보 표시, memberDetail
		// => 실패 -> 친절하게 안내하고 재수정 유도, updateForm
		String uri="member/memberDetail" ;
		mv.addObject("apple", vo);
		// => Update 성공/실패 모두 출력시 필요하므로
		if ( service.update(vo)>0 ) {
			mv.addObject("message", "~~ 회원정보 수정 성공 ~~"); 
			
			// ** name을 수정한 경우에는 session에 보관해놓은 이름도 변경해야하는 경우
			// => 관리자가 member 정보를 변경하는 경우를 제외하고,
			//    로그인한 본인의 장보를 수정하는 경우 name을 변경하면 session 의 loginName 도 동일하게 변경 
			// => loginID "admin" 이 아니고, loginName 과 vo.getName() 가 다른경우
			if ( !"admin".equals(request.getSession().getAttribute("loginID")) && 
				 !vo.getName().equals(request.getSession().getAttribute("loginName")) ) {
				request.getSession().setAttribute("loginName", vo.getName());
			}
		}else {
			uri="member/updateForm" ;
			mv.addObject("message", "~~ 회원정보 수정 실패, 다시 하세요 ~~");
		}
		// 3) View 처리
		mv.setViewName(uri);
		return mv;
	} //mupdate
	
	// ** Member Delete **
	@RequestMapping(value="/mdelete", method = RequestMethod.GET )
	public ModelAndView mdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo, RedirectAttributes rttr) {
		// 1) 요청분석
		// 1.1) login 정보: session 에서 loginID 를 get
		// 1.2) 관리자 기능: loginID=='admin' and Parameter id 가 존재하는경우 
		String loginID="";
		if ( request.getSession().getAttribute("loginID")!=null ) {
			loginID=(String)request.getSession().getAttribute("loginID");
		}else {
			mv.addObject("message", "~~ session loginID 없음, 로그인 후 이용하세요 ~~");
			mv.setViewName("/member/loginForm");
			return mv; // 메서드 종료 
		}
		// => 관리자에 의한 강제탈퇴 확인
		//	  Parameter 로 전달된 id 를 삭제해야함  (이미 vo 에 담겨있음) 	
		if  (!(loginID.equals("admin") && request.getParameter("id")!=null)) {
			// loginID 를 삭제
			vo.setId(loginID);
		} // else 면 vo에 담겨있는 id를 삭제하면 됨. 
		
		// 2) Service 실행
		// => 성공  
		//	-> 본인탈퇴: 반드시 session 처리 해야함
		//	-> 강제탈퇴: 관리자의 session 유지
		if ( service.delete(vo)>0 ) {
			if ( !loginID.equals("admin") ) {
				request.getSession().invalidate(); 
			}
			rttr.addFlashAttribute("message", "~~ 회원 탈퇴 성공, 1개월 후 재가입 가능합니다. ~~");
		}else {
			rttr.addFlashAttribute("message", "~~ 회원 탈퇴 실패 , 다시하세요 ~~");
		}
		// 3) View 처리
		mv.setViewName("redirect:home");
		return mv;
	} //mdelete
	
} //class
