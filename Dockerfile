FROM openjdk:21-jdk
WORKDIR /app
COPY target/Sales_Savvy-0.0.1-SNAPSHOT.jar Sales_Savvy.jar
COPY src/main/resources/application.properties /app/config/application.properties
EXPOSE 9090
ENTRYPOINT ["java","-jar","Sales_Savvy.jar","--spring.config.location=file:/app/config/application.properties"]