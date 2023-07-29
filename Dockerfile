FROM openjdk:17
EXPOSE 8080
ARG encryption_password
ADD /target/sesh-service.jar /sesh-service.jar
CMD java -Djasypt.encryptor.password=$encryption_password -jar /sesh-service.jar