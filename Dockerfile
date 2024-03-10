# Stage 1: Create the runtime image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files from the Jenkins workspace to the runtime image
COPY app/target/app-0.0.1.jar ./app-0.0.1.jar
COPY life-relation/target/life-relation-0.0.1.jar ./life-relation-0.0.1.jar
COPY life-shared/target/life-shared-0.0.1.jar ./life-shared-0.0.1.jar
COPY security-core/target/security-core-0.0.1.jar ./security-core-0.0.1.jar
COPY user/target/user-0.0.1.jar ./user-0.0.1.jar

# Copy the ConfigMap and Secret files
COPY app/src/main/resources/openshift/configmap.yaml ./configmap.yaml
COPY app/src/main/resources/openshift/secret.yaml ./secret.yaml

# Expose port 8080
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "./app-0.0.1.jar"]
