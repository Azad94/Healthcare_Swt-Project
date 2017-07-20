#!/bin/ash

exec java -Djava.security.egd=file:/dev/./urandom  -Dspring.profiles.active=dev -jar /app.jar
