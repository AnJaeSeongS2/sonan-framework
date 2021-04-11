# woowahan-di-support (미사용. Dependency에서 제외됨.)

## WoowahanLocalVariableTableParameterNameDiscoverer

### String[] getParameterNames(Method method)

- `Method` 의 변수명을 추출합니다. 

### String[] getParameterNames(Constructor<?> ctor)

- `Constructor` 의 변수명을 추출합니다.

## WoowahanPathMatcher

### Map<String, String> extractUriTemplateVariables(String pattern, String path)

- Ant Pattern 의 Path Variable 을 Map 형태로 추출

### boolean match(String pattern, String path)

- Pattern 매치 여부

## 안내
- 이 모듈은 수정될 수 없습니다.