# Start with a base Alpine image
#FROM alpine:3.16
#
## Install necessary packages
#RUN apk add --no-cache wget tar bash
#
## Download and install OpenJDK 21
#RUN wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk/21.0.5+11/openlogic-openjdk-21.0.5+11-linux-x64.tar.gz -O /tmp/openjdk.tar.gz \
#    && mkdir -p /opt/openjdk-21 \
#    && tar -zxf /tmp/openjdk.tar.gz --strip-components=1 -C /opt/openjdk-21 \
#    && rm /tmp/openjdk.tar.gz

## Set environment variables for JDK 21
#ENV JAVA_HOME=/opt/openjdk-21
#ENV PATH="$JAVA_HOME/bin:$PATH"

FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY build/libs/locale_bundles.jar app.jar

# Declare the build argument and create the directory
ARG LOG_FOLDER
RUN mkdir -p ${LOG_FOLDER}

# Expose the port
ARG LOCAL_BUNDLES_SERVICE_PORT
EXPOSE ${LOCAL_BUNDLES_SERVICE_PORT}

# Run the JAR file
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-cp", "app.jar:BOOT-INF/lib/*", "org.springframework.boot.loader.launch.JarLauncher"]