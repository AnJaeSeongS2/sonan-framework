# sonan-container-bootstrapper

`sonan-container-bootstrapperr`은 `Tomcat`을 기본 servlet container로 지원하지만, 다른 vendor의 추가 지원이 가능하게 확장을 염두한 모듈.

## 요구사항
1. `다른 was | servlet container | netty 등등`으로도 확장가능한 설계.

## 결과
* 지금은 Tomcat만 지원된다.