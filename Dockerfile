FROM openjdk:17-jdk-oracle

WORKDIR /var/app

COPY pom.xml pom.xml
COPY src src
COPY .mvn .mvn
COPY mvnw mvnw

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.active.profiles=prod