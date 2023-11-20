FROM openjdk:17
COPY app/target/app-0.0.1.jar app-0.0.1.jar
COPY life-relation/target/life-relation-0.0.1.jar life-relation-0.0.1.jar
COPY life-shared/target/life-shared-0.0.1.jar life-shared-0.0.1.jar
COPY security-core/target/security-core-0.0.1.jar security-core-0.0.1.jar
COPY user/target/user-0.0.1.jar /user-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app-0.0.1.jar"]