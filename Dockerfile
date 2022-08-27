FROM azul/zulu-openjdk-alpine:17

ENV JARFILE kt-assets.jar
ENV SERVICE_NAME kt-assets

COPY deployment/target/$JARFILE /app/$JARFILE

EXPOSE 8080 8081

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/kt-assets.jar"]