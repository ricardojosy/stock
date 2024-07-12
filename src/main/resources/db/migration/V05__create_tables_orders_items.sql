CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    total NUMERIC(10, 2),
    create_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    price NUMERIC(10, 2) NOT NULL,
	quantity INTEGER NOT NULL,
    total NUMERIC(10, 2) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);
