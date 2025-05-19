
# Family Finance Manager

A Spring Boot application for managing household finances with multiple users.

## Overview

Family Finance Manager is a comprehensive solution for families to track their finances together. It allows multiple users to connect to a shared household, record transactions, track savings goals, and manage their financial health collectively.

## Features

- **User Management**
  - User registration and authentication
  - Profile management
  - Personal savings goals

- **Household Management**
  - Create and join households
  - View household members
  - Connect to other users' households

- **Financial Tracking**
  - Record income and expenses
  - Track transaction history
  - Calculate current balance
  - Monitor savings progress

- **Accounting Features**
  - Period closers for financial records
  - Balance calculations
  - Historical financial data

## Technology Stack

- **Backend**: Java 17, Spring Boot
- **Database**: PostgreSQL (configured via Spring Data JPA)
- **Security**: Spring Security with JWT authentication
- **Build Tool**: Maven

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/codecool/backend/
│   │   │   ├── controller/         # REST endpoints
│   │   │   ├── model/              # Entity classes
│   │   │   ├── repository/         # Database repositories
│   │   │   ├── service/            # Business logic
│   │   │   ├── config/             # Configuration classes
│   │   │   ├── security/           # Security-related classes
│   │   │   └── BackendApplication.java  # Main class
│   │   └── resources/              # Application properties
│   └── test/                       # Test classes
├── pom.xml                         # Dependencies and build configuration
└── README.md                       # This file
```

## Setup & Installation

### Prerequisites

- JDK 17 or higher
- Maven
- PostgreSQL database

### Steps

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/family-finance-manager.git
   cd family-finance-manager
   ```

2. Configure database connection in `application.properties`
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/financemgr
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   ```

3. Build the application
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

The application exposes the following main API endpoints:

### User Management

- `POST /api/users/register` - Register a new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/profile` - Update user profile
- `GET /api/users/current` - Get current authenticated user
- `GET /api/users/my-household` - Get current user's household members
- `GET /api/users/my-
