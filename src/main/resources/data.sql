create table appointment (id varchar(255) not null, appointment_date date, appointment_time time, id_service varchar(255), id_user varchar(255), primary key (id)) engine=InnoDB;
create table login (id varchar(255) not null, instant datetime(6), token varchar(512), id_user varchar(255) not null, primary key (id)) engine=InnoDB;
create table services (id varchar(255) not null, description varchar(255), image varchar(255), name varchar(255), price double precision, stock integer, primary key (id)) engine=InnoDB;
create table user_roles (user_id varchar(255) not null, roles varchar(255)) engine=InnoDB;
create table users (id varchar(255) not null, email varchar(255), gender varchar(255), image varchar(255), name varchar(255), password varchar(255), phone_number varchar(255), surname varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table services drop index if exists UK_h4rqgjwnqidx6mvj4i22dxwxe;
alter table services add constraint UK_h4rqgjwnqidx6mvj4i22dxwxe unique (name);
alter table users drop index if exists UK_6dotkott2kjsp8vw4d0m25fb7;
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users drop index if exists UK_r43af9ap4edm43mmtq01oddj6;
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table appointment add constraint FK5k0oem3ypbsnpc0kt1htx37w4 foreign key (id_service) references services (id);
alter table appointment add constraint FKmldusone9n43v3q6qrtjvb9jh foreign key (id_user) references users (id);
alter table login add constraint FKddrmlhg56oaq3coq9xohjulr4 foreign key (id_user) references users (id);
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id);

insert into users values ('c1334d57-120b-437b-baef-cf5b5f68cc3e','porofernandez@freljorld.com','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Admin', '$2a$12$tkmFeFcSZ4CLCgjbNhgrO.1D3izDlrNjidrkOZZlOvPlJm2D/oBYq', '234567890', 'freljorld', 'Admin');
insert into users values ('76b2071a-3f97-4666-bd76-3d3d38ca677d','aocrigane0@slideshare.net','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Juan', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '678429049', 'Batista', 'jbatista49');
insert into users values ('a049aff3-d439-4e77-9e3b-e6ffa59d9119','lmorsom1@merriam-webster.com','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Miguel', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '643424231', 'Montero', 'mmontero31');
insert into users values ('8c2e01c4-7bbb-4f65-bee5-410df2fa429d','mkimbury2@wp.com','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Lorenzo', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '647293745', 'Castillo', 'lcastillo45');
insert into users values ('40eb045d-1b22-469e-9014-0bc4bbe4a3c1','dberick3@behance.net','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Pablo', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '639443819', 'Belmonte', 'pbelmonte19');
insert into users values ('436eecf5-ca23-4650-8da6-71a9c7a8f99d','jdezamora4@google.co.jp','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Daniel', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '712191483', 'Zamora', 'dzamora83');
insert into users values ('4b1d3ee3-0baf-4d0e-9aaa-72418b723d3e','tvinnicombe5@webeden.co.uk','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Javier', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '715558889', 'Manzano', 'jmanzano89');
insert into users values ('0b1810ec-6191-492c-91b6-04ea0c93dab8','glongmaid6@printfriendly.com','Male','http://localhost:13169/rest/files/1646675189773_user.png', 'Martin', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '645294912', 'Palacios', 'mpalacios12');
insert into users values ('9dccf8c2-8a7f-4117-a2ca-ae14c35c9abf','tosband7@amazon.co.jp','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Teresa', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '696142642', 'de Maria', 'tdemaria42');
insert into users values ('bff28d7f-869a-4f0f-ae7e-2c74c23d4746','kheineken8@sitemeter.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Laura', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '698142732', 'Cerezo', 'lcerezo32');
insert into users values ('3a49cf85-3bfa-40e7-9c54-7db0518696ca','rcrothers9@mlb.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Raquel', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '687128442', 'Hermanas', 'rhermanas42');
insert into users values ('b778d509-c6ee-40b0-9320-93b4601e7f9c','bgaynesfo@over-blog.com','Female','http://localhost:13169/rest/files/1646675189773_user.png','Bella', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '714474232', 'Gallardo', 'bgallardo32');
insert into users values ('a6894032-abf6-4f93-b70e-030bb07dc45a','hhuskc@zimbio.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Hilda', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '716553900', 'Humarera', 'hhumarera900');
insert into users values ('2e9f78be-6563-42ee-83bb-e8f72c938646','kkellyd@independent.co.uk','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Karen', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '633594773', 'Kellingstong', 'kkellingstong73');
insert into users values ('7ee77bd1-1563-4937-bb14-ecd3d2503a05','hcowelle@github.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Helena', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '683471234', 'Collado', 'hcollado34');
insert into users values ('6c3607d4-21c9-4137-a49e-83383b6277ee','lgrigoriof@bizjournals.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Lorena', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '693841432', 'Gregorio', 'lgregorio32');
insert into users values ('d6381995-0703-4da7-8206-4c31392b4e9b','acoltang@reuters.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Adriana', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '693231474', 'Colgado', 'acolgado74');
insert into users values ('d8425a3b-c57c-4577-80e2-59a2397db67e','kgertrayh@gravatar.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Kelly', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '701412984', 'Gertray', 'kgertray84');
insert into users values ('a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1','fcawderyi@nbcnews.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Filomena', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '698443227', 'Casimiro', 'fcasimiro27');
insert into users values ('4d047ef1-206f-43be-a1c3-d5578c3846d3','tfigurej@163.com','Female','http://localhost:13169/rest/files/1646675189773_user.png', 'Tamara', '$2a$12$EZKesxbYLoZ6x3/NL.TJSODhGW5jNOpua4j.newEgPCPPZMxfdUo2', '684339226', 'Figueroa', 'tfigueroa26');

