FROM openjdk:17
EXPOSE 8080
ADD /target/seshservice.jar /seshservice.jar
CMD java -jar /seshservice.jar