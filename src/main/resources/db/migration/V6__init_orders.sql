-- Create orders table (if not already created)
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) DEFAULT 'SYSTEM',
    last_modified_by VARCHAR(255) DEFAULT 'SYSTEM',
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Create order_items table (if not already created)
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    item_quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) DEFAULT 'SYSTEM',
    last_modified_by VARCHAR(255) DEFAULT 'SYSTEM',
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Insert test orders
INSERT INTO orders (customer_id, total_amount) VALUES
    (1, 2149.95),
    (2, 1499.95),
    (3, 649.99),
    (1, 1249.97),
    (4, 1349.95),
    (5, 349.98),
    (2, 2199.95),
    (3, 699.95),
    (4, 589.97),
    (5, 1309.98);

-- Insert test order_items
INSERT INTO order_items (order_id, product_id, item_quantity, unit_price) VALUES
    (1, 1, 1, 999.99),
    (1, 6, 5, 229.99),
    (2, 5, 1, 1199.99),
    (2, 2, 3, 100.00),
    (3, 12, 1, 649.99),
    (4, 14, 3, 249.99),
    (5, 15, 5, 149.99),
    (5, 8, 2, 49.99),
    (6, 10, 2, 349.99),
    (7, 1, 1, 999.99),
    (7, 5, 1, 599.99),
    (7, 3, 1, 599.97),
    (8, 4, 1, 349.99),
    (8, 9, 1, 349.96),
    (9, 23, 3, 119.99),
    (9, 24, 2, 129.99),
    (10, 3, 1, 799.00),
    (10, 13, 1, 329.99),
    (10, 20, 1, 179.99);
