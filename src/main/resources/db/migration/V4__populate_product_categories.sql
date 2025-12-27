-- Smartphones
UPDATE products
SET category_id = 1
WHERE product_name LIKE '%iPhone%'
   OR product_name LIKE '%Samsung Galaxy%'
   OR product_name LIKE '%Google Pixel%'
   OR product_name LIKE '%OnePlus%'
   OR product_name LIKE '%Motorola Edge%'
   OR product_name LIKE '%Xiaomi%'
   OR product_name LIKE '%Sony Xperia%'
   OR product_name LIKE '%Oppo%'
   OR product_name LIKE '%Vivo%';

-- Laptops
UPDATE products
SET category_id = 2
WHERE product_name LIKE '%Dell XPS%'
   OR product_name LIKE '%HP Spectre%'
   OR product_name LIKE '%Lenovo ThinkPad%';

-- Headphones / Earbuds
UPDATE products
SET category_id = 3
WHERE product_name LIKE '%AirPods%'
   OR product_name LIKE '%Galaxy Buds%'
   OR product_name LIKE '%WH-1000XM5%'
   OR product_name LIKE '%Charge 5%';

-- Gaming Consoles
UPDATE products
SET category_id = 4
WHERE product_name LIKE '%Nintendo Switch%'
   OR product_name LIKE '%ROG Phone%';

-- Cameras
UPDATE products
SET category_id = 5
WHERE product_name LIKE '%Canon EOS%' ;

-- Speakers
UPDATE products
SET category_id = 6
WHERE product_name LIKE '%Echo Dot%'
   OR product_name LIKE '%JBL Flip%'
   OR product_name LIKE '%Soundcore%';

-- Accessories / Others
UPDATE products
SET category_id = 7
WHERE category_id IS NULL;
