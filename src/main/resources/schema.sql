CREATE TABLE client (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    dni VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE product (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(19,2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE invoice (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at TIMESTAMP,
    client_id BIGINT NOT NULL,
    total DECIMAL(19,2) NOT NULL,
    CONSTRAINT fk_invoice_client FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE invoice_detail (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quantity INT NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    product_id BIGINT NOT NULL,
    invoice_id BIGINT NOT NULL,
    CONSTRAINT fk_detail_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_detail_invoice FOREIGN KEY (invoice_id) REFERENCES invoice(id)
);