# notification-service

##Run project
mvn compile
mvn exec:java -Dexec.mainClass="notificationservice.NotificationServiceApplication"

##Dependencies:
org.springframework.boot:spring-boot-starter-data-jpa
org.springframework.boot:spring-boot-starter-web
org.springframework.boot:spring-boot-starter-test
org.hibernate:hibernate-validator:6.1.0.Final
org.springdoc:springdoc-openapi-ui:1.6.4
com.h2database:h2

##OpenAPI Documentation
localhost:28852/docs - JSON
localhost:28852/docs.html - Swagger

