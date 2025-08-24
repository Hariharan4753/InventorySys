# ---------- Build Stage ----------
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy Maven files first to cache dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Copy the rest of the code and build
COPY src src
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
