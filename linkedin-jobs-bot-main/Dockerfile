FROM gradle:latest

WORKDIR /app
COPY . /app
RUN gradle build -x test
ENTRYPOINT ["java", "-jar", "/app/build/libs/linkedin-jobs-bot-0.0.1-SNAPSHOT.jar"]