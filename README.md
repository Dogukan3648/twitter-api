# 🐦 Twitter REST API

A social media REST API built with **Java, Spring Boot, Spring Security, and PostgreSQL**, featuring user authentication, tweet management, comments, likes, and retweets.

The application follows a layered backend architecture and includes a React frontend for interacting with the API.

Developed as part of the Workintech Full-Stack Developer program.

## 🌐 Live Demo

**[Open React Application](https://twitter-frontend-afvu.onrender.com)**

**[View Source Code](https://github.com/Dogukan3648/twitter-api)**

The React application is deployed on Render. Backend availability depends on the active deployment.

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot 4.1.2
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Maven

### Frontend
- React
- JavaScript
- Vite
- Tailwind CSS
- Axios

### Security & API Documentation
- HTTP Basic Authentication
- BCrypt Password Hashing
- Swagger UI / OpenAPI
- CORS Configuration

### Testing
- JUnit 5
- Mockito
- Spring Boot Test

### Deployment
- Docker
- Render

---

## ✨ Features

### Authentication & Security
- User registration and login
- Password hashing with BCrypt
- HTTP Basic Authentication for protected endpoints
- Authorization based on resource ownership
- Duplicate email validation
- Input validation and centralized error handling

### Tweet Management
- Create tweets
- Retrieve tweets by ID
- List tweets by user
- Update and delete owned tweets
- Validate tweet content (maximum 280 characters)

### Comment Management
- Create, update, and delete comments
- List comments associated with a tweet
- Validate comment content
- Enforce ownership-based permissions

### Likes & Retweets
- Like and unlike tweets
- Prevent duplicate likes
- Create and remove retweets
- Prevent duplicate retweets
- Retrieve like status and retweet information

---

## 🏗️ Project Architecture

The backend follows a layered architecture to separate business logic, HTTP handling, and database operations.

| Layer | Responsibility |
|---|---|
| Controller | Handles HTTP requests and responses |
| DTO | Transfers and validates request data |
| Service | Implements business rules and authorization |
| Repository | Performs database operations |
| Entity | Maps Java objects to database tables |
| Exception | Provides centralized error handling |
| Config | Defines security and CORS configuration |

The backend source code is organized under:

`src/main/java/com/workintech/twitterapi/`

The repository also contains a separate React application inside the `frontend/` directory.

### Design Principles

- Separation of concerns
- Dependency injection
- Interface-based service design
- Layered architecture
- Transactional database operations
- Centralized exception handling

---

## 🗄️ Database Design

PostgreSQL is used for persistent data storage.

The database contains five main tables:

| Table | Description |
|---|---|
| `users` | Registered users and authentication data |
| `tweets` | Tweets created by users |
| `comments` | Comments associated with tweets |
| `likes` | Relationships between users and liked tweets |
| `retweets` | Relationships between users and retweeted tweets |

### Entity Relationships

- A user can create multiple tweets.
- A tweet belongs to one user.
- A tweet can have multiple comments.
- Users can like and retweet multiple tweets.
- Duplicate likes and retweets are prevented through application rules and database constraints.

The database schema is available in [`schema.sql`](https://github.com/Dogukan3648/twitter-api/blob/main/schema.sql).

---

## 🔗 REST API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/register` | Register a new user |
| POST | `/login` | Authenticate a user |

### Tweets

| Method | Endpoint | Description |
|---|---|---|
| POST | `/tweet` | Create a tweet |
| GET | `/tweet/findById/{id}` | Retrieve a tweet |
| GET | `/tweet/findByUserId/{userId}` | Retrieve a user's tweets |
| PUT | `/tweet/{id}` | Update a tweet |
| DELETE | `/tweet/{id}` | Delete a tweet |

### Comments

| Method | Endpoint | Description |
|---|---|---|
| POST | `/comment` | Create a comment |
| GET | `/comment/tweet/{tweetId}` | List comments for a tweet |
| PUT | `/comment/{id}` | Update a comment |
| DELETE | `/comment/{id}` | Delete a comment |

### Likes

| Method | Endpoint | Description |
|---|---|---|
| POST | `/like` | Like a tweet |
| POST | `/dislike` | Remove a like |
| GET | `/like/status/{tweetId}` | Check like status |

### Retweets

| Method | Endpoint | Description |
|---|---|---|
| POST | `/retweet` | Retweet a tweet |
| GET | `/retweet/tweet/{tweetId}` | Retrieve retweet information |
| DELETE | `/retweet/{id}` | Remove a retweet |

All endpoints except registration, login, and API documentation require authentication.

---

## 🔐 Security

Spring Security protects the application's REST endpoints.

### Authentication

The application uses HTTP Basic Authentication.

- User passwords are hashed using BCrypt.
- Login credentials are validated through Spring Security.
- Protected endpoints require valid credentials.
- Authentication failures return appropriate HTTP error responses.

### Authorization

Ownership validation prevents unauthorized modifications.

For example:
- Only the tweet owner can update or delete their tweet.
- Comment operations enforce ownership rules.
- Duplicate likes and retweets are rejected.

**Note:** This project uses HTTP Basic Authentication, not JWT-based authentication. HTTPS is required when transmitting credentials in a deployed environment.

---

## 📖 Swagger / OpenAPI

The project includes Swagger UI for interactive API documentation.

After starting the backend locally, open:

`http://localhost:3000/swagger-ui/index.html`

OpenAPI specification:

`http://localhost:3000/v3/api-docs`

Swagger allows developers to inspect endpoints, request models, and API operations.

---

## ⚙️ Local Installation

### Prerequisites

- Java 17
- PostgreSQL
- Node.js and npm
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/Dogukan3648/twitter-api.git
cd twitter-api
```

### 2. Configure PostgreSQL

Create a database named `twitter_db`.

Import the database schema:

```bash
psql -U postgres -d twitter_db -f schema.sql
```

The application uses Hibernate schema validation rather than automatically creating tables.

### 3. Configure Environment Variables

The backend supports the following environment variables:

| Variable | Description |
|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `PORT` | Server port (defaults to 3000) |

Example local configuration:

```env
DB_URL=jdbc:postgresql://localhost:5432/twitter_db
DB_USERNAME=postgres
DB_PASSWORD=your_database_password
PORT=3000
```

Set these variables in your operating system or IDE run configuration before starting the application.

Do not commit actual database credentials to GitHub.

### 4. Run the Backend

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs locally at:

`http://localhost:3000`

### 5. Run the React Frontend

Open a separate terminal:

```bash
cd frontend
npm install
```

Create a `.env` file inside `frontend/`:

```env
VITE_API_URL=http://localhost:3000
```

Start the frontend:

```bash
npm run dev
```

Open:

`http://localhost:3200`

---

## 🧪 Unit Testing

The backend includes automated tests using JUnit 5 and Mockito.

### Tested Scenarios

- Successful user registration
- Tweet creation and retrieval
- Tweet update authorization
- Tweet deletion
- Comment creation and ownership validation
- Prevention of duplicate likes
- Prevention of duplicate retweets
- Comment updates
- Removing likes and retweets
- Invalid login handling

### Run Tests

```bash
./mvnw test
```

For Windows:

```bash
mvnw.cmd test
```

### Last Documented Test Result

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The documented result includes **14 service unit tests and one Spring Boot application context test**.

---

## ⚛️ React Frontend

The repository contains a React interface developed to demonstrate interactions with the Spring Boot API.

The frontend supports:

- User registration and login
- Tweet creation and listing
- Tweet editing and deletion
- Comment management
- Likes and retweets
- REST API communication with Axios
- Loading, success, and error feedback

The frontend is located in the [`frontend`](https://github.com/Dogukan3648/twitter-api/tree/main/frontend) directory.

CORS is configured to permit requests from the local React development server and the deployed Render frontend.

---

## 🚀 Deployment

The repository contains a Dockerfile for deploying the Spring Boot application.

The React frontend has been deployed to Render.

**Frontend:** https://twitter-frontend-afvu.onrender.com

The backend deployment requires a PostgreSQL database and the appropriate environment variables.

Deployment availability should be checked independently before testing the hosted application.

---

## 🎯 Future Improvements

- Add API integration tests
- Introduce CI/CD with GitHub Actions
- Improve deployment monitoring and reliability
- Expand API documentation with request/response examples
- Evaluate token-based authentication as a future enhancement

---

## 👨‍💻 Developer

**Doğukan Bozkır**

Computer Engineering Graduate | Full-Stack Developer

- [GitHub](https://github.com/Dogukan3648)
- [LinkedIn](https://www.linkedin.com/in/dogukanbozkir/)
- [Portfolio](https://dogukan-portfolio-zeta.vercel.app/)

---

*Built to practice backend architecture, RESTful API development, security, and automated testing.*
