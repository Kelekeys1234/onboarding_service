# Project Name: Calvary Onboarding System

## Description
The **Calvary Onboarding System** is a Spring Boot application that provides user onboarding, authentication, and application processing functionalities. It integrates with MongoDB for database operations and includes Keycloak for authentication. The project also leverages Kafka for messaging and Elasticsearch for search functionalities.

## Features
- User Registration & Authentication
- First-time Password Setup
- Application Management (CRUD Operations)
- Role-based Authorization with Keycloak
- Elasticsearch Integration for Fast Searching
- Kafka Messaging for Microservices Communication
- Swagger API Documentation

## Tech Stack
- **Backend:** Java 11, Spring Boot, Spring Security, Keycloak
- **Database:** MongoDB
- **Messaging:** Kafka
- **Search:** Elasticsearch
- **API Documentation:** Swagger
- **Version Control:** Git & GitHub

## Setup Instructions

### Prerequisites
Ensure you have the following installed:
- Java 11
- Maven 3+
- MongoDB
- Keycloak Server
- Kafka (Optional for messaging features)

### Installation Steps
1. **Clone the Repository:**
   ```sh
   git clone https://github.com/your-repo/calvary-onboarding.git
   cd calvary-onboarding
   ```

2. **Configure Application Properties:**
   Update `application.properties` or `application.yml` with your database, Keycloak, and other configurations.

3. **Build the Project:**
   ```sh
   mvn clean install
   ```

4. **Run the Application:**
   ```sh
   mvn spring-boot:run
   ```

5. **Access API Documentation:**
   Open your browser and navigate to:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Git Configuration

### Change Git Username & Email
To change your Git username and email, run:
```sh
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

### Git Workflow
1. **Create a new branch:**
   ```sh
   git checkout -b feature-branch
   ```
2. **Commit your changes:**
   ```sh
   git add .
   git commit -m "Added new feature"
   ```
3. **Push to GitHub:**
   ```sh
   git push origin feature-branch
   ```
4. **Create a Pull Request on GitHub.**

## Contribution Guidelines
- Follow coding best practices.
- Use meaningful commit messages.
- Create pull requests for any changes.
- Run tests before committing code.

## License
This project is licensed under [MIT License](LICENSE).
