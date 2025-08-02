FROM amazoncorretto:17.0.16-alpine AS builder

WORKDIR /app

RUN apk add --no-cache tar gzip bash && \
    rm -rf /var/cache/apk/*

COPY ./ ./

RUN chmod +x deploy.sh && ./deploy.sh

FROM amazoncorretto:17.0.16-alpine

WORKDIR /app


COPY --from=builder /app/target/*.jar application.jar

RUN addgroup -S appuser && \
    adduser -S appuser -G appuser && \
    chown appuser:appuser /app
USER appuser


ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "application.jar"]