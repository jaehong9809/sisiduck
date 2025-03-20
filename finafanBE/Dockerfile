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

# Add Prometheus JMX exporter
RUN mkdir -p /opt/jmx_exporter
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.16.1/jmx_prometheus_javaagent-0.16.1.jar /opt/jmx_exporter/jmx_prometheus_javaagent.jar
COPY config/prometheus/jmx_exporter.yml /opt/jmx_exporter/config.yml

EXPOSE 8081

ENTRYPOINT ["java", \
    "-javaagent:/opt/jmx_exporter/jmx_prometheus_javaagent.jar=8081:/opt/jmx_exporter/config.yml", \
    "-jar", \
    "app.jar"]