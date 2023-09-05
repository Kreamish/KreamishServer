FROM openjdk:17-jdk-oracle

WORKDIR /var/app

COPY pom.xml pom.xml
COPY src src
COPY .mvn .mvn
COPY mvnw mvnw

RUN chmod +x mvnw
COPY target/kream-0.0.1-SNAPSHOT.jar kream-0.0.1-SNAPSHOT.jar

RUN ./mvnw clean package

CMD java -jar target/kream-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
CMD java -jar kream-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
