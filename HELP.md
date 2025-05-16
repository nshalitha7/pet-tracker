# Pet Tracker Application

This is a Java Spring Boot application that manages pet tracking data and calculates the number of pets currently
outside of the power saving zone, grouped by `petType` and `trackerType`.

## Tech Stack

- Java 21
- Spring Boot 3
- H2 Database
- JUnit 5 / Mockito / MockMvc
- MapStruct
- Maven

## Features

- Submit pet tracking data via REST API
- Retrieve list of all pets
- Get summary of pets outside the power saving zone
- In-memory H2 database with web console enabled
- Easily replaceable storage layer
- Unit + integration test coverage
- Swagger UI

## Running the Application

```bash
# Run from root directory
./mvnw spring-boot:run

# Or via IntelliJ:
- Open project
- Run PetTrackerApplication.java
```

## Access H2 Console

- Visit: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:tractivedb
- Username: sa
- Password:

## Running Tests

```bash
    ./mvnw clean test
```

## Test Coverage

The project includes:

- Unit tests for service layer logic (Mockito)
- Integration tests for REST endpoints (MockMvc)
- Validation tests for request constraints
- Coverage: critical paths > 90% tested (100%)

For full test coverage report:

```bash
    ./mvnw clean verify
    open target/site/jacoco/index.html
```

## API Endpoints

Create a Pet

```bash
    curl -X POST http://localhost:8080/api/v1/pets \
    -H "Content-Type: application/json" \
    -d '{
    "petType": "CAT",
    "trackerType": "SMALL",
    "ownerId": 101,
    "inZone": false,
    "lostTracker": true
    }'
```

```bash
    curl -X POST http://localhost:8080/api/v1/pets \
    -H "Content-Type: application/json" \
    -d '{
    "petType": "DOG",
    "trackerType": "BIG",
    "ownerId": 102,
    "inZone": true
    }'
```

List Pets

```bash
    curl http://localhost:8080/api/v1/pets
```

Summary of Pets Outside Zone

```bash
      curl http://localhost:8080/api/v1/pets/summary
```

## Swagger UI

After starting the app, visit:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Future Enhancements

- Add pagination for `GET /pets`
- Store timestamp on pet creation for historical tracking
- Add filtering (eg: by `petType`, `inZone`, etc.)

### Scalability Suggestions

- Replace H2 with PostgreSQL or another persistent DB
- Add caching (Redis) for frequent `summary` queries
- Expose metrics via Actuator (eg: `/actuator/prometheus`)
- Deploy with Docker & configure CI/CD
- If needed, split read/write endpoints for CQRS pattern
- Use DTO versioning for backward-compatible changes
