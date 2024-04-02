CREATE TABLE sec_user_entity
(
    id       int IDENTITY (1, 1) NOT NULL,
    username varchar(255),
    email    varchar(255),
    password varchar(255),
    session  varchar(255),
    CONSTRAINT pk_sec_user_entity PRIMARY KEY (id)
)
GO

CREATE TABLE todo_item_entity
(
    id               int IDENTITY (1, 1) NOT NULL,
    name             varchar(255),
    item_description varchar(255),
    created_date     date,
    modified_date    date,
    status           varchar(255),
    CONSTRAINT pk_todo_item_entity PRIMARY KEY (id)
)
GO