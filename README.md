# etom-batch
kurly 물류 데이터를 자동으로 생성하여 저장해주기 위한 배치 프로그램
---
## feature
- CSV -> Object로 변환
- 랜덤 주문 생성 기능
- 주문 -> 배송정보로 변경 기능

## folder structure
- domain: 모델 및 레파지토리, 핵심 Entity를 제공
- cofiguration: querydsl 및 기타 설정
- job: 스프링 배치에서 데이터를 처리하는 Job 모음

## applicaation.yml
```yml
spring:
  datasource:
    jdbc-url: 
    username:
    password: 
    driver-class: 
    hibernate:
      naming:
        implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    properties:
      hibernate:
        jdbc:
          batch_size: 500
        ddl-auto: none
        format_sql: true
        show-sql: false
  batch:
    job:
      names: ${job.name:NONE}
    jdbc:
      initialize-schema: never

server:
  port: 8090
```

## 개선사항
- reader, processor, writer 패턴으로 동작하여 청크지향 프로세싱하도록 변경.
- 공통 모듈은 Jar파일로 분리시켜 모듈화하여 사용할 수 있도록 변경
