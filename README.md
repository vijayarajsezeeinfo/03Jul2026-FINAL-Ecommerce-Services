# Ecommerce Services

A RESTful E-Commerce backend application developed using **Java**, **Spring MVC**, **JDBC**, and **MySQL**. The application provides secure REST APIs for managing namespaces, users, addresses, products, inventory, shopping carts, payments, and complete order processing using JWT authentication, caching, and stored procedure-based database operations.

---

# Technologies Used

## Backend
- Java 21
- Spring MVC 5
- JDBC
- Maven

## Database
- MySQL 8
- MySQL Stored Procedures
- MySQL Functions

## Security
- JWT (JSON Web Token)
- BCrypt Password Encoder

## Caching
- Ehcache
- Redis

## Web Server
- Apache Tomcat 9

## Development Tools
- Eclipse IDE
- Postman
- SQLyog
- Git
- GitHub

## Logging
- SLF4J

---

# API Endpoints

## Authentication

**POST** `baseUrl/login`

- User Login

---

## Namespace

**POST** `baseUrl/{authToken}/namespace/update`

- Create Namespace
- Update Namespace
- Soft Delete Namespace
- Reactivate Namespace

**GET** `baseUrl/{authToken}/namespace/{namespaceCode}`

- Get Namespace By Code

**GET** `baseUrl/{authToken}/namespace`

- Get All Namespaces

---

## User

**POST** `baseUrl/{authToken}/user/update`

- Create User
- Update User
- Soft Delete User
- Reactivate User

**GET** `baseUrl/{authToken}/user/{userCode}`

- Get User By Code

**GET** `baseUrl/{authToken}/user/{namespaceCode}`

- Get All Users

---

## Address

**POST** `baseUrl/{authToken}/address/update`

- Create Address
- Update Address
- Soft Delete Address
- Reactivate Address

**GET** `baseUrl/{authToken}/address/{addressCode}`

- Get Address By Code

**GET** `baseUrl/{authToken}/address/{namespaceCode}`

- Get All Addresses

---

## Brand

**POST** `baseUrl/{authToken}/brand/update`

- Create Brand
- Update Brand
- Soft Delete Brand
- Reactivate Brand

**GET** `baseUrl/{authToken}/brand/{brandCode}`

- Get Brand By Code

**GET** `baseUrl/{authToken}/brand/{namespaceCode}`

- Get All Brands

---

## Category

**POST** `baseUrl/{authToken}/category/update`

- Create Category
- Update Category
- Soft Delete Category
- Reactivate Category

**GET** `baseUrl/{authToken}/category/{categoryCode}`

- Get Category By Code

**GET** `baseUrl/{authToken}/category/{namespaceCode}`

- Get All Categories

---

## Product

**POST** `baseUrl/{authToken}/product/update`

- Create Product
- Update Product
- Soft Delete Product
- Reactivate Product

**GET** `baseUrl/{authToken}/product/{productCode}`

- Get Product By Code

**GET** `baseUrl/{authToken}/product/{namespaceCode}`

- Get All Products

---

## Product Inventory

**POST** `baseUrl/{authToken}/productInventory/update`

- Create Product Inventory
- Update Product Inventory
- Soft Delete Product Inventory
- Reactivate Product Inventory

**GET** `baseUrl/{authToken}/productInventory/{productCode}`

- Get Product Inventory By Product Code

**GET** `baseUrl/{authToken}/productInventory/{namespaceCode}`

- Get All Product Inventories

---

## Cart Item

**POST** `baseUrl/{authToken}/cartItem/update`

- Create Cart Item
- Update Cart Item
- Soft Delete Cart Item
- Reactivate Cart Item

**GET** `baseUrl/{authToken}/cartItem/{cartItemCode}`

- Get Cart Item By Code

**GET** `baseUrl/{authToken}/cartItem/{namespaceCode}`

- Get All Cart Items

---

## Payment

**GET** `baseUrl/{authToken}/payment/{paymentCode}`

- Get Payment By Code

**GET** `baseUrl/{authToken}/payment/{namespaceCode}`

- Get All Payments

---

## Order Request

**POST** `baseUrl/{authToken}/order/update`

- Place Order
- Update Order Status

**GET** `baseUrl/{authToken}/order/{orderCode}`

- Get Order By Code

**GET** `baseUrl/{authToken}/order/{namespaceCode}`

- Get All Orders

---

# Project Flow

## Authentication Flow

- User authentication is performed through the Login API.
- User passwords are encrypted using BCrypt Password Encoder.
- JWT tokens are generated after successful authentication.
- Protected APIs require a valid authentication token.

---

## Namespace Flow

- Namespace operations are managed through the Namespace Controller.
- Namespace information is cached using Redis to improve application performance.

---

## User Flow

- User operations are managed through the User Controller.
- Authentication tokens and user information are cached using Ehcache.
- A Shopping Cart is automatically created whenever a new user is created through the User IUD process.

---

## Address Flow

- Users can maintain multiple addresses.
- Every address belongs to a specific user and namespace.

---

## Product Flow

- Products are organized under Brands and Categories.
- Every Product has a corresponding Product Inventory.
- Product Inventory maintains the available stock quantity.

---

## Product Inventory Flow

- Product Inventory operations are managed through the Product Inventory Controller.
- Available quantity is automatically updated after successful order placement.

---

## Cart Flow

The application does not contain a separate Cart Controller.

- Shopping Cart creation is handled automatically through the EZEE_SP_USER_IUD process.

---

## Cart Item Flow

- Cart Item operations are managed through the Cart Item Controller.
- Cart Items are associated with the user's shopping cart.

---

## Order Flow

The application does not contain separate controllers for **Orders** and **Order Items**.

Orders, Order Items, and Payments are managed through a single **Order Request Controller** during order placement.

When an order is placed, the application automatically:

- Validates product availability.
- Calculates the total payable amount.
- Creates the Order.
- Creates the corresponding Order Items.
- Creates the Payment.
- Calculates the balance amount.
- Updates Product Inventory after successful order placement.

---

## Payment Flow

- Payment information is managed through the Payment Controller.
- During order placement, payment information is also processed through the Order Request Controller to complete the order workflow.

---

# Caching

## Ehcache

Used for:

- Authentication Token Cache
- User Cache

## Redis

Used for:

- Namespace Cache

---

# Database

The project uses:

- MySQL Database
- Stored Procedures
- SQL Functions
- Soft Delete and Reactivation
- Random Code Generation

### Database Tables

- Namespace
- User
- Address
- Cart
- Cart Items
- Brand
- Category
- Product
- Product Inventory
- Orders
- Order Items
- Payments

---

# Project Architecture

```
Controller
        ↓
Service
        ↓
DAO
        ↓
Stored Procedures
        ↓
MySQL Database
```

---

# Key Features

- RESTful API Design
- Layered Architecture
- DTO Pattern
- DAO Pattern
- JDBC-Based Data Access
- Stored Procedure Driven CRUD Operations
- JWT Authentication
- BCrypt Password Encryption
- Ehcache Integration
- Redis Integration
- Automatic Shopping Cart Creation
- Product Inventory Management
- Complete Order Processing Workflow
- Global Exception Handling
- Input Validation
- SLF4J Logging

---

# Author

**Vijayaraj Selvaraj**

Java Full Stack Developer

GitHub: https://github.com/vijayarajsezeeinfo

---

# License

This project is developed for learning, practice, and portfolio purposes.
