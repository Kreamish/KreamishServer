FROM openjdk:17-jdk-oracle

WORKDIR /var/app

COPY pom.xml pom.xml
COPY src src
COPY .mvn .mvn
COPY mvnw mvnw

RUN chmod +x mvnw

<<<<<<< HEAD
RUN ./mvnw clean package

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
=======
RUN ./mvnw clean package -DskipTests

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.active.profiles=prod
>>>>>>> 7331366 (feat: KS-7 Dockerfile 작성 및 prod 구성 (docker network 내 mysql dns 사용))
