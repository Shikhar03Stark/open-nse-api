FROM maven:3.8.5-openjdk-17-slim as build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:17
# Set the working directory in the container
WORKDIR /app
# Expose PORT
EXPOSE 5480
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/nse-stock-price-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar", "nse-stock-price-0.0.1-SNAPSHOT.jar"]