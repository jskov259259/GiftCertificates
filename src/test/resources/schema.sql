DROP TABLE IF EXISTS gift_certificate;
CREATE TABLE gift_certificate
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    price DECIMAL NOT NULL,
    duration BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP
);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS certificates_tags;
CREATE TABLE certificates_tags
(
    certificate_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES gift_certificate(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id),
    UNIQUE (certificate_id, tag_id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    age INTEGER
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    certificate_id INT NOT NULL REFERENCES gift_certificate(id),
    purchase_time TIMESTAMP NOT NULL,
    price DECIMAL NOT NULL,
    UNIQUE(user_id, certificate_id)
);
