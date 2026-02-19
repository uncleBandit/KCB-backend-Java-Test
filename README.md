Bank Masking Spring Boot Project
Project Overview

This project demonstrates a reusable Spring Boot Starter Library for masking sensitive data in logs, and a sample CRUD Books API that uses it.

Modules:

bank-masking-spring-boot-starter – Reusable Spring Boot starter for masking sensitive fields in logs.

bank-books-api-demo – Sample Spring Boot CRUD API for managing books, demonstrating the masking starter.

Features
Starter Library – bank-masking-spring-boot-starter

Masks sensitive fields when logged (e.g., email, phone number, SSN, credit card).

Configurable via application.yaml:

p11:
  masking:
    enabled: true
    fields:
      - email
      - phoneNumber
      - ssn
      - creditCardNumber
    mask-style: PARTIAL
    mask-character: "*"

Supports masking styles:

FULL – completely masked: ********

PARTIAL – partially masked: jo***@gmail.com

LAST4 – show only last 4 chars: ********1234

Safe for nested objects and lists.

Original objects remain unmodified.

Can be enabled or disabled via configuration.

Consumer Application – bank-books-api-demo

Spring Boot 3+, H2 in-memory database.

CRUD REST API for Book entity:

@Entity
public class Book {
    private Long id;
    private String title;
    private String author;
    private String email;
    private String phoneNumber;
    private String publisher;
}

Endpoints:

POST /books – Create book

GET /books – List books

GET /books/{id} – Get book by ID

PUT /books/{id} – Update book

DELETE /books/{id} – Delete book

Logs automatically mask sensitive fields (email, phoneNumber) while keeping DB values unmasked.

Project Structure
kcb-parent/
│
├─ bank-masking-spring-boot-starter/   # Masking library
├─ kcb-books-api-demo/                # Consumer API demonstrating masking
├─ pom.xml                             # Parent Maven POM
└─ README.md
How to Run Locally

Clone the repository:

git clone <YOUR_REPO_URL>
cd kcb-parent

Build the project:

mvn clean install

Run the Books API:

cd bank-books-api-demo
mvn spring-boot:run

Test API endpoints at http://localhost:8080/books.

Running Tests

Run all unit and integration tests:

mvn test

Test results are shown in the console.

Unit tests verify masking logic.

Integration tests ensure logs are masked while DB stores original values.

Coverage report (Jacoco):

mvn jacoco:report

Open target/site/jacoco/index.html to view coverage.

Project ensures ≥ 80% code coverage.

Design Decisions

Masking implemented via service – avoids modifying original objects.

Configuration-based – fields, style, enable/disable.

SOLID principles – starter is modular, reusable, and testable.

Logging strategy – used AOP/interceptors to mask sensitive fields in logs.

Assumptions

Only fields specified in application.yaml are sensitive.

Nested objects and lists can contain sensitive fields.

H2 in-memory DB is sufficient for demonstration.

Next Steps / Optional Improvements

Annotation-based masking @Mask on fields.

Swagger/OpenAPI documentation.

CI/CD pipeline for automated builds and tests.

Dockerization for easy deployment.

Contact

For any questions, please contact:
pitmwania@gmail.com
