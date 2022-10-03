CREATE TABLE gift_certificate (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(128) NOT NULL ,
    description VARCHAR(128) NOT NULL ,
    price DECIMAL NOT NULL ,
    duration INT CHECK ( duration > 0 ) ,
    create_date TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE certificate_tag (
    certificate_id INT REFERENCES gift_certificate(id) ,
    tag_id INT REFERENCES tag(id),
    PRIMARY KEY (certificate_id, tag_id)
);
