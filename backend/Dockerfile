FROM openjdk:8u131-jre-alpine

ARG JAR_FILE
ADD ${JAR_FILE} /usr/share/present/present.jar

EXPOSE 8080

ENTRYPOINT /usr/bin/java -jar /usr/share/present/present.jar --spring.profiles.active=docker --db.username=$DB_USER --db.password=$DB_PASS