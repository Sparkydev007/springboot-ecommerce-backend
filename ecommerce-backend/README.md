# Ecommerce Backend API

A comprehensive Spring Boot REST API for an ecommerce platform with product management, order processing, inventory tracking, and secure authentication.

## 🚀 Features

### Product Management
- ✅ Create, read, update, delete products
- ✅ Inventory quantity tracking
- ✅ Price management
- ✅ Public product browsing

### Order Management
- ✅ Place orders with multiple items
- ✅ Automatic inventory reduction
- ✅ Order status tracking (PENDING → CONFIRMED → CANCELLED)
- ✅ Customer order history
- ✅ Async order processing

### Security & Authentication
- ✅ Spring Security with Basic Authentication
- ✅ Role-based access control (USER, ADMIN)
- ✅ Protected endpoints with proper authorization
- ✅ Secure password handling

### Data & Validation
- ✅ H2 in-memory database (for development)
- ✅ JPA/Hibernate ORM
- ✅ Bean validation with custom error messages
- ✅ Global exception handling
- ✅ Transaction management

## 🛠️ Technology Stack

- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** H2 (in-memory) / MySQL (production)
- **Security:** Spring Security
- **ORM:** JPA/Hibernate
- **Build Tool:** Maven
- **Validation:** Bean Validation (Jakarta)

## 📁 Project Structure

```
ecommerce-backend/
├── src/main/java/com/ecommerce/
│   ├── controller/
│   │   ├── OrderController.java
│   │   └── ProductController.java
│   ├── service/
│   │   ├── OrderService.java
│   │   └── ProductService.java
│   ├── repository/
│   │   ├── OrderRepository.java
│   │   └── ProductRepository.java
│   ├── entity/
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── Product.java
│   ├── dto/
│   │   └── OrderRequest.java
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   └── SecurityConfig.java
│   └── EcommerceApplication.java
├── src/main/resources/
│   └── application.yml
├── pom.xml
└── README.md
```

## 🔧 Setup & Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ecommerce-backend
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`

## 🔐 Authentication

The API uses Basic Authentication with predefined users:

| Username | Password | Role  | Permissions |
|----------|----------|-------|-------------|
| `user`   | `password` | USER  | Place orders, view own orders |
| `admin`  | `password` | ADMIN | All user permissions + manage products |

### Authentication Headers
```bash
# For USER role
Authorization: Basic dXNlcjpwYXNzd29yZA==

# For ADMIN role
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

## 📡 API Endpoints

### Product Endpoints

#### Get All Products
```http
GET /products
```
- **Auth:** None (public)
- **Response:** List of all products

#### Get Product by ID
```http
GET /products/{id}
```
- **Auth:** None (public)
- **Response:** Single product details

#### Create Product
```http
POST /products
Content-Type: application/json
Authorization: Basic YWRtaW46cGFzc3dvcmQ=

{
  "name": "Laptop",
  "price": 999.99,
  "quantity": 10
}
```
- **Auth:** ADMIN required
- **Response:** Created product

#### Update Product
```http
PUT /products/{id}
Content-Type: application/json
Authorization: Basic YWRtaW46cGFzc3dvcmQ=

{
  "name": "Gaming Laptop",
  "price": 1299.99,
  "quantity": 5
}
```
- **Auth:** ADMIN required
- **Response:** Updated product

#### Delete Product
```http
DELETE /products/{id}
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```
- **Auth:** ADMIN required
- **Response:** 204 No Content

### Order Endpoints

#### Place Order
```http
POST /orders
Content-Type: application/json
Authorization: Basic dXNlcjpwYXNzd29yZA==

{
  "customerId": "CUST001",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```
- **Auth:** USER required
- **Response:** Created order with status PENDING
- **Notes:** Automatically reduces inventory, calculates total

#### Get Order by ID
```http
GET /orders/{id}
Authorization: Basic dXNlcjpwYXNzd29yZA==
```
- **Auth:** USER required
- **Response:** Order details with items

