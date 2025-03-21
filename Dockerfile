# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/*.jar app.jar

# Expose the application port (update this if needed)
EXPOSE 8915

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
