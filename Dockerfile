FROM gradle:8.7-jdk17

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew clean build -x test --no-daemon

COPY . .

EXPOSE 8080

ENTRYPOINT ["./gradlew", "bootRun", "--no-daemon"]