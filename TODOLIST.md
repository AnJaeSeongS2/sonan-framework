# TODOLIST

* 목표: 7일차까지 기본기능 제공 계획을 짠다.  (**지체됨** > 최종 제출은 기본기능 구현까지만으로 마무리할 것. 시간과 무관히 지속적으로 확장, 유지보수를 염두한 개발을 한다.)
* 추가 목표: 7일차까지 구현된 기본기능을 확장한 확장 기능 제공 계획을 짠다. (**지체됨** > 최종 제출은 기본기능 구현까지만으로 마무리할 것. 시간과 무관히 지속적으로 확장, 유지보수를 염두한 개발을 한다.)
* 제외할 목표: 부차기능, FE관련 기능, 선택과 집중에 반하는 기능
* **2일 남은 상황의 목표**: 1일 > BeanDefinition기반으로 AutoWired annotation이 붙어있는 곳에 injection. 2일 > jackson 사용부 web module측에 추가 및 app 개발완료.
* Route 처리 규칙 변경 안내
  routing 속도 개선을 위해 /shops/{id} 가 아닌 /shops/@{id} 처럼 #을 encodedvariable앞에 붙이도록 정책화.
* **제출시점에 마무리 못한 부분 : 추가 소요시간 1일 예상.**
  * View Resolver 동작처리
  * Response 전달
  * View Resovler, 사용자 app의 테스트 코드 작성


