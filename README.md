# FlipFit Gym Management System - POS Projects

This repository contains three different implementations of the FlipFit Gym Management System:

1. **JEDI-FLIPFLIT-JAVA-POS** - Console-based application
2. **JEDI-FLIPFLIT-JAVA-COLLECTION-POS** - Collection-based implementation
3. **JEDI-FLIPFLIT-JAVA-RESTAPI-POS** - REST API implementation using Dropwizard

## 📋 Prerequisites

Before running any of the projects, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git** (for version control)

## 🗄️ Database Setup

All projects use the same MySQL database schema. Follow these steps to set up the database:

### 1. Install and Start MySQL

```bash
# On macOS using Homebrew
brew install mysql
brew services start mysql

# On Ubuntu/Debian
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql

# On Windows
# Download and install MySQL from https://dev.mysql.com/downloads/mysql/
```

### 2. Create Database and Schema

```bash
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE flipfit_schema;
USE flipfit_schema;

# Exit MySQL
exit
```

### 3. Import Database Schema

```bash
# Navigate to the console application directory
cd JEDI-FLIPFLIT-JAVA-POS

# Import the schema
mysql -u root -p flipfit_schema < schema.sql
```

### 4. Configure Database Connection

Update the database credentials in the configuration files:

**For Console and Collection Projects:**
- File: `src/main/resources/database.properties`

**For REST API Project:**
- File: `src/main/resources/database.properties`

```properties
# Update these values according to your MySQL setup
db.url=jdbc:mysql://localhost:3306/flipfit_schema
db.username=your_mysql_username
db.password=your_mysql_password
```

## 🚀 Running the Projects

### 1. Console Application (JEDI-FLIPFLIT-JAVA-POS)

This is the main console-based application with a menu-driven interface.

```bash
# Navigate to the project directory
cd JEDI-FLIPFLIT-JAVA-POS

# Compile and run using Maven
mvn clean compile exec:java -Dexec.mainClass="com.flipfit.client.FlipfitApplication"

# Alternative: Compile and run manually
mvn clean compile
java -cp target/classes com.flipfit.client.FlipfitApplication
```

**Features:**
- Interactive console menu
- Role-based access (Customer, Gym Owner, Admin)
- Complete gym management functionality

**Usage:**
1. Run the application
2. Select your role from the main menu:
   - Customer (View gyms, book slots, manage bookings)
   - Gym Owner (Manage gyms, slots, view bookings)
   - Admin (Approve gym owners, manage system)

### 2. Collection-Based Implementation (JEDI-FLIPFLIT-JAVA-COLLECTION-POS)

This version uses Java Collections for data storage instead of database.

```bash
# Navigate to the project directory
cd JEDI-FLIPFLIT-JAVA-COLLECTION-POS

# Clean and compile
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.flipfit.client.FlipfitApplication"

# Alternative manual execution
java -cp target/classes com.flipfit.client.FlipfitApplication
```

**Features:**
- In-memory data storage using Collections
- Same interface as console application
- No database dependency required

### 3. REST API Implementation (JEDI-FLIPFLIT-JAVA-RESTAPI-POS)

This is a RESTful web service implementation using Dropwizard framework.

```bash
# Navigate to the project directory
cd JEDI-FLIPFLIT-JAVA-RESTAPI-POS

# Clean and compile
mvn clean compile

# Package the application
mvn package

# Run the REST API server
java -jar target/JEDI-FLIPFLIT-JAVA-RESTAPI-POS-1.0-SNAPSHOT.jar server

# Alternative using Maven
mvn exec:java -Dexec.mainClass="com.flipfit.App"
```

**Features:**
- RESTful API endpoints
- JSON request/response format
- Dropwizard framework with embedded Jetty server
- Swagger documentation available

**API Endpoints:**
- Customer Management: `/api/customers`
- Gym Owner Management: `/api/owners`
- Admin Management: `/api/admin`
- Booking Management: `/api/bookings`

**Testing the API:**
```bash
# Health check
curl http://localhost:8080/hello

# Example API calls (replace with actual endpoints)
curl -X GET http://localhost:8080/api/customers
curl -X POST http://localhost:8080/api/customers -H "Content-Type: application/json" -d '{"name":"John","email":"john@example.com"}'
```

