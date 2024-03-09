drop database if exists FestivalFLow;
create database FestivalFlow;
use FestivalFlow;

create table day
(
    id          int auto_increment,
    name        varchar(10),
    description varchar(200),
    primary key (id)
);

create table location
(
    id          int auto_increment not null,
    name        varchar(20)        not null,
    description varchar(1000),
    image  blob,
    day_id int,
    primary key (id),
    foreign key (day_id) references day (id)
);

create table shift
(
    id              int auto_increment not null,
    description     varchar(1000),
    name            varchar(20)        not null,
    location_id     int,
    start_time       date               not null,
    end_time         date               not null,
    max_collaborator int                not null,
    primary key (id),
    foreign key (location_id) references location (id)
);

create table collaborator
(
    id        int auto_increment not null,
    phone     varchar(11)        not null,
    first_name varchar(20),
    last_name  varchar(20),
    age       date,
    size      varchar(3),
    email     varchar(100),
    primary key (id)

);

create table association
(
    shift_id        int not null,
    collaborator_id int not null,
    status          int not null,
    primary key (shift_id, collaborator_id)
);

CREATE VIEW shift_availability AS
SELECT
    s.id AS shift_id,
    s.max_collaborator - COUNT(a.collaborator_id) AS available_slots,
    s.location_id as location_id
FROM shift s
         LEFT JOIN association a ON s.id = a.shift_id
GROUP BY s.id;

insert into day (name, description)
values ('Friday', 'First day of the festival');
insert into day (name, description)
values ('Saturday', 'Second day of the festival');
insert into day (name, description)
values ('Sunday', 'Third day of the festival');

insert into location (name, description, day_id)
values ('Main Stage', 'Main stage of the festival', 1);
insert into location (name, description, day_id)
values ('Second Stage', 'Second stage of the festival', 1);
insert into location (name, description, day_id)
values ('Third Stage', 'Third stage of the festival', 2);
insert into location (name, description, day_id)
values ('Fourth Stage', 'Fourth stage of the festival', 3);

insert into shift (description, name, location_id, start_time, end_time, max_collaborator)
values
    ('Primo turno del giorno', 'Primo turno', 1, '2024-03-09 12:00:00', '2024-03-09 14:00:00', 5),
    ('Secondo turno del giorno', 'Secondo turno', 1, '2024-03-09 14:00:00', '2024-03-09 16:00:00', 5),
    ('Terzo turno del giorno', 'Terzo turno', 1, '2024-03-09 16:00:00', '2024-03-09 18:00:00', 5),
    ('Primo turno del giorno', 'Primo turno', 2, '2024-03-09 12:00:00', '2024-03-09 14:00:00', 5),
    ('Secondo turno del giorno', 'Secondo turno', 2, '2024-03-09 14:00:00', '2024-03-09 16:00:00', 5),
    ('Terzo turno del giorno', 'Terzo turno', 3, '2024-03-09 16:00:00', '2024-03-09 18:00:00', 5),
    ('Primo turno del giorno', 'Primo turno', 4, '2024-03-09 12:00:00', '2024-03-09 14:00:00', 5);


insert into collaborator (phone, first_name, last_name, age, size, email)
values ('0606060606', 'Il', 'Pirla', '1990-01-01', 'M', 'joekueng05@gmail.com');
insert into collaborator (phone, first_name, last_name, age, size, email)
values ('0606060607', 'Jane', 'Doe', '1990-01-01', 'S', 'jane.doe@gmail.com');
insert into collaborator (phone, first_name, last_name, age, size, email)
values ('0606060608', 'Bob', 'Smith', '1990-01-01', 'M', 'bob.smith@gmail.com');
insert into collaborator (phone, first_name, last_name, age, size, email)
values ('0606060609', 'Alice', 'Johnson', '1990-01-01', 'L', 'alice.johnson@gmail.com');
insert into collaborator (phone, first_name, last_name, age, size, email)
values ('0606060610', 'Charlie', 'Brown', '1990-01-01', 'XL', 'charlie.brown@gmail.com');

insert into association (shift_id, collaborator_id, status)
values (1, 1, 1);
insert into association (shift_id, collaborator_id, status)
values (1, 2, 1);
insert into association (shift_id, collaborator_id, status)
values (3, 3, 1);
insert into association (shift_id, collaborator_id, status)
values (2, 4, 1);
insert into association (shift_id, collaborator_id, status)
values (2, 5, 1);