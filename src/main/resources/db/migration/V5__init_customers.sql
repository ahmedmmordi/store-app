-- Create the customers table (if not already created)
CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_username VARCHAR(30) NOT NULL UNIQUE,
    customer_email VARCHAR(50) NOT NULL UNIQUE,
    customer_phone VARCHAR(20),
    customer_address VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) DEFAULT 'SYSTEM',
    last_modified_by VARCHAR(255) DEFAULT 'SYSTEM'
);

-- Insert test customers
INSERT INTO customers (customer_username, customer_email, customer_phone, customer_address) VALUES
    ('john_doe', 'john.doe@gmail.com', '+1234567890', '123 Main St, New York, NY'),
    ('jane_smith', 'jane.smith@yahoo.com', '+1987654321', '456 Oak Ave, Los Angeles, CA'),
    ('michael_brown', 'michael.brown@hotmail.com', '+1122334455', '789 Pine Rd, Chicago, IL'),
    ('emily_johnson', 'emily.johnson@gmail.com', '+15551234567', '321 Maple St, Houston, TX'),
    ('david_wilson', 'david.wilson@outlook.com', '+14155559876', '654 Elm St, Phoenix, AZ'),
    ('sarah_miller', 'sarah.miller@gmail.com', '+16175550123', '987 Cedar Ave, Philadelphia, PA'),
    ('robert_moore', 'robert.moore@yahoo.com', '+12125551234', '246 Birch Rd, San Antonio, TX'),
    ('lisa_taylor', 'lisa.taylor@gmail.com', '+13015556789', '135 Spruce St, San Diego, CA'),
    ('kevin_anderson', 'kevin.anderson@outlook.com', '+14085550987', '864 Walnut Ave, Dallas, TX'),
    ('laura_thomas', 'laura.thomas@gmail.com', '+15045551234', '753 Hickory Rd, San Jose, CA'),
    ('brian_jackson', 'brian.jackson@yahoo.com', '+17035559876', '951 Chestnut St, Austin, TX'),
    ('karen_white', 'karen.white@gmail.com', '+18045551234', '159 Redwood Ave, Jacksonville, FL'),
    ('steven_harris', 'steven.harris@hotmail.com', '+19015556789', '357 Poplar St, Fort Worth, TX'),
    ('nancy_martin', 'nancy.martin@gmail.com', '+13105559876', '258 Willow Rd, Columbus, OH'),
    ('george_clark', 'george.clark@yahoo.com', '+17125551234', '147 Fir St, Charlotte, NC');
