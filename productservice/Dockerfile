# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Add the application JAR file ## productservice-0.0.1-SNAPSHOT-plain.jar
COPY build/libs/productservice.jar productservice.jar

# Declare the build argument
ARG LOG_FOLDER
# Create the directory
RUN mkdir -p ${LOG_FOLDER}

# port
ARG PRODUCT_SERVICE_PORT
EXPOSE ${PRODUCT_SERVICE_PORT}

# Run the JAR file
#ENTRYPOINT ["java", "-jar", "productservice.jar"]
ENTRYPOINT ["java", "-cp", "productservice.jar:BOOT-INF/lib/*", "org.springframework.boot.loader.launch.JarLauncher"]