INSERT INTO categories (category_name, parent_id) VALUES ('Electronics', null);
INSERT INTO categories (category_name, parent_id) VALUES ('Phones', 1);
INSERT INTO categories (category_name, parent_id) VALUES ('Laptops', 1);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('iPhone 13', 2, 'Celphone Acme', 999.99, true, 0);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Dell XPS 15', 3, 'Laptop gamer', 2499.99, true, 1);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Ideapad Lenovo', 3, 'Laptop Lenovo', 1499.99, true, 2);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Poco X6', 2, 'Celphone Poco X6', 1999.99, true, 3);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Poco X6 Pro', 2, 'Celphone Poco X6 Pro', 2999.99, true, 2);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Keyboad', 3, 'Keyboard', 9.99, true, 5);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Keyboad wireless', 3, 'Keyboard wireless', 19.99, true, 5);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Keyboad gamer', 3, 'Keyboard gamer', 89.99, true, 3);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Mouse', 3, 'Mouse', 9.99, true, 5);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Mouse wireless', 3, 'Mouse wireless', 24.99, true, 5);
INSERT INTO products (product_name, category_id, description, price, available, quantity) VALUES ('Mouse gamer', 3, 'Mouse gamer', 99.99, true, 5);

