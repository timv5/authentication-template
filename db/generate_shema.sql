create table users
(
    user_id bigint,
    user_email varchar(50),
    user_password varchar(256),
    user_is_active boolean
);

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1;

create table roles
(
    role_id bigint,
    role_name varchar(256)
);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    user_time_created date,
    user_time_updated date
);