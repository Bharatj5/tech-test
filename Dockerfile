FROM openjdk:8-jdk-alpine
COPY target/tech-test-0.0.1-SNAPSHOT.jar tech-test-0.0.1.jar
ENTRYPOINT ["java","-jar","/tech-test-0.0.1.jar"]