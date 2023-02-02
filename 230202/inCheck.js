/**
** 입력값의 무결성 확인
** member 무결성 확인사항
// ID : 길이(4~10), 영문자,숫자 로만 구성
// Password : 길이(4~10), 영문,숫자,특수문자로 구성, 특수문자는 반드시 1개 이상 포함할것
// Password2 : 재입력후 일치성 확인
// Name : 길이(2이상), 영문 또는 한글로 만 입력
// Info : 길이로 입력 여부만 확인 (X)
// Jno : select 를 이용 (X)
// BirthDay : 입력 여부 확인  ( length == 10 )
// Age: 정수의 범위  ( 숫자이면서, '.'이 없어야함 )  
// Point : 실수 ( 구간설정 100 ~ 10000 까지만 가능 )

** 작성 규칙
   => JavaScript function 으로 정의 하고 
      결과를 true or false 로 return
 */

// ** ID
function idCheck() {
   let id = document.getElementById('id').value;
   if (id.length < 4 || id.length > 10) {
      document.getElementById('iMessage').innerHTML = 'ID 의 길이는 4~10 입니다. !!!';
      // document.getElementById('id').focus();
      return false;
   } else if (id.replace(/[a-z.0-9]/gi, '').length > 0) {
      document.getElementById('iMessage').innerHTML = 'ID는 영문과 숫자로만 입력하세요. !!!';
      return false;
   } else {
      document.getElementById('iMessage').innerHTML = '';
      return true;
   }
} //idCheck

// ** Password : 길이4이상, 영문, 숫자와 특수문자는 반드시 1개 이상 포함할것
// => input Tag의 type password 의 경우에는 기본적으로 영문자판으로 입력됨
// => 특수문자 : HTML 특수문자 리스트표 참고 (http://kor.pe.kr/util/4/charmap2.htm)
function pwCheck() {
   let password = document.getElementById('password').value;
   if (password.length < 4 || password.length > 10) {
      document.getElementById('pMessage').innerHTML = 'Password 의 길이는 4~10 입니다. !!!';
      return false;
   } else if (password.replace(/[!-*.@]/gi,'').length >= password.length) { // 특수문자 반드시 포함
      document.getElementById('pMessage').innerHTML = 'Password 는 특수문자를 반드시 1개 이상 포함해야 합니다. !!!';
      return false;
   } else if (password.replace(/[a-z.0-9.!-*.@]/gi, '').length > 0) {
      document.getElementById('pMessage').innerHTML = 'Password 는 영문, 숫자, 특수문자로만 입력하세요. !!!';
      return false;
   } else {
      document.getElementById('pMessage').innerHTML = '';
      return true;
   }
} //pwCheck

// ** Password 재입력 확인 : password 와 동일성 확인
function pw2Check() {
   let password = document.getElementById('password').value;
   let password2 = document.getElementById('password2').value;
   if (password != password2) {
      document.getElementById('p2Message').innerHTML = 'Password가 일치하지 않습니다.';
      return false;
   } else {
      document.getElementById('p2Message').innerHTML = '';
      return true;
   }
}// pw2Check

// ** Name
function nmCheck() {
   let name = document.getElementById('name').value;
   if (name.length < 2) {
      document.getElementById('nMessage').innerHTML = '이름의 길이는 2글자 이상입니다.';
      return false;
   } else if (name.replace(/[a-z.가-힣]/gi, '').length > 0) {
      document.getElementById('nMessage').innerHTML = '이름은 영문 또는 한글로만 입력하세요.';
      return false;
   } else {
      document.getElementById('nMessage').innerHTML = '';
      return true;
   }
} //nmCheck

// ** Age : 정수
// => Number.isInteger(n) : 정수 확인가능_n 이 정수일때만 true
//    단, parseInt(point) 사용시 주의
//      - 실수 입력시 정수로 바뀌어처리됨
//      - 문자가 포함된 숫자도 앞쪽의 숫자값만 가져와 정수 return
function agCheck() {
   let age = document.getElementById('age').value;
   console.log('** parseInt(age) =>' + parseInt(age));
   console.log('** Number.isInteger(age) =>' + Number.isInteger(age));

   // ** 숫자가 아닌 값이 있는지 확인
   // => parseInt(point) 가 정확하게 숫자로만 입력된 경우에만 정수를 return 하지않기때문
   if (age.replace(/[^0-9]/g, '').length < age.length || 
      Number.isInteger(parseInt(age)) == false) {
      document.getElementById('aMessage').innerHTML = '나이는 숫자로만 입력하세요.';
      return false;
   } else {
      document.getElementById('aMessage').innerHTML = '';
      return true;
   }

} // agCheck

// ** Point : 실수
// => 실수, 범위확인하기 100~10000
// => parseFloat(point)
//      -> 오류일때 NaN return
//      -> 확인 : Number.isNaN(parseFloat(point))
//		-> 단, 숫자로 시작하면 뒤쪽에 문자가 섞여있어도 숫자값만 사용함 (NaN을 return 하지않음)
function poCheck() {
   let point = document.getElementById('point').value;
   console.log('** parseFloat(point) =>' + parseFloat(point));
   if (point.length < 1) {
      document.getElementById('oMessage').innerHTML = 'point를 입력하세요.';
      return false;
   } else if (Number.isNaN(parseFloat(point)) || 
         point.replace(/[^0-9./.]/g, '').length < point.length) {
      document.getElementById('oMessage').innerHTML = 'point를 숫자로 정확하게 입력하세요.';
      return false;
   } else if (parseFloat(point) < 100 || parseFloat(point) > 10000) {
      document.getElementById('oMessage').innerHTML = 'point의 범위를 벗어납니다. (100~10000)';
      return false;
   } else {
      document.getElementById('oMessage').innerHTML = '';
      return true;
   }
} //poCheck

// ** Birthday : 길이 10 인지 확인
function bdCheck() {
   let birthday = document.getElementById('birthday').value;
   if(birthday.length != 10) {
      document.getElementById('bMessage').innerHTML = 'birthday 를 정확하게 입력하세요.';
      return false;
   } else {
      document.getElementById('bMessage').innerHTML = '';
      return true;
   }
} // bdCheck


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 <!-- 
** 정규 표현식 (정규식:Regular Expression) 객체 : RegExp
=> 자바스크립트의 기본 내장 객체 중의 하나
=> 특정한 규칙을 가진 문자열 집합을 표현하는데 사용하는 형식
* 생성
   var regExp1= new RegExp('text') ;
   var regExp2= /text/ ; 
* 메서드   
   test() : 정규식과 일치하는 문자열이 있으면 true 아니면  false 를 return 
   exec() : 정규식과 일치하는 문자열을 return 
* 예제   
   var regExp= /script/ ; 
   var s='Javascript jQuery Ajax';
   
   var output = regExp.test(s) ;
   alert(output) ; 
* 그러나 주로 문자열의 메서드와 같이 사용됨
    
/.../ : 정규식 RegExp 의 리터럴

** 플래그 문자 
g : global, 전역비교
i : 대문자는 소문자 변환후 비교 (대/소문자 구분 없음)
m : 여러줄의 검사 수행
   ( 각줄을 개별문자로 인식하고 검사해줌
    예 : 'JavaScript\njQuery\nAjax' )

\. : . 문자 (. 는 한 문자를 의미하나 \. 는 . 문자를 의미함)
a-z : abcdefghijklmnopqrstuvwxyz 와 같음
0-9 : 0123456789 와 같음
: : : 문자
_ : _ 문자
- : - 문자
[~.~] : ~ 와 ~ , Or 의 묶음
[..] : Or 의 묶음. 안에 기록된 1가지외 중복 적용됨.
[^...] : 내부내용의 부정. 기록된 이외의 것을 찾음.
+ : 하나 이상의 반복적용. (단어(?) 찾음)

*/
