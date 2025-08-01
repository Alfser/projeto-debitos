FROM amazoncorretto:17 AS base

VOLUME /tmp

WORKDIR /var/application

FROM base AS builder

VOLUME /tmp

WORKDIR /var/application

RUN yum install -y tar gzip

COPY ./ ./

RUN bash deploy.sh

FROM base AS runner

WORKDIR /var/application

COPY --from=builder /var/application/target/*.jar app.jar

COPY --from=builder /var/application/src/main/resources/jasper src/main/resources/jasper

ENTRYPOINT java -jar app.jar
