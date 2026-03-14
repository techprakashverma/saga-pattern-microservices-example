# Saga-pattern-microservices-example
A microservices-based application demonstrating the Saga Design Pattern using Spring Boot and Apache Kafka to manage distributed transactions across services such as order, stock, payment, delivery, and Notification.


This project demonstrates a **Spring Boot Microservices Architecture** implementing:

- Eureka Service Discovery
- API Gateway
- JWT Authentication
- Multiple Microservices
- H2 In-Memory Databases

Each microservice maintains its **own database**.

---

# Architecture Components

- **Eureka Server** – Service discovery
- **API Gateway** – Entry point for all APIs
- **Auth Service** – User authentication and JWT token generation
- **Stock Service** – Inventory management
- **Order Service** – Order processing

---

## Project Flow
1. Start Eureka Server
2. Start API Gateway
3. Start Auth Service
4. Start Stock Service
5. Start Order Service

Application workflow:
1. Register user using Auth Service
2. Login to receive JWT Token
3. Add stock items using Stock Service
4. Create order via API Gateway
5. Gateway validates JWT Token
6. Request is forwarded to Order Service
---

# Microservices Ports

| Service | Port |
|--------|------|
| Eureka Server | 8761 |
| API Gateway | 8079 |
| Auth Service | 8085 |
| Order Service | 8080 |
| Stock Service | 8082 |

---

# 1. Eureka Server

Eureka Dashboard 

http://localhost:8761/


All microservices register themselves with Eureka.

---

# 2. Auth Service

Database: **testdb**

H2 Console:
http://localhost:8085/h2-console/


Used for:

- User Registration
- User Login
- JWT Token Generation

---

## Register User

```bash
curl --request POST \
  --url http://localhost:8085/auth/register \
  --header 'content-type: application/json' \
  --data '{
  "username": "raj",
  "password": "123"
}'
```
## Login User
```bash
curl --request POST \
--url http://localhost:8085/auth/login \
--header 'content-type: application/json' \
--data '{
"username": "raj",
"password": "123"
}'
```
## Login Response

```bash
{
  "role": "ROLE_USER",
  "username": "raj",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWoiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzczNDY4NDEwLCJleHAiOjE3NzM0NzIwMTB9.nNmywMvXCwSnlsqDZqEDnxVUlZAISGGuRTAGTSwS4z8"
}
```
Save the JWT token and use it in API Gateway requests.

# 3. Stock Service

Database: stockdb

H2 Console: http://localhost:8082/h2-console

Used to manage stock/inventory.
## Add Stock Item
```bash
curl --request POST \
--url http://localhost:8082/api/addItems \
--header 'content-type: application/json' \
--data '{
"item": "i-phone-16",
"quantity": "100"
}'
```

# 4. Order Service

Database: ordersdb

H2 Console: http://localhost:8080/h2-console/
Requests are sent through API Gateway.

API Gateway URL:http://localhost:8079

## Create Order

```bash
curl --request POST \
--url http://localhost:8079/order-ms/api/orders \
--header 'authorization: Bearer <JWT_TOKEN>' \
--header 'content-type: application/json' \
--data '{
"email": "abc@gmail.com",
"item": "i-phone-16",
"quantity": "5",
"amount": "60000",
"paymentMode": "UPI",
"address": "Pune"
}'
```
**Example Authorization Header** : Authorization: Bearer *eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWoiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzczNDY4NDEwLCJleHAiOjE3NzM0NzIwMTB9.nNmywMvXCwSnlsqDZqEDnxVUlZAISGGuRTAGTSwS4z8*

## H2 Database Consoles

| Service | URL |
|-------|------|
| Auth Service | http://localhost:8085/h2-console |
| Stock Service | http://localhost:8082/h2-console |
| Order Service | http://localhost:8080/h2-console |

---
## Technologies Used

- Java 17
- Spring Boot
- Spring Cloud
- Spring Security
- JWT Authentication
- Eureka Service Discovery
- Spring Cloud Gateway
- H2 Database
- Maven

---








