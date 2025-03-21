# Use an official Maven image to build the project
FROM maven:3.8.6-openjdk-17 AS builder

# Set working directory in container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/onborading-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8915

# Run the application
CMD ["java", "-jar", "app.jar"]
