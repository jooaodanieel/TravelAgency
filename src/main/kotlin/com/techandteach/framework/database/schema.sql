CREATE DATABASE "travelagency";

GRANT ALL PRIVILEGES ON DATABASE "travelagency" TO root;

\c "travelagency";

CREATE TABLE IF NOT EXISTS customers (

    id varchar(255) not null primary key,
    name varchar(255) unique not null,
    email varchar(255) unique not null,
    password varchar(255) not null

);