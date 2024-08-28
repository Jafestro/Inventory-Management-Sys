# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file and project files
COPY pom.xml /app
COPY src /app/src

# Install Maven and build the project
RUN apk add --no-cache maven
RUN mvn -f /app/pom.xml clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "/main/java/com/reppuhallinta/inventory_management_sys/InventoryManagementSysApplication.java"]
