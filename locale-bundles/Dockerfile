# Use an official OpenJDK runtime as a parent image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy the entire project. This necessary when the i18n folder is in the project root and not in the own resources folder
#COPY . /app
# Add the application JAR file
COPY ./build/libs/locale-bundles-0.0.1-SNAPSHOT-plain.jar locale_bundles.jar

# Set environment variable. This necessary when the i18n folder is in the project root and not in the own resources folder
#ENV PROJECT_ROOT=${PROJECT_ROOT}

# Declare the build argument
ARG LOG_FOLDER
# Create the directory
RUN mkdir -p ${LOG_FOLDER}

# port
ARG LOCAL_BUNDLES_SERVICE_PORT
EXPOSE ${LOCAL_BUNDLES_SERVICE_PORT}

# Run the JAR file
ENTRYPOINT ["java", "-jar", "locale_bundles.jar"]
