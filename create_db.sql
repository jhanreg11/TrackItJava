# MADE BY: Jacob Hanson-Regalado
# STUDENT ID: 1732241

create database if not exists trackit;
use trackit;
create user if not exists 'goofy'@'localhost' identified by 'gooberson';
grant all on trackit.* to 'goofy'@'localhost';

drop table if exists entry;
drop table if exists item;
drop table if exists user;
drop procedure if exists generate_entries;

create table user (username varchar(50) unique not null primary key,
    password varchar(100) not null,
    date datetime not null
    );

create table item (id varchar(36) unique  not null primary key,
    userId varchar(50) not null,
    name varchar(50) not null,
    price double not null,
    date datetime not null,

    foreign key (userId)
        references user(username)
        on update cascade
        on delete cascade
    );

create table entry (id varchar(36) unique not null primary key,
    itemId varchar(36) not null,
    userId varchar(50) not null,
    amount double not null,
    units int not null,
    date datetime not null,

    foreign key (itemId)
        references item(id)
        on update cascade
        on delete cascade,

    foreign key (userId)
        references user(username)
        on update cascade
        on delete cascade
    );

/*
 Seed Database for Demo.
 */

insert into user values ('goofy', 'goober', '2019-01-01');
insert into item values ('04a6835b-c1a4-443f-9ab9-8193e3735af7', 'goofy', 'hotdog', '5.75', '2019-01-01');
insert into item values ('a7394a0e-d9aa-4b2e-98cd-42a65ffbec2a', 'goofy', 'sandwich', '6.75', '2019-01-01');
insert into item values ('aa552f3d-3e6c-4e2c-9e4f-2a86f59fe0f2', 'goofy', 'fries', '1.75', '2019-01-01');
insert into item values ('187952df-9410-482e-b526-ccec464e01a8', 'goofy', 'hamburger', '8.75', '2019-01-01');

create procedure generate_entries()
begin
    declare num_rows bigint;
    loop_label: loop
        set num_rows = (select count(id) from entry);
        if  num_rows > 502 then
            leave loop_label;
        end if;

        insert into entry values (uuid(),
            (select id from item order by rand() limit 1),
            'goofy',
            (select rand() * (200) - 75),
            (select floor(rand() * 30)),
            (select date_add('2019-01-01', interval num_rows day))
        );
    end loop loop_label;
end;

call generate_entries();