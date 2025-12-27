-- Create the categories table (if not already created)
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    category_description TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) DEFAULT 'SYSTEM',
    modified_by VARCHAR(255) DEFAULT 'SYSTEM'
);

-- Insert test categories
INSERT INTO categories (category_name, category_description) VALUES
    ('Smartphones', 'All types of smartphones from various brands.'),
    ('Laptops', 'All laptops including ultrabooks and gaming laptops.'),
    ('Headphones', 'Noise cancelling and wireless headphones.'),
    ('Earbuds', 'Wireless earbuds and small portable audio devices.'),
    ('Cameras', 'Digital cameras, mirrorless, and DSLR cameras.'),
    ('Gaming', 'Gaming consoles and accessories.'),
    ('Speakers', 'Portable and smart speakers for home or outdoor use.'),
    ('Accessories', 'Computer and mobile accessories like mice, chargers, etc.');
