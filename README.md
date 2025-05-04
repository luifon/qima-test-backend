
QIMA Test - Backend Setup Guide
===============================

🧰 Tech Stack:
--------------
- Spring Boot 3.4
- Spring Security
- Spring Data JPA
- Flyway
- PostgreSQL
- Docker Compose

🚀 How to Run the Backend:
--------------------------

1. Clone the repository and navigate to the backend project root.

2. Start the PostgreSQL database using Docker Compose:

   ```bash
   docker-compose up -d
   ```

   This will start a PostgreSQL container with the default config:
   - Username: `admin`
   - Password: `admin`
   - Port: `5432`

3. Configure application.yaml if needed (already set to connect to PostgreSQL on localhost:5432).

4. Run Flyway migrations:

   Flyway is already integrated. On Spring Boot startup, it will automatically run migration scripts from `src/main/resources/db/migration`.

5. Run the Spring Boot application:

   ```bash
   ./mvnw spring-boot:run
   ```

6. Access the backend API:

   The application will be available at: `http://localhost:8080`

🔐 Superuser:
-------------
Basic Auth is enabled. Use the following credentials:

- Username: `admin`
- Password: `admin123`

These credentials are set in the Spring Security configuration.


