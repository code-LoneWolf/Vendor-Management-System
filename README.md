# Vendor Management System (Backend)

##  Real-World Backend System for Subscription & Vendor Operations

This project is a backend system designed to manage vendors, customers, agents, and their subscription lifecycle in a structured and scalable way.

Instead of building a simple CRUD application, the focus here was to simulate how real production systems handle **authentication, role-based access, business rules, and automation**.

---

##  Project Objective

The system solves common operational challenges in vendor-based services:

* Managing multiple vendors and their assigned agents
* Tracking customer subscriptions with expiry and renewal logic
* Automating subscription status updates
* Providing role-based access for secure operations
* Maintaining clean and scalable backend architecture

---

##  Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA (Hibernate)
* MySQL
* Maven

---

##  Architecture

The project follows a layered architecture to keep responsibilities clean and maintainable:

```
Controller → Service → Repository → Entity
```

* **Controller Layer** → Handles API requests
* **Service Layer** → Contains business logic
* **Repository Layer** → Communicates with database
* **Entity Layer** → Maps database tables

This structure makes the system easy to scale and modify.

---

##  Security Implementation

* JWT-based authentication system
* Stateless session management
* Role-based access control (ADMIN, AGENT)
* BCrypt password encryption
* Custom authentication endpoint (`/auth/login`)

---

##  Core Features

### Vendor Management

* Create, update, delete vendors
* Fetch vendor details
* Vendor-based data segregation

---

### Customer Management

* Manage customer records
* Link customers to vendors

---

### Agent Management

* Assign agents to vendors
* Role-based restrictions on access

---

### Subscription Management

* Create and update subscriptions
* Automatic status handling (ACTIVE / EXPIRED)
* Renewal support (3 months, 6 months, 1 year)
* Expiry tracking

---

### Vendor Transfer Tracking

* Transfer subscriptions between vendors
* Maintain complete transfer history for auditing

---

### Automation (Scheduled Tasks)

* Background job automatically marks expired subscriptions
* Runs periodically without manual intervention

---

### QR-Based Verification (API Ready)

* Unique token generated per subscription
* Used for verification through QR scanning
* Endpoint to validate subscription authenticity

---

##  API Highlights

### Authentication

* `POST /auth/login` → Generate JWT token

---

### Vendors

* `GET /vendors`
* `POST /vendors`
* `PUT /vendors/{id}`
* `DELETE /vendors/{id}`

---

### Customers

* `GET /customers`
* `POST /customers`

---

### Agents

* `GET /agents`
* `POST /agents`

---

### Subscriptions

* `POST /subscriptions`
* `PUT /subscriptions/{id}`
* `GET /subscriptions`
* `POST /subscriptions/{id}/renew`
* `GET /subscriptions/expired`

---

### Verification

* `GET /subscriptions/verify/{id}?token=...`

---

##  Key Design Decisions

* Used **DTOs instead of exposing entities** to improve security and flexibility
* Implemented **JWT authentication** to make the system stateless and scalable
* Applied **role-based access control** for real-world security handling
* Added **scheduled jobs** to automate business logic
* Maintained **clean separation of concerns** for maintainability

---

##  How to Run

1. Clone the repository
2. Configure MySQL

```sql
CREATE DATABASE vendor_management;
```

3. Update `application.properties`

4. Run the application

```bash
mvn spring-boot:run
```

---

##  What Makes This Project Different

* Not just CRUD — includes **real business logic**
* Implements **authentication + authorization properly**
* Uses **production-style architecture**
* Handles **automation and lifecycle management**
* Designed with **scalability in mind**

---

##  Future Improvements

* Payment integration
* Notification system (SMS/Email reminders)
* QR code image generation
* Docker deployment
* Cloud hosting

---

##  Author

**Gokul R**
Computer Science Engineer

Focused on building backend systems that are clean, scalable, and practical.
