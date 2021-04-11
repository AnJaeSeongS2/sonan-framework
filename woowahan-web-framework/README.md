# woowahan-web-framework (수정: `woowahan-di-framework`에서 이름 변경됨.)

## 구현 방향 설정
* `TODOLIST.md`를 작성하고, 지속적으로 관리할 것입니다. (DONE) 
  * 해당 파일에서 자세한 개발 이력 확인 가능
* di에 대한 기능 구현을 우선 진행. (DONE)
* 요구사항을 매번 작업시작전에 재확인할 것입니다. (DONE)
* 모듈 세분화하고 책임을 잘 분배할 것입니다. (DONE)
* 과제의 의도는 프로그래밍 습관체크라는 점을 명심. 구현의 아이디어도 과제 내용에 대해서 확장을 염두해 둬야함. 프레임워크 기본 기능을 확장가능하게 개발해놓는 것을 목표로 설정할 것. 도메인의 적절한 분리 ex) Message 인터페이스 기반 컴포넌트간 통신 | 타 모듈에서는 Servlet으로 컨테이너가 뜨는 지 조차 몰라도 되는 구조. (DONE)
* `woowahan-di-support` 을 사용하지 않는다. (Spring-core dependency 제거.) (DONE)




---
## 원본 문서 
`woowahan-di-framework`(수정: `woowahan-web-framework`으로 모듈 변경됨.) 은 `HttpServlet` 의 빈 껍데기뿐인 구현체 `DispatcherServlet`를 가지고 있습니다.

```java
public class DispatcherServlet extends HttpServlet {
}
```

![mvc-context-hierarchy](https://docs.spring.io/spring/docs/current/spring-framework-reference/images/mvc-context-hierarchy.png)

[Spring Framework Docs : Web on Servlet Stack](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc) 단원을 참고하셔도 좋고, 직접 구성하셔도 좋습니다.

`DispatcherServlet` 을 구현하여 아래 요구사항을 만족시켜주세요.

## 요구사항

1. `IoC(Inversion of Control) container` 를 구현하여 `의존성 주입(DI, Dependency Injection)` 을 할 수 있도록 합니다.
    - `Java Reflection` 을 사용해주세요.
    - `@Controller`, `@Service`, `@Component` 어노테이션을 사용한 객체는 반드시 `IoC container` 의 관리 대상이 되어야 하며, 이 외의 관리 대상은 자유롭게 구성해주세요.
    - `com.woowahan.framework.web.annotation` 에 추가 어노테이션을 구성하여도 됩니다.
    
2. `@RequestMapping` 어노테이션을 사용하여 `GET`, `POST` 요청을 처리할 수 있도록 합니다.
    - `@RequestMapping` 은 `@Controller` 어노테이션을 사용한 `class` 에서만 동작하도록 합니다.
    - `@RequestMapping` 의 `value` 는 `요청 path` 와 매칭되며, `methods` 는 `요청 method` 와 매칭됩니다.
    - `@RequestMapping`의 `@Target`이 `{ElementType.METHOD, ElementType.TYPE}` 인 점을 고려하여 hierarchy 구조를 작성할 수 있도록 합니다.
        - 예를 들어 아래 구조는 `/depth1/depth2` 요청을 처리할 수 있어야 합니다.
        ```java
          @RequestMapping("/depth1")
          public class ExampleController {
              
              @RequestMapping("/depth2")
              public ExampleResponse example() {
                  ...
              }       
          }
        ```
    
3. 정적 Resource 와 Json 응답을 처리할 수 있어야 합니다.
    - Json 응답 처리는 `Fasterxml Jackson` 을 사용해주세요.
    - `resources/static` 하위 경로의 리소스를 내려줄 수 있어야 합니다. 

4. logback 프레임워크(http://logback.qos.ch)를 이용하여 의미있는 로그를 남깁니다.
5. JUnit5 를 이용해서 테스트 케이스를 작성합니다.

## 안내

- 요구사항을 구현함에 있어 충분한 의존성이 제공되고 있습니다. 현재 정의된 의존성 외 외부 의존성은 추가할 수 없습니다.
- 사전 작성된 코드는 자유롭게 변형, 분리, 리펙토링 되어도 무방합니다.
- 사전 제공된 클래스를 모두 사용하지 않아도 됩니다.
- `woowahan-di-support` 에서 제공하는 클래스를 이용할 수 있습니다.