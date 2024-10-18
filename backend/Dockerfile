# 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# 실행 스테이지
FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java","-jar","/app/app.jar"]