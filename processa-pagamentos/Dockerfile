FROM amazoncorretto:17.0.16-alpine AS builder

WORKDIR /app

RUN apk add --no-cache tar gzip bash

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN ./gradlew dependencies

COPY . .

RUN chmod +x deploy.sh && ./deploy.sh

# Image to run
FROM amazoncorretto:17.0.16-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar application.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar", "application.jar"]