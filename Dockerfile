FROM adoptopenjdk/openjdk11

MAINTAINER Yohan Bouali

VOLUME ["/eshop-ms-membership"]

WORKDIR /eshop-ms-membership

ARG JAR_FILE=target/eshop-ms-membership-1.0.0-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar  

CMD ["java","-jar", "app.jar"]

EXPOSE 8070