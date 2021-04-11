# woowahan-app

`resources/static/index.html` 에는 가게를 조회, 수정, 삭제 할 수 있는 어플리케이션이 작성되어 있습니다.
`woowahan-di-framework` 를 사용하여 어플리케이션을 완성해주세요.

## 요구사항에서 마이너 수정한 사항

* routing 속도 개선을 위해 기존 api인 /shops/{id} 가 아닌 /shops/@{id} 으로 @을 encodedvariable앞에 붙이도록 정책화.

  * ```
    Router class 참고. request 날리는 측이 PathVariable임을 알려줄 수 있게끔 api개선
    ```



## 요구사항

1. `/` Path 진입시 `resources/static` 경로 하위의 `index.html` 이 출력되어야 합니다.
2. `index.html` 에 구현되어 있는 아래 기능이 정상 동작하여야 합니다.
   
    - 기능 
        - 가게 목록 조회
            ```
            curl localhost:8080/shops -X GET -H 'Content-Type: application/json'
            ```
        - 가게 단건 조회
            ```
            curl localhost:8080/shops/@1 -X GET -H 'Content-Type: application/json'
            ```
        - 가게 추가
            ```
            curl localhost:8080/shops -X POST -H 'Content-Type: application/json' -d "{\"name\": \"우아한 가게\", \"address\": \"배민시 배민동 123-1\"}"
            ```
        - 가게 수정
            ```
            curl localhost:8080/shops/@1 -X PUT -H 'Content-Type: application/json' -d "{\"name\": \"우아한 가게\", \"address\": \"배민시 배민동 123-1\"}"
            ```
        - 가게 삭제
            ```
            curl localhost:8080/shops/@1 -X DELETE -H 'Content-Type: application/json'
            ```
        
    - 가게 최소 스팩
      ```
      {
        id: 1,
        name: '가게명',
        address: '주소'
      }
      ```
  ```
    
3. logback 프레임워크(http://logback.qos.ch)를 이용하여 의미있는 로그를 남깁니다.
4. JUnit5 를 이용해서 테스트 케이스 작성합니다.

## 안내
- 요구사항을 구현함에 있어 충분한 의존성이 제공되고 있습니다. 현재 정의된 의존성 외 외부 의존성은 추가할 수 없습니다.
- # 사전 작성된 코드는 자유롭게 변형, 분리, 리펙토링 되어도 무방합니다.
  ```