| 색인 | 작업                                                         | 상태 | 달성률 | 예상 완료일 | 완료일 | 작업 소요시간 |
| ----|-------------------------------------------------------- | ---- | -----: | ----------: | -----: | ------------- |
| 1|junit5 추가                                                | DONE | 100% | 04-04 | 04-04 | 1h |
| 2|CRUD api 추가 (추가 요건 들어올 것을 대비한 설계, 확장가능한 설계) | TIMEOUT | 50% | 04-07 | 04-08 | 2h |
| 3|**!(부차기능)**Test기반으로 RestDoc 추가                                  | TIMEOUT | 0% | x | x | x |
| 4|logback 기반으로 의미있는 로그 추가                            | DONE | 100% | 04-04 | 04-08 | 30m |
| 5|/ path진입시 resources/static 경로의 index.html 출력. InternalResourceViewResolver 구현할 것. 우선순위는 늦추도록.      | TIMEOUT | 30% | 04-07 | 04-08 | 30m |
| 6|di (context 모듈 구현)                                                         | WORKING | 100% | 04-04 | 04-08 | 다른 task에서 계산됨. |
| 7|DispatcherServlet 구현 (di , mvc 분리해야할 듯.)           | WORKING | 50% | 04-07 | x | 3h |
| 8|IoC container 구현 -> DI 제공 (reflection 사용.)           | DONE | 100% | 04-04 | 04-08 | 다른 task에서 계산됨. |
| 9|정적 resource와 json응답처리 가능해야함. Fasterxml Jackson, resources/static 하위경로의 리소스를 내려줄 수 있어야함( view resolver기능) | TIMEOUT | 30% | 04-07 | x | 다른 task에서 계산됨. |
| 10|**!(부차기능)**LifeCycle 관련 기능 추가 바람. (요구조건에는 없음. 그러나, app자체의 start가 완료됐을 때, log도 찍고 etc.. 해줘야 할 듯. 공통로직 관련되어...)<br />ControllerLifecylceInvocation은 추가됐다. | DONE | 100% | 04-07 | 04-07 | 1h |
| 11|Tomcat기반으로 부팅되는 개념인 듯. (별도 모듈화가 좋을 듯. ex: was-bootstraper) | WORKING | 20% | 03-27 | x | 1h |
| 13|@Service, @Repository 클래스 스캔  빈 주입시 Service가 다른 Service를 주입하는 경우 고려 @Inject가 없는 경우 기본생성자를 이용하여 빈으로 등록 그 외 경우는 initializeInjectedBeans로 빈 등록 @Inject에 필요한 객체를 찾아서 주입 / 생성 기존의 @Controller를 스캔하는 ControllerScanner 클래스를 확장된 BeanScanner와 통합 | DONE | 100% | 04-07 | 04-07 | 다른 task에서 계산됨. |
| 13.5 |Component 등록과정에서 고려할 점. <br />inner class, anonymous class | REJECT | 0% | x | x | x |
| 14|@Configuration 어노테이션으로 빈 설정                     | REJECT | 0% | x | x | x |
| 15|di , mvc 분리해야할 듯.<br />woowahan-context가 di용 context, bean을 담당하고,<br />woowahan-di-framework가 mvc기능을 하게 분화됨 <br />                                   | DONE | 100% | x | x | 1h |
| 17|TomcatWebServer -> WebApplicationServer 로 변경, Tomcat 은 구현체중 하나로 간다. WebApplicationServer변경시 어떻게 대응할 것인지 고려함. SPI로 기본적으로는 Tomcat으로 구동되게 한다. | REJECT | 30% | 04-07 | 04-07 | 1h |
| 18|Gradle 개념 습득                                          | DONE | 완벽할 수 없다. | x | x | 2h |
| 19|logback 사용법 습득 및 적용                               | DONE | 100% | 03-26 | 03-26 | 1h |
| 20|**!(부차기능)**<br />servlet 3.0 NIO Servlet으로 구현할 까..? Transaction 필요도 없잖아. | REJECT | 0% | x | x | x |
| 21|**!(부차기능)**<br />HandlersTypes interface for reducing class scanning time<br />https://opennote46.tistory.com/168 | REJECT | 0% | x | x | x |
| 22|WebAppBackendFramework이므로, servlet 개념을 몰라도 작성가능하게 개발돼야한다. | WORKING | 50% | x | x | x |
| 23 |gradlew 개념 습득<br />과제 이후 알아서 공부할 것. | REJECT | 0% | x | x | x |
| 25 |bean 처리에 대한 기본 기능 스펙정리<br />multi same type bean 에 대한 충돌 처리<br />bean id 처리<br />bean scope처리<br /> | DONE | 100% | 03-30 | 04-01 | 다른 task에서 계산됨. |
| 26 |**!(부차기능)**<br />transaction 처리 가 가능하게끔 확장 고려(Transaction - AOP - Bean) (bean입장에서는 transaction 처리가 될 것을 고려한 개발할 필요가 없다. 관심사가 나뉘어있음. Transaction moudle 측이 알아서 bean이 변화될 때 <<<< 처리를 추가로 하) | REJECT | 0% | x | x | x |
| 27 |**!(부차기능)**<br />기본적인 aop 모듈은 필요할 듯. 공통 호출로직, 공통 비지니스 로직 상황별 로깅 관련 지원.<br />spring은 proxy기반 aop이므로, 기존 비지니스로직(target)은 proxy화 되고, proxy에서 상황별 advice로직과 본래 target 로직이 수행될 것이다. | REJECT | 0% | x | x | x |
| 29 |**di module**<br />@Service, @Controller, @Configuration with ~~@Bean~~, @Autowired, ~~@Qulifier 필수 제공~~<br />미지원: @Lazy - MSA에서는 Lazy는 좋지 못하다. Readiness로 진입했을 때 최적의 성능을 보여주길 바람. | DONE | 100% | 04-07 | 04-07 | 다른 task에서 계산됨. |
| 30 |@Repository 에 대응될 web 측의 repository 기본 구현체가 필요하다.<br />지금 app 은 repository로 memory를 쓴다고 가정하고 진행한다.<br />memory vendor의 기본 구현체를 만든다. | DONE | 100% | 04-05 | 04-05 | 1h |
| 31 |**mvc module**<br />Controller내에서 **@RequestMapping** 으로 class, method에 처리. **@RequestBody** 로 json input을 java Object로 자동전환.<br />**@ResponseBody**로 java Object output을 json output으로 자동전환 (Jackson이 사용됨)<br />**@PathVariable**로  @RequestMapping에서 전달된 url내의 variable을 로직에서 제공받음.<br />Controller에 대해:<br />이 제품은, api호출시, data만 반환하면 되므로 RestController같은 annotation만 지원하면 된다. 차후 확장기능으로 view resolver가 추가되면, Controller기능도 추가지원할 수 있을 것... | DONE | 100% | 04-07 | 04-08 | 3h |
| 32 |**!(부차기능)**<br />validation module**<br />유효성 검사 (di 책임은 아닌듯.) **@Valid, @InitBinder**<br />외부 origin에서의 접근 허용부분 처리**@CrossOrigin** | REJECT | 0% | x | x | x |
| 33 |**!(부차기능)**<br />mvc (동기)건 webflux (비동기) 상관없이 component재사용을 고려해 개발. | REJECT | 0% | x | x | x |
| 34 |**!(부차기능)**context @scope 관련 기능으로 singleton, prototype분만 아니라, request, session등도 구분가능하게 변경. | REJECT | 0% | x | x | x |
| 35 |war로 패키징될 때, web.xml에 servlet지정 및 적용 테스트. | REJECT | 0% | x | x | x |
| 36 |WebFlux를 밴치마킹하려면, RouteFunctions, RouteFunction 참고바람. | REJECT | 0% | x | x | x |
| 37 |**#woowahan-context** module 새로 추가. container-bootstrapper <-> context <-> di-framework  로 진행하고,  di-framework입장에서는, context가 servlet's context인 지, 다른 container 의 context를 통해서인 지 모르게 끔. | DONE | 100% | 03-30 | 04-01 | 30m |
| 38 |DispatcherServlet구현시, HandlerMapping기반으로 dispatch되게끔 작성.<br />해당 핵심 기능을 Route class로 구현해놓음. | DONE | 100% | 04-07 | 04-08 | 다른 task에서 취합됨. |
| 39 |Bean이 Context에 저장되게끔 진행.<br />BeanDefinition, BeanDefinition 기반으로 getBean이 처리되는 것을 원함.<br />Singleton, prototype만 지원. (이 과제는 session엔 관심이 없다.) | DONE    |   100% |       03-30 | 04-01 | 2h |
| 42 |**(!오버스펙)** **context Hierarchy**<br />servlet-context에서 root-web-applicatoin-context, dispatcher's web-application-context를 구분하려면 아래를 참고.<br />https://codediver.tistory.com/147<br />https://jaehun2841.github.io/2018/10/21/2018-10-21-spring-context/#webxml-%EC%9D%B4%EB%9E%80<br />https://live-everyday.tistory.com/164 | WORKING | 30% | 03-30 | x | 3h |
| 43 |woowahan-java-util 추가. <br />NotNull, Nullable 가독성, 등을 위해 추가. | DONE | 100% | 03-31 | 03-31 | 30m |
| 44 |Mockito 는 외부종속성이지만, test에만 사용하므로 요구사항 구현 제한에서 빠질 수 있다. 이를 활용해 ServletContext같은 것의 mocking처리를 한다. | WORKING | 60% | 03-31 | ~ | 30m |
| 45 |1. BeanIdentifier : ClassName:BeanName<br />2. BeanDefinition : ClassName:BeanName:Scope<br />3. BeanDefinitionRegistry 구현체 추가. (ex: GenericApplicationContext)<br />bean에 대한 정보를 GenericApplicationContext에서 관리. | DONE | 100% | 03-31 | 04-01 | 3h |
| 47 |logback-support 모듈 추가. <br />logbook 사용에 공통화될 부분, 및 정책을 이 모듈에 공통화. | DONE | 100% | 04-01 | 04-01 | 30m |
| 48 |BeanDefinition은 ApplicationContext측에서의 classLoader 기반으로 정의되어야 한다.<br />BeanIdentifier는 Class<?> 를 보유하면 ClassLoading을 ApplicationContext측이 아닌, BeanIdentifier를 생성한 측에서 해버린다. Class<? >이 아닌 String className기반으로 변경이 필요하다. 그이후, 확인된 classLoader기반으로 Class.forName으로 불러와야한다. | DONE | 100% | 04-01 | 04-01 | 1h |
| 49 |JAVA지만, Scala나 Kotlin 언어처럼 @Nullable을 추가하는 정책으로 변경.<br /> | DONE | 100% | 04-01 | 04-01 | 20m |
| 50 |java언어이므로 (Default는 nullable인 java...) field/method/param, etc..에 @NotNull을 추가하는 정책을 취했는데, 이게 가독성을 해치고 작업 능률을 떨어트린다. java지만 default는  notNull인 것으로 취급하며 진행한다. 차후 @Nullable, @NotNull 이 compile타임에 영향을 주면 될 것. | DONE | 100% | 04-01 | 04-01 | 10m |
| 51 |logback 처리될 로직은 start, end사이에 공통화가 가능해 보인다. aop개념을 짧은시간내에 접목시킬 수 있는 지 확인 할 것.<br />dispatcherServlet에서 처리할 것. | TIMEOUT | - | - | - | - |
| 52 |ComponentScan을 통해 Component들이 Bean으로서 BeanDefinition이 적절하게 등록되게 변경 | DONE | 100% | 04-04 | 04-05 | 3h |
| 53 |**BeanDefinition 등록 전략.**<br />spring 에서는 bean definition을 classLoading없이 Bean MetadataReading하는 매커니즘을 통한 file io기반으로 진행하게된다. <br />이것을 구현하기란 규모가 큰 구현이므로, 이대신 별도 classloader를 두고, 이 별도 classloader기반으로 BeanDefinition이 관리되고, 이를 통해 Bean init과 Bean 관리가 됨을 보여줄 것이다. **이 상대적으로 규모가 작게 개발하는 별도 classloader는, 향후 MetadataReading 매커니즘과 유사한 구현으로 리팩토링 가능하게끔 염두해서 개발되어야한다.**<br /><br />Metadata는 별도 meta관리용 classloader를 통해 해당 classloader에 basepackage로 지정된 package하위 모든 class를 읽히게 하므로써 해당 framework의 동작은 별도 classloader가 활용됨을 정책으로 삼음.<br />따라서, class loading 시점에 동작되는 것들 (static field, static {} )을 사용한 class를 bean으로 등록하는 것을 권장하지 않음.  해당 동작을 유사히 구현하려면 contructor를 통해 유사하게 진행할 것.<br /><br />ResourceLoader마냥, classLoader를 지정하지 않으면, Thread.currentThread.getContextClassLoader()가 default이다.<br />basePackage 기반으로 별도 classloader가 scan을 할때는, 외부라이브러리로 등록돼있는 *`org.reflections:reflections:0.9.11`* 을 사용한다.<br />해당 classloader에서는 loading이후 resolve할 필요 없다. | DONE | 100% | 04-04 | 04-05 | 7h |
| 54 |기존 ServletWebServerApplicationContext (on Spring-boot)는 너무 하는 일이 많은 class이다. webserver도 만들고, context도 관리하고.. context본연의 기능만 남긴 ApplicationContext를 만들고. ServletContextInitializer를 생성해 제공도 하고... webserver만드는 것은 책임을 다른 클래스에 위임하게 한다.<br /> | TIMEOUT | 20% | x | x | x |
| 55 |Component scan 이 쉽게끔, Component 취급되는 bean들은 enum BeanRegistrable으로 관리하고 있는다. | DONE | 100% | 04-04 | 04-04 | 30m |
| 56 |@Bean 만 ElementType.METHOD 인 관계로 차후 확장할 부분으로 넘겨둔다. 현재로서는 자체 생산하는 Class에 대해서만 Bean으로서 관리되게끔 진행.<br />차후 추가기능으로 @Bean을 지원할 예정. | TIMEOUT | 0% | x | x | x |
| 57 |[container-bootstrapper] ServletBootstrapperDefault가 booting하면서 servletContext를 이용해 제대로 BeanDefinition을 등록하게끔 구현 및 검증. | DONE | 100% | 04-05 | 04-05 | 2h |
| 58 |[java-util] reflection invokeMethod, getField, setField 관련 유틸 추가. 및 XXXXAnyway 버전의 method 들은 테스트에서만 사용하는 reflection임을 명시.<br />reflection util의 기능 분리. 및 autoWired기능에 쓸 것을 염두해두고 reflection기능 자체별로 별도 클래스에 제대로 구현할 것.<br />[reopen] constructor util 추가. Predicate기반 filter사용가능하게끔 추가. ReflectionUtil class로 다시 통합. | DONE | 100% | 04-05 | 04-05 | 5h |
| 59 |[context] cloud 지원시, container가 warming-up 시간이 존재하는 것은 좋지 않다. singleton bean 들은 pre-initialize하자.<br />bean initialize to ApplicationContext -> bean's inner Injection 진행 -> injection을 위해 getBean이 진행됨 -> 필요에 따라 bean initialize가 연쇄될 수 있음. | DONE | 100% | 04-07 | 04-07 | 1h |
| 60 |Model을 통해 Repository에 등록하는 매커니즘을 ID기반으로 진행되게끔 구현.<br />Model에게서 Id를 추출할 유틸 생성 | DONE | 100% | 04-05 | 04-05 | 1h |
| 61 |**(!! 중요 )** 최종 jar파일의 경량화를 위해 Spring-core dependency 를 가진 woowahan-di-support를 사용하지 않는다.<br /><br />woowahan-di-support가 org.springframework:spring-core:5.1.9.RELEASE 를 dependency로 가지고있어서 Reflection처리를 support하려고 하고있으나, 이런 dependency를 가져서 jar가 무거워진다.<br />리플렉션 처리는 java-util module로 알아서 구현한다. | DONE | 100% | 04-05 | 04-05 | 다른 task에서 계산됨. |
| 62 |framework를 사용하는 고객이 App을 작성할 때, Test코드를 적게 작성하도록 고려한 개발을 한다. <br /> 각종 기본 구현체, 각종 Annotation기반 구현을 지원한다. | WORKING | 50% | 04-04 | x | x |
| 63 |Type 과 내부 method에 달려있는 @RequestMapping 의 value (path)에 대한 parsing util을 추가한다. | DONE         |            100% |       04-08 |  04-08 | 3h                         |
| 64 |Model <-> Jackson json을 이 가능하게끔 유틸화 | DONE | 100% | 04-06 | 04-06 | 다른 task에서 계산됨. |
| 65 |**(부가기능)**framework 사용 고객 입장에서 jackson을 app코드에서 직접 사용해야하는 부분이 별로인 것 같다. jackson을 framework단에서만 들고있고 util method로 뺴주자.<br />json-support 라는 별도 모듈이 생겨야 하며, <br />TODO: json-support 모듈을 별도로 생성하고, 타 vendor 구현체를 사용하는 방식은 spi를 따른다. <br />TODO: default spi 구현체는 JacksonUtil일 것. <br />TODO: 고객은 app 작성시 vendor 상관없이 framework가 제공하는 annotation, util api만 call하게끔 할 것. | WORKING | 50% | 04-05 | x | 30m |
| 66 |framework 사용 고객이, logging을 직접 app코드에 추가하고 싶지 않을 것이다. framework차원에서 알아서 비지니스 로직에서 logging 되게끔 작업한다.<br />고객은 예외만 던지면 된다. | WORKING | 30% | x | x | x |
| 67 |**(부가기능)** Repository구현체의 경우, 고객이 직접 vendor를 선택하게끔 돼 버렸는데, app코드에서는 vendor무관히 코딩가능하게 개선할 것. | REJECT | 0% | x | x | x |
| 68 |method reflection 수행에 대해. <br /><br />@RequestMapping method인 경우, value에 variable {aaa} 로 추가가능하다.<br />해당 method 내의 param중에 variable과 getName이 같은 param Parameter에 String to XXXX convert를 진행해 주입해준다.<br /><br /><br />@Autowired Constructor 인 경우, value[]에 값이 있으면 해당 beanName기반으로 해당 method내의 param들 순서대로 beanName으로서 getBean을 처리해준다. <br />getBean된 내용을 param Parameter에 주입해준다. | DONE | 100% | 04-07 | 04-07 | 3h |
| 69 |DispatcherServlet 입장에서 router 처리 시나리오 정리.<br /><br />Map<String, Set<RequestMethod<br />url : /shops/aaa/bbb/ccc -> shops 검색 -> x | WORKING | 50% | 04-08 |  | 2h |
| 70 |Url <-> Path \| PathVariableName \| PathVariable , encoding, decoding 지원 UrlUtil추가. | WORKING | 100% | 04-07 | 04-07 | 4h |
| 71 |servletContainer 기동 타이밍에 bean pre-initialize 기본 지원.<br />차후 lazy-initialize 지원 할 것. | DONE | 100% | 04-07 | 04-07 | 1h |
| 72 |BeanDefinition에 BeanRegistrable annotation 에 대한 정보도 추가. 이 또한 cannonicalName으로 취득한다. (for MetaClassLoader <-> AppClassLoader) | DONE | 100% | 04-07 | 04-07 | 30m |
| 73 |annotation 관련  ReflectionUtil추가. | DONE | 100% | 04-07 | 04-07 | 30m |
| 74 |Controller Bean LifecyleInvocaion 추가.<br />Route 에서 이 invocation기능을 이용해 Controller의 route정보를 취득한다. | DONE | 100% | 04-07 | 04-07 | 3h |
| 75 |framework system용 bean들을 관리할 수 있게, beanMetaFinderForSystem 도입 및 기존 beanMetaFinder와 동시사용 최적화 지원. | DONE | 100% | 04-08 | 04-08 | 30m |
| 76 |사용자 app은 testcode를 적게 생성해도 되게끔 개발하기 편리하도록 의도해야한다. | TIMEOUT | x | x | x | x |
| 77 |고객이 직접 json화 하는 것은 좋지 않다. 고객은 객체를 반환할 뿐이고, framework가 객체를 json string화 하여 response로 주도록 한다. | DONE | 100% | 04-08 | 04-08 | 다른 작업에서 시간 감안됨. |
| 78 |ViewResolver 가 @Service로 등록돼야한다. ResourceViewResolver, JsonStringViewResolver 2가지가 필요하다.<br />ResourceViewResolver 는 String반환일때 동작하며, prefix `resources/static` suffix`.html` 이렇게 붙여 사용 가능하다.<br />JsonStringViewResolver 는 그외에 Object -> JsonStr 변환을 시도해 응답한다.<br />이마저도 실패하면, 원본 Object로 응답하려 시도한다. | WORKING | 70% | 04-08 | x | 1h |
| 79 |Dispatcher SErvlet에서 Request기반으로 적절히 Route 처리한다. | WORKING | 80% | 04-08 | x | 3h |
| 80 |Bean을 외부에서도 활용할 수 있게 BeanManager를 추가. BeanManager 는 GenericApplicationContext를 사용할 시, RootApplicationContext를 활용하게끔 초기화된다. | DONE | 100% | 04-08 | 04-08 | 1h |
| 81 |Route 처리 규칙 변경 안내<br />로직 속도 개선을 위해 /shops/{id} 가 아닌 /shops/@{id} 처럼 #을 encodedvariable앞에 붙이도록 정책화. | DONE(문서화) | 100% | - | - | - |
| 82 |APP 개발 | WORKING | 60% | 04-10 | - | 30m |
| 83 |logback Marker 조건문에도 추가. | DONE | 100% | 04-08 | 04-08 | 10m |
| 84 |[최적화]<br />DispatcherServlet에서 취득한 정보로 router 처리할 때 별도 single thread EM으로 처리되게끔 할 지 여부 파악중...<br /> * 지금 repo는 memory cached이기때문에, IO job이 아니므로 EM처리해도된다. cached의 invalidate는 별도 thread에서 알아서 진행해 줄것. 따라서, 현 프로젝트의 shop api  service의 동작은 em구조로 가는 것이 더 효율 적이다.<br /> get : post,put,delete 비중이 99:1 정도로 판단되는 shop 데이터 특성상, thread 생성을 최소화할 em구조가 나아보인다.<br />em이 받을 event 는 dispatch될 route정보와 requestBody가 포함되며, HttpServletResponse.getWriter()도 포함된다.<br /><br />get은 thread생성 1회<br /><br /><br />/resources/static/index.html 파일로 viewResolver가 반환하려고한다면, 이는 Event thread에서는 IO잡을 하지 않게끔 해야한다.<br />event thread에서 작업결과 view Resolver 처리할 때, ViewResolver가 hasIO true일 경우에 별도 thread로 IO잡을 해준다. | WORKING | 50% | 04-10 | 04-10 | 2h |
| 85 |Model에서 Id로 지정된 field가 곂칠 경우 IdAutoChanger handler를 달 수 있는 기능 추가. (ex: DefaultIntegerIdAutoIncrementModel)<br />GenericMapRepository에 우선 적용. | DONE | 100% | 04-09 | 04-09 | 2h |
| 86 |Repository 인터페이스 세분화.<br /><br />Repository < RepositoryAccessibleAll < GenericMapRepository | DONE | 100% | 04-10 | 04-10 | 30m |
| 87 |Url PathVariable Prefix # -> @로 변경. (#은 해시뱅 쓰이므로 reuqestUri로 포함되지 않는다.) | DONE | 100% | 04-10 | 04-10 | 30m |
| 88   | 각종 Filter (CaracterEncodingFilter, ChachedRequestBodyFiler) 추가 및 연결 | DONE         | 100% | 04-10 | 04-10 | 30m |
| 89   | TODO: HttpServletRequest, HttpServletResponse 사용 Test코드 개선<br />현재로서는 규모가 큰 Request,Response 객체들도 Mock을 사용하려 의도했으나, 진짜 실효성있는 테스트가 되고 있는 지 모르겠으므로,  상위단계의 Test 코드 작성은 토이프로젝트 이후 공부이후 개선한다.<br />규모가 작은 클래스 개발이 이래서 중요하다. | PAUSE        | 20% | 04-10 | 04-10 | 1h. |
| 90 | ViewResolver 를 Message인터페이스 기반으로 진행하게끔 변경 |  |  |  |  |  |
| 91 | **container-bootstrapper와 mvc간의 loose coupling 설계<**br /> container-bootstrapper측에서는 ContainerVendorSelection을 App에서 Configuration으로서 등록하지 않았으면 default처리되도록할 것. <br />ContainerVendorSelection에 의해 (SyncServlet -> 에 해당하는 Req/Resp MessageSender pick as ParentInterface. <br /> ContainerVendorSelection에 의해  SyncServlet -> 에 해당하는 ModelResovlerList, ViewResovlerList set도 vendor별로 따로 pick as ParentInterface. <br />ResponseMessageSender(상속받은) (autoWired) -> send(Object vendorRequestObject, ResponseMessage) |  |  |  |  |  |
| 92 | BeanRegistrable Annotation들이 BeanDefinition으로 등록되는 순서와 refreshBean시 생성되는 순서를 아래와 같이 변경. <br />Configuration > Bean > Service > Controller > Repository | DONE | 100% | 04-11 | 04-11 | 30m |
|  |  |  |  |  |  |  |

