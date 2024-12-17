create table IF NOT exists users (
    id bigserial PRIMARY KEY,
    username varchar(50) unique not null,
    email varchar(50) unique not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    title varchar(50) not null
);
