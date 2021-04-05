# TODOLIST

* 목표: 7일차까지 기본기능 제공 계획을 짠다.  (**지체됨** > 최종 제출은 기본기능 구현까지만으로 마무리할 것. 시간과 무관히 지속적으로 확장, 유지보수를 염두한 개발을 한다.)
* 추가 목표: 7일차까지 구현된 기본기능을 확장한 확장 기능 제공 계획을 짠다. (**지체됨** > 최종 제출은 기본기능 구현까지만으로 마무리할 것. 시간과 무관히 지속적으로 확장, 유지보수를 염두한 개발을 한다.)
* 제외할 목표: 부차기능, FE관련 기능, 선택과 집중에 반하는 기능
* **2일 남은 상황의 목표**: 1일 > BeanDefinition기반으로 AutoWired annotation이 붙어있는 곳에 injection. 2일 > jackson 사용부 web module측에 추가 및 app 개발완료.


| 색인 | 작업                                                         | 상태 | 달성률 | 예상 완료일 | 완료일 | 작업 소요시간 |
| ----|-------------------------------------------------------- | ---- | -----: | ----------: | -----: | ------------- |
| 1|junit5 추가                                                | DONE | 100% | 04-04 | 04-04 | 1h |
| 2|CRUD api 추가 (추가 요건 들어올 것을 대비한 설계, 확장가능한 설계) |      |        |             |        |               |
| 3|**!(부차기능)**Test기반으로 RestDoc 추가                                  |  |        |             |        |               |
| 4|logback 기반으로 의미있는 로그 추가                            | WORKING | 50% | 04-04 |        | 30m |
| 5|/ path진입시 resources/static 경로의 index.html 출력.      |      |        |             |        |               |
| 6|di                                                         | WORKING | 50% | 04-04 |        |               |
| 7|DispatcherServlet 구현 (di , mvc 분리해야할 듯.)           |      |        |             |        |               |
| 8|IoC container 구현 -> DI 제공 (reflection 사용.)           | WORKING | 50% | 04-04 |        |               |
| 9|정적 resource와 json응답처리 가능해야함. Fasterxml Jackson, resources/static 하위경로의 리소스를 내려줄 수 있어야함( view resolver기능) |      |        |             |        |               |
| 10|**!(부차기능)**LifeCycle 관련 기능 추가 바람. (요구조건에는 없음. 그러나, app자체의 start가 완료됐을 때, log도 찍고 etc.. 해줘야 할 듯. 공통로직 관련되어...) |      |        |             |        |               |
| 11|Tomcat기반으로 부팅되는 개념인 듯. (별도 모듈화가 좋을 듯. ex: was-bootstraper) | WORKING | 20% | 03-27 |  | 1h |
| 13|@Service, @Repository 클래스 스캔  빈 주입시 Service가 다른 Service를 주입하는 경우 고려 @Inject가 없는 경우 기본생성자를 이용하여 빈으로 등록 그 외 경우는 initializeInjectedBeans로 빈 등록 @Inject에 필요한 객체를 찾아서 주입 / 생성 기존의 @Controller를 스캔하는 ControllerScanner 클래스를 확장된 BeanScanner와 통합 |      |        |             |        |               |
| 13.5 |Component 등록과정에서 고려할 점. <br />inner class, anonymous class |  | | | | |
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
| 25 |bean 처리에 대한 기본 기능 스펙정리<br />multi same type bean 에 대한 충돌 처리<br />bean id 처리<br />bean scope처리<br /> | DONE | 100% | 03-30 | 04-01 | (곂침) |
| 26 |**!(부차기능)**<br />transaction 처리 가 가능하게끔 확장 고려(Transaction - AOP - Bean) (bean입장에서는 transaction 처리가 될 것을 고려한 개발할 필요가 없다. 관심사가 나뉘어있음. Transaction moudle 측이 알아서 bean이 변화될 때 <<<< 처리를 추가로 하) | | | | | |
| 27 |**!(부차기능)**<br />기본적인 aop 모듈은 필요할 듯. 공통 호출로직, 공통 비지니스 로직 상황별 로깅 관련 지원.<br />spring은 proxy기반 aop이므로, 기존 비지니스로직(target)은 proxy화 되고, proxy에서 상황별 advice로직과 본래 target 로직이 수행될 것이다. | | | | | |
| 29 |**di module**<br />@Service, @Controller, @Configuration with @Bean, @Autowired, @Qulifier 필수 제공<br />미지원: @Lazy - MSA에서는 Lazy는 좋지 못하다. Readiness로 진입했을 때 최적의 성능을 보여주길 바람. | | | | | |
| 30 |**!(부차기능)**<br />**repo module**<br />@Repository에 대응될 부분. 기본 vendor는 file or json형태에 대응쉬운 mongodb예정. | | | | | |
| 31 |**mvc module**<br />Controller내에서 **@RequestMapping** 으로 class, method에 처리. **@RequestBody** 로 json input을 java Object로 자동전환.<br />**@ResponseBody**로 java Object output을 json output으로 자동전환 (Jackson이 사용됨)<br />**@PathVariable**로  @RequestMapping에서 전달된 url내의 variable을 로직에서 제공받음.<br />Controller에 대해:<br />이 제품은, api호출시, data만 반환하면 되므로 RestController같은 annotation만 지원하면 된다. 차후 확장기능으로 view resolver가 추가되면, Controller기능도 추가지원할 수 있을 것... | | | | | |
| 32 |**!(부차기능)**<br />validation module**<br />유효성 검사 (di 책임은 아닌듯.) **@Valid, @InitBinder**<br />외부 origin에서의 접근 허용부분 처리**@CrossOrigin** | | | | | |
| 33 |**!(부차기능)**<br />mvc (동기)건 webflux (비동기) 상관없이 component재사용을 고려해 개발. | | | | | |
| 34 |**!(부차기능)**context @scope 관련 기능으로 singleton, prototype분만 아니라, request, session등도 구분가능하게 변경. | | | | | |
| 35 |war로 패키징될 때, web.xml에 servlet지정 및 적용 테스트. | | | | | |
| 36 |WebFlux를 밴치마킹하려면, RouteFunctions, RouteFunction 참고바람. | | | | | |
| 37 |**#woowahan-context** module 새로 추가. container-bootstrapper <-> context <-> di-framework  로 진행하고,  di-framework입장에서는, context가 servlet's context인 지, 다른 container 의 context를 통해서인 지 모르게 끔. | DONE | 100% | 03-30 | 04-01 | 30m |
| 38 |DispatcherServlet구현시, HandlerMapping기반으로 dispatch되게끔 작성. | | | | | |
| 39 |Bean이 Context에 저장되게끔 진행.<br />BeanDefinition, BeanDefinition 기반으로 getBean이 처리되는 것을 원함.<br />Singleton, prototype만 지원. (이 과제는 session엔 관심이 없다.) | DONE    |   100% |       03-30 | 04-01 | 2h |
| 40 |bean Singleton 내부에 prototype일 경우, 항상 새 field값을 사용하게끔 옵션 제공.<br />singleton은 web-application-context상의 map으로 관리. prototype인 경우 그냥 생성해서 제공. 따로 관리안함. | | | | | |
|      |                                                              | | | | | |
| 42 |**(!오버스펙)** **context Hierarchy**<br />servlet-context에서 root-web-applicatoin-context, dispatcher's web-application-context를 구분하려면 아래를 참고.<br />https://codediver.tistory.com/147<br />https://jaehun2841.github.io/2018/10/21/2018-10-21-spring-context/#webxml-%EC%9D%B4%EB%9E%80<br />https://live-everyday.tistory.com/164 | WORKING | 100% | 03-30 | 04-01 | 3h |
| 43 |woowahan-java-util 추가. <br />NotNull, Nullable 가독성, 등을 위해 추가. | DONE | 100% | 03-31 | 03-31 | 30m |
| 44 |Mockito 는 외부종속성이지만, test에만 사용하므로 요구사항 구현 제한에서 빠질 수 있다. 이를 활용해 ServletContext같은 것의 mocking처리를 한다. | WORKING | 60% | 03-31 | ~ | 30m |
| 45 |1. BeanIdentifier : ClassName:BeanName<br />2. BeanDefinition : ClassName:BeanName:Scope<br />3. BeanDefinitionRegistry 구현체 추가. (ex: GenericApplicationContext)<br />bean에 대한 정보를 GenericApplicationContext에서 관리. | DONE | 100% | 03-31 | 04-01 | 3h |
| 46 |bean 등록에 대한 작성을 annotation기반으로 진행할 탠데, compile타임에 Same BeanIdentifier exception 이 발생해야한다. runtime에 문제생기면 상당히 개발하기 불편하다. |  |  |  |  |  |
| 47 |logback-support 모듈 추가. <br />logbook 사용에 공통화될 부분, 및 정책을 이 모듈에 공통화. | DONE | 100% | 04-01 | 04-01 | 30m |
| 48 |BeanDefinition은 ApplicationContext측에서의 classLoader 기반으로 정의되어야 한다.<br />BeanIdentifier는 Class<?> 를 보유하면 ClassLoading을 ApplicationContext측이 아닌, BeanIdentifier를 생성한 측에서 해버린다. Class<? >이 아닌 String className기반으로 변경이 필요하다. 그이후, 확인된 classLoader기반으로 Class.forName으로 불러와야한다. | DONE | 100% | 04-01 | 04-01 | 1h |
| 49 |JAVA지만, Scala나 Kotlin 언어처럼 @Nullable을 추가하는 정책으로 변경.<br /> | DONE | 100% | 04-01 | 04-01 | 20m |
| 50 |java언어이므로 (Default는 nullable인 java...) field/method/param, etc..에 @NotNull을 추가하는 정책을 취했는데, 이게 가독성을 해치고 작업 능률을 떨어트린다. java지만 default는  notNull인 것으로 취급하며 진행한다. 차후 @Nullable, @NotNull 이 compile타임에 영향을 주면 될 것. | DONE | 100% | 04-01 | 04-01 | 10m |
| 51 |logback 처리될 로직은 start, end사이에 공통화가 가능해 보인다. aop개념을 짧은시간내에 접목시킬 수 있는 지 확인 할 것. | | | | | |
| 52 |ComponentScan을 통해 Component들이 Bean으로서 BeanDefinition이 적절하게 등록되게 변경 | DONE | 100% | 04-04 | 04-05 | 3h |
| 53 |**BeanDefinition 등록 전략.**<br />spring 에서는 bean definition을 classLoading없이 Bean MetadataReading하는 매커니즘을 통한 file io기반으로 진행하게된다. <br />이것을 구현하기란 규모가 큰 구현이므로, 이대신 별도 classloader를 두고, 이 별도 classloader기반으로 BeanDefinition이 관리되고, 이를 통해 Bean init과 Bean 관리가 됨을 보여줄 것이다. **이 상대적으로 규모가 작게 개발하는 별도 classloader는, 향후 MetadataReading 매커니즘과 유사한 구현으로 리팩토링 가능하게끔 염두해서 개발되어야한다.**<br /><br />Metadata는 별도 meta관리용 classloader를 통해 해당 classloader에 basepackage로 지정된 package하위 모든 class를 읽히게 하므로써 해당 framework의 동작은 별도 classloader가 활용됨을 정책으로 삼음.<br />따라서, class loading 시점에 동작되는 것들 (static field, static {} )을 사용한 class를 bean으로 등록하는 것을 권장하지 않음.  해당 동작을 유사히 구현하려면 contructor를 통해 유사하게 진행할 것.<br /><br />ResourceLoader마냥, classLoader를 지정하지 않으면, Thread.currentThread.getContextClassLoader()가 default이다.<br />basePackage 기반으로 별도 classloader가 scan을 할때는, 외부라이브러리로 등록돼있는 *`org.reflections:reflections:0.9.11`* 을 사용한다.<br />해당 classloader에서는 loading이후 resolve할 필요 없다. | DONE | 100% | 04-04 | 04-05 | 7h |
| 54 |기존 ServletWebServerApplicationContext (on Spring-boot)는 너무 하는 일이 많은 class이다. webserver도 만들고, context도 관리하고.. context본연의 기능만 남긴 ApplicationContext를 만들고. ServletContextInitializer를 생성해 제공도 하고... webserver만드는 것은 책임을 다른 클래스에 위임하게 한다.<br /> |  |  |  | | |
| 55 |Component scan 이 쉽게끔, Component 취급되는 bean들은 enum BeanRegistrable으로 관리하고 있는다. | DONE | 100% | 04-04 | 04-04 | 30m |
| 56 |@Bean 만 ElementType.METHOD 인 관계로 차후 확장할 부분으로 넘겨둔다. 현재로서는 자체 생산하는 Class에 대해서만 Bean으로서 관리되게끔 진행.<br />차후 추가기능으로 @Bean을 지원할 예정. |  |  |  |  |  |
| 57 |[container-bootstrapper] ServletBootstrapperDefault가 booting하면서 servletContext를 이용해 제대로 BeanDefinition을 등록하게끔 구현 및 검증. | DONE | 100% | 04-05 | 04-05 | 2h |
| 58 |[java-util] reflection invokeMethod, getField, setField 관련 유틸 추가. 및 XXXXAnyway 버전의 method 들은 테스트에서만 사용하는 reflection임을 명시. | DONE | 100% | 04-05 | 04-05 | 1h |

