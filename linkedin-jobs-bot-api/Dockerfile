FROM gradle:latest AS build
WORKDIR /app
COPY ../build.gradle /app
COPY ../settings.gradle /app
COPY . /app/
COPY ../modules /app/modules
ARG KEYSTORE_PASSWORD
RUN mkdir -p src/main/resources/keystore && \
        keytool -genkeypair \
        -alias mykey \
        -keyalg RSA \
        -keystore src/main/resources/keystore/keystore.p12 \
        -storetype PKCS12 \
        -storepass ${KEYSTORE_PASSWORD} \
        -keypass ${KEYSTORE_PASSWORD} \
        -dname "CN=example.com, OU=IT, O=Example Corp, L=City, ST=State, C=US" \
        -validity 3650
RUN gradle :linkedin-jobs-bot-api:build -x test --info

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/linkedin-jobs-bot-api/build/libs/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar /app/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/linkedin-jobs-bot-api-0.0.1-SNAPSHOT.jar"]