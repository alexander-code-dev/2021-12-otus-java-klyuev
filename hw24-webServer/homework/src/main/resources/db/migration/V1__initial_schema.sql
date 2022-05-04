-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50)
);
create table address
(
    id   bigint not null primary key,
    street varchar(50),
    client_id bigint
);
CREATE SEQUENCE ADDRESS_ID_SEQ;
ALTER TABLE ADDRESS ALTER COLUMN id SET DEFAULT nextval('ADDRESS_ID_SEQ');

create table phone
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint
);
CREATE SEQUENCE PHONE_ID_SEQ;
ALTER TABLE PHONE ALTER COLUMN id SET DEFAULT nextval('PHONE_ID_SEQ');
