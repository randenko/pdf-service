FROM maven:3.6.3-openjdk-11 as build
WORKDIR /workspace/app
COPY src src
COPY pom.xml .
RUN mvn clean package spring-boot:repackage

FROM openjdk:11-jre-slim
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-XX:MaxRAM=2048M", "-XshowSettings:vm", "-cp", "app:app/lib/*", "com.paperstreetsoftware.pdf.App"]