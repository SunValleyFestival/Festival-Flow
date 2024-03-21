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
    adults_only boolean not null,
    primary key (id),
    foreign key (day_id) references day (id)
);

create table shift
(
    id              int auto_increment not null,
    description     varchar(1000),
    name            varchar(20)        not null,
    location_id     int,
    start_time       time               not null,
    end_time         time               not null,
    max_collaborator int                not null,
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
    years_experience int,
    email varchar(100) not null,
    town varchar(100),
    primary key (id)

);

create table association
(
    shift_id        int not null,
    collaborator_id int not null,
    comment varchar(1000),
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
values ('Venerdì', 'Primo giorno di festa');
insert into day (name, description)
values ('Sabato', 'Secondo giorno di festa');
insert into day (name, description)
values ('Domenica', 'Terzo giorno di festa');

insert into location (name, description, day_id)
values ('Main Stage', 'Main stage VE', 1);
insert into location (name, description, day_id)
values ('Bar', 'Turno bar VE', 1);
insert into location (name, description, day_id)
values ('Cassa', 'Cassa VE', 1);
insert into location (name, description, day_id)
values ('Bicchieri', 'Raccolta bicchieri VE', 1);
insert into location (name, description, day_id)
values ('Rifornimento', 'Rifornimento bar VE', 1);

insert into location (name, description, day_id)
values ('Main Stage', 'Main stage SA', 2);
insert into location (name, description, day_id)
values ('Bar', 'Turno bar SA', 2);
insert into location (name, description, day_id)
values ('Cassa', 'Cassa SA', 2);
insert into location (name, description, day_id)
values ('Bicchieri', 'Raccolta bicchieri SA', 2);
insert into location (name, description, day_id)
values ('Rifornimento', 'Rifornimento bar SA', 2);


insert into location (name, description, day_id)
values ('Main Stage', 'Main stage DO', 3);
insert into location (name, description, day_id)
values ('Bar', 'Turno bar DO', 3);
insert into location (name, description, day_id)
values ('Cassa', 'Cassa DO', 3);
insert into location (name, description, day_id)
values ('Bicchieri', 'Raccolta bicchieri DO', 3);
insert into location (name, description, day_id)
values ('Rifornimento', 'Rifornimento bar DO', 3);



insert into shift (name, description, location_id, start_time, end_time, max_collaborator, adults_only)
values ('Turno 1', 'Primo turno', 1, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 1, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 1, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 2, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 2, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 2, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 3, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 3, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 3, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 4, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 4, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 4, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 5, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 5, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 5, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 6, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 6, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 6, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 7, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 7, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 7, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 8, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 8, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 8, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 9, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 9, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 9, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 10, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 10, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 10, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 11, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 11, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 11, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 12, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 12, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 12, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 13, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 13, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 13, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 14, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 14, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 14, '16:00', '18:00', 5, true),

       ('Turno 1', 'Primo turno', 15, '12:00', '14:00', 5, true),
       ('Turno 2', 'Secondo turno', 15, '14:00', '16:00', 5, false),
       ('Turno 3', 'Terzo turno', 15, '16:00', '18:00', 5, true);



insert into collaborator (phone, first_name, last_name, age, size,years_experience, email, town)
values ('0606060607', 'Jane', 'Doe', '1990-01-01', 'S', 2,'jane.doe@gmail.com', 'Serravalle');
insert into collaborator (phone, first_name, last_name, age,size, years_experience,email, town)
values ('0606060608', 'Bob', 'Smith', '1990-01-01', 'M',3, 'bob.smith@gmail.com', 'Blenio');
insert into collaborator (phone, first_name, last_name, age, size,years_experience, email, town)
values ('0606060609', 'Alice', 'Johnson', '1990-01-01', 'L', 0,'alice.johnson@gmail.com', 'Biasca');
insert into collaborator (phone, first_name, last_name, age, size,years_experience, email, town)
values ('0606060610', 'Charlie', 'Brown', '1990-01-01', 'XL',2, 'charlie.brown@gmail.com', 'Bellinzona');
insert into collaborator (phone, first_name, last_name, age, size,town,years_experience, email) values ('1234567890', 'Mario', 'Rossi', '1990-01-01', 'M', 'Roma',2, 'mario.rosssssssi@gmail.com'),
                                                                                                       ('1234567890', 'Luca', 'Bianchi', '1990-01-01', 'M', 'Roma',2, 'mario.rosssssssi@gmail.com'),
                                                                                                       ('1234567890', 'Giuseppe', 'Verdi', '1990-01-01', 'M', 'Roma',1, 'mario.rosssssssi@gmail.com'),
                                                                                                       ('1234567890', 'Giovanni', 'Battista', '1990-01-01', 'M', 'Roma',0, 'mario.rosssssssi@gmail.com'),
                                                                                                       ('1234567890', 'Giovanni', 'Battista', '1990-01-01', 'M', 'Roma',0, 'ciao.ciao@gmail.com'),
                                                                                                       ('1234567890', 'Giovanni', 'Battista', '1990-01-01', 'M', 'Roma',0, '2ij.soijd@gmail.com');



insert into association (shift_id, collaborator_id, status)
values (1, 1, 1);
insert into association (shift_id, collaborator_id, status)
values (4, 2, 1);
insert into association (shift_id, collaborator_id, status)
values (7, 3, 2);
insert into association (shift_id, collaborator_id, status)
values (10, 4, 2);

insert into association (shift_id, collaborator_id, status)
values (1, 7, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (1, 2, "ACCEPTED");
insert into association (shift_id, collaborator_id, status)
values (3, 3, "REJECTED");
insert into association (shift_id, collaborator_id, status)
values (2, 4, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (2, 5, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (5, 6, "PENDING");

insert into association (shift_id, collaborator_id, status)
values (1, 5, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (1, 6, "ACCEPTED");
insert into association (shift_id, collaborator_id, status)
values (3, 9, "REJECTED");
insert into association (shift_id, collaborator_id, status)
values (2, 2, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (4, 5, "PENDING");
insert into association (shift_id, collaborator_id, status)
values (9, 6, "ACCEPTED");