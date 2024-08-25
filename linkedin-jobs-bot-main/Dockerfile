#FROM gradle:latest
#
#WORKDIR /app
#COPY . /app
#RUN gradle build -x test
#ENTRYPOINT ["java", "-jar", "/app/build/libs/linkedin-jobs-bot-main-0.0.1-SNAPSHOT.jar"]

FROM gradle:latest AS build
WORKDIR /app
COPY . /app
RUN gradle build -x test

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/linkedin-jobs-bot-main-0.0.1-SNAPSHOT.jar /app/linkedin-jobs-bot-main.jar
ENTRYPOINT ["java", "-jar", "/app/linkedin-jobs-bot-main.jar"]