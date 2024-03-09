# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the Maven project file to the container
COPY pom.xml .

# Copy the source code
COPY app ./app
COPY life-relation ./life-relation
COPY life-shared ./life-shared
COPY security-core ./security-core
COPY user ./user

# Build the application
RUN mvn clean package

# Stage 2: Create the runtime image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files from the build stage to the runtime image
COPY --from=build /app/app/target/app-0.0.1.jar ./app-0.0.1.jar
COPY --from=build /app/life-relation/target/life-relation-0.0.1.jar ./life-relation-0.0.1.jar
COPY --from=build /app/life-shared/target/life-shared-0.0.1.jar /life-shared-0.0.1.jar
COPY --from=build /app/security-core/target/security-core-0.0.1.jar /security-core-0.0.1.jar
COPY --from=build /app/user/target/user-0.0.1.jar /user-0.0.1.jar

# Copy the ConfigMap and Secret files
COPY --from=build /app/app/src/main/resources/openshift/configmap.yaml ./configmap.yaml
COPY --from=build /app/app/src/main/resources/openshift/secret.yaml ./secret.yaml

# Expose port 8080
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "./app-0.0.1.jar"]