insert into user_roles (user_id, roles)
values ('c1334d57-120b-437b-baef-cf5b5f68cc3e', 'USER');
insert into user_roles (user_id, roles)
values ('c1334d57-120b-437b-baef-cf5b5f68cc3e', 'ADMIN');
insert into user_roles (user_id, roles)
values ('76b2071a-3f97-4666-bd76-3d3d38ca677d', 'USER');
insert into user_roles (user_id, roles)
values ('76b2071a-3f97-4666-bd76-3d3d38ca677d', 'USER');
insert into user_roles (user_id, roles)
values ('a049aff3-d439-4e77-9e3b-e6ffa59d9119', 'USER');
insert into user_roles (user_id, roles)
values ('8c2e01c4-7bbb-4f65-bee5-410df2fa429d', 'USER');
insert into user_roles (user_id, roles)
values ('40eb045d-1b22-469e-9014-0bc4bbe4a3c1', 'USER');
insert into user_roles (user_id, roles)
values ('436eecf5-ca23-4650-8da6-71a9c7a8f99d', 'USER');
insert into user_roles (user_id, roles)
values ('4b1d3ee3-0baf-4d0e-9aaa-72418b723d3e', 'USER');
insert into user_roles (user_id, roles)
values ('0b1810ec-6191-492c-91b6-04ea0c93dab8', 'USER');
insert into user_roles (user_id, roles)
values ('9dccf8c2-8a7f-4117-a2ca-ae14c35c9abf', 'USER');
insert into user_roles (user_id, roles)
values ('bff28d7f-869a-4f0f-ae7e-2c74c23d4746', 'USER');
insert into user_roles (user_id, roles)
values ('3a49cf85-3bfa-40e7-9c54-7db0518696ca', 'USER');
insert into user_roles (user_id, roles)
values ('b778d509-c6ee-40b0-9320-93b4601e7f9c', 'USER');
insert into user_roles (user_id, roles)
values ('a6894032-abf6-4f93-b70e-030bb07dc45a', 'USER');
insert into user_roles (user_id, roles)
values ('2e9f78be-6563-42ee-83bb-e8f72c938646', 'USER');
insert into user_roles (user_id, roles)
values ('7ee77bd1-1563-4937-bb14-ecd3d2503a05', 'USER');
insert into user_roles (user_id, roles)
values ('6c3607d4-21c9-4137-a49e-83383b6277ee', 'USER');
insert into user_roles (user_id, roles)
values ('d6381995-0703-4da7-8206-4c31392b4e9b', 'USER');
insert into user_roles (user_id, roles)
values ('d8425a3b-c57c-4577-80e2-59a2397db67e', 'USER');
insert into user_roles (user_id, roles)
values ('a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1', 'USER');
insert into user_roles (user_id, roles)
values ('4d047ef1-206f-43be-a1c3-d5578c3846d3', 'USER');

