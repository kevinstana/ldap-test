-- Basic users table. Used to save application specific data about LDAP users and external users.
create table if not exists users (
    id bigserial PRIMARY KEY,
    username varchar(255) unique not null,
    email varchar(255) unique not null,
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
    username varchar(255) unique not null,
    password varchar(255) not null
);


-- Theses
CREATE TABLE IF NOT EXISTS theses (
    id bigserial primary key,
    title VARCHAR(255) unique not null,
    description text not null,
    created_by bigint not null,
    created_at timestamp not null,
    last_modified timestamp,
    last_modified_by bigint,
    professor_id bigint not null,
    student_id bigint unique,
    second_reviewer_id bigint,
    third_reviewer_id bigint,
    status_id bigint not null,
    professor_grade numeric(3, 1) check (professor_grade between 1 and 10),
    second_reviewer_grade numeric(3, 1) check (second_reviewer_grade between 1 and 10),
    third_reviewer_grade numeric(3, 1) check (third_reviewer_grade between 1 and 10),
    views int,
    doc_link text,

    constraint fk_theses_created_by foreign key (created_by) references users (id),
    constraint fk_theses_last_modified_by foreign key (last_modified_by) references users (id),
    constraint fk_theses_professor_id foreign key (professor_id) references users (id),
    constraint fk_theses_student_id foreign key (student_id) references users (id),
    constraint fk_theses_second_reviewer_id foreign key (second_reviewer_id) references users (id),
    constraint fk_theses_third_reviewer_id foreign key (third_reviewer_id) references users (id)
);


-- Thesis status types and table
create type thesis_status_type as enum ('DRAFT', 'AVAILABLE', 'IN_PROGRESS', 'PENDING_PRESENTATION', 'REVIEWED', 'PUBLISHED', 'CANCELLED');

create table if not exists thesis_status (
    id serial primary key,
    status thesis_status_type unique not null
);

alter table theses
add constraint fk_thesis_status foreign key (status_id) references thesis_status(id);

insert into thesis_status (status) values 
    ('DRAFT'),
    ('AVAILABLE'),
    ('IN_PROGRESS'),
    ('PENDING_PRESENTATION'),
    ('REVIEWED'),
    ('PUBLISHED'),
    ('CANCELLED')
on conflict do nothing;
