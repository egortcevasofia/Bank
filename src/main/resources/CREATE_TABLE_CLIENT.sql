create table client(
id bigserial not null primary key,
first_name varchar(100) not null,
last_name varchar(100) not null,
age integer not null,
date_of_birth date not null
);

TRUNCATE account;

DROP table client;