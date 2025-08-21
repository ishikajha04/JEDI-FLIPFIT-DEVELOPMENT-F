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

-- Payment Methods table
CREATE TABLE FlipfitPayment (
    paymentId INT PRIMARY KEY AUTO_INCREMENT,
    customerId INT NOT NULL,
    lastFourDigits VARCHAR(4) NOT NULL,
    cardHolderName VARCHAR(100) NOT NULL,
    expiryDate VARCHAR(5) NOT NULL,
    isActive BOOLEAN DEFAULT true,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    CONSTRAINT unique_card_per_customer UNIQUE (customerId, lastFourDigits)
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

-- Triggers for automated actions

DELIMITER //

-- Update available seats when booking is confirmed
CREATE TRIGGER after_booking_confirm
AFTER UPDATE ON Booking
FOR EACH ROW
BEGIN
    IF NEW.status = 'CONFIRMED' AND OLD.status != 'CONFIRMED' THEN
        UPDATE Slot
        SET availableSeats = availableSeats - 1
        WHERE slotId = NEW.slotId;
    ELSEIF OLD.status = 'CONFIRMED' AND NEW.status = 'CANCELLED' THEN
        UPDATE Slot
        SET availableSeats = availableSeats + 1
        WHERE slotId = NEW.slotId;
    END IF;
END;
//

-- Create notification after booking status change
CREATE TRIGGER after_booking_status_change
AFTER UPDATE ON Booking
FOR EACH ROW
BEGIN
    IF NEW.status != OLD.status THEN
        INSERT INTO Notification (userId, title, message, notificationType)
        SELECT
            b.customerId,
            CONCAT('Booking ', NEW.status),
            CONCAT('Your booking #', NEW.bookingId, ' has been ', NEW.status),
            'BOOKING_UPDATE'
        FROM Booking b
        WHERE b.bookingId = NEW.bookingId;
    END IF;
END;
//

-- Notify waitlist when slot becomes available
CREATE TRIGGER after_slot_availability_change
AFTER UPDATE ON Slot
FOR EACH ROW
BEGIN
    IF NEW.availableSeats > OLD.availableSeats AND NEW.availableSeats > 0 THEN
        INSERT INTO Notification (userId, title, message, notificationType)
        SELECT
            w.customerId,
            'Slot Available',
            CONCAT('A slot you were waiting for is now available at center #', NEW.centerId),
            'WAITLIST_NOTIFICATION'
        FROM Waitlist w
        WHERE w.slotId = NEW.slotId AND w.status = 'WAITING'
        ORDER BY w.position
        LIMIT NEW.availableSeats;

        UPDATE Waitlist
        SET status = 'NOTIFIED'
        WHERE slotId = NEW.slotId AND status = 'WAITING'
        ORDER BY position
        LIMIT NEW.availableSeats;
    END IF;
END;
//

DELIMITER ;

-- Create indexes for better performance
CREATE INDEX idx_user_email ON User(email);
CREATE INDEX idx_booking_customer ON Booking(customerId);
CREATE INDEX idx_slot_center ON Slot(centerId);
CREATE INDEX idx_payment_status ON Payment(paymentStatus);
CREATE INDEX idx_notification_user_date ON Notification(userId, createdAt);
CREATE INDEX idx_gym_owner ON GymCenter(ownerId);
CREATE INDEX idx_waitlist_status ON Waitlist(status);
CREATE INDEX idx_payment_customer ON FlipfitPayment(customerId, isActive);

-- Views for common queries

-- Available slots view
CREATE VIEW vw_available_slots AS
SELECT
    s.slotId,
    gc.name AS gymName,
    gc.location,
    s.dayOfWeek,
    s.startTime,
    s.endTime,
    s.availableSeats,
    s.capacity
FROM Slot s
JOIN GymCenter gc ON s.centerId = gc.centerId
WHERE s.isActive = true AND s.availableSeats > 0;

-- Customer booking history view
CREATE VIEW vw_customer_bookings AS
SELECT
    b.bookingId,
    u.name AS customerName,
    gc.name AS gymName,
    s.dayOfWeek,
    s.startTime,
    s.endTime,
    b.bookingDate,
    b.status,
    p.paymentStatus
FROM Booking b
JOIN Customer c ON b.customerId = c.customerId
JOIN User u ON c.customerId = u.userId
JOIN Slot s ON b.slotId = s.slotId
JOIN GymCenter gc ON s.centerId = gc.centerId
LEFT JOIN Payment p ON b.bookingId = p.bookingId;

-- Grant appropriate permissions
GRANT SELECT, INSERT, UPDATE ON flipfit.* TO 'app_user'@'localhost';
GRANT SELECT ON flipfit.vw_available_slots TO 'customer'@'localhost';
GRANT SELECT ON flipfit.vw_customer_bookings TO 'customer'@'localhost';
