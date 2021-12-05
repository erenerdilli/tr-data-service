FROM maven:3.8.3-jdk-11 AS MAVEN_BUILD

COPY ./ ./

RUN mvn clean package -Dmaven.test.skip

FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=MAVEN_BUILD target/trade-data-service.jar app.jar

EXPOSE 8080

CMD ["java","-jar","/app.jar"]