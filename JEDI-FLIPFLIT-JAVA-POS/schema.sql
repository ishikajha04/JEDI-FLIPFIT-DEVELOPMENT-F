
-- Drop existing tables if they exist (in correct order to handle foreign key constraints)
DROP TABLE IF EXISTS flipfit_payments;
DROP TABLE IF EXISTS flipfit_waitlist;
DROP TABLE IF EXISTS flipfit_bookings;
DROP TABLE IF EXISTS flipfit_cards;
DROP TABLE IF EXISTS flipfit_slots;
DROP TABLE IF EXISTS flipadmin_idadmin_iduser_idfit_gym_centers;
DROP TABLE IF EXISTS flipfit_customers;
DROP TABLE IF EXISTS flipfit_gym_owners;
DROP TABLE IF EXISTS flipfit_admins;
DROP TABLE IF EXISTS flipfit_users;


-- Base Users table (for common user attributes)
CREATE TABLE flipfit_users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    role ENUM('CUSTOMER', 'GYM_OWNER', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Customers table
CREATE TABLE flipfit_customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    location VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES flipfit_users(user_id) ON DELETE CASCADE
);

-- Gym Owners table
CREATE TABLE flipfit_gym_owners (
    owner_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    pan_id VARCHAR(20) UNIQUE NOT NULL,
    aadhar_number VARCHAR(20) UNIQUE NOT NULL,
    gst_number VARCHAR(20),
    verification_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES flipfit_users(user_id) ON DELETE CASCADE
);

-- Admins table
CREATE TABLE flipfit_admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES flipfit_users(user_id) ON DELETE CASCADE
);

-- Gym Centers table
CREATE TABLE flipfit_gym_centers (
    center_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    approval_status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES flipfit_gym_owners(owner_id) ON DELETE CASCADE
);

-- Slots table
CREATE TABLE flipfit_slots (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    center_id INT NOT NULL,
    day ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL DEFAULT 10,
    available_seats INT NOT NULL DEFAULT 10,
    price DECIMAL(10, 2) NOT NULL DEFAULT 100.00,
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (center_id) REFERENCES flipfit_gym_centers(center_id) ON DELETE CASCADE,
    UNIQUE KEY unique_slot (center_id, day, start_time, end_time)
);

-- Customer Cards table
CREATE TABLE flipfit_cards (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    card_holder_name VARCHAR(255) NOT NULL,
    expiry_date DATE NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    card_type ENUM('CREDIT', 'DEBIT') DEFAULT 'DEBIT',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES flipfit_customers(customer_id) ON DELETE CASCADE
);

-- Bookings table
CREATE TABLE flipfit_bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    slot_id INT NOT NULL,
    center_id INT NOT NULL,
    booking_date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'PENDING', 'WAITLISTED') DEFAULT 'PENDING',
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_id INT NULL,
    FOREIGN KEY (customer_id) REFERENCES flipfit_customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (slot_id) REFERENCES flipfit_slots(slot_id) ON DELETE CASCADE,
    FOREIGN KEY (center_id) REFERENCES flipfit_gym_centers(center_id) ON DELETE CASCADE
);

-- Payments table
CREATE TABLE flipfit_payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    card_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('SUCCESS', 'FAILED', 'PENDING') DEFAULT 'PENDING',
    payment_method ENUM('CARD', 'UPI', 'NET_BANKING') DEFAULT 'CARD',
    transaction_id VARCHAR(255) UNIQUE,
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES flipfit_bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES flipfit_customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES flipfit_cards(card_id) ON DELETE CASCADE
);

-- Waitlist table (storing customer queue for each slot)
CREATE TABLE flipfit_waitlist (
    waitlist_id INT AUTO_INCREMENT PRIMARY KEY,
    slot_id INT NOT NULL,
    customer_id INT NOT NULL,
    position INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (slot_id) REFERENCES flipfit_slots(slot_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES flipfit_customers(customer_id) ON DELETE CASCADE,
    UNIQUE KEY unique_waitlist_entry (slot_id, customer_id)
);

-- Add foreign key for payment_id in bookings table
ALTER TABLE flipfit_bookings
ADD CONSTRAINT fk_booking_payment
FOREIGN KEY (payment_id) REFERENCES flipfit_payments(payment_id) ON DELETE SET NULL;

-- Create indexes for better performance
CREATE INDEX idx_users_email ON flipfit_users(email);
CREATE INDEX idx_users_role ON flipfit_users(role);
CREATE INDEX idx_customers_location ON flipfit_customers(location);
CREATE INDEX idx_gym_centers_location ON flipfit_gym_centers(location);
CREATE INDEX idx_gym_centers_approval ON flipfit_gym_centers(approval_status);
CREATE INDEX idx_slots_center_day ON flipfit_slots(center_id, day);
CREATE INDEX idx_slots_availability ON flipfit_slots(is_available, available_seats);
CREATE INDEX idx_bookings_customer ON flipfit_bookings(customer_id);
CREATE INDEX idx_bookings_date ON flipfit_bookings(booking_date);
CREATE INDEX idx_bookings_status ON flipfit_bookings(status);
CREATE INDEX idx_waitlist_slot ON flipfit_waitlist(slot_id, position);

-- Insert sample admin user
INSERT INTO flipfit_users (email, password, phone_number, role)
VALUES ('admin@flipfit.com', 'admin123', '9999999999', 'ADMIN');

INSERT INTO flipfit_admins (user_id, name)
VALUES (LAST_INSERT_ID(), 'System Administrator');

-- Sample data for testing (optional)
-- Gym Owner
INSERT INTO flipfit_users (email, password, phone_number, role)
VALUES ('owner1@flipfit.com', 'owner123', '9876543210', 'GYM_OWNER');

INSERT INTO flipfit_gym_owners (user_id, name, pan_id, aadhar_number, gst_number, verification_status)
VALUES (LAST_INSERT_ID(), 'John Doe', 'ABCDE1234F', '123456789012', 'GST123456789', 'APPROVED');

-- Customer
INSERT INTO flipfit_users (email, password, phone_number, role)
VALUES ('customer1@flipfit.com', 'customer123', '9876543211', 'CUSTOMER');

INSERT INTO flipfit_customers (user_id, name, address, location)
VALUES (LAST_INSERT_ID(), 'Jane Smith', '123 Main St', 'Delhi');

-- Gym Center
INSERT INTO flipfit_gym_centers (owner_id, name, location, address, approval_status)
VALUES (1, 'FitZone Delhi Central', 'Delhi', '456 Fitness Avenue, Connaught Place', 'APPROVED');

-- Sample Slots
INSERT INTO flipfit_slots (center_id, day, start_time, end_time, capacity, available_seats, price) VALUES
(1, 'Monday', '06:00:00', '08:00:00', 20, 20, 150.00),
(1, 'Monday', '18:00:00', '20:00:00', 25, 25, 200.00),
(1, 'Tuesday', '06:00:00', '08:00:00', 20, 20, 150.00),
(1, 'Wednesday', '06:00:00', '08:00:00', 20, 20, 150.00),
(1, 'Thursday', '06:00:00', '08:00:00', 20, 20, 150.00),
(1, 'Friday', '06:00:00', '08:00:00', 20, 20, 150.00),
(1, 'Saturday', '08:00:00', '10:00:00', 30, 30, 250.00),
(1, 'Sunday', '08:00:00', '10:00:00', 30, 30, 250.00);