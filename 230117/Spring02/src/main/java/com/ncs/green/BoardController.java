package com.ncs.green;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.BoardService;
import vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	BoardService service;
	
	// ** BoardList: /blist
	@RequestMapping(value = "/blist", method=RequestMethod.GET )
	public ModelAndView blist(ModelAndView mv) {
		mv.addObject("banana", service.selectList());      
		mv.setViewName("/board/boardList");
		return mv;
	}//blist
	
	// ** BoardDetail: /bdetail
	@RequestMapping(value = "/bdetail", method=RequestMethod.GET)
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		// ** Detail & 조회수 증가 , Update Form 출력                                              
		
		// => 조회수 증가 : 테이블의 cnt=cnt+1
		//    - 글보는이(loginID)와 글쓴이가 다를때 
        //	  - 글보는이(loginID)가 "admin" 이 아닌경우 
		//    - 수정요청이 아닌경우
		
		vo=service.selectOne(vo);
		if ( vo!=null ) {
			// ** 조회수 증가
			// => 로그인 id 확인
			String loginID = (String)request.getSession().getAttribute("loginID");
			if ( !vo.getId().equals(loginID) &&
				 !"admin".equals(loginID) &&
				 !"U".equals(request.getParameter("jCode")) ) {
				// => 조회수 증가
				if ( service.countUp(vo)>0 ) vo.setCnt(vo.getCnt()+1); 
			} //if_증가조건
		} //조회수 증가
		
		mv.addObject("apple", vo);  
		// ** View 처리
		// => Update 요청이면 bupdateForm.jsp
		String uri = "/board/boardDetail";
		if ( "U".equals(request.getParameter("jCode")) ) {
			uri = "/board/bupdateForm";
		}
		mv.setViewName(uri);
		return mv;
	}//bdetail	
	
	// ** Insert: /binsert
	// => Get: binsertForm
	// => Post : Service 처리
	@RequestMapping(value="/binsert", method=RequestMethod.GET )
	public ModelAndView binsertForm(ModelAndView mv) {
		mv.setViewName("/board/binsertForm");
		return mv;
	} //binsertForm
	
	@RequestMapping(value="/binsert", method=RequestMethod.POST )
	public ModelAndView binsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// ** Service
		// => 성공 : redirect blist
		// => 실패 : binsertForm (재입력 유도)
		String uri="redirect:blist";
		if ( service.insert(vo)>0 ) {
			// => 성공: message 처리
			rttr.addFlashAttribute("message", "~~ 새글 등록 성공 ~~");
		}else {
			// => 실패: message, uri 처리
			mv.addObject("message", "~~ 새글 등록 실패, 다시 하세요 ~~");
			uri="/board/binsertForm" ;
		}
		mv.setViewName(uri);
		return mv;
	} //binsert
	
	// ** Update: /bupdate
	@RequestMapping(value="/bupdate", method=RequestMethod.POST )
	public ModelAndView bupdate(ModelAndView mv, BoardVO vo) {
		// ** Service
		// => 성공 : boardDetail
		// => 실패 : bupdateForm (재수정 유도)
		String uri="/board/boardDetail";
		
		// => 수정후 결과 View 출력시 사용하기위해 
		//    수정하지않는 값들도 전달받아서 보관
		mv.addObject("apple", vo);
		
		if ( service.update(vo)>0 ) {
			// => 성공: message 처리
			mv.addObject("message", "~~ 글 수정 성공 ~~");
		}else {
			// => 실패: message, uri 처리
			mv.addObject("message", "~~ 글 수정 실패, 다시 하세요 ~~");
			uri="/board/bupdateForm" ;
		}
		mv.setViewName(uri);
		return mv;
	} //bupdate
	
	// ** Delete: bdelete
	@RequestMapping(value="/bdelete", method=RequestMethod.GET )
	public ModelAndView bdelete(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// ** Service
		// => 성공 : redirect blist
		// => 실패 : redirect bdetail (seq가 필요)
		String uri="redirect:blist";
		if ( service.delete(vo)>0 ) {
																																						// => 성공: message 처리
			rttr.addFlashAttribute("message", "~~ 글 삭제 성공 ~~");
		}else {
			// => 실패: message, redirect bdetail (seq가 필요)
			rttr.addFlashAttribute("message", "~~ 글 삭제 실패 ~~");
			uri="redirect:bdetail?seq="+vo.getSeq();
		}
		mv.setViewName(uri);
		return mv;
	} //bdelete
	
	// ** Reply Insert: /rinsert
	// => Get: rinsertForm
	// => Post : Service 처리
	@RequestMapping(value="/rinsert", method=RequestMethod.GET )
	public ModelAndView rinsertForm(ModelAndView mv, BoardVO vo) {
		// => vo 에는 전달된 부모글의 root, step, indent 가 담겨있고,
		//    이 값들을 View 에 숨겨놓아야함 (hiddden 으로) -> 댓글입력시 필요하므로 
		// => 매핑메서드의 인자로 정의된 vo 는 request.setAttribute 와 동일 scope
		//    단, 클래스명의 첫글자를 소문자로 ...  ${boardVO.root}
		// 	  그러므로 아래와같은 구문은 필요없음.
		//		mv.addObject("apple", vo);
		
		mv.setViewName("/board/rinsertForm");
		return mv;
	} //rinsertForm
	
	@RequestMapping(value="/rinsert", method=RequestMethod.POST )
	public ModelAndView rinsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// ** Service
		// => 성공 : redirect blist
		// => 실패 : 재입력 유도 (/board/rinsertForm 으로)
		String uri="redirect:blist";
		
		// ** vo 의 값
		// => id, title, content : 사용
		// => 부모의 root : 동일
		// => 부모의 step : step+1
		// => 부모의 indent : indent+1
		vo.setStep(vo.getStep()+1);
		vo.setIndent(vo.getIndent()+1);
		
		if ( service.rinsert(vo)>0 ) {
			rttr.addFlashAttribute("message", "~~ 댓글 등록 성공 ~~");
		}else {
			uri="/board/rinsertForm";
			mv.addObject("message", "~~ 댓글 등록 실패, 다시 하세요 ~~");
		}
		
		mv.setViewName(uri);
		return mv;
	} //rinsert 
	
} //class
