/**
 *  ** AjaxTest 02
	=> 반복문에 이벤트 적용하기
	=> axmlist : id별로 board조회, 관리자기능 (delete 버튼), Image(File)Download 
	=> axblist : 상세글 보기
 */
  "use strict"
  
 // ** axmlist
 function axmlist() {
 	$.ajax({
 		type:'Get',
 		url:'axmlist',
 		success:(resultPage) => {
 			$('#resultArea1').html(resultPage);
 		},
 		error: () => {
 			$('#resultArea1').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
 	$('#resultArea2').html('');
 } //axmlist
 
// ** ajax ID BoardList
// => id 별로 board조회 (resultArea2 사용)
function aidBList(id) {
 	$.ajax({
 		type:'Get',
 		url:'aidblist',
 		data: {
 			id:id
 		},
 		success:(resultPage) => {
 			$('#resultArea2').html(resultPage);
 		},
 		error: () => {
 			$('#resultArea2').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
} //aidBList
 
// ** ajax Delete Member
// => 관리자 기능으로 id를 인자로 전달받아 삭제
// => Response 는 JSON 으로 삭제 결과만 전달받음
function axDelete(id) {
 	$.ajax({
 		type:'Post',
 		url:'axdelete',
 		data: {
 			id:id
 		},
 		success:(resultData) => {
 			if ( resultData.code==200 ) {
 			// 삭제성공
 			// => 삭제됨을 표시
 				alert("~~ 삭제 되었습니다 !! ~~ ");
 				// => JS
 				//document.getElementById(id).innerHTML='Deleted';
 				//document.getElementById(id).style.color='Gray';
 				//document.getElementById(id).addEventListener('click', null);
 				
 				// => jQuery
 				$('#'+id).html('Deleted')  // $('#banana')
 				.css({ 	color:'Gray',
 						fontWeight:'bold' })
 				.attr('onclick', null)
 				.removeClass('textlink');			
 			}else {
 			// 실패
 				alert("~~ 삭제 실패 ~~ ");
 			}
 			$('#resultArea2').html('');
 		},
 		error: () => {
 			$('#resultArea2').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
 } //axDelete
 
// ** Ajax BoardList ~~~~~~~~~~~~~~~~~~~~~~~~
function axblist() {
 	$.ajax({
 		type:'Get',
 		url:'axblist',
 		success:(resultPage) => {
 			$('#resultArea1').html(resultPage);
 		},
 		error: () => {
 			$('#resultArea1').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
 	$('#resultArea2').html('');
} //axblist

// Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력
function jsBDetail1(seq) {
 	$.ajax({
 		type:'Get',
 		url:'jsbdetail',
 		data: {
 			seq:seq
 		},
 		success:(resultData) => {
 			if ( resultData.content.length < 1 ) 
 				 $('#resultArea2').html("*** Content => <br> ~~ 출력할 내용이 없습니다 ~~");
 			else $('#resultArea2').html("*** Content => <br>"+resultData.content);
 		},
 		error: () => {
 			$('#resultArea2').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
} //jsBDetail1

// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span Tag)에 글 내용 출력하기

// ** 이벤트 객체
// => 이벤트 객체 전달 Test (이벤트 핸들러의 첫 번째 매개변수에 전달) 
// => 이벤트 객체의 property 
//	  - type : 발생된 이벤트 종류
//	  - target : 이벤트를 발생시킨 객체
//	  - currentTarget : 현재 이벤트리스너를 실행하고 있는 DOM 객체
//	  - defaultPrevented : true/false 이벤트의 기본동작이 취소되었는지를 나타냄
// => 이벤트 객체의 메서드 
//	  - preventDefault() : 이벤트의 기본동작을 취소시켜줌
//	  - stopPropagation(): 이벤트 흐름(Propagation, 버블) 을 중단
//    - return false : e.preventDefault() + e.stopPropagation()	
// => 마우스포인터 위치인식
//    - e.pageX, e.pageY : 전체 Page 기준
//	  - e.clientX, e.clientY : 보여지는 화면 기준-> page Scroll 시에 불편함

function jsBDetail2(e, seq, spanId) {
	
	// ** 이벤트 속성값 확인하기 
	console.log('*** e.type => '+e.type);	
	console.log('*** e.target => '+e.target);
	console.log(`*** e.pageX=${e.pageX}, e.pageY=${e.pageY}`);
	console.log(`*** e.clientX=${e.clientX}, e.clientY=${e.clientY}`);

	$('#resultArea2').html('');
	
	if ( $('#'+spanId).html()=='' ) {
		// => 출력한 content 가 없음 : ajax 실행
		$.ajax({
	 		type:'Get',
	 		url:'jsbdetail',
	 		data: {
	 			seq:seq
	 		},
	 		success:(resultData) => {
	 			// => clear
	 			$('.content').html('');
	 			// => 출력
	 			$('#'+spanId).html(resultData.content);
	 		},
	 		error: () => {
	 			$('#resultArea2').html('~~ Error 발생 !!! ~~~');
	 		}
 		}); //ajax
	}else {
		// => 출력한 content 가 있음 : clear
		$('#'+spanId).html('');
	} 
} //jsBDetail2

// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 
// => div 출력위치 : 마우스 포인터 위치
// => 마우스포인터 위치인식
//	  - event객체 (이벤트핸들러 첫번째 매개변수) 가 제공
//    - e.pageX, e.pageY : 전체 Page 기준
//	  - e.clientX, e.clientY : 보여지는 화면 기준-> page Scroll 시에 불편함

// => MouseOver
function jsBDetail3(e, seq) {
	// 1) 마우스포인터 위치값 확인
	let mleft=e.pageX;
	let mtop=e.pageY;
	console.log('** mleft => '+mleft);
	console.log('** mtop => '+mtop);
	
	// 2) ajax
 	$.ajax({
 		type:'Get',
 		url:'jsbdetail?seq='+seq,
 		success:(resultData) => {
 			// span에 이전에 출력했던 글은 삭제후, 새글 출력 
 			$('.content').html('');
 			$('#content').html(resultData.content)
 			.css({
 				// div를 위치를 지정해서 표시 
 				display:'block',
 				left:mleft,
 				top:mtop
 			}); //css
 		},
 		error: () => {
 			$('#resultArea2').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
} //jsBDetail3
 
 // => MouseOut
 // => 출력되어있는 div 를 사라지도록 함
 function jsBDetail4() {
 
 	$('#content').css('display','none'); 
 	// jQuery 메서드
 	// => $('#content').hide() / $('#content').show()
 	
 } // jsBDetail4
 
// ** bCriList 를 Ajax로 구현
// => 검색조건 & Paging , Ajax 구현
function axBoardcri(url) {
 	$.ajax({
 		type: 'Get',
 		url: url,
 		success:(resultPage) => {
 			$('#resultArea1').html(resultPage);
 		},
 		error: () => {
 			$('#resultArea1').html('~~ Error 발생 !!! ~~~');
 		}
 	}); //ajax
 	$('#resultArea2').html('');
 } //axBoardcri
 
 
 