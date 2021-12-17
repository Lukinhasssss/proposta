FROM openjdk:17
WORKDIR proposta
EXPOSE 8080
ADD /build/libs/proposta-0.0.1-SNAPSHOT.jar proposta.jar
ENTRYPOINT ["java", "-jar", "proposta.jar"]