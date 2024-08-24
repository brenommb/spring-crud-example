--liquibase formatted sql

--changeset brenommb:1
CREATE TABLE cards (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);