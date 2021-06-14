create table bank_client(
bank_id bigint not null REFERENCES bank(id),
client_id bigint primary key REFERENCES client(id),
);
--add constraint  primary key_
-- ее наполняет bankServise при сохранении клиента
--будет заполняться когда создаем