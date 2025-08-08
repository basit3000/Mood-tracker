# Mood Tracker 

A simple mood tracker app with a backend and mobile functionalities.

[TO-DO]
https://trello.com/b/HlrMXS2S/mood-tracker

## How to setup (Windows)

1. ```bash
    ./gradlew.bat build
   ```

2. ```bash
   docker-compose up --build
   ```

# How to run (Windows)

```bash
docker-compose up
```

## For live changes (Windows)

After running docker, use

```bash
./gradlew bootRun --continuous
```

## If docker-compose fails because of gradle use this command
```bash
gradle wrapper
```