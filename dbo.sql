CREATE DATABASE [TODODB]
GO

USE [TODODB];
GO


CREATE TABLE sec_user_entity
(
    id       int IDENTITY (1, 1) NOT NULL,
    username varchar(255)        NOT NULL,
    email    varchar(255)        NOT NULL,
    password varchar(255)        NOT NULL,
    session  varchar(255),
    CONSTRAINT pk_sec_user_entity PRIMARY KEY (id)
)
GO

CREATE TABLE todo_item_entity
(
    id               int IDENTITY (1, 1) NOT NULL,
    user_id          int                 NOT NULL,
    item_description varchar(255)        NOT NULL,
    created_date     date                NOT NULL,
    modified_date    date,
    status           varchar(255)        NOT NULL,
    CONSTRAINT pk_todo_item_entity PRIMARY KEY (id),
    CONSTRAINT fk_todo_item_entity_user_id
        FOREIGN KEY (user_id)
            REFERENCES sec_user_entity (id)
)
GO