create database if not exists appointment;

use appointment;
SELECT DATABASE();
create table user(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(255) unique,
    phone_number VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    roles ENUM('admin','customer') NOT NULL,
    is_Active boolean DEFAULT TRUE,
    create_At timestamp default current_timestamp,
    last_login datetime
    
);

create table availability(
id INT AUTO_INCREMENT PRIMARY KEY,
admin_id INT NOT NULL,
date DATE NOT NULL,
time_slot TIME NOT NULL,
create_At TIMESTAMP,
duration INT DEFAULT 30,
is_Active boolean DEFAULT TRUE,
FOREIGN KEY (admin_id) REFERENCES user(id)	
);

create table appointment(
id INT AUTO_INCREMENT PRIMARY KEY,
date_time DATETIME NOT NULL,
customer_id INT NOT NULL,
admin_id INT NOT NULL,
status ENUM('booked', 'cancelled', 'completed') NOT NULL DEFAULT 'booked',
duration INT default 30,
created_by ENUM('admin', 'customer') NOT NULL DEFAULT 'customer',
FOREIGN KEY (customer_id) REFERENCES user(id),
FOREIGN KEY (admin_id) REFERENCES user(id)

);

create table notifications(
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
appointment_id INT NULL,
message TEXT,
sent_at TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES user(id),
FOREIGN KEY (appointment_id) REFERENCES appointment(id)

);