insert into services values ('7dafe5fd-976b-450a-9bab-17ab450a4fff', 'Corte de pelo para hombres', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Corte pelo Hombre', 15, 4);
insert into services values ('e4813d9c-cbb1-4f50-b997-3467ea5ace3e', 'Corte de pelo para niños', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Corte Niños', 11, 4);
insert into services values ('a6bc5b80-213c-4579-8335-f104d1bbfbb6', 'Corte de pelo básico para mujeres', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Corte pelo Mujer', 16, 4);
insert into services values ('c32008c1-86e7-47a8-86c6-0a34d9a971ad', 'Peinados bajo peticiones especiales', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Peinado Especial', 20, 2);
insert into services values ('baeac544-4bef-40f9-aed3-8730f80f27d8', 'Tintura de color completo', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Tinte Color Completo', 28, 3);
insert into services values ('7f5401a6-7862-4b3c-a7cf-1ab90a76da01', 'Mechas de uno o dos colores', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Mechas', 35, 3);
insert into services values ('3c9a49fa-e203-49ab-9ef0-09d4d7511fb7', 'Teñido gradiente', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Californianas', 50, 3);
insert into services values ('6b428efe-b051-49c7-a6c8-f4bec2b7c8d5', 'Levantado de color', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Decoloración', 60, 2);
insert into services values ('3423fc1b-694e-447b-af7e-2a21ccf45e7c', 'Alisado de keratina', 'http://localhost:13169/rest/files/1646673674286_service.png', 'Alisado de Keratina', 175, 1);

insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('5b4dce42-9142-469e-93c0-70ff2a26f03c', '2022-02-13', '10:00:00', '76b2071a-3f97-4666-bd76-3d3d38ca677d', '7dafe5fd-976b-450a-9bab-17ab450a4fff');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('b1d2a72e-ffcb-4458-9351-35dea125b797', '2022-02-24', '11:15:00', '0b1810ec-6191-492c-91b6-04ea0c93dab8', '7dafe5fd-976b-450a-9bab-17ab450a4fff');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('e4781fb4-3be0-4eaf-af1c-3809761f1f21', '2022-02-27', '12:30:00', '9dccf8c2-8a7f-4117-a2ca-ae14c35c9abf', 'baeac544-4bef-40f9-aed3-8730f80f27d8');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('1810996a-3d0f-4576-9d07-4b7fa11a4456', '2022-03-05', '13:45:00', '76b2071a-3f97-4666-bd76-3d3d38ca677d', '7dafe5fd-976b-450a-9bab-17ab450a4fff');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('9e2dcf7f-867f-47f1-9b99-21c12eecf55c', '2022-03-08', '14:00:00', '3a49cf85-3bfa-40e7-9c54-7db0518696ca', 'a6bc5b80-213c-4579-8335-f104d1bbfbb6');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('6e625b74-2725-4d7d-a40a-4a68aead3ae2', '2022-02-13', '15:15:00', 'a6894032-abf6-4f93-b70e-030bb07dc45a', 'baeac544-4bef-40f9-aed3-8730f80f27d8');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('a92cd43d-c68f-4485-9882-8addefbd90bf', '2022-03-03', '16:15:00', 'c1334d57-120b-437b-baef-cf5b5f68cc3e', 'a6bc5b80-213c-4579-8335-f104d1bbfbb6');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('7db8fe32-1c22-4241-965b-f47e491e223c', '2022-03-04', '17:00:00', 'a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1', 'a6bc5b80-213c-4579-8335-f104d1bbfbb6');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('9856be1a-22cd-4c0a-b66f-f31e51117dca', '2022-04-12', '18:00:00', '2e9f78be-6563-42ee-83bb-e8f72c938646', 'a6bc5b80-213c-4579-8335-f104d1bbfbb6');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('242506a8-44a5-4d8a-b19d-2f64d763fe38', '2022-03-21', '19:45:00', 'a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1', 'baeac544-4bef-40f9-aed3-8730f80f27d8');
insert into appointment (id, appointment_date, appointment_time, id_user, id_service) values ('efd584b8-15ae-4743-8a1d-c4b4f6060b86', '2022-02-26', '20:15:00', 'c1334d57-120b-437b-baef-cf5b5f68cc3e', '3423fc1b-694e-447b-af7e-2a21ccf45e7c');

insert into login values ('233149e4-b6f3-4692-ac71-2e8123bc24b2', '2022-02-13 09:57:00.07', '123213412', '76b2071a-3f97-4666-bd76-3d3d38ca677d');
insert into login values ('6403b535-a665-47be-bd1c-491520df59a2', '2022-02-01 09:14:00.04', '814142974', '0b1810ec-6191-492c-91b6-04ea0c93dab8');
insert into login values ('0ff90996-0259-4527-8593-fd4ba236a9a3', '2022-02-20 10:30:00.09', '124771249', '9dccf8c2-8a7f-4117-a2ca-ae14c35c9abf');
insert into login values ('6f09f4a7-f512-47c3-87f4-114dcfd575ff', '2022-02-22 09:45:00.14', '124814771', '76b2071a-3f97-4666-bd76-3d3d38ca677d');
insert into login values ('ed58b76f-9144-4c80-8762-9c4ef3db1e3c', '2022-02-25 09:07:01.07', '252528284', '3a49cf85-3bfa-40e7-9c54-7db0518696ca');
insert into login values ('78593ff2-edc5-4973-b57e-d64650e7eeda', '2022-02-19 08:07:01.07', '258081724', 'a6894032-abf6-4f93-b70e-030bb07dc45a');
insert into login values ('8babf873-55b3-4f1c-b207-1618eec240ea', '2022-02-20 14:07:01.07', '582072077', 'c1334d57-120b-437b-baef-cf5b5f68cc3e');
insert into login values ('6b94ef36-9484-445a-b0e3-e7d065663e5f', '2022-02-05 16:07:01.07', '108737017', 'a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1');
insert into login values ('2bdfc328-77dd-40b9-a479-a3dd03d20373', '2022-02-09 14:07:01.07', '275072041', '2e9f78be-6563-42ee-83bb-e8f72c938646');
insert into login values ('5c19b9c6-7e6b-4a82-bcd5-e3439fb16434', '2022-02-10 12:07:01.07', '012740172', 'a12ce02c-4f2e-4cbf-86b1-db5e1e95fba1');
insert into login values ('f72b1d01-34bd-4761-891a-20230ee6bf1b', '2022-02-12 14:07:01.07', '102740174', 'c1334d57-120b-437b-baef-cf5b5f68cc3e');
