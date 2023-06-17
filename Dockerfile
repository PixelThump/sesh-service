FROM openjdk:17
EXPOSE 8080
ADD /target/sesh_service.jar /sesh_service.jar
CMD java -jar /sesh_service.jar