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
    startTime       time               not null,
    endTime         time               not null,
    maxCollaborator int                not null,
    primary key (id),
    foreign key (location_id) references location (id)
);

create table collaborator
(
    id        int auto_increment not null,
    phone     varchar(11)        not null,
    firstName varchar(20),
    lastName  varchar(20),
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
    s.maxCollaborator - COUNT(a.collaborator_id) AS available_slots,
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

insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('First shift of the day', 'First shift', 1, '12:00:00', '14:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('Second shift of the day', 'Second shift', 1, '14:00:00', '16:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('Third shift of the day', 'Third shift', 1, '16:00:00', '18:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('First shift of the day', 'First shift', 2, '12:00:00', '14:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('Second shift of the day', 'Second shift', 2, '14:00:00', '16:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('Third shift of the day', 'Third shift', 3, '16:00:00', '18:00:00', 5);
insert into shift (description, name, location_id, startTime, endTime, maxCollaborator)
values ('First shift of the day', 'First shift', 4, '12:00:00', '14:00:00', 5);

insert into collaborator (phone, firstName, lastName, age, size, email)
values ('0606060606', 'Il', 'Pirla', '1990-01-01', 'M', 'joekueng05@gmail.com');
insert into collaborator (phone, firstName, lastName, age, size, email)
values ('0606060607', 'Jane', 'Doe', '1990-01-01', 'S', 'jane.doe@gmail.com');
insert into collaborator (phone, firstName, lastName, age, size, email)
values ('0606060608', 'Bob', 'Smith', '1990-01-01', 'M', 'bob.smith@gmail.com');
insert into collaborator (phone, firstName, lastName, age, size, email)
values ('0606060609', 'Alice', 'Johnson', '1990-01-01', 'L', 'alice.johnson@gmail.com');
insert into collaborator (phone, firstName, lastName, age, size, email)
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