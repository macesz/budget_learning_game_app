
# Petconomy Gamified Budgeting and Finance Manager App

A full-stack application for managing household finances with multiple users, featuring a gamified learning experience.

## Overview

Family Finance Manager is a comprehensive solution for families to track their finances together. It allows multiple users to connect to a shared household, record transactions, track savings goals, and manage their financial health collectively. The application includes a unique gamification feature where users nurture a virtual pet that evolves as they develop healthy saving habits.

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

- **Gamified Financial Learning**
  - Virtual pet companion that grows with savings achievements
  - Pet evolution tied to reaching financial milestones
  - Educational modules on financial literacy
  - Rewards for consistent saving habits and completing financial challenges
  - Visual representation of financial progress through pet's health and evolution

## Technology Stack

### Backend
- Java 17
- Spring Boot
- PostgreSQL (configured via Spring Data JPA)
- Spring Security with JWT authentication
- Maven

### Frontend
- React
- Node.js
- CSS
- Tailwind CSS
- Daisy UI (component library)


```

## Setup & Installation

### Prerequisites

- JDK 17 or higher
- Maven
- Node.js (v14 or higher)
- npm or yarn
- PostgreSQL database

### Backend Setup

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/family-finance-manager.git
   cd family-finance-manager/backend
   ```

2. Configure database connection in `application.properties`
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/financemgr
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   ```

3. Build and run the backend
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory
   ```bash
   cd ../frontend
   ```

2. Install dependencies
   ```bash
   npm install
   # or
   yarn install
   ```

3. Create a `.env` file for environment variables
   ```
   REACT_APP_API_URL=http://localhost:8080/api
   ```

4. Start the development server
   ```bash
   npm start
   # or
   yarn start
   ```

5. The application will be available at `http://localhost:3000`

## API Documentation

The application exposes the following main API endpoints:

### User Management

- `POST /api/users/register` - Register a new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/profile` - Update user profile
- `GET /api/users/current` - Get current authenticated user
- `GET /api/users/my-household` - Get current user's household members
- `GET /api/users/my-saving` - Get current user's savings progress

### Accounting

- `GET /api/accounting/balance/{householdId}` - Get household balance
- `GET /api/accounting/balance/current` - Get current user's household balance
- `POST /api/accounting/closer/{householdId}` - Create a new closer
- `GET /api/accounting/closer/{householdId}/latest` - Get the latest closer
- `GET /api/accounting/closer/{householdId}` - Get all closers for a household
- `GET /api/accounting/savings` - Get current user's savings

### Gamification

- `GET /api/pet/status` - Get current pet status and evolution stage
- `GET /api/pet/achievements` - Get achievements and milestones reached
- `POST /api/pet/interact` - Interact with virtual pet
- `GET /api/learning/modules` - Get available financial learning modules
- `POST /api/learning/complete` - Mark a learning module as completed

## Virtual Pet Evolution System

The application features a unique virtual pet system that grows and evolves as users improve their financial habits:

1. **Evolution Stages**:
   - Egg stage: New users start with a pet egg
   - Baby stage: Unlocked after first savings goal reached
   - Youth stage: Achieved after consistent saving for one month
   - Adult stage: Reached after multiple financial milestones
   - Special forms: Unique evolutions based on saving patterns and financial knowledge

2. **Growth Factors**:
   - Regular deposits to savings
   - Reaching savings goals
   - Completing financial education modules
   - Maintaining budget discipline
   - Household collaborative achievements

3. **Pet Care**:
   - Feed your pet by adding to savings
   - Keep it healthy by avoiding unnecessary expenses
   - Help it grow by learning financial concepts

## UI Features

- **Responsive Design**: Works on mobile, tablet, and desktop
- **Dark/Light Mode**: Toggle between light and dark themes
- **Interactive Dashboard**: Visual overview of finances with charts and graphs
- **Virtual Pet Interface**: Interactive pet habitat with animations and evolutions
- **User-Friendly Forms**: Streamlined transaction entry and budget management
- **Daisy UI Components**: Sleek, consistent interface using Daisy UI elements

## Testing

### Backend Tests
```bash
# Run all tests
cd backend
mvn test

# Run specific test class
mvn test -Dtest=UserEntityServiceTest
```

### Frontend Tests
```bash
# Run all tests
cd frontend
npm test
```

## Future Enhancements

- Mobile app integration
- Expanded pet evolution paths
- Budget planning features
- Financial reports and analytics
- Expense categorization
- Bill reminders and scheduling
- AR features to interact with your financial pet
- Multiplayer challenges and pet interactions between household members
- Social features for sharing achievements
- Progressive web app (PWA) support

