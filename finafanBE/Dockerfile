FROM gradle:7.6-jdk17 AS build

WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# 타임존 설정
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]