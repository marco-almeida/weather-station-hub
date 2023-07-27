FROM openjdk:17-jdk-alpine AS build

WORKDIR /usr/src/app

COPY ./pom.xml ./pom.xml
COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw

RUN chmod +x ./mvnw

# account for CRLF line endings
RUN apk add --no-cache dos2unix && dos2unix ./mvnw && chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

COPY ./src ./src

RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-alpine

# Copy the jar from the build stage
COPY --from=build /usr/src/app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]