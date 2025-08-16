# HomeIt – Spring Microservices System Design

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Java](https://img.shields.io/badge/Java-22+-orange)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Streaming-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-green)

HomeIt is a comprehensive sample project built step by step in the book **Spring System Design in Practice**. It demonstrates how to design and implement a scalable, resilient microservice-based system using **Spring Boot**, **Spring Cloud**, and modern system design patterns.

The system models a rental property platform where **tenants**, **landlords**, and **realtors** interact — with features like proposals, property listings, conflict mediation, ratings, and secure payments.

## 📌 Features

### Core Services
- **🏠 Rental Property Service** - Manage rental listings, property details, and realtor scores
- **📋 Proposal Service** - Tenants can create rental proposals, negotiate offers, and finalize contracts
- **👤 User & Auth Service** - Handles user registration, login, JWT-based authentication, and OAuth2.0 authorization
- **📧 Notification Service** - Event-driven service powered by **Kafka** to notify users of proposals, approvals, and changes
- **🔐 Revoke Token Service** - Secure token invalidation using reactive **Spring WebFlux**

### Data & Storage
- **SQL Database** (PostgreSQL/H2) for relational data
- **NoSQL Database** (MongoDB) for event-driven persistence
- **Event Streaming** with Apache Kafka for real-time notifications

### Infrastructure & Observability
- **API Gateway & Service Discovery** - Implemented using **Spring Cloud Gateway** and **Eureka Discovery** for routing and load balancing
- **Resilience & Fault Tolerance** - Circuit breaker, retries, rate limiting with **Resilience4j**
- **Service Monitoring** - Health checks and metrics with **Spring Actuator**

## 🏗️ System Architecture

```
                   ┌────────────────────┐
                   │   API Gateway      │
                   │ (Spring Cloud)     │
                   └─────────┬──────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
┌───────▼───────┐   ┌────────▼────────┐   ┌───────▼────────┐
│ Rental Service│   │ Proposal Service│   │ Auth Service   │
│ (SQL/JPA)     │   │ (Kafka Pub/Sub) │   │ (JWT, OAuth2)  │
└───────▲───────┘   └────────▲────────┘   └───────▲────────┘
        │                    │                    │
        │          ┌─────────▼─────────┐          │
        │          │ Notification Svc  │          │
        │          │ (Kafka, WebFlux)  │          │
        │          └─────────┬─────────┘          │
        │                    │                    │
        │          ┌─────────▼─────────┐          │
        │          │   Message Queue   │          │
        │          │    (Kafka)        │          │
        │          └───────────────────┘          │
        │                                         │
   ┌────▼─────┐                              ┌────▼─────┐
   │PostgreSQL│                              │ MongoDB  │
   │    H2    │                              │(Events)  │
   └──────────┘                              └──────────┘
```

## ⚙️ Tech Stack

### Backend Frameworks
- **Spring Boot 3.x** - Core application framework
- **Spring Web** - REST API development
- **Spring WebFlux** - Reactive programming
- **Spring Data JPA** - Data persistence layer
- **Spring Security** - Authentication & authorization

### Cloud & Distributed Systems
- **Spring Cloud Gateway** - API Gateway and routing
- **Eureka Discovery Service** - Service registration and discovery
- **Spring Cloud Config Server** - Centralized configuration management

### Security
- **JWT (JSON Web Tokens)** - Stateless authentication
- **OAuth2** - Authorization framework
- **Spring Security** - Comprehensive security framework

### Databases
- **PostgreSQL** - Primary relational database
- **H2 Database** - In-memory database for development
- **MongoDB** - Document store for event-driven data

### Messaging & Events
- **Apache Kafka** - Event streaming platform
- **Spring Kafka** - Kafka integration

### DevOps & Tools
- **Gradle** - Build automation tool
- **Docker & Docker Compose** - Containerization
- **JUnit 5** - Unit testing framework
- **Spring Test** - Integration testing
- **RestAssured** - API testing

### Resilience & Monitoring
- **Resilience4j** - Fault tolerance library
- **Circuit Breaker** - Prevent cascading failures
- **Retry & Bulkhead** - Resilience patterns
- **Spring Actuator** - Application monitoring

## 🚀 Getting Started

### Prerequisites

Before running HomeIt, ensure you have the following installed:

- **Java 22+** (Java 25+ recommended)
- **Docker & Docker Compose** (for Kafka, MongoDB, PostgreSQL)
- **Gradle 8+**
- **IntelliJ IDEA** (optional but recommended)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/gaurav700/homeit.git
   cd homeit
   ```

2. **Start infrastructure services**
   ```bash
   docker-compose up -d kafka zookeeper mongodb postgresql
   ```

3. **Build the project**
   ```bash
   ./gradlew clean build
   ```

4. **Start services in order**
   ```bash
   # Start Eureka Discovery Service
   ./gradlew :eureka-server:bootRun
   
   # Start Config Server
   ./gradlew :config-server:bootRun
   
   # Start core services
   ./gradlew :auth-service:bootRun
   ./gradlew :rental-service:bootRun
   ./gradlew :proposal-service:bootRun
   ./gradlew :notification-service:bootRun
   
   # Start API Gateway
   ./gradlew :api-gateway:bootRun
   ```

### Docker Compose Setup

For a complete containerized setup:

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## 📖 API Documentation

### Authentication Endpoints
```http
POST /api/auth/register     # User registration
POST /api/auth/login        # User login
POST /api/auth/refresh      # Token refresh
POST /api/auth/revoke       # Token revocation
```

### Rental Property Endpoints
```http
GET    /api/rentals            # List all properties
POST   /api/rentals            # Create new property
GET    /api/rentals/{id}       # Get property details
PUT    /api/rentals/{id}       # Update property
DELETE /api/rentals/{id}       # Delete property
```

### Proposal Endpoints
```http
GET    /api/proposals          # List user proposals
POST   /api/proposals          # Create new proposal
GET    /api/proposals/{id}     # Get proposal details
PUT    /api/proposals/{id}     # Update proposal
DELETE /api/proposals/{id}     # Cancel proposal
```

### Notification Endpoints
```http
GET    /api/notifications      # Get user notifications
PUT    /api/notifications/{id} # Mark as read
```

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Integration Tests
```bash
./gradlew integrationTest
```

### Run API Tests
```bash
./gradlew apiTest
```

## 🔧 Configuration

### Application Profiles
- `dev` - Development profile with H2 database
- `test` - Testing profile with embedded services
- `prod` - Production profile with external services

### Environment Variables
```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=homeit
DB_USERNAME=homeit_user
DB_PASSWORD=homeit_pass

# Kafka Configuration
KAFKA_BROKERS=localhost:9092

# JWT Configuration
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# OAuth2 Configuration
OAUTH2_CLIENT_ID=your-client-id
OAUTH2_CLIENT_SECRET=your-client-secret
```

## 📊 Monitoring & Health Checks

### Actuator Endpoints
- `/actuator/health` - Application health status
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application information
- `/actuator/prometheus` - Prometheus metrics

### Service Discovery
Access Eureka Dashboard at: `http://localhost:8761`

## 🛡️ Security Features

- **JWT-based Authentication** - Stateless token authentication
- **OAuth2 Integration** - Third-party authentication
- **Role-based Authorization** - TENANT, LANDLORD, REALTOR roles
- **Token Revocation** - Secure token invalidation
- **Rate Limiting** - API rate limiting with Resilience4j

## 🔄 Event-Driven Architecture

### Kafka Topics
- `rental-events` - Property listing events
- `proposal-events` - Proposal lifecycle events
- `notification-events` - User notification events
- `user-events` - User registration/profile events

## 📁 Project Structure

```
homeit-microservices/
├── api-gateway/              # Spring Cloud Gateway
├── eureka-server/            # Service Discovery
├── config-server/            # Configuration Server
├── auth-service/             # Authentication & Authorization
├── rental-service/           # Property Management
├── proposal-service/         # Proposal Management
├── notification-service/     # Event-driven Notifications
├── revoke-token-service/     # Token Revocation (WebFlux)
├── common/                   # Shared utilities and models
├── docker-compose.yml        # Container orchestration
└── README.md                 # This file
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📚 Learning Resources

This project is designed as a learning resource for:
- **Microservices Architecture** patterns and best practices
- **Spring Boot & Spring Cloud** ecosystem
- **Event-driven Architecture** with Kafka
- **Resilience Patterns** in distributed systems
- **API Gateway** and service discovery
- **Reactive Programming** with WebFlux

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For questions and support:
- 📧 Email: gaurav700@example.com
- 💬 Discussions: [GitHub Discussions](https://github.com/gaurav700/homeit/discussions)
- 🐛 Issues: [GitHub Issues](https://github.com/gaurav700/homeit/issues)

---

**Built with ❤️ for learning Spring Microservices Architecture**
