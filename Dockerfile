FROM gradle:7-jdk19 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:19

EXPOSE 8080:80
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/jmedia.jar
ENTRYPOINT ["java","-jar","/app/jmedia.jar"]