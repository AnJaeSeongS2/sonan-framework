# TODOLIST


| 색인 | 작업                                                         | 상태 | 달성률 | 예상 완료일 | 완료일 | 작업 소요시간 |
| ----|-------------------------------------------------------- | ---- | -----: | ----------: | -----: | ------------- |
| 1|junit5 추가                                                |      |        |             |        |               |
| 2|CRUD api 추가 (추가 요건 들어올 것을 대비한 설계, 확장가능한 설계) |      |        |             |        |               |
| 3|Test기반으로 RestDoc 추가                                  |      |        |             |        |               |
| 4|logback FW로 의미있는 로그 추가                            |      |        |             |        |               |
| 5|/ path진입시 resources/static 경로의 index.html 출력.      |      |        |             |        |               |
| 6|di                                                         |      |        |             |        |               |
| 7|DispatcherServlet 구현 (di , mvc 분리해야할 듯.)           |      |        |             |        |               |
| 8|IoC container 구현 -> DI 제공 (reflection 사용.)           |      |        |             |        |               |
| 9|정적 resource와 json응답처리 가능해야함. Fasterxml Jackson, resources/static 하위경로의 리소스를 내려줄 수 있어야함( view resolver기능) |      |        |             |        |               |
| 10|LifeCycle 관련 기능 추가 바람. (요구조건에는 없음. 그러나, app자체의 start가 완료됐을 때, log도 찍고 etc.. 해줘야 할 듯. 공통로직 관련되어...) |      |        |             |        |               |
| 11|Tomcat기반으로 부팅되는 개념인 듯. (별도 모듈화가 좋을 듯. ex: was-bootstraper) | WORKING | 20% | 03-27 |  | 1h |
| 12|+ADVANCED @ComponentScan                                  |      |        |             |        |               |
| 13|@Service, @Repository 클래스 스캔  빈 주입시 Service가 다른 Service를 주입하는 경우 고려 @Inject가 없는 경우 기본생성자를 이용하여 빈으로 등록 그 외 경우는 initializeInjectedBeans로 빈 등록 @Inject에 필요한 객체를 찾아서 주입 / 생성 기존의 @Controller를 스캔하는 ControllerScanner 클래스를 확장된 BeanScanner와 통합 |      |        |             |        |               |
| 14|@Configuration 어노테이션으로 빈 설정                     |      |        |             |        |               |
| 15|di , mvc 분리해야할 듯.                                   |      |        |             |        |               |
| 16|DI xml은 제외할 것.                                       |      |        |             |        |               |
| 17|TomcatWebServer -> WebApplicationServer 로 변경, Tomcat 은 구현체중 하나로 간다. WebApplicationServer변경시 어떻게 대응할 것인지 고려함. SPI로 기본적으로는 Tomcat으로 구동되게 한다. |      |        |             |        |               |
| 18|Gradle 개념 습득                                          |      |        |             |        |               |
| 19|logback 사용법 습득 및 적용                               | DONE | 100% | 03-26 | 03-26 | 1h |
| 20|servlet 3.0 NIO Servlet으로 구현할 까..? Transaction 필요도 없잖아. |      |        |             |        |               |
| 21|HandlersTypes interface for reducing class scanning time<br />https://opennote46.tistory.com/168 |      |        |             |        |               |
| 22|WebAppBackendFramework이므로, servlet 개념을 몰라도 작성가능하게 개발돼야한다. |      |        |             |        |               |
| 23 |gradlew 개념 습득 | | | | | |