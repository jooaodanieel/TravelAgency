CREATE DATABASE "travelagencytest";

GRANT ALL PRIVILEGES ON DATABASE "travelagencytest" TO root;

\c "travelagencytest";

CREATE TABLE IF NOT EXISTS customers (

    id varchar(255) not null primary key,
    name varchar(255) unique not null,
    email varchar(255) unique not null,
    password varchar(255) not null

);

CREATE TABLE IF NOT EXISTS credit_cards (

    id serial primary key,
    provider varchar(255) not null,
    owner varchar(255) not null,
    number varchar(255) not null,
    cvc varchar(255) not null,
    expiration varchar(255) not null,
    customer_id varchar(255),
    constraint fk_customer
        foreign key(customer_id)
            references customers(id)
                on delete cascade

);

CREATE TABLE IF NOT EXISTS airlines (

    id varchar(255) not null primary key,
    name varchar(255) unique not null

);