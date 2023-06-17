FROM openjdk:17
EXPOSE 8080
ADD /target/backend.jar /feeder-webservice.jar
cmd java -jar /backend.jar