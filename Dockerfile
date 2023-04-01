FROM maven:3.8.3-openjdk-17 AS build
COPY src /kantakap-auction-file/src
COPY pom.xml /kantakap-auction-file
RUN mvn -f /kantakap-auction-file/pom.xml clean package

FROM openjdk:17-alpine
COPY --from=build /kantakap-auction-file/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]
