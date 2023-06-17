FROM openjdk:17
EXPOSE 8080
ADD /target/PixelVibe_Backend.jar /PixelVibe_Backend.jar
CMD java -jar /PixelVibe_Backend.jar