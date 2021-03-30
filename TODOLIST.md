# TODOLIST

* 목표: 7일차까지 기본기능 제공 계획을 짠다.
* 추가 목표: 7일차까지 구현된 기본기능을 확장한 확장 기능 제공 계획을 짠다.
* 제외할 목표: 부차기능, FE관련 기능, 선택과 집중에 반하는 기능


| 색인 | 작업                                                         | 상태 | 달성률 | 예상 완료일 | 완료일 | 작업 소요시간 |
| ----|-------------------------------------------------------- | ---- | -----: | ----------: | -----: | ------------- |
| 1|junit5 추가                                                |      |        |             |        |               |
| 2|CRUD api 추가 (추가 요건 들어올 것을 대비한 설계, 확장가능한 설계) |      |        |             |        |               |
| 3|**!(부차기능)**Test기반으로 RestDoc 추가                                  |      |        |             |        |               |
| 4|logback 기반으로 의미있는 로그 추가                            |      |        |             |        |               |
| 5|/ path진입시 resources/static 경로의 index.html 출력.      |      |        |             |        |               |
| 6|di                                                         |      |        |             |        |               |
| 7|DispatcherServlet 구현 (di , mvc 분리해야할 듯.)           |      |        |             |        |               |
| 8|IoC container 구현 -> DI 제공 (reflection 사용.)           |      |        |             |        |               |
| 9|정적 resource와 json응답처리 가능해야함. Fasterxml Jackson, resources/static 하위경로의 리소스를 내려줄 수 있어야함( view resolver기능) |      |        |             |        |               |
| 10|**!(부차기능)**LifeCycle 관련 기능 추가 바람. (요구조건에는 없음. 그러나, app자체의 start가 완료됐을 때, log도 찍고 etc.. 해줘야 할 듯. 공통로직 관련되어...) |      |        |             |        |               |
| 11|Tomcat기반으로 부팅되는 개념인 듯. (별도 모듈화가 좋을 듯. ex: was-bootstraper) | WORKING | 20% | 03-27 |  | 1h |
| 13|@Service, @Repository 클래스 스캔  빈 주입시 Service가 다른 Service를 주입하는 경우 고려 @Inject가 없는 경우 기본생성자를 이용하여 빈으로 등록 그 외 경우는 initializeInjectedBeans로 빈 등록 @Inject에 필요한 객체를 찾아서 주입 / 생성 기존의 @Controller를 스캔하는 ControllerScanner 클래스를 확장된 BeanScanner와 통합 |      |        |             |        |               |
| 14|@Configuration 어노테이션으로 빈 설정                     |      |        |             |        |               |
| 15|di , mvc 분리해야할 듯.                                   |      |        |             |        |               |
| 16|DI xml은 제외할 것.                                       |      |        |             |        |               |
| 17|TomcatWebServer -> WebApplicationServer 로 변경, Tomcat 은 구현체중 하나로 간다. WebApplicationServer변경시 어떻게 대응할 것인지 고려함. SPI로 기본적으로는 Tomcat으로 구동되게 한다. |      |        |             |        |               |
| 18|Gradle 개념 습득                                          |      |        |             |        |               |
| 19|logback 사용법 습득 및 적용                               | DONE | 100% | 03-26 | 03-26 | 1h |
| 20|**!(부차기능)**<br />servlet 3.0 NIO Servlet으로 구현할 까..? Transaction 필요도 없잖아. |      |        |             |        |               |
| 21|**!(부차기능)**<br />HandlersTypes interface for reducing class scanning time<br />https://opennote46.tistory.com/168 |      |        |             |        |               |
| 22|WebAppBackendFramework이므로, servlet 개념을 몰라도 작성가능하게 개발돼야한다. |      |        |             |        |               |
| 23 |gradlew 개념 습득 | | | | | |
| 24 |spring-bean 에서 존재하는 bean managing 부분 이전, WebApplicationContext에 bean을 관리 + scope존재  + 이 context는 ServletContext를 통해 저장됨. (FrameworkServlet 참고) Spring-context module에서 Bean관리 annot포함됨. Bean scope관련 로직도 포함됨 + FW에 대응되는 context관련됨.<br />ctx.registerBean(O) 처럼 webApplicationContext에 bean이 자동 등록될 것. | | | | | |
| 25 |bean 처리에 대한 기본 기능 스펙정리<br />multi same type bean 에 대한 충돌 처리<br />bean id 처리<br />bean scope처리<br />bean 사용할 때 편하게 처리<br />bean | | | | | |
| 26 |**!(부차기능)**<br />transaction 처리 가 가능하게끔 확장 고려(Transaction - AOP - Bean) (bean입장에서는 transaction 처리가 될 것을 고려한 개발할 필요가 없다. 관심사가 나뉘어있음. Transaction moudle 측이 알아서 bean이 변화될 때 <<<< 처리를 추가로 하) | | | | | |
| 27 |**!(부차기능)**<br />기본적인 aop 모듈은 필요할 듯. 공통 호출로직, 공통 비지니스 로직 상황별 로깅 관련 지원.<br />spring은 proxy기반 aop이므로, 기존 비지니스로직(target)은 proxy화 되고, proxy에서 상황별 advice로직과 본래 target 로직이 수행될 것이다. | | | | | |
| 29 |**di module**<br />@Service, @Repository (DB말고 file기반 처리 추가예정), @Controller, @Configuration with @Bean, @Autowired, @Qulifier 필수 제공<br />미지원: @Lazy - MSA에서는 Lazy는 좋지 못하다. Readiness로 진입했을 때 최적의 성능을 보여주길 바람. @Component - 너무 포괄적인 anno이므로 제거. | | | | | |
| 30 |**!(부차기능)**<br />**repo module**<br />@Repository에 대응될 부분. 기본 vendor는 file or json형태에 대응쉬운 mongodb예정. | | | | | |
| 31 |**mvc module**<br />Controller내에서 **@RequestMapping** 으로 class, method에 처리. **@RequestBody** 로 json input을 java Object로 자동전환.<br />**@ResponseBody**로 java Object output을 json output으로 자동전환 (Jackson이 사용됨)<br />**@PathVariable**로  @RequestMapping에서 전달된 url내의 variable을 로직에서 제공받음.<br />Controller에 대해:<br />이 제품은, api호출시, data만 반환하면 되므로 RestController같은 annotation만 지원하면 된다. 차후 확장기능으로 view resolver가 추가되면, Controller기능도 추가지원할 수 있을 것... | | | | | |
| 32 |**!(부차기능)**<br />validation module**<br />유효성 검사 (di 책임은 아닌듯.) **@Valid, @InitBinder**<br />외부 origin에서의 접근 허용부분 처리**@CrossOrigin** | | | | | |
| 33 |**!(부차기능)**<br />mvc (동기)건 webflux (비동기) 상관없게 동작하게 | | | | | |
| 34 |context @scope 관련 기능으로 request, session정도는 구분지을 수 있게. | | | | | |
| 35 |war로 패키징될 때, web.xml에 servlet지정 및 적용 테스트. | | | | | |
| 36 |WebFlux를 밴치마킹하려면, RouteFunctions, RouteFunction 참고바람. | | | | | |
| 37 |**#woowahan-context** module 새로 추가. container-bootstrapper <-> context <-> di-framework  로 진행하고,  di-framework입장에서는, context가 servlet's context인 지, 다른 container 의 context를 통해서인 지 모르게 끔. | | | | | |
| 38 |DispatcherServlet구현시, HandlerMapping기반으로 dispatch되게끔 작성. | | | | | |
| 39 |Bean이 Context에 저장되게끔 진행.<br />BeanDefinition, BeanDefinition 기반으로 getBean이 처리되는 것을 원함.<br />Singleton, prototype만 지원. (이 과제는 session엔 관심이 없다.) | | | | | |
| 40 |bean Singleton 내부에 prototype일 경우, 항상 새 field값을 사용하게끔 옵션 제공.<br />singleton은 web-application-context상의 map으로 관리. prototype인 경우 그냥 생성해서 제공. 따로 관리안함. | | | | | |
| 41 |bean name을 위해 각 @Component의 value 는 name으로 식별될 것. bean name auto define 기본전략은,  Bean가 붙은 ClassSimpleName, methodName, fieldName 임. | | | | | |
| 42 |**(!오버스펙)** **context Hierarchy**<br />servlet-context에서 root-web-applicatoin-context, dispatcher's web-application-context를 구분하려면 아래를 참고.<br />https://codediver.tistory.com/147<br />https://jaehun2841.github.io/2018/10/21/2018-10-21-spring-context/#webxml-%EC%9D%B4%EB%9E%80<br />https://live-everyday.tistory.com/164 | | | | | |