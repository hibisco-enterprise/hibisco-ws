#
# Build stage
#
FROM maven:3.8.4-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:11-jdk
EXPOSE 8080
COPY --from=build /home/app/target/*.jar hibisco-ws.jar
ENTRYPOINT ["java","-jar","/hibisco-ws.jar"]