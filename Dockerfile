FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/Proyecto-0.0.1.jar
COPY ${JAR_FILE} app_proyectoSchool.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_proyectoSchool.jar"]
