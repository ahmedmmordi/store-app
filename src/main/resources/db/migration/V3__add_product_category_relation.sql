-- Add category_id column to products
ALTER TABLE products
    ADD COLUMN category_id BIGINT;

-- Add foreign key constraint linking products to the categories
ALTER TABLE products
    ADD CONSTRAINT fk_products_category
    FOREIGN KEY (category_id)
    REFERENCES categories(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
