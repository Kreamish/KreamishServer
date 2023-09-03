FROM openjdk:17-jdk-oracle

WORKDIR /var/app

COPY target/kream-0.0.1-SNAPSHOT.jar kream-0.0.1-SNAPSHOT.jar

CMD java -jar kream-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod