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
add constraint fk_last_modified_by foreign key (last_modified_by) references users (id);

-- Role types and table.
create type role_type as enum ('STUDENT', 'PROFESSOR', 'SECRETARY', 'ADMIN');

create table if not exists roles (
    id serial primary key,
    role role_type unique not null
);

alter table users
add constraint fk_role foreign key (role_id) references roles (id);

insert into
    roles (role)
values ('STUDENT'),
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
    started_at timestamp,
    last_modified timestamp,
    last_modified_by bigint,
    professor_id bigint not null,
    student_id bigint unique,
    second_reviewer_id bigint,
    third_reviewer_id bigint,
    status_id bigint not null,
    professor_grade numeric(3, 1) check (
        professor_grade between 1 and 10
    ),
    second_reviewer_grade numeric(3, 1) check (
        second_reviewer_grade between 1 and 10
    ),
    third_reviewer_grade numeric(3, 1) check (
        third_reviewer_grade between 1 and 10
    ),
    file_name varchar(255),
    file_size bigint,
    published_at timestamp,
    constraint fk_theses_created_by foreign key (created_by) references users (id),
    constraint fk_theses_last_modified_by foreign key (last_modified_by) references users (id),
    constraint fk_theses_professor_id foreign key (professor_id) references users (id),
    constraint fk_theses_student_id foreign key (student_id) references users (id),
    constraint fk_theses_second_reviewer_id foreign key (second_reviewer_id) references users (id),
    constraint fk_theses_third_reviewer_id foreign key (third_reviewer_id) references users (id)
);

-- Thesis status types and table
create type thesis_status_type as enum ('AVAILABLE', 'IN_PROGRESS', 'PENDING_REVIEW', 'REVIEWED', 'PUBLISHED');

create table if not exists thesis_status (
    id serial primary key,
    status thesis_status_type unique not null
);

alter table theses
add constraint fk_thesis_status foreign key (status_id) references thesis_status (id);

insert into
    thesis_status (status)
values ('AVAILABLE'),
    ('IN_PROGRESS'),
    ('PENDING_REVIEW'),
    ('REVIEWED'),
    ('PUBLISHED')
on conflict do nothing;

-- Courses table
create table if not exists courses (
    id bigserial primary key,
    name varchar(255) unique not null,
    url varchar(255) unique
);

create table if not exists course_theses (
    course_id bigserial not null,
    thesis_id bigserial not null,
    primary key (course_id, thesis_id),
    foreign key (course_id) references courses (id),
    foreign key (thesis_id) references theses (id)
);

-- Tasks
create table if not exists tasks (
    id bigserial primary key,
    title varchar(255) unique not null,
    description text,
    thesis_id bigint not null,
    created_at timestamp not null,
    priority_id bigint not null,
    status_id bigint not null
);

create type task_priority_type as enum ('LOW', 'MEDIUM', 'HIGH');

create table if not exists task_priority (
    id bigserial primary key,
    priority task_priority_type not null
);

create type task_status_type as enum ('IN_PROGRESS', 'DONE');

create table if not exists task_status (
    id bigserial primary key,
    status task_status_type not null
);

alter table tasks
add constraint fk_task_thesis_id foreign key (thesis_id) references theses (id);

alter table tasks
add constraint fk_task_status foreign key (status_id) references task_status (id);

alter table tasks
add constraint fk_task_priority foreign key (priority_id) references task_priority (id);

insert into
    task_status (status)
values ('IN_PROGRESS'),
    ('DONE')
on conflict do nothing;

insert into
    task_priority (priority)
values ('LOW'),
    ('MEDIUM'),
    ('HIGH')
on conflict do nothing;

-- Task Files
create table if not exists task_files (
    id bigserial not null,
    filename varchar(255) not null,
    filesize bigint not null,
    task_id bigserial not null,
    foreign key (task_id) references tasks (id)
);

-- Thesis Requests
create type thesis_request_status_type as enum ('PENDING', 'APPROVED', 'REJECTED', 'INVALID');

create table if not exists thesis_request_status (
    id bigserial primary key,
    status thesis_request_status_type not null
);

insert into
    thesis_request_status (status)
values ('PENDING'),
    ('APPROVED'),
    ('REJECTED'),
    ('INVALID')
on conflict do nothing;

create table if not exists thesis_requests (
    id bigserial primary key,
    student_id bigint not null,
    thesis_id bigint not null,
    description text not null,
    pdf varchar(255) not null,
    pdf_size bigint not null,
    status_id bigint,
    created_at timestamp not null
);

alter table thesis_requests
add constraint fk_request_status_id foreign key (status_id) references thesis_request_status (id);

alter table thesis_requests
add constraint fk_request_student_id foreign key (student_id) references users (id);

alter table thesis_requests
add constraint fk_request_thesis_id foreign key (thesis_id) references theses (id);

-- Dates
create table if not exists assignment_dates (
    id bigserial primary key,
    from_date timestamp not null,
    to_date timestamp not null
);

create table if not exists reviewing_dates (
    id bigserial primary key,
    from_date timestamp not null,
    to_date timestamp not null
);