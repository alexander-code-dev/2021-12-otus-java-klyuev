CREATE TABLE CLIENT
(
    id   BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE SEQUENCE CLIENT_ID_SEQ;
ALTER TABLE CLIENT ALTER COLUMN id SET DEFAULT nextval('CLIENT_ID_SEQ');
--INSERT INTO CLIENT (name) values ('Aleksander');
--INSERT INTO CLIENT (name) values ('Vasya');

CREATE TABLE ADDRESS
(
    id          BIGINT NOT NULL PRIMARY KEY,
    street      VARCHAR(50),
    client_id   BIGINT
);
CREATE SEQUENCE ADDRESS_ID_SEQ;
ALTER TABLE ADDRESS ALTER COLUMN id SET DEFAULT nextval('ADDRESS_ID_SEQ');
--INSERT INTO ADDRESS (street, client_id) values ('Lenina', 1);
--INSERT INTO ADDRESS (street, client_id) values ('Rumjana', 2);

CREATE TABLE PHONE
(
    id          BIGINT NOT NULL PRIMARY KEY,
    number      VARCHAR(50),
    client_id   BIGINT
);
CREATE SEQUENCE PHONE_ID_SEQ;
ALTER TABLE PHONE ALTER COLUMN id SET DEFAULT nextval('PHONE_ID_SEQ');
--INSERT INTO PHONE (number, client_id) values ('555-13-22', 1);
--INSERT INTO PHONE (number, client_id) values ('8-333-986-78-87', 1);
--INSERT INTO PHONE (number, client_id) values ('8-222-888-98-15', 2);