## 🔧 Build and Package

### Building Individual Projects

```bash
# For Console Application
cd JEDI-FLIPFLIT-JAVA-POS
mvn clean package

# For Collection-based Application
cd JEDI-FLIPFLIT-JAVA-COLLECTION-POS
mvn clean package

# For REST API Application
cd JEDI-FLIPFLIT-JAVA-RESTAPI-POS
mvn clean package
```

### Building All Projects from Root

```bash
# From the root directory
mvn clean compile -f pom.xml
```

## 📚 Project Structure

All three projects follow a similar Maven structure. Here's the detailed structure for the REST API implementation (others follow the same pattern):

```
📁 JEDI-FLIPFLIT-JAVA-RESTAPI-POS/
├── 📄 pom.xml                                     # Maven configuration with Dropwizard dependencies
├── 📄 dependency-reduced-pom.xml                  # Generated Maven file
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/flipfit/
│   │   │   ├── 📄 App.java                        # Main Dropwizard application entry point
│   │   │   ├── 📁 bean/                           # Data models/POJOs
│   │   │   ├── 📁 business/                       # Business logic layer
│   │   │   ├── 📁 client/                         # Client interfaces (if any)
│   │   │   ├── 📁 dao/                            # Database access objects
│   │   │   ├── 📁 exception/                      # Custom exceptions
│   │   │   ├── 📁 restController/                 # REST API controllers
│   │   │   └── 📁 utils/                          # Utility classes
│   │   └── 📁 resources/
│   │       └── 📄 database.properties             # Database configuration
│   └── 📁 test/
│       └── 📁 java/                               # Unit tests
└── 📁 target/                                     # Maven build output
    ├── 📄 JEDI-FLIPFLIT-JAVA-RESTAPI-POS-1.0-SNAPSHOT.jar
    ├── 📄 original-JEDI-FLIPFLIT-JAVA-RESTAPI-POS-1.0-SNAPSHOT.jar
    └── 📁 classes/                                # Compiled classes
```

**Note:** The Console Application (JEDI-FLIPFLIT-JAVA-POS) and Collection-based Application (JEDI-FLIPFLIT-JAVA-COLLECTION-POS) follow the same structure, with the main differences being:
- Console/Collection apps use `FlipfitApplication.java` as the main entry point
- REST API uses `App.java` as the Dropwizard application entry point
- REST API includes `restController/` package for REST endpoints

## 🛠️ Troubleshooting

### Common Issues and Solutions

1. **MySQL Connection Error**
   ```bash
   # Check if MySQL is running
   brew services list | grep mysql  # macOS
   sudo systemctl status mysql      # Linux
   
   # Restart MySQL if needed
   brew services restart mysql      # macOS
   sudo systemctl restart mysql     # Linux
   ```

2. **Java Version Issues**
   ```bash
   # Check Java version
   java -version
   
   # Should show Java 17 or higher
   # Update JAVA_HOME if needed
   export JAVA_HOME=/path/to/java17
   ```

3. **Maven Build Failures**
   ```bash
   # Clean Maven cache
   mvn clean
   
   # Force update dependencies
   mvn clean compile -U
   ```

4. **Port Already in Use (REST API)**
   ```bash
   # Find process using port 8080
   lsof -i :8080
   
   # Kill the process
   kill -9 <PID>
   
   # Or run on different port
   java -jar target/app.jar server --port 8081
   ```

## 📖 Usage Examples

### Console Application Usage
```
=== FLIPFIT MAIN MENU ===
Please select your role:
1. Customer
2. Gym Owner  
3. Admin
4. Exit
Enter your choice (1-4): 1

=== CUSTOMER MENU ===
1. Register
2. Login
3. View Available Gyms
4. Book Slot
5. View My Bookings
6. Cancel Booking
7. Back to Main Menu
```

### REST API Usage
```bash
# Register a new customer
curl -X POST http://localhost:8080/api/customers/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/customers/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'

# View available gyms
curl -X GET http://localhost:8080/api/gyms
```

## 📝 License

This project is part of the JEDI training program.

**Note:** Make sure to update the database credentials in the configuration files before running any project. The default password in the configuration files should be changed for security reasons.
