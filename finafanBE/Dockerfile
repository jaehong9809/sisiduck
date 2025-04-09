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

# 모니터링 도구 설치
RUN apt-get update && apt-get install -y curl

EXPOSE 8081

ENTRYPOINT ["java", "-Dmanagement.endpoints.web.exposure.include=health,info,prometheus,metrics", "-Dmanagement.endpoint.prometheus.enabled=true", "-jar", "app.jar"]