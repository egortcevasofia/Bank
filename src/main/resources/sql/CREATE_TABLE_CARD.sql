create table card(
id bigserial not null primary key,
balance numeric(19, 2) not null,
type_card varchar(100) not null,
client_id bigint,
FOREIGN KEY (client_id) REFERENCES client(id)
)