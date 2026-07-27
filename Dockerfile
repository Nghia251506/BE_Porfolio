FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Cache dependencies trước
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# ========== STAGE 2: Runtime siêu nhẹ ==========
FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache wget \
    && addgroup --system spring \
    && adduser --system --ingroup spring spring

USER spring:spring
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Ho_Chi_Minh"
ENV PORT=5000
EXPOSE ${PORT}

# Không có actuator → check OpenAPI docs (public)
HEALTHCHECK --interval=30s --timeout=5s --start-period=90s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://127.0.0.1:${PORT}/v3/api-docs || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=${PORT} --server.address=0.0.0.0"]
