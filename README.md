# 🚀 E-Commerce Backend API

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Central-red.svg)](https://maven.apache.org/)
[![H2](https://img.shields.io/badge/Database-H2%20%2B%20MySQL-orange.svg)](https://h2database.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A production-ready **E-Commerce Backend** built with **Spring Boot 3.2.0** and **Java 17**. Features RESTful APIs for managing products and orders, JWT-based security, JPA/Hibernate for persistence, and comprehensive error handling.

## ✨ Features

- **Product Management**: CRUD operations for products (create, read, update, delete).
- **Order Management**: Place orders, view order history with items.
- **RESTful APIs**: Clean, well-documented endpoints following REST conventions.
- **Spring Security**: Secure endpoints with authentication & authorization.
- **Data Persistence**: JPA/Hibernate with H2 (dev) & MySQL (prod) support.
- **Input Validation**: Bean Validation (JSR-303) for robust request handling.
- **Global Exception Handling**: Centralized error responses.
- **Production-Ready**: Logging, health checks, actuator endpoints.

## 🛠 Tech Stack

| Category | Technologies |
|----------|--------------|
| **Backend** | Spring Boot 3.2.0, Spring Web, Spring Data JPA, Spring Security, Spring Validation |
| **Database** | H2 (In-memory), MySQL 8.0 |
| **Language** | Java 17 |
| **Build** | Maven |
| **ORM** | Hibernate |
| **Validation** | Bean Validation API |

## 📱 API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `POST` | `/api/products` | Create new product |
| `PUT` | `/api/products/{id}` | Update product |
| `DELETE` | `/api/products/{id}` | Delete product |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders` | Place new order |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `GET` | `/api/orders/user/{userId}` | Get orders by user |

**Request Examples:**

**Create Product:**
```json
{
  \"name\": \"iPhone 15\",
  \"description\": \"Latest iPhone model\",
  \"price\": 999.99,
  \"stock\": 50
}
```

**Place Order:**
```json
{
  \"userId\": 1,
  \"items\": [
    {
      \"productId\": 1,
      \"quantity\": 2
    }
  ]
}
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0 (optional, H2 works out-of-box)

### Clone & Run
```bash
git clone <your-repo-url>
cd ecommerce-backend
mvn spring-boot:run
```

Server runs on `http://localhost:8080`

### Database Setup (MySQL)
1. Create database: `ecommerce_db`
2. Update `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_db
    username: root
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
```

### API Testing
Use Postman, curl, or Swagger (add springdoc-openapi-ui dependency for Swagger UI).

**Sample curl:**
```bash
# Get products
curl -X GET http://localhost:8080/api/products

# Create product
curl -X POST http://localhost:8080/api/products \\
  -H \"Content-Type: application/json\" \\
  -d '{\"name\":\"Laptop\",\"price\":1299.99,\"stock\":10}'
```

## 🏗 Project Structure

```
ecommerce-backend/
├── src/main/java/com/ecommerce/
│   ├── EcommerceApplication.java      # Main Spring Boot app
│   ├── config/                        # Security & config
│   ├── controller/                    # REST controllers
│   ├── dto/                           # Data Transfer Objects
│   ├── entity/                        # JPA entities
│   ├── exception/                     # Global exception handler
│   ├── repository/                    # JPA repositories
│   └── service/                       # Business logic
└── src/main/resources/
    └── application.yml                # App configuration
```

## 🔒 Security

- Protected endpoints require authentication.
- Add Spring Security JWT dependency for token-based auth.
- Customize `SecurityConfig.java` for your needs.

## 🧪 Testing

```bash
mvn test
```

## 📈 Performance & Monitoring

Spring Boot Actuator endpoints:
- `/actuator/health`
- `/actuator/info`
- `/actuator/metrics`

## 🤝 Contributing

1. Fork the project
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Prathmesh Bunde**  
[LinkedIn](https://www.linkedin.com/in/prathmeshbunde) | [Leetcode](https://leetcode.com/u/prathmeshbunde)

---

⭐ **Star this repo if you found it helpful!** ⭐

**Made with ❤️ using Spring Boot**

