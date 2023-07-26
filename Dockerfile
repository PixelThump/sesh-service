FROM openjdk:17
EXPOSE 8080
ADD /target/sesh-service.jar /sesh-service.jar
CMD java -jar /sesh-service.jar