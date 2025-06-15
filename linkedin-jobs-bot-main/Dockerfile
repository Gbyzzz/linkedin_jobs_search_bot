#FROM gradle:latest
#
#WORKDIR /app
#COPY . /app
#RUN gradle build -x test
#ENTRYPOINT ["java", "-jar", "/app/build/libs/linkedin-jobs-bot-main-0.0.1-SNAPSHOT.jar"]

FROM gradle:latest AS build
WORKDIR /app
COPY ../build.gradle /app
COPY ../settings.gradle /app
COPY . /app/
COPY ../modules /app/modules
RUN gradle :linkedin-jobs-bot-main:build -x test --info

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/linkedin-jobs-bot-main/build/libs/linkedin-jobs-bot-main-0.0.1-SNAPSHOT.jar /app/linkedin-jobs-bot-main.jar
ENTRYPOINT ["java", "-jar", "/app/linkedin-jobs-bot-main.jar"]