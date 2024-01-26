drop database if exists FestivalFLow;
create database FestivalFlow;
use FestivalFlow;

create table day(
    id int auto_increment,
    name varchar(10),
    description varchar(200),
    primary key (id)
);

create table location
(
    id          int auto_increment not null,
    name        varchar(20)        not null,
    description varchar(1000),
    day_id int,
    primary key (id),
    foreign key (day_id) references day(id)
);

create table shift
(
    id              int auto_increment not null,
    description     varchar(1000),
    name            varchar(20)        not null,
    location_id        int,
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
    request varchar(1000),
    collaborator_friend_id int,
##maybe use telephone or username
    primary key (shift_id, collaborator_id)
)