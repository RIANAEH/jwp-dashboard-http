# 톰캣 구현하기

## 기능 요구 사항

### 1단계 - HTTP 서버 구현하기

- [X] http://localhost:8080/index.html 페이지에 접근 가능하다.
  - [X] Http Method가 GET인지 확인한다.
  - [X] / 일 경우 안녕 문자열을 응답하고 그 외에는 파일을 읽어온다. 
- [X] 접근한 페이지의 js, css 파일을 불러올 수 있다.
  - [X] css: text/css
  - [X] js: application/javascript
- [X] uri의 QueryString을 파싱하는 기능이 있다.
  - [X] http://localhost:8080/login?account=gugu&password=password으로 접속하면 로그인 페이지(login.html) 페이지에 보여준다.
  - [X] Query String을 파싱해서 아이디, 비밀번호가 일치하면 회원을 조회한 결과를 출력한다.
  - [X] Query String 형식에 대한 예외처리를 추가한다. 

### 2단계 - 로그인 구현하기

- [X] 로그인을 할 수 있다. 
  - [X] 상태 응답 코드를 302로 반환한다. 
  - [X] 로그인에 성공하면 /index.html로 리다이렉트한다.
  - [X] 로그인에 실패하면 401.html로 리아디렉트한다.
- [X] http://localhost:8080/register으로 접속하면 회원가입 페이지(register.html)를 보여준다.
- [X] POST 방식으로 회원가입을 할 수 있다. 
  - [X] request header를 읽는다.
  - [X] request body를 읽는다.
  - [X] request body를 Content-Type에 맞게 파싱한다. 
  - [X] 회원가입을 완료하면 /index.html로 리다이렉트한다.
  - [X] 회원가입에 실패하는 경우 /register.html로 리다이렉트한다. (중복된 아이디)
- [X] 로그인을 POST 방식으로 변경한다.
- [X] 로그인에 성공하면 HTTP Reponse의 헤더에 Set-Cookie가 존재한다.
  - [X] HTTP Request Header의 Cookie에 JSESSIONID가 없므면 HTTP Response Header에 Set-Cookie를 추가한다.
  - [X] 이미 있으면 로그인한 사용자로 간주하고 /index.html로 리다이렉트한다.
- [X] 서버에 세션을 관리하는 클래스가 있고, 쿠키로부터 전달 받은 JSESSIONID 값이 저장된다.
  - [X] 로그인된 상태에서 /login 페이지에 GET으로 접근하면 이미 로그인한 상태이기 때문에 /index.html로 리다이렉트한다.

### 3단계 - 리팩터링

- [X] HTTP Request, HTTP Response 클래스로 나눠서 구현했다. 
- [X] Controller 인터페이스와 RequestMapping 클래스를 활용하여 if절을 제거했다.
  - [X] 기존의 Controller 인터페이스를 변형해 request, response를 받도록 변경한다.
  - [X] Controller 인터페이스를 구현하는 AbstractController를 만들고 여기서 메서드에 따른 분기를 진행한다. 
  - [X] 각 Controller 구현체에는 doGet(), doPost() 만을 구현한다. 

### 4단계 - 동시성 확장하기

- [X] 스레드 풀을 사용해 스레드를 재사용하도록 구현한다.
  - [X] acceptCount(최소 100), maxThreads(최소 200)를 설정한다.
- [X] SessionManager에서 session를 스레드 세이프하게 구현한다.
  - [X] 동시성 컬렉션(Concurrent Collections)를 적용한다.

### 추가로 고려해볼 수 있는 부분

- [X] 예외 처리를 세분화 한다. 
  - [X] 지원하지 않는 HTTP Method인 경우 405 코드와 index.html을 응답한다.
  - [X] NOT FOUND는 404 코드와 404.html을 응답한다.
- [ ] 더 다양한 Content Type을 지원한다. 
  - [X] svg
- [ ] RFC2616을 참고해 이름을 리팩토링한다.
- [X] 비지니스 로직 상 처리하지 못한 예외가 발생하면 500.html로 리다이렉트한다.
  - [X] 무한 리다이렉트 문제가 발생해 500 코드를 응답하도록 변경한다. 
- [ ] DTO를 만들어 요청 데이터에 대한 검증을 한다.
- [X] BufferedReader와 같은 자원을 명시적으로 회수한다.
- [X] Controller, Service를 싱글톤으로 만든다.

## 요청 처리 과정

![IMG_6336](https://user-images.githubusercontent.com/45311765/188080053-6a203f41-3def-4989-a236-b9976ce0f4a7.jpg)
