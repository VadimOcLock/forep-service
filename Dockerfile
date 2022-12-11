FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build "/home/gradle/src/build/libs/ru.fortum.forep.forep-service-0.0.1-all.jar" /app/forep-service.jar
ENTRYPOINT ["java","-jar","/app/forep-service.jar"]