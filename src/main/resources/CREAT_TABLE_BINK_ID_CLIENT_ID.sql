create table account(
bank_id bigserial not null primary key,
client_id bigserial not null primary key,
client_id bigint,
FOREIGN KEY (client_id) REFERENCES client(id)
);

TRUNCATE account;

DROP table account;