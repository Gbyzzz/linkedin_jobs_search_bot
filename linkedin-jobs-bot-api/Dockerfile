FROM gradle:latest AS build
WORKDIR /app
COPY ../build.gradle /app
COPY ../settings.gradle /app
COPY . /app/
COPY ../modules /app/modules
RUN gradle :linkedin-jobs-bot-api:build -x test --info

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/linkedin-jobs-bot-api/build/libs/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar /app/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar"]