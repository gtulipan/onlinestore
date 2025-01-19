# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the .env file
COPY .env .env

# Add the application JAR file ## auth2service-0.0.1-SNAPSHOT-plain.jar
COPY build/libs/auth2service.jar auth2service.jar

# Declare the build argument
ARG LOG_FOLDER
# Create the directory
RUN mkdir -p ${LOG_FOLDER}

# port
ARG AUTH2_SERVICE_PORT
EXPOSE ${AUTH2_SERVICE_PORT}

# Run the JAR file
#ENTRYPOINT ["java", "-jar", "auth2service.jar"]
ENTRYPOINT ["java", "-cp", "auth2service.jar:BOOT-INF/lib/*", "org.springframework.boot.loader.launch.JarLauncher"]