FROM openjdk:21
EXPOSE 8080
ADD build/libs/productservice-0.0.1-SNAPSHOT.jar productservice.jar
ENTRYPOINT ["java", "-jar", "/productservice.jar"]