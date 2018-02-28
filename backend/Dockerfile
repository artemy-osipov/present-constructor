FROM openjdk:8u131-jre-alpine

ARG JAR_FILE

WORKDIR /usr/share/present/
ADD ${JAR_FILE} ./present.jar

EXPOSE 8080

ENTRYPOINT /usr/bin/java -jar present.jar --spring.profiles.active=docker --db.username=$DB_USER --db.password=$DB_PASS