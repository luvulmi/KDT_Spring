package com.ncs.green;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;

//*** JSON 제이슨, (JavaScript Object Notation) **********
//=> 자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
// 속성(key) 과 값(value) 이 하나의 쌍을 이룸
		
//** JAVA의 Data 객체 -> JSON 변환하기
//1) GSON
//	: 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
//	즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
		
//2) @ResponseBody (매핑 메서드에 적용)
//	: 메서드의 리턴값이 View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
//	이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
//	MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환
//	view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도
//	@JsonIgnore : VO 에 적용하면 변환에서 제외

//3) jsonView
//=> Spring 에서 MappingJackson2JsonView를 사용해서
//	 ModelAndView를 json 형식으로 반환해 준다.
//=> 방법
//	-> pom dependency추가
//	-> 설정화일 xml 에 bean 등록 
//	( 안하면 /WEB-INF/views/jsonView.jsp 를 찾게되고  없으니 404 발생 )
//	-> return할 ModelAndView 생성시 View_Name을 "jsonView"로 설정
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	@Autowired 
	PasswordEncoder passwordEncoder;
	// ** PasswordEncoder interface 구현 클래스
	// => Pbkdf2PasswordEncoder, BCryptPasswordEncoder, 
	//    SCryptPasswordEncoder, StandardPasswordEncoder, 
	//    NoOpPasswordEncoder
	// => 대표적인 BCryptPasswordEncoder root-context.xml (적용) 또는 
	//    servlet-context.xml 에 bean설정 후 @Autowired 가능
	
	
	// ** Image (File) Download
	// => 전달받은 path 와 파일명으로 File 객체를 만들어 view 로보냄 
	@GetMapping("/dnload")
	public ModelAndView dnload(HttpServletRequest request, ModelAndView mv,
				@RequestParam("dnfile")	String dnfile) {
				//=> 동일기능 	String dnfile=request.getParameter("dnfile");
		
		// 1) 파일 & path 확인
		// => 요청 Parameter 를 확인, fileName
		String realPath = request.getRealPath("/"); //deprecated Method
		String fileName = dnfile.substring(dnfile.lastIndexOf("/")+1);
		// dnfile => resources/uploadImage/robot.png
		
		// => realpath 확인, 개발중인지, 배포했는지 에 따라 결정
		// => 해당화일 File 찾기
		if ( realPath.contains(".eclipse.") )  // 개발중 (배포전: eclipse 개발환경)
			 realPath = "C:\\MTest\\MyWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\"+fileName;
		else realPath += "resources\\uploadImage\\"+fileName;
		
		// 2) 해당 화일 (path+fileName) 객체화
		File file = new File(realPath);
		mv.addObject("downloadFile", file);
		
		// 3) response 처리 (response의 body 에 담아줌) 
		// => Java File 객체 -> File 정보를 response 에 전달
		mv.setViewName("downloadView");
		return mv;
	} //dnload
	
	// ** Ajax Member Delete **
	// => 관리자기능 : session 삭제가 필요없음
	@RequestMapping(value="/axdelete", method = RequestMethod.POST )
	public ModelAndView axdelete(ModelAndView mv, MemberVO vo) {
		
		if ( service.delete(vo)>0 ) {
			mv.addObject("code", "200");
		}else {
			mv.addObject("code", "201");
		}
		mv.setViewName("jsonView");
		return mv;
	} //axdelete
	
	// ** Ajax MemberList
	@RequestMapping(value="/axmlist")
	public ModelAndView axmlist(ModelAndView mv) {
		mv.addObject("banana", service.selectList());   
		mv.setViewName("/axTest/axMemberList");
		return mv;
	} //axmlist
	
	// ** Response 로 JsonData 를 전송
	@RequestMapping(value="/jslogin", method = RequestMethod.POST)
	public ModelAndView jslogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {
		// 1) 요청분석
		// => response 의 한글처리 (Ajax 요청 결과로 Data 전송시에는 필수) 
		String password=vo.getPassword();
		response.setContentType("text/html; charset=UTF-8");
		
		// 2) Service 실행
		// => 성공 -> 로그인정보 보관후, code="200"
		// => 실패 -> id오류 code="201" , pw오류 code="202"
		vo=service.selectOne(vo);
		if ( vo!=null ) {
			// ** id 일치 -> Password 확인 : 암호화 이후
			if ( passwordEncoder.matches(password, vo.getPassword()) ) {
				// 로그인 성공 -> session 에 로그인정보 보관, code="200"
				request.getSession().setAttribute("loginID", vo.getId());
				request.getSession().setAttribute("loginName", vo.getName());
				mv.addObject("code", "200");
			}else {
				// password 오류
				mv.addObject("code", "202");
				mv.addObject("message", "~~ password 오류 !! 다시 하세요 ~~");
			}
		}else { // id 오류
			mv.addObject("code", "201");
			mv.addObject("message", "~~ ID 오류 !! 다시 하세요 ~~");
		}
		// 3) View 처리 ( Json Format Data 을 Response 로 ) 
		// => viewName 을 "jsonView";
		// => API 사용을 위한 dependency, 설정 필요함
		mv.setViewName("jsonView");
		return mv;
	} //jslogin
	
	// ** ID 중복확인 **
	@RequestMapping(value="/idDupCheck")
	public ModelAndView idDupCheck(ModelAndView mv, MemberVO vo) {
		// 1) newID 확인
		// => 존재하면 : 사용불가
		if ( service.selectOne(vo) != null ) {
			// 사용불가
			mv.addObject("idUse", "F");
		}else {
			// 사용가능
			mv.addObject("idUse", "T");
		}
		mv.setViewName("/member/idDupCheck");
		return mv;
	} //idDupCheck
	
	
	// ** Member Check List *************************** 
	// => SearchCriteria, PageMaker 적용하고, mapper에 반복문 적용 
	@RequestMapping(value="/mchecklist")
	public ModelAndView mchecklist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		// ** Paging 준비
		cri.setSnoEno();
		
		// 1) Check_Box 처리
		// => check 선택이 없는경우 check 의 값은 null 값으로
		//    mapper 에서 정확하게 처리하기위함
		if ( cri.getCheck()!=null && cri.getCheck().length<1 )
				cri.setCheck(null);
		
		// 2) Service 실행
		// => 선택하지 않은경우, 선택한 경우 모두 mapper 의 Sql 로 처리
		mv.addObject("banana", service.checkList(cri));
		
		// 3) View 처리 => PageMaker
		pageMaker.setCriteria(cri);
		pageMaker.setTotalRowsCount(service.checkCount(cri));
		
		mv.addObject("pageMaker", pageMaker);
		mv.setViewName("member/mCheckList");
    	return mv;
	} //mchecklist
	
	// ** Criteria PageList ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// => ver02 : SearchCriteria cri
	@RequestMapping(value = "/mcrilist", method=RequestMethod.GET )
	public ModelAndView mcrilist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		// => rowsPerPage, currPage, searchType, keyword 값은 Parameter 로 전달
		//    자동으로 set -> 그러므로 currPage 를 이용해서 setSnoEno 만 하면됨.
		cri.setSnoEno();
		
		// 2) Service 처리
		// => SearchType 의 선택이 없는경우 SearchType 의 값은 null 값으로 set
		//    mapper 에서 정확하게 처리하기위함
		if ( cri.getSearchType()!=null && cri.getSearchType().length()<1 ) {
			cri.setSearchType(null);
		}
		mv.addObject("banana", service.searchList(cri)); // ver02
		
		// 3) View 처리 => PageMaker
		// => cri, totalRowsCount (DB에서 읽어와야함)
		pageMaker.setCriteria(cri);
		pageMaker.setTotalRowsCount(service.searchTotalCount(cri)); // ver02: 조건과 일치하는 Rows 갯수
		mv.addObject("pageMaker", pageMaker);
		
		mv.setViewName("/member/mCriList");
		return mv;
	}//mcrilist
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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
			// ** id 일치 -> Password 확인 : 암호화 이전
			//if (vo.getPassword().equals(password)) {
			
			// -> Password 확인 : 암호화 이후
			if ( passwordEncoder.matches(password, vo.getPassword()) ) {
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
	public ModelAndView mjoin(HttpServletRequest request, 
					ModelAndView mv, MemberVO vo) throws IOException {
		
		// 1) 요청분석
		// => 한글처리 (web.xml 에서 Filter 설정), 
		// => request 처리 ( 매핑메서드의 매개변수로 VO를 정의하면 자동처리 )
		
		// ** MultipartFile ***********************
		// => MultipartFile 타입의 uploadfilef 의 정보에서 
		//    upload된 image 화일과 화일명을 get 처리,
		// => upload된 image 화일은 서버의 정해진 폴더 (물리적위치)에 저장 하고, -> file1
		
		// => 이 위치에 대한 정보를 table에 저장 (vo의 UploadFile 에 set) -> file2
		//		resources/uploadImage/bbb.gif -> Table 의 저장위치
		
		// ** image 화일명 중복시 : 나중 이미지로 update 됨. 
		
		// ** Image 물리적위치 에 저장
		// 1) 현재 웹어플리케이션의 실행 위치 확인 : 
		// => eslipse 개발환경 (배포전)
		//    D:\MTest\myWork\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring02\

		// => 톰캣서버에 배포 후 : 서버내에서의 위치가 됨
		//    D:\MTest\IDESet\apache-tomcat-9.0.41\webapps\Spring02\
		String realPath = request.getRealPath("/");
		System.out.println("** realPath => "+realPath);
		
		// 2) 위 의 값을 이용해서 실제저장위치 확인 
		// => 개발중인지, 배포했는지 에 따라 결정
		if ( realPath.contains(".eclipse.") )  // 개발중 (배포전: eclipse 개발환경)
			 realPath = "C:\\MTest\\MyWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\" ;
		else realPath += "resources\\uploadImage\\";
		
		// ** 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우)  만들어 준다
		File f1 = new File(realPath);
		if ( !f1.exists() )  f1.mkdir();
		// => realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
		//    존재하지 않으면 디렉토리 생성
		
		// ** 기본 이미지 지정하기 
		String  file1, file2="resources/uploadImage/basicman4.png" ;
		
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		//    -> String getOriginalFilename(), 
		//    -> void transferTo(File destFile),
		//    -> boolean isEmpty()
		
		MultipartFile uploadfilef = vo.getUploadfilef(); // file 의 내용및 화일명 등 전송된 정보들
		if ( uploadfilef!=null && !uploadfilef.isEmpty() ) {
			// ** Image를 선택함 -> Image저장 ( 경로_realPath + 화일명 )
			// 1) 물리적 저장경로(file1) 에 Image 저장
			file1= realPath + uploadfilef.getOriginalFilename(); // 저장경로 완성
			uploadfilef.transferTo(new File(file1));
			
			// 2) Table 저장 (file2) 준비
			file2="resources/uploadImage/" + uploadfilef.getOriginalFilename();
		} //Image를 선택함
		
		// ** 완성된 경로 vo 에 set & Service 처리
		vo.setUploadfile(file2);
		String uri = "/member/loginForm";
		
		// *** PasswordEncoder (암호화 적용하기)
		// => BCryptPasswordEncoder 적용
		//    encode(rawData) -> digest 생성 & vo에 set  
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		
		// 2) Service 실행
		// => 성공 -> 로그인 유도, loginForm
		// => 실패 -> joinForm 재가입 유도
		
		/* *** Transaction_AOP Test 
		1. dependency 확인
			=> AspectJ ,  AspectJ Weaver 
		
	 	2. servlet-context.xml AOP 설정
	 	
	  	3. Rollback Test
	  	3.1) Aop xml 적용전 => insert1 은 입력되고, insert2 에서  500_Dupl..Key  오류 발생
	  	3.2) Aop xml 적용후 => insert2 에서 오류발생시 모두 Rollback 되어 insert1, insert2 모두 입력 안됨
	
	  	3.1) Transaction 적용전 : 동일자료 2번 insert
		    => 첫번째는 입력완료 되고, 두번째자료 입력시 Key중복 오류발생 (500 발생)
		3.2) Transaction 적용후 : 동일자료 2번 insert
		    => 첫번째는 입력완료 되고, 두번째 자료입력시 Key중복 오류발생 하지만,
		       rollback 되어 둘다 입력 안됨
		 
		service.insert(vo) ; // insert1 -> Test 용       
		 */
		if ( service.insert(vo)>0 ) {   // insert2 (Transaction 적용)
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
	public ModelAndView mupdate(HttpServletRequest request, 
			ModelAndView mv, MemberVO vo) throws IOException {
		// ** 요청분석
		// => 한글처리 (web.xml 에서 Filter 설정), 
		// => request 처리 ( 매핑메서드의 매개변수로 VO를 정의하면 자동처리 )
		
		// ** Image 처리
		// 1) 경로 확인
		String realPath = request.getRealPath("/");
		System.out.println("** realPath => "+realPath);
		
		// 2) 위 의 값을 이용해서 실제저장위치 확인 
		// => 개발중인지, 배포했는지 에 따라 결정
		if ( realPath.contains(".eclipse.") )  // 개발중 (배포전: eclipse 개발환경)
			 realPath = "C:\\MTest\\MyWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\" ;
		else realPath += "resources\\uploadImage\\";
		
		// 3) 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우)  만들어 준다
		File f1 = new File(realPath);
		if ( !f1.exists() )  f1.mkdir();
		
		// 4) 기본 이미지 지정하기 
		String  file1, file2="resources/uploadImage/basicman4.png" ;
		
		// 5) MultipartFile
		MultipartFile uploadfilef = vo.getUploadfilef(); // file 의 내용및 화일명 등 전송된 정보들
		if ( uploadfilef!=null && !uploadfilef.isEmpty() ) {
			// ** New File 선택한 경우 => 이때만 newFile 을 vo 에 set
			// 5.1) 물리적 저장경로(file1) 에 Image 저장
			file1= realPath + uploadfilef.getOriginalFilename(); // 저장경로 완성
			uploadfilef.transferTo(new File(file1));
			
			// 5.2) Table 저장 (file2) 준비
			file2="resources/uploadImage/" + uploadfilef.getOriginalFilename();
			
			// 5.3) newFile 을 vo 에 set
			vo.setUploadfile(file2);
		} //Image를 선택함
		
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
