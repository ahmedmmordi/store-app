-- Create the products table (if not already created)
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    product_rating DOUBLE NOT NULL,
    product_description TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) DEFAULT 'SYSTEM',
    modified_by VARCHAR(255) DEFAULT 'SYSTEM'
    );

-- Insert test products
INSERT INTO products (product_name, product_price, product_rating, product_description) VALUES
    ('Apple iPhone 14', 999.99, 4.8, 'A high-end smartphone from Apple.'),
    ('Samsung Galaxy S23 phone', 899.50, 4.6, 'Samsung flagship smartphone with advanced features.'),
    ('Google Pixel 8', 799.00, 4.5, 'Google\'s latest Pixel phone with clean Android experience.'),
    ('Sony WH-1000XM5 Headphones', 349.99, 4.7, 'Industry-leading noise cancelling headphones.'),
    ('Dell XPS 13 Laptop', 1199.99, 4.4, 'Compact and powerful ultrabook from Dell.'),
    ('Nintendo Switch', 299.99, 4.9, 'Popular hybrid gaming console from Nintendo.'),
    ('Logitech MX Master 3 Mouse', 99.99, 4.6, 'Ergonomic wireless mouse for productivity.'),
    ('Amazon Echo Dot', 49.99, 4.3, 'Smart speaker with Alexa voice assistant.'),
    ('Fitbit Charge 5', 129.95, 4.2, 'Fitness tracker with heart rate monitoring.'),
    ('Canon EOS R10 Camera', 979.00, 4.5, 'Mirrorless camera for photography enthusiasts.'),
    ('OnePlus 11 phone', 699.00, 4.4, 'Flagship phone from OnePlus with fast performance.'),
    ('Motorola Edge 40 phone', 649.99, 4.3, 'Motorola smartphone with edge display.'),
    ('Xiaomi Redmi Note 13 Phone', 329.99, 4.1, 'Affordable smartphone with decent specs.'),
    ('Apple AirPods Pro', 249.99, 4.6, 'Wireless earbuds with active noise cancellation.'),
    ('Samsung Galaxy Buds2', 149.99, 4.5, 'Compact wireless earbuds from Samsung.'),
    ('Sony Xperia 1 IV Phone', 1199.00, 4.2, 'Sony\'s high-end smartphone with 4K display.'),
    ('HP Spectre x360 Laptop', 1399.99, 4.5, 'Premium 2-in-1 laptop with touch display.'),
    ('Lenovo ThinkPad X1 Carbon', 1299.99, 4.4, 'Business ultrabook with robust build.'),
    ('Asus ROG Phone 7', 899.00, 4.6, 'Gaming smartphone with high refresh rate.'),
    ('Google Pixel Buds Pro', 199.99, 4.3, 'Wireless earbuds from Google with good sound.'),
    ('Oppo Reno 10 Phone', 599.99, 4.1, 'Mid-range smartphone from Oppo.'),
    ('Vivo V27 Phone', 549.00, 4.0, 'Stylish smartphone with good camera.'),
    ('JBL Flip 6 Speaker', 119.99, 4.5, 'Portable Bluetooth speaker with powerful sound.'),
    ('Anker Soundcore Liberty 4', 129.99, 4.4, 'Affordable high-quality wireless earbuds.'),
    ('Apple iPhone SE', 429.99, 4.3, 'Compact and powerful iPhone SE model.');
