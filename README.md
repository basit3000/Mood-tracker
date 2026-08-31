# Mood Tracker

Spring Boot web app for registering users, signing in, and (intended) logging daily moods into MongoDB. UI is server-rendered Thymeleaf + Bootstrap.

> **Scope note:** Despite older copy, this repo is a **web** app only. There is no Android/iOS client in-tree.

## Tech stack

| Layer | Technology |
|-------|------------|
| Runtime | Java 17, Gradle Wrapper |
| Framework | Spring Boot 3.2.4 |
| Security | Spring Security (form login, BCrypt) |
| Persistence | Spring Data MongoDB |
| UI | Thymeleaf, Bootstrap 5 |
| Containers | Docker Compose (`mongo:7.0.14` + app) |

## Architecture

```
Browser  →  Spring MVC (Thymeleaf)
                │
                ├─ /public/**   HomeController   (register, login pages)
                ├─ /login       Spring Security  (form POST processor)
                ├─ /mood/**     MoodController   (dashboard; mood CRUD on feature branch)
                └─ /admin/**    AdminController  (user/mood admin JSON or redirects)
                │
                └─ MongoDB collections: User, mood
```

**Auth flow (verified in `SecurityConfig`):**

1. Public pages under `/public/**` and static `/css/**`, `/js/**` are open.
2. Login page is `GET /public/login`. Form must POST to **`/login`** (Spring Security `loginProcessingUrl`), with field name **`email`** (not `username`).
3. Successful login redirects to `/mood/dashboard`.
4. Passwords are BCrypt-hashed in `UserServices.save`; new users get role `USER`.
5. `CustomUserService` loads users by email for authentication.

## Project structure

```
├── build.gradle / settings.gradle   # Spring Boot 3.2.4, Mongo, Security, Thymeleaf
├── Dockerfile                       # gradle:8.7-jdk17, ENTRYPOINT bootRun
├── docker-compose.yml               # mongo + app (src bind-mounted)
├── .env.example                     # Compose / Spring env keys
└── src/main/
    ├── java/com/example/app/
    │   ├── AppApplication.java      # @SpringBootApplication entrypoint
    │   ├── controller/              # Home, Mood, Admin
    │   ├── model/                   # User, Mood documents
    │   ├── repository/              # MongoRepository interfaces
    │   ├── Service/ or service/     # UserServices (+ MoodServices on feature branch)
    │   └── SecurityConfig/ or securityConfig/
    └── resources/
        ├── application.properties   # Mongo URI + server.port from env
        ├── static/css/
        └── templates/               # home, login, register, dashboard, …
```

## Environment

Copy `.env.example` → `.env` and fill values. Compose reads these keys:

| Variable | Purpose |
|----------|---------|
| `MONGO_INITDB_ROOT_USERNAME` / `MONGO_INITDB_ROOT_PASSWORD` | Mongo root user created on first volume init |
| `MONGO_DB` | Logical DB name (use in the URI path) |
| `MONGO_PORT` | Host port mapped to container `27017` |
| `SPRING_DATA_SERVER_PORT` | Host port mapped to app `8080`; also passed as `SERVER_PORT` inside the app container |
| `SPRING_DATA_MONGODB_URI` | Full Spring Mongo URI (must reach host `mongo` on the Compose network) |

Example `.env`:

```env
MONGO_INITDB_ROOT_USERNAME=root
MONGO_INITDB_ROOT_PASSWORD=changeme
MONGO_DB=moodtracker
MONGO_PORT=27017
SPRING_DATA_SERVER_PORT=8080
SPRING_DATA_MONGODB_URI=mongodb://root:changeme@mongo:27017/moodtracker?authSource=admin
```

`application.properties` expects:

- `SPRING_DATA_MONGODB_URI`
- `SERVER_PORT` (Compose sets this from `SPRING_DATA_SERVER_PORT`)

## Setup

### Prerequisites

- JDK 17+
- Docker & Docker Compose (recommended)
- Or: local MongoDB 7.x if running the app outside Compose

### Docker (recommended)

```bash
cp .env.example .env   # then edit values
docker compose up --build
```

App: `http://localhost:${SPRING_DATA_SERVER_PORT}` (default `8080` if set).  
Mongo: host port `${MONGO_PORT}`.

Live edits: Compose bind-mounts `./src` into `/app/src`. Spring DevTools livereload is enabled in `application.properties`. For a continuous local rebuild without Compose’s app service, you can still run `./gradlew bootRun --continuous` against a running Mongo.

### Local Gradle (without Docker app)

```bash
# Start Mongo yourself, then:
export SPRING_DATA_MONGODB_URI='mongodb://…'
export SERVER_PORT=8080
./gradlew bootRun
```

Windows: use `gradlew.bat`. If the wrapper JAR is missing, regenerate with `gradle wrapper`.

## Routes (`main`)

