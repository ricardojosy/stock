INSERT INTO categories (category_name, parent_id) VALUES ('Electronics', null);
INSERT INTO categories (category_name, parent_id) VALUES ('Phones', 1);
INSERT INTO categories (category_name, parent_id) VALUES ('Laptops', 1);
INSERT INTO products (product_name, category_id, description, price, available) VALUES ('iPhone 13', 2, 'Celphone Acme', 999.99, true);
INSERT INTO products (product_name, category_id, description, price, available) VALUES ('Dell XPS 15', 3, 'Laptop gamer', 1499.99, true);
