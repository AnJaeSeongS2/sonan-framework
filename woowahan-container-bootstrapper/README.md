# woowahan-container-bootstrapper

`woowahan-container-bootstrapperr`은 `Tomcat`을 기본 servlet container로 지원하지만, 다른 vendor의 추가 지원이 가능하게 확장을 염두한 모듈.

## 요구사항
1. `다른 was | servlet container | netty 등등`으로도 확장가능한 설계.
2. servlet을 container로 사용할 경우, `servlet 3.1` 이상을 지원하는 만 정상 동작.