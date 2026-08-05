# ==============================
# BUILD STAGE
# ==============================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy common library
COPY common-library /app/common-library

# Install common-library into Docker's local Maven repository
RUN mvn -f /app/common-library/pom.xml clean install -DskipTests

# Copy portfolio-service
COPY portfolio-service /app/portfolio-service

# Build portfolio-service
RUN mvn -f /app/portfolio-service/pom.xml clean package -DskipTests


# ==============================
# RUNTIME STAGE
# ==============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/portfolio-service/target/portfolio-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]