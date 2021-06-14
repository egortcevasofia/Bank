create table account(
id bigserial not null primary key,
login varchar(100) not null,
password varchar(100) not null,
account_status varchar(100) not null,
client_id bigint,
FOREIGN KEY (client_id) REFERENCES client(id)
);

TRUNCATE account;

DROP table account;

