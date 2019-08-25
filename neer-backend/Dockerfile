FROM openjdk:12.0.2 AS build

WORKDIR /src
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

COPY build.gradle.kts gradle.properties gradlew settings.gradle.kts /src/
COPY resources/ /src/resources/
COPY src /src/resources/
COPY test /src/test/
COPY gradle/ /src/gradle/
RUN ./gradlew shadowJar

FROM openjdk:11.0.4-jre

WORKDIR /app

COPY --from=build /src/build/libs/ /app/

EXPOSE 3000
CMD java -jar /app/backend-*.jar