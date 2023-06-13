FROM openjdk:17-jdk-oracle

WORKDIR /var/app

COPY pom.xml pom.xml
COPY src src
COPY .mvn .mvn
COPY mvnw mvnw

RUN chmod +x mvnw

<<<<<<< HEAD
<<<<<<< HEAD
RUN ./mvnw clean package

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
=======
RUN ./mvnw clean package -DskipTests
=======
RUN ./mvnw clean package
>>>>>>> fc6e69e (fix: 빌드 시 테스트 구동 및 spring.profiles.active 기본 값 설정)

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.active.profiles=prod
>>>>>>> 7331366 (feat: KS-7 Dockerfile 작성 및 prod 구성 (docker network 내 mysql dns 사용))
