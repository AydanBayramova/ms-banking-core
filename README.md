# ms-banking-core

This project is a microservices-based banking system developed using **Spring Boot**, **Docker**, and **GitLab CI/CD**. The system includes services for managing accounts, identity, currency, and transfers, with integrated Eureka server for service discovery and API Gateway for routing.

---

## 🛠 Technologies Used
- **Java** with **Spring Boot** (Microservices)
- **Eureka Server** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **PostgreSQL** (Database)
- **MongoDB** (NoSQL Database for Identity)
- **RabbitMQ** (Message Broker)
- **Docker & Docker Compose** (Containerization)
- **Prometheus & Grafana** (Monitoring & Visualization)
- **GitLab CI/CD** (Continuous Integration and Deployment)

---

## 📂 Project Structure
```plaintext
ms-banking-core/
│
├── eureka-server/       # Eureka server for service discovery
├── gateway/             # API Gateway for routing requests
├── ms-account/          # Service for managing accounts
├── ms-currency/         # Service for handling currency-related operations
├── ms-identity/         # Identity management service (MongoDB-based)
├── ms-transfer/         # Service for managing fund transfers
│
├── .gitlab-ci.yml       # GitLab CI/CD pipeline configuration
├── docker-compose.yml   # Multi-container orchestration
└── .env                 # Environment variables


🚀 Features
Service Discovery: Centralized service discovery with Eureka.
API Gateway: Routes and load-balances traffic across microservices.
Multi-Database Support:
PostgreSQL for ms-account and ms-transfer.
MongoDB for ms-identity.
Messaging: Asynchronous communication using RabbitMQ.
Monitoring:
Prometheus for metrics collection.
Grafana for data visualization.
CI/CD Integration: Fully automated pipeline for build, test, and deployment using GitLab CI/CD.




Here is a concise and clear README.md file tailored to your project, incorporating details from our previous discussions:

markdown
Copy code
# ms-banking-core

This project is a microservices-based banking system developed using **Spring Boot**, **Docker**, and **GitLab CI/CD**. The system includes services for managing accounts, identity, currency, and transfers, with integrated Eureka server for service discovery and API Gateway for routing.

---

## 🛠 Technologies Used
- **Java** with **Spring Boot** (Microservices)
- **Eureka Server** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **PostgreSQL** (Database)
- **MongoDB** (NoSQL Database for Identity)
- **RabbitMQ** (Message Broker)
- **Docker & Docker Compose** (Containerization)
- **Prometheus & Grafana** (Monitoring & Visualization)
- **GitLab CI/CD** (Continuous Integration and Deployment)

---
```plaintext'''

## 📂 Project Structure
```plaintext
ms-banking-core/
│
├── eureka-server/       # Eureka server for service discovery
├── gateway/             # API Gateway for routing requests
├── ms-account/          # Service for managing accounts
├── ms-currency/         # Service for handling currency-related operations
├── ms-identity/         # Identity management service (MongoDB-based)
├── ms-transfer/         # Service for managing fund transfers
│
├── .gitlab-ci.yml       # GitLab CI/CD pipeline configuration
├── docker-compose.yml   # Multi-container orchestration
└── .env                 # Environment variables
🚀 Features
Service Discovery: Centralized service discovery with Eureka.
API Gateway: Routes and load-balances traffic across microservices.
Multi-Database Support:
PostgreSQL for ms-account and ms-transfer.
MongoDB for ms-identity.
Messaging: Asynchronous communication using RabbitMQ.
Monitoring:
Prometheus for metrics collection.
Grafana for data visualization.
CI/CD Integration: Fully automated pipeline for build, test, and deployment using GitLab CI/CD.
⚙️ Prerequisites
Docker and Docker Compose installed.
Git installed for version control.
Java 17+ and Maven (if running locally).
🖥️ Setup & Run
1. Clone the Repository
bash
Copy code
git clone https://github.com/AydanBayramova/ms-banking-core.git
cd ms-banking-core
2. Configure Environment
Create a .env file in the root directory and set the required variables (e.g., database credentials, RabbitMQ credentials).
3. Start with Docker Compose
bash
Copy code
docker-compose up --build
4. Access Services
Service	Endpoint
Eureka Server	http://localhost:8762
API Gateway	http://localhost:8081
ms-account	http://localhost:8085
ms-currency	http://localhost:8083
ms-identity	http://localhost:8084
ms-transfer	http://localhost:8090
Prometheus	http://localhost:9090
Grafana	http://localhost:3000
📊 Monitoring
Prometheus is set up to collect metrics from all microservices.
Grafana dashboards are configured to visualize key metrics.