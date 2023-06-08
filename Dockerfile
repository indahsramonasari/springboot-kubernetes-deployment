FROM openjdk:11-jdk-slim
COPY target/customer-management-service-0.0.1-SNAPSHOT.jar /app/

ENV TZ="Asia/Jakarta"

CMD java -jar app/customer-management-service-0.0.1-SNAPSHOT.jar