| Method | Path | Auth | Behavior |
|--------|------|------|----------|
| GET | `/public/home` | Public | Landing page |
| GET | `/public/login` | Public | Login form |
| GET/POST | `/public/register` | Public | Registration form → saves user → redirect login |
| POST | `/login` | Public | Spring Security form login (`email` + `password`) |
| GET | `/mood/dashboard` | Authenticated | Dashboard for current user |
| GET | `/mood/hello` | Authenticated | Returns all moods via repository (controller returns `List<Mood>` from a `@Controller` — not a polished API) |
| GET | `/admin/showAllUsers` | Authenticated | JSON list of all users |
| DELETE | `/admin/delete/{userId}` | Authenticated | Deletes user by id |

Logout uses Spring Security’s default logout endpoint (permitted for all).

### Data model (`main`)

**User** (`User` collection): `id`, `name`, `age`, unique `email`, `role`, `password`.

**Mood** (`mood` collection): `id`, `feel`, `note`, `date` (`LocalDate`). On `main` there is no `userId` field and no history query by user.

## Open feature work (`UpdateDetail_Feature` / PR #3)

Not on `main` yet. Verified on that branch for developers merging or reviewing:

| Area | What lands |
|------|------------|
| Mood CRUD | `GET/POST /mood/addMoodPage`, `GET /mood/history`, `POST /mood/deleteNote/{id}`, `POST /mood/editNote/{id}`, `POST /mood/editNote` |
| Profile | `GET/POST /mood/updateDetails` (password confirm before update) |
| Admin | `/admin/**` restricted with `hasAuthority("admin")`; list users, delete user, delete post, history by user id/email |
| Validation | Custom annotations: `@AgeLimit`, `@DuplicateEmail`, `@StrongPwd` (length ≥ 8, uppercase + digit); `@AgeLimit` currently accepts age ≥ **5** |
| Mood model | Adds `userId`; `date` becomes `Instant`; timestamps use the user’s `timezone` |
| Packages | Controllers/services under `service` / `securityConfig` (lowercase); template `register.html` (lowercase) |
| Login form | Posts to `/login` (fixes the main-branch form action mismatch) |

**Role caveat:** registration still sets role `"USER"`, while admin matchers check authority `"admin"`. Admins must be provisioned in Mongo with that exact role string; `@PreAuthorize` is present on the admin controller but method security is not separately enabled—HTTP matchers in `SecurityConfig` are the effective gate.

## Known pitfalls (`main`)

1. **Login form action** — `login.html` posts to `/public/login`, but Security only processes **`/login`**. Use `/login` as the form action (already fixed on the feature branch).
2. **Dashboard dead links** — Dashboard buttons point at `/mood/track` and `/mood/history`; those handlers are **not** implemented on `main` (feature branch uses `/mood/addMoodPage` and `/mood/history`).
3. **Register template name** — Controller returns view `"register"`; file on `main` is `Register.html`. Case-sensitive filesystems (Linux containers) may 404; feature branch renames to `register.html`.
4. **Duplicate source in some controllers** — `HomeController`, `AdminController`, and `SecurityConfig` on `main` contain duplicated fields/methods and will fail to compile until cleaned up. Prefer fixing those before relying on a green `bootRun`.
5. **`bootJar` main class** — `build.gradle` sets `com.example.mood.MoodApplication`, but the real entrypoint is `com.example.app.AppApplication`. Docker uses `bootRun`, which still discovers the `@SpringBootApplication` class; a packaged JAR build needs the main class corrected.
6. **Dockerfile build order** — The image runs `gradlew clean build -x test` **before** `COPY . .`, so that build step has no application sources. Runtime relies on `bootRun` after the full copy.
7. **Admin surface** — On `main`, `/admin/**` only requires authentication (any logged-in user), not an admin role.
8. **Empty env values** — Leaving `SPRING_DATA_SERVER_PORT` / `MONGO_PORT` blank breaks Compose port mapping. Set explicit integers.
9. **Mongo volume auth** — Changing root credentials after the first `mongo_data` volume init has no effect; remove the volume or create users manually.

## Troubleshooting

| Symptom | Likely cause | What to try |
|---------|--------------|-------------|
| Compose fails on Gradle | Wrapper / first build issues | `./gradlew wrapper`, then `docker compose up --build` |
| App can’t reach Mongo | Bad URI host or auth | Inside Compose use hostname `mongo` and `authSource=admin` |
| Login always fails / no POST handling | Form posts to `/public/login` | Change form `th:action` to `@{/login}` |
| Register 404 after POST redirect works but GET register fails | `Register.html` vs `register` | Rename template to `register.html` |
| Port already in use | Host port conflict | Change `SPRING_DATA_SERVER_PORT` / `MONGO_PORT` in `.env` |
| Changes not picked up | Stale container without mount | Confirm `./src` volume; restart app service |

## Related links

- Trello board (product backlog): https://trello.com/b/HlrMXS2S/mood-tracker
- Open feature PR: [#3 Admin features](https://github.com/basit3000/Mood-tracker/pull/3) (`UpdateDetail_Feature`)
