# EcoRepairHub

Spring Boot backend scaffold generated from the EcoRepairHub ERD, with six JPA entities:

| Entity | Table | Notes |
|---|---|---|
| `User` | `users` | submits repair requests |
| `Collector` | `collectors` | collects repair requests, has a `zone` |
| `Admin` | `admins` | approves repair requests |
| `RepairingCenter` | `repairing_centers` | repairs requests, has a `status` (`ACTIVE`/`INACTIVE`) |
| `RepairRequest` | `repair_requests` | central entity — FKs to User, Collector, Admin, RepairingCenter |
| `RequestTracking` | `request_tracking` | FK to RepairRequest — logs each stage change over time |

## Structure

```
src/main/java/com/ecorepairhub/
  entity/         6 JPA entities
  repository/     matching Spring Data repositories
  enums/          RequestStage, CenterStatus
  EcoRepairHubApplication.java
src/main/resources/application.properties
```

## Run it

```bash
gradle bootRun
```

(No Gradle wrapper is bundled here since it needs a network download; if you'd
like one, run `gradle wrapper` once inside the project, or ask and I'll add
`gradlew`/`gradlew.bat` files directly.)

Defaults to an in-memory H2 database, so it runs immediately with no setup —
console at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:ecorepairhub`).

To use MySQL instead: in `application.properties`, comment out the H2 block and
uncomment the MySQL block, then set your own username/password.

## What's included vs. what's not

Entities, repositories (with a few derived-query finders), and a working
application/DB config are included. **Service and REST controller layers are
not** — this is deliberately just the persistence layer matching your ERD, so
you can build the API on top however you want. Say the word if you'd like me
to add a REST layer (CRUD controllers + DTOs) or a `data.sql` seed file next.
