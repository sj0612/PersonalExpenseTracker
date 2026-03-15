# Personal Expense Tracker (Spring Boot)

A Spring Boot 3 backend for tracking personal income/expense entries with JWT authentication, PostgreSQL persistence, dashboard aggregations, and Swagger UI.

## Tech Stack

- Java 17
- Spring Boot 3.3.2
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- SpringDoc OpenAPI / Swagger UI

## Prerequisites

- JDK 17 installed (`java` and `javac` available)
- PostgreSQL running on `localhost:5432`
- Maven wrapper (already included: `mvnw.cmd`)

## Configuration

Current defaults in `src/main/resources/application.properties`:

- DB URL: `jdbc:postgresql://localhost:5432/petracker`
- DB user: `postgres`
- DB password: `postgres`
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`
- Demo data seed: enabled

You can change these values directly in `application.properties` for your environment.

## First-Time Setup (Windows PowerShell)

Create database (if missing):

```powershell
& "C:\Program Files\PostgreSQL\16\bin\psql.exe" -U postgres -c "CREATE DATABASE petracker;"
```

Build project:

```powershell
cd D:\Projects\PersonalExpenseTracker
.\mvnw.cmd clean compile
```

Run application:

```powershell
cd D:\Projects\PersonalExpenseTracker
.\mvnw.cmd spring-boot:run
```

## Demo User and Seeded Data

On startup, demo bootstrap (`DemoDataInitializer`) inserts:

- 1 demo user
- 6 categories
- Multi-month entries (both debit and credit) for dashboard APIs

Default demo credentials:

- Email: `demo@petracker.com`
- Password: `Demo@123`

> Seeding is idempotent: restarting the app does not duplicate demo rows.

## Swagger / API Docs

After server start:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Auth flow in Swagger:

1. Call `POST /user/login`
2. Copy `data.Token` from response
3. Click **Authorize** in Swagger UI
4. Paste token as Bearer token

## API Quick Reference

### Public

- `POST /user/signup` - register user
- `POST /user/login` - get JWT token

### Secured (Bearer Token required)

- `GET /entry/get?page=0&size=10&sortBy=added_Time&sortDirection=desc`
- `POST /entry/add`
- `PUT /entry/update`
- `DELETE /entry/delete?id={entryId}`
- `GET /dashboard/getCategoryWiseAmount/CategorySums`
- `GET /dashboard/getCategoryWiseAmount/MonthlySummary`

## Dashboard Aggregation Endpoints

### Category-wise totals

`GET /dashboard/getCategoryWiseAmount/CategorySums`

Response shape:

```json
[["Bills",12550.0],["Entertainment",7600.0],["Food",5000.0]]
```

### Month-wise debit/credit summary

`GET /dashboard/getCategoryWiseAmount/MonthlySummary`

Response shape:

```json
[["Mar-2026",13340.0,85000.0],["Feb-2026",6510.0,170000.0]]
```

Format is:

- `[monthYear, totalDebit, totalCredit]`

## Common Troubleshooting

### 1) `No compiler is provided in this environment`

You are running with JRE or wrong Java in `PATH`.

```powershell
java -version
javac -version
```

Ensure JDK 17 is used.

### 2) DB/Hibernate startup failures

- Verify PostgreSQL service is running
- Verify DB `petracker` exists
- Verify username/password in `application.properties`

### 3) Port 8080 already in use

```powershell
netstat -ano | findstr ":8080"
Stop-Process -Id <PID> -Force
```

### 4) Swagger opens but secured APIs fail

- Login first via `/user/login`
- Add JWT token using Swagger **Authorize**

## Useful Commands

Compile only:

```powershell
cd D:\Projects\PersonalExpenseTracker
.\mvnw.cmd compile
```

Run tests:

```powershell
cd D:\Projects\PersonalExpenseTracker
.\mvnw.cmd test
```

## Notes for Git

Do not commit temporary local artifacts like:

- `*.log`
- root-level debug `*.json` (API output snapshots)

Add to `.gitignore` if needed.

