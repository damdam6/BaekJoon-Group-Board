## SpringBoot Version (3.1.5)
- Version : 3.1.5
- Project : Gradle-Groovy
- Java Version : 17
- 선정 이유 : 
    - JAVA 17 버전 필수로 앞으로 자바 8 -> 17로 넘어가는 만큼 경험해보기 위해
    - 3.0, 3.1 큰 차이 없음으로 높은 버전으로 진행
    - 참고 : SNAPSHOT : 기능 추가중인 버전으로 선정하지 않는것이 좋음 
- Dependencies
    - SpringBoot DevItools
    - Lombok
    - Spring Web
    - MyBatis Framework
    - MySQL Driver

- Dependencies Version
https://spring.io/projects/spring-boot#learn
Version 선택 -> Reference Doc. -> Dependency Versions 

## MyBatis Version (3.0.1 이상)
- SpringBoot 자동 지정 
- [마이바티스 3.0 이상부터 @Mapper annotation 활용 가능](https://thenicesj.tistory.com/317)

```implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1'```

[Version 참고](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)