#### Get Orders by Customer
```http
GET /orders/customer/{customerId}
Authorization: Basic dXNlcjpwYXNzd29yZA==
```
- **Auth:** USER required
- **Response:** List of customer's orders

#### Update Order Status
```http
PUT /orders/{id}/status?status=CONFIRMED
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```
- **Auth:** ADMIN required
- **Response:** Updated order

## 🧪 Testing Examples

### Using cURL

1. **Get all products (no auth needed):**
   ```bash
   curl http://localhost:8080/products
   ```

2. **Create a product (admin auth):**
   ```bash
   curl -u admin:password -X POST http://localhost:8080/products \
     -H "Content-Type: application/json" \
     -d '{"name":"Laptop","price":999.99,"quantity":10}'
   ```

3. **Place an order (user auth):**
   ```bash
   curl -u user:password -X POST http://localhost:8080/orders \
     -H "Content-Type: application/json" \
     -d '{"customerId":"CUST001","items":[{"productId":1,"quantity":2}]}'
   ```

### Using PowerShell

```powershell
# Get products
Invoke-WebRequest -Uri "http://localhost:8080/products" -UseBasicParsing

# Create product (admin)
$body = '{"name":"Mouse","price":25.99,"quantity":50}' | ConvertTo-Json
$cred = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("admin:password"))
Invoke-WebRequest -Uri "http://localhost:8080/products" -Method POST `
  -Headers @{"Authorization"="Basic $cred"; "Content-Type"="application/json"} `
  -Body $body -UseBasicParsing

# Place order (user)
$orderBody = '{"customerId":"CUST001","items":[{"productId":1,"quantity":1}]}'
$cred = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("user:password"))
Invoke-WebRequest -Uri "http://localhost:8080/orders" -Method POST `
  -Headers @{"Authorization"="Basic $cred"; "Content-Type"="application/json"} `
  -Body $orderBody -UseBasicParsing
```

## 🗄️ Database Schema

### Products Table
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL
);
```

### Orders Table
```sql
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL
);
```

### Order Items Table
```sql
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

## 🔍 H2 Database Console

Access the H2 console at: `http://localhost:8080/h2-console`

**Connection Settings:**
- **Driver Class:** `org.h2.Driver`
- **JDBC URL:** `jdbc:h2:mem:ecommerce_db`
- **Username:** `sa`
- **Password:** *(leave empty)*

## ⚠️ Error Handling

The API returns appropriate HTTP status codes:

- **200 OK:** Successful operation
- **201 Created:** Resource created
- **204 No Content:** Resource deleted
- **400 Bad Request:** Validation errors
- **401 Unauthorized:** Authentication required
- **403 Forbidden:** Insufficient permissions
- **404 Not Found:** Resource not found
- **409 Conflict:** Insufficient stock
- **500 Internal Server Error:** Server error

### Error Response Format
```json
{
  "error": "Detailed error message"
}
```

## 🔄 Business Logic

### Order Processing Flow
1. **Validation:** Check product existence and stock availability
2. **Calculation:** Compute total amount from item prices
3. **Inventory Update:** Reduce product quantities atomically
4. **Order Creation:** Save order with PENDING status
5. **Async Processing:** Background task updates status to CONFIRMED

### Inventory Management
- Orders automatically reduce product stock
- Insufficient stock throws 409 Conflict error
- All inventory operations are transactional

## 🚀 Production Deployment

### Switching to MySQL

1. **Update `application.yml`:**
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/ecommerce_db
       username: your_mysql_user
       password: your_mysql_password
       driver-class-name: com.mysql.cj.jdbc.Driver
     jpa:
       hibernate:
         ddl-auto: update
   ```

2. **Update `pom.xml`:**
   - Remove H2 dependency
   - Keep MySQL connector

3. **Create MySQL database:**
   ```sql
   CREATE DATABASE ecommerce_db;
   ```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or issues, please create an issue in the repository.

---

**Happy coding! 🎉**