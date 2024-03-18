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
    start_time time not null,
    end_time   time not null,
    max_collaborator int                not null,
    adults_only boolean not null,
    primary key (id),
    foreign key (location_id) references location (id)
);

create table collaborator
(
    id        int auto_increment not null,
    phone varchar(11),
    first_name varchar(20),
    last_name  varchar(20),
    age       date,
    size      varchar(3),
    town     varchar(20),
    email varchar(100) not null,
    primary key (id)

);

create table association
(
    shift_id        int not null,
    collaborator_id int not null,
    status ENUM ('PENDING', 'ACCEPTED', 'REJECTED') not null,
    primary key (shift_id, collaborator_id)
);

CREATE VIEW shift_availability AS
SELECT
    s.id AS shift_id,
    s.location_id,
    (s.max_collaborator - COALESCE(SUM(CASE WHEN a.status != 2 THEN 1 ELSE 0 END), 0)) AS available_slots
FROM shift s
         LEFT JOIN
     association a ON s.id = a.shift_id
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

insert into shift (description, name, location_id, start_time, end_time, max_collaborator, adults_only)
values ('Primo turno del giorno', 'Primo turno', 1, '12:00', '2024-03-09 14:00:00', 5, true),
       ('Secondo turno del giorno', 'Secondo turno', 1, '14:00', '2024-03-09 16:00:00', 5, false),
       ('Terzo turno del giorno', 'Terzo turno', 1, '16:00', '2024-03-09 18:00:00', 5, true),
       ('Primo turno del giorno', 'Primo turno', 2, '12:00', '2024-03-09 14:00:00', 5, true),
       ('Secondo turno del giorno', 'Secondo turno', 2, '14:00', '2024-03-09 16:00:00', 5, false),
       ('Terzo turno del giorno', 'Terzo turno', 3, '16:00', '2024-03-09 18:00:00', 5, false),
       ('Primo turno del giorno', 'Primo turno', 4, '12:00', '2024-03-09 14:00:00', 5, true);


insert into collaborator (phone, first_name, last_name, age, size,town, email) values ('1234567890', 'Mario', 'Rossi', '1990-01-01', 'M', 'Roma', 'mario.rosssssssi@gmail.com'),
('1234567890', 'Luca', 'Bianchi', '1990-01-01', 'M', 'Roma', 'mario.rosssssssi@gmail.com'),
('1234567890', 'Giuseppe', 'Verdi', '1990-01-01', 'M', 'Roma', 'mario.rosssssssi@gmail.com'),
('1234567890', 'Giovanni', 'Battista', '1990-01-01', 'M', 'Roma', 'mario.rosssssssi@gmail.com');

insert into association (shift_id, collaborator_id, status)
values (1, 1, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (1, 2, "ACCEPTED");
insert into association (shift_id, collaborator_id, status)
values (3, 3, "REJECTED");
insert into association (shift_id, collaborator_id, status)
values (2, 4, "PENDING");