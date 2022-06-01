

-- Crear base de datos de Todo

drop database if exists todo;

create database todo;

use todo;

-- Crear tabla todorecord

create table todorecord(
    tid varchar(50) primary key,
    title varchar(200) not null,
    description varchar(255),
    -- state varchar(100) not null default 'new',
    state enum('new', 'in_progress', 'blocked', 'finished') not null,
    startdate timestamp not null,
    enddate timestamp
);



-- ------------------------------------------
-- Procedimientos almacenados
-- ------------------------------------------


delimiter $$

create procedure create_todo(
	in ptid varchar(50), 
    in ptitle varchar(200),
    in pdescription varchar(255),
	in pstate varchar(20),
    in pstartdate timestamp,
    in penddate timestamp
)
begin
	
    if penddate is null then
		set penddate = now();
    end if;
    
	insert into todorecord(tid, title, description, state, startdate, enddate) values (ptid, ptitle, pdescription, pstate, pstartdate, penddate);
    
end$$


delimiter ;


call create_todo('todo-1', 'Mi primer todo', 'este es mi primer todo', 'new', now(), null);





insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-1', 'Mi primer todo', 'este es mi primer todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-2', 'Mi segundo todo', 'este es mi segundo todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-3', 'Mi tercer todo', 'este es mi tercer todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-4', 'Mi cuarto todo', 'este es mi cuarto todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-5', 'Mi quinto todo', 'este es mi quinto todo', 'blocked', now(), now());



-- CRUD
-- Create (inserción), Read (select), Update (update), Delete (delete)


select * from todorecord where state = 'blocked';


select * from todorecord;


delete from todorecord where tid = 'todo-1';

select tid, title, description, state, startdate, enddate from todorecord where tid = 'todo-4'


select * from todorecord where title like '%cuarto%';
