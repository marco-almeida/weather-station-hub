# FROM openjdk:17-jdk-alpine

# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar

# EXPOSE 8080

# ENTRYPOINT ["java","-jar","/app.jar"]

# Start with a base image containing Java runtime and Maven
FROM maven:3.8.1-openjdk-17 AS build

# Make source folder
WORKDIR /usr/src/app

# Copy the pom.xml file
COPY ./pom.xml ./pom.xml

# Download all required dependencies into one layer
RUN mvn dependency:go-offline -B

# Copy your other files
COPY ./src ./src

# Build with maven
RUN mvn package -DskipTests

# ----
# Final stage : Setup for running the application
# ----
FROM openjdk:17-jdk-alpine

# App port
EXPOSE 8080

# Copy the jar from the build stage
COPY --from=build /usr/src/app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]