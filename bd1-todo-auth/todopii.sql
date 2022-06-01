
drop database todopii;
create database todopii;

use todopii;


create table clients(
client_id varchar(50) primary key,
client_secret varchar(50) not null
);

create table sessions(
session_id varchar(50) primary key,
client_id varchar (50),
createdat timestamp not null,
foreign key(client_id) references clients(client_id)
);

insert into clients values('cliente-1', 'clientepass');
insert into sessions values ('sesion-1', 'cliente-1', CURRENT_TIMESTAMP);

-- Procedimientos almacenados

drop procedure if exists create_session;
delimiter $$
create procedure create_session(in client_id_param varchar(50))
begin 
	declare client_id_exists bool;
    declare session_exists bool;
    declare session_diff int;
    
    select if(count(*) = 1, true, false) into client_id_exists from clients where client_id = client_id_param;
    
    if client_id_exists = true then
		select if (count(*) = 1, true, false) as session_exists from sessions where session_id = client_id_param;
        if session_exists then
			select minute(timediff(utc_timestamp(), createdat)) into session_diff from sessions where client_id = client_id_param;
            if session_diff <= 30 then
				select 'cliente existe, sesion existe, sesion activa';
                select client_id, session_id, createdat, if(minute(timediff(utc_timestamp(), createdat
                )) <= 30, "ACTIVE", "INACTIVE") as sessionStatus from sessions where client_id = client_id_param;
			else 
				start transaction;
				 update sessions set createdat = utc_timestamp() where client_id = client_id_param;
				commit;
                 select client_id, session_id, createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, "ACTIVE", "INACTIVE") as sessionStatus from sessions where client_id = client_id_param;
			end if;
		else
			start transaction;
				insert into sessions(client_id, session_id, createdat) values (client_id_param, uuid(), utc_timestamp());
                select client_id, session_id, createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, "ACTIVE", "INACTIVE") as sessionStatus from sessions where client_id = client_id_param;
			commit;
        end if;
	else
		select 'no existe';
	end if;
end;
$$

delimiter ;


drop procedure if exists validate_session;
delimiter $$
create procedure validate_session(in session_id_param varchar(50))
begin 
    declare session_exists bool;
    declare session_diff int;

	select if (count(*) = 1, true, false) into session_exists from sessions where session_id = session_id_param;
    select session_exists;
	if session_exists then
		select minute(timediff(utc_timestamp(), createdat)) into session_diff from sessions where session_id = session_id_param;
		if session_diff <= 30 then
			select 'Sesion existe, valida';
			select client_id, session_id, createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, "ACTIVE", "INACTIVE") as sessionStatus from sessions where session_id = session_id_param;
		else 
			select 'Sesion existe, no valida';
			select client_id, session_id, createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, "ACTIVE", "INACTIVE") as sessionStatus from sessions where session_id = session_id_param;
		end if;
	else
		select 'Sesion no existe';
	end if;
end;
$$

delimiter ;
    

