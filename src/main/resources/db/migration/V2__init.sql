CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    firstname VARCHAR(128) NOT NULL,
    lastname  VARCHAR(128) NOT NULL
);

CREATE TABLE orders
(
    id             SERIAL PRIMARY KEY,
    certificate_id INT REFERENCES gift_certificate (id),
    users_id       INT REFERENCES users (id),
    cost           DECIMAL CHECK ( cost > 0 ),
    purchase_date  TIMESTAMP
);

insert into users (firstname, lastname)
values ('Aaaa', 'Bbbb'),
       ('Cccc', 'Dddd');