-- FlipFit Gym Management System Database Schema

-- Create the database
CREATE DATABASE IF NOT EXISTS flipfit;
USE flipfit;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Role table for managing user types
CREATE TABLE Role (
    roleId INT PRIMARY KEY AUTO_INCREMENT,
    roleName VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Base User table
CREATE TABLE User (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    roleId INT,
    isActive BOOLEAN DEFAULT true,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);

-- Customer specific details
CREATE TABLE Customer (
    customerId INT PRIMARY KEY,
    address TEXT,
    dateOfBirth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    membershipStatus ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    FOREIGN KEY (customerId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Gym Owner details
CREATE TABLE GymOwner (
    ownerId INT PRIMARY KEY,
    businessName VARCHAR(100),
    businessLicense VARCHAR(50),
    verificationStatus ENUM('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING',
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ownerId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Admin details
CREATE TABLE Admin (
    adminId INT PRIMARY KEY,
    department VARCHAR(50),
    adminLevel INT DEFAULT 1,
    FOREIGN KEY (adminId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Gym Center information
CREATE TABLE GymCenter (
    centerId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    ownerId INT NOT NULL,
    contactNumber VARCHAR(15),
    description TEXT,
    capacity INT,
    isApproved BOOLEAN DEFAULT false,
    operatingHours VARCHAR(100),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ownerId) REFERENCES GymOwner(ownerId),
    INDEX idx_location (location)
);

-- Slot management
CREATE TABLE Slot (
    slotId INT PRIMARY KEY AUTO_INCREMENT,
    centerId INT NOT NULL,
    dayOfWeek ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'),
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    capacity INT NOT NULL,
    availableSeats INT NOT NULL,
    isActive BOOLEAN DEFAULT true,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (centerId) REFERENCES GymCenter(centerId),
    CONSTRAINT check_time CHECK (startTime < endTime),
    CONSTRAINT check_capacity CHECK (capacity > 0 AND availableSeats >= 0)
);

-- Booking management
CREATE TABLE Booking (
    bookingId INT PRIMARY KEY AUTO_INCREMENT,
    customerId INT NOT NULL,
    slotId INT NOT NULL,
    bookingDate DATE NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (slotId) REFERENCES Slot(slotId),
    INDEX idx_booking_date (bookingDate)
);

-- Payment tracking
CREATE TABLE Payment (
    paymentId INT PRIMARY KEY AUTO_INCREMENT,
    bookingId INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paymentStatus ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    paymentMethod VARCHAR(50),
    transactionId VARCHAR(100) UNIQUE,
    FOREIGN KEY (bookingId) REFERENCES Booking(bookingId)
);

-- Notification system
CREATE TABLE Notification (
    notificationId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    title VARCHAR(100),
    message TEXT NOT NULL,
    notificationType VARCHAR(50),
    isRead BOOLEAN DEFAULT false,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(userId),
    INDEX idx_user_notification (userId, isRead)
);

-- Waitlist management
CREATE TABLE Waitlist (
    waitlistId INT PRIMARY KEY AUTO_INCREMENT,
    customerId INT NOT NULL,
    slotId INT NOT NULL,
    requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('WAITING', 'NOTIFIED', 'EXPIRED', 'CONVERTED') DEFAULT 'WAITING',
    position INT,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (slotId) REFERENCES Slot(slotId),
    INDEX idx_slot_waitlist (slotId, status)
);

-- Scheduler for system tasks
CREATE TABLE Scheduler (
    schedulerId INT PRIMARY KEY AUTO_INCREMENT,
    taskName VARCHAR(100) NOT NULL,
    taskType VARCHAR(50),
    scheduledTime TIMESTAMP,
    status ENUM('PENDING', 'RUNNING', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    lastRunTime TIMESTAMP NULL,
    nextRunTime TIMESTAMP NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default roles
INSERT INTO Role (roleName, description) VALUES
('ADMIN', 'System administrator with full access'),
('CUSTOMER', 'Regular gym customer'),
('GYM_OWNER', 'Owner of gym centers');