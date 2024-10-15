# Use an official OpenJDK runtime as a parent image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Add the application JAR file
COPY build/libs/productservice-0.0.1-SNAPSHOT-plain.jar productservice.jar

# Declare the build argument
ARG LOG_FOLDER

# Set environment variable
RUN echo "The folder is: ${LOG_FOLDER}"

# Create the directory
RUN mkdir -p ${LOG_FOLDER}

# port
ARG PRODUCT_SERVICE_PORT
EXPOSE ${PRODUCT_SERVICE_PORT}

# Run the JAR file
ENTRYPOINT ["java", "-jar", "productservice.jar"]
