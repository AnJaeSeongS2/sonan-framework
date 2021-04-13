# sonan-web (수정: `sonan-di-framework`에서 이름 변경됨.)

## 구현 방향 설정

---
* `TODOLIST.md`를 작성하고, 지속적으로 관리할 것이다. (DONE) 
  * 해당 파일에서 자세한 개발 이력 확인 가능
* di에 대한 기능 구현을 우선 진행. (DONE)
* 요구사항을 매번 작업시작전에 재확인할 것이다. (DONE)
* 모듈 세분화하고 책임을 잘 분배할 것이다. (DONE)
* 과제의 의도는 프로그래밍 습관체크라는 점을 명심. 구현의 아이디어도 과제 내용에 대해서 확장을 염두해 둬야함. 프레임워크 기본 기능을 확장가능하게 개발해놓는 것을 목표로 설정할 것. 도메인의 적절한 분리 ex) Message 인터페이스 기반 컴포넌트간 통신 | 타 모듈에서는 Servlet으로 컨테이너가 뜨는 지 조차 몰라도 되는 구조. (DONE)

---

## 설계 참고 사항

---
*  [spring docs](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core) 참고 
*  IoC Container 역할을 제대로하는 web framework를 구현하는 것이 본 토이 프로젝트의 목표.

## 의존성

---
1. spring 의존성은 미포함.
2. junit5, logback, reflections, jackson 의존성 포함.