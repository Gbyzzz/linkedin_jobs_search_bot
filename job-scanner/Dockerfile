FROM gradle:latest

WORKDIR /app
COPY . /app
RUN gradle build -x test
ENTRYPOINT ["java", "-jar", "/app/build/libs/job-scanner-0.0.1-SNAPSHOT.jar"]