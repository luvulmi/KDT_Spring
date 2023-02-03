/**
// ** JQuery Ajax
// => 메서드형태로 지원 $.ajax(....)
// => 메서드의 매개변수를 통해 필요한 속성 설정
// => 설정 : method(Type:Get/Post), url, data(parameter) , result  
 */
$(function () {
  $('#jqTest').click(function () {
    $.ajax({
      type: 'Get',
      url: 'ax03_parameterResult.jsp',
      data: {
        id: $('#id').val(),
        password: $('#password').val(),
        name: $('#name').val()
      },
      success: function (result) {
        alert('~~ JQuery Ajax Test 성공 ~~');
        $('#resultArea').html('** 서버 Response_JQ => <br>' + result);
      },
      error: function () {
        /*없는 url 로 Test*/
        alert('~~ JQuery Ajax Test 실패 ~~');
        $('#resultArea').html('** 서버 Response_JQ => Error !!! <br>');
      }
    }); //ajax
  }); //click
}); //ready

/*
** $.ajax 메서드   ******************
 *  
 * 1. 기본형식
 * $.ajax({옵션속성:값}); 
 * => $.ajax 함수는 XMLHttpRequest 객체를 반환함.
 * 
 * 2. 옵션속성 
 * => https://hsj0511.tistory.com/205 참고
 * 
 * url:문자열 - 요청url 설정

=> type:문자열 - GET/POST설정
   data:객체,문자열 - 요청 매개변수 설정 
   dataType: return Data Type - xml, html, json, jsonp, text, script
   success:함수
       => 성공시 호출할 함수 설정
       => 매개변수가 응답 결과를 받아줌 
   error:함수 - 실패시 호출할 함수 설정
   async:불리언 - 동기/비동기 설정 (True/False)
   beforeSend:HTTP 요청전에 발생하는 이벤트 핸들러
***************************************
*/