#FROM gradle:latest
#
#WORKDIR /app
#COPY . /app
#RUN gradle build -x test
#ENTRYPOINT ["java", "-jar", "/app/build/libs/job-scanner-0.0.1-SNAPSHOT.jar"]

# Stage 1: Build the application
FROM gradle:latest AS build
WORKDIR /app
COPY . /app
RUN gradle build -x test

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/job-scanner-0.0.1-SNAPSHOT.jar /app/job-scanner.jar
ENTRYPOINT ["java", "-jar", "/app/job-scanner.jar"]