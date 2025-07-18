# Voting System API

# 🗳️ Blockchain-Based School Voting System API

This is a secure and scalable **Spring Boot** API for a school delegate voting system, integrated with a **Blockchain smart contract** for verifiable vote storage. It supports user registration, authentication, election management, candidate voting, and result retrieval with Web3 and PostgreSQL support.

---

## 🚀 Tech Stack

### Backend
- **Spring Boot** (Java 21) — RESTful API
- **Spring Security + JWT** — Authentication & Authorization
- **Flyway** — Database version control
- **PostgreSQL** — Relational Database
- **Web3j** — Ethereum blockchain integration
- **Ganache** — Local Ethereum blockchain environment

### Other Tools
- **Lombok** — Reduces boilerplate in Java code
- **Maven** — Dependency management and build
- **IntelliJ IDEA** — Development IDE
- **OpenAPI / Postman** — API testing

---

## 📦 Features

- ✅ Member Registration with OTP Verification
- 🔐 Secure Login with OTP Verification with JWT Access and Refresh Tokens
- JWT Access and Refresh Tokens
- 🗳️ Vote Casting on Blockchain and Transactions stored in DB
- 📊 Real-time Result Fetching from Smart Contract
- 🗃️ Admin Election and Candidate Management (via DB or API)
- 🔄 Refresh Token Endpoint
- 🧪 Secure CRUD endpoints for Members

---

## ⚙️ Setup Instructions

### 1. Prerequisites

- Java 21
- PostgreSQL
- Ganache CLI/Desktop
```bash
 sudo npm install -g ganache
 ganache --version

```
- POSTMAN
- IntelliJ IDEA (recommended)

---

### 2. Clone the Repository

```bash
git clone https://github.com/Kiarie-Victor/votingsystemapi.git
cd voting-system-api
```

## 🗄️ 3. Database Setup

### ✅ Requirements
Ensure **PostgreSQL** is installed and running on your machine.

---

### 📥 Import the Preconfigured Database

The project includes a preconfigured PostgreSQL database (schema + sample data) to simplify setup.

#### 🔧 Steps:

1. **Create an empty database** named `votingsystemdb`:

   ```sql
   CREATE DATABASE votingsystemdb;
   ```

2. **Import the full schema and data**:
```bash
psql -U postgres -d votingsystemdb -f initial_db_dump.sql

```

📌 The initial_db_dump.sql file is located in the root directory of this project.
✅ It contains all required tables, relationships, Flyway migrations, and sample users (admin + students).

## 🗄️ 4. Configure Application Properties
   Update src/main/resources/application.properties
   ```properties
   # PostgreSQL Config

spring.datasource.url=jdbc:postgresql://localhost:5432/votingsystemdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword


# Ganache/Web3
# Get your ganache private key by running this command on your terminal
# ganache --chain.chainId=1337 --chain.networkId=1337 --wallet.totalAccounts=10 --wallet.defaultBalance=1000

blockchain.private.key=your_ganache_private_key
blockchain.node.url=http://127.0.0.1:7545

   ```

## 🗄️ 4. Build and Run your application

## 5. 🔐 Authentication Flow

- Student logs in using `regNo` and `password`
- A 6-digit OTP is sent to the student’s registered email "you can hard code it to your email for testing"
- OTP is verified via `/api/auth/verify-otp`
- Upon success, access & refresh JWT tokens are issued
- Tokens are used to access protected endpoints

---

## 6. 🚀 API Endpoints

### 🔐 Authentication

- `POST /api/auth/login` — Login with `regNo` and `password`
- `POST /api/auth/register` — Register a new user (student)
- `POST /api/auth/verify-otp` — Verify the 6-digit OTP sent via email

### 👥 Members (JWT Protected)

- `GET /api/members` — List all members
- `POST /api/members` — Create a new member
- `PUT /api/members/{id}` — Update a member
- `DELETE /api/members/{id}` — Delete a member

### 🗳️ Voting (JWT Protected)

- `GET /api/vote/status` — Check if the logged-in student has already voted
- `POST /api/vote/` — Cast a vote (stored on blockchain and PostgreSQL)



