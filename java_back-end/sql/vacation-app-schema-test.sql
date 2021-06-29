-- create tables and relationships
drop database if exists vacation_app_test;
create database vacation_app_test;

use vacation_app_test;

-- create location
create table location (
	location_id int primary key auto_increment,
	description varchar(50) not null
);

-- create vacation
create table vacation (
	vacation_id int primary key auto_increment,
	start_date date not null,
	end_date date not null,
	description varchar(250) not null,
	leasure_level int not null,
	location_id int not null,
	constraint fk_vacation_location_id
        foreign key (location_id)
        references location(location_id)
);

-- create user_type
create table user_type (
	user_type_id int primary key auto_increment,
	user_type_type varchar(50) not null
);

-- create user
create table user (
	user_id int primary key auto_increment,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	user_name varchar(50) not null,
	password varchar(50) not null,
	user_type_id tinyint not null,
	constraint fk_user_user_type_id
        foreign key (user_type_id)
        references user_type(user_type_id)
);

-- create comment
create table comment (
	comment_id int primary key auto_increment,
	text varchar(250) not null,
	user_id int not null,
	vacation_id int not null,
	constraint fk_comment_user_id
        foreign key (user_id)
        references user(user_id),
	constraint fk_comment_vacation_id
        foreign key (vacation_id)
        references vacation(vacation_id)
);

-- create vacation_user
create table vacation_user (
	vacation_id int not null,
	user_id int not null,
	identifier varchar(50) not null,
	constraint pk_vacation_user
        primary key (user_id, vacation_id),
	constraint fk_vacation_user_user_id
        foreign key (user_id)
        references user(user_id),
	constraint fk_vacation_user_vacation_id
        foreign key (vacation_id)
        references vacation(vacation_id)
	constraint uq_vacation_user_identifier_vacation_id
        unique (identifier, vacation_id)
);
	
use vacation_app_test;
drop procedure if exists set_known_good_state;
-- change default delimiter
delimiter //
create procedure set_known_good_state()
begin
	delete location;
	alter table location auto_increment = 1;
	delete vacation;
	alter table vacation auto_increment = 1;
	delete user_type;
	alter table user_type auto_increment = 1;
	delete user;
	alter user auto_increment = 1;
	delete comment;
	alter comment auto_increment = 1;
	delete vacation_user;
	alter vacation_user auto_increment = 1;
	
	-- inserts
	use vacation_app;

	-- user_type inserts
	insert into user_type
		(user_type_id, user_type_type)
	values
		(0, 'Admin'),
		(1, 'User');
		
	-- location inserts
	insert into location
		(location_id, description)
	values
		(1, 'St.Paul, MN'),
		(2, 'Madison, WI'),
		(3, 'Orlando, FL'),
		(4, 'New York, NY'),
		(5, 'Los Angeles, CA'),
		(6, 'Honolulu, HI');

	-- user inserts
	insert into user
		(user_id, first_name, last_name, user_name, password, user_type_id)
	values
		(1, 'Robert', 'Entenmann', 'robb@gmail.com', 'robert123', 1),
		(2, 'Killian', 'Carlsen-Phelan', 'killian@gmail.com', 'killian123', 0),
		(3, 'Kathryn', 'Rowzee', 'kathryn@gmail.com', 'kathryn123', 1);
		
	-- vacation inserts
	insert into vacation
		(vacation_id, start_date, end_date, description, leasure_level, location_id)
	values
		(1, '2021-07-04', '2021-07-11', 'A trip to the capital of MN', 1, 1),
		(2, '2021-10-31', '2021-11-04', 'Halloween Fun Trip', 1, 2),
		(3, '2021-11-20', '2021-11-29', 'Disney World Tour Trip', 2, 3),
		(4, '2022-01-04', '2022-01-11', 'New Years Trip', 2, 4),
		(5, '2021-07-04', '2021-07-11', 'Hollywood Trip', 3, 5);
		(6, '2021-07-04', '2021-07-11', 'Beaches and Sun Trip', 3, 6);
		
	-- comment inserts
	insert into comment
		(comment_id, text, user_id, vacation_id)
	values
		(1, 'Was a very boring and uneventful trip.', 1, 1),
		(2, 'Had a blast and was a lot of fun.', 2, 3),
		(3, 'Got to lay on the beach and relax.', 3, 6);

	-- vacation_user inserts
	insert into vacation_user
		(vacation_id, user_id)
	select
		vacation.vacation_id,
		user.user_id,
		concat(vacation.vacation_id, '-', user.user_id)
	from vacation
	inner join user
	where user.user_id not in (6, 8)
	and vacation.vacation_id != 2;
end //
-- Change the statement terminator back to the original.
delimiter ;

use vacation_app_test;
-- safe updates still apply in Workbench
-- disable them
set sql_safe_updates = 0;
call set_known_good_state();
-- re-enable
set sql_safe_updates = 1;