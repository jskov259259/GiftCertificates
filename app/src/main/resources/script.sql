DROP TABLE IF EXISTS gift_certificate;
CREATE TABLE gift_certificate
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    price MONEY NOT NULL,
    duration INTERVAL NOT NULL,
    create_date TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP
);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);
