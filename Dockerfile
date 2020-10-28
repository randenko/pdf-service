FROM maven:3.6.3-openjdk-14 as build
WORKDIR /workspace/app
COPY src src
COPY pom.xml .
RUN mvn clean package spring-boot:repackage

FROM openjdk:14-alpine
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-XshowSettings:vm", "-cp", "app:app/lib/*", "com.paperstreetsoftware.pdf.App"]