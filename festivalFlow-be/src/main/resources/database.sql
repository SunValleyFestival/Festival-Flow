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
    day_id      int,
    primary key (id),
    foreign key (day_id) references day (id)
);

create table shift
(
    id              int auto_increment not null,
    description     varchar(1000),
    name            varchar(20)        not null,
    location_id     int,
    time            time               not null,
    day             int                not null,
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
    primary key (id)

);

create table association
(
    shift_id        int not null,
    collaborator_id int not null,
    status          int not null,
    primary key (shift_id, collaborator_id)
);

insert into day (name, description)
values ('Friday', 'First day of the festival');
insert into day (name, description)
values ('Saturday', 'Second day of the festival');
insert into day (name, description)
values ('Sunday', 'Third day of the festival');

insert into location (name, description, day_id)
values ('Main Stage', 'Main stage of the festival', 1);
insert into location (name, description, day_id)
values ('Second Stage', 'Second stage of the festival', 2);
insert into location (name, description, day_id)
values ('Third Stage', 'Third stage of the festival', 2);
insert into location (name, description, day_id)
values ('Fourth Stage', 'Fourth stage of the festival', 3);
insert into location (name, description, day_id)
values ('Main Stage', 'Main stage of the festival', 1);

insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('First shift of the day', 'First shift', 1, '12:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('Second shift of the day', 'Second shift', 1, '14:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('Third shift of the day', 'Third shift', 1, '16:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('First shift of the day', 'First shift', 2, '12:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('Second shift of the day', 'Second shift', 2, '14:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('Third shift of the day', 'Third shift', 3, '16:00:00', 1, 5);
insert into shift (description, name, location_id, time, day, maxCollaborator)
values ('First shift of the day', 'First shift', 4, '12:00:00', 1, 5);

insert into collaborator (phone, firstName, lastName, age, size)
values ('0606060606', 'John', 'Doe', '1990-01-01', 'M');
insert into collaborator (phone, firstName, lastName, age, size)
values ('0606060607', 'Jane', 'Doe', '1990-01-01', 'S');
insert into collaborator (phone, firstName, lastName, age, size)
values ('0606060608', 'John', 'Smith', '1990-01-01', 'L');
insert into collaborator (phone, firstName, lastName, age, size)
values ('0606060609', 'Jane', 'Smith', '1990-01-01', 'M');
insert into collaborator (phone, firstName, lastName, age, size)
values ('0606060610', 'John', 'Doe', '1990-01-01', 'XL');

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