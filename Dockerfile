FROM openjdk:17
WORKDIR proposta
EXPOSE 8080
ADD /build/libs/*.jar proposta.jar
ENTRYPOINT ["java", "-jar", "proposta.jar"]