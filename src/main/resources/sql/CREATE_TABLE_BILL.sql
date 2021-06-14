create table bill(
id bigserial not null primary key,
date_of_creation timestamp not null,
payment numeric(19, 2) not null,
client_id bigint,
FOREIGN KEY (client_id) REFERENCES client(id)
)