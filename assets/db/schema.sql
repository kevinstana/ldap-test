-- Basic users table. Used to save application specific data about LDAP users and external users.
create table if not exists users (
    id bigserial PRIMARY KEY,
    username varchar(50) unique not null,
    email varchar(50) unique not null,
    role_id bigint not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    created_at timestamp not null,
    last_modified timestamp,
    last_modified_by bigint,
    is_enabled boolean not null
);

alter table users
add constraint fk_last_modified_by foreign key (last_modified_by) references users(id);


-- Role types and table.
create type role_type as enum ('STUDENT', 'PROFESSOR', 'SECRETARY', 'ADMIN');

create table if not exists roles (
    id serial primary key,
    role role_type unique not null
);

alter table users
add constraint fk_role foreign key (role_id) references roles(id);

insert into roles (role) values 
    ('STUDENT'),
    ('PROFESSOR'),
    ('SECRETARY'),
    ('ADMIN')
on conflict do nothing;

-- External users table. Used to authenticate admin and test users.
create table if not exists external_users (
    id bigserial PRIMARY KEY,
    username varchar(50) unique not null,
    password varchar(255) not null
);

INSERT INTO external_users (username, password) VALUES
    ('admin', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe');

INSERT INTO users (username, email, role_id, first_name, last_name, created_at, is_enabled) VALUES
    ('admin', 'admin@test.com', 4, 'Admin', 'One', NOW(), true);