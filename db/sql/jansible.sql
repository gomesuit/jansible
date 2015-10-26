SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS choices;
DROP TABLE IF EXISTS parameter;
DROP TABLE IF EXISTS module;




/* Create Tables */

CREATE TABLE choices
(
	module_name varchar(80) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	choice_value varchar(80) NOT NULL,
	PRIMARY KEY (module_name, parameter_name, choice_value)
);


CREATE TABLE module
(
	module_name varchar(80) NOT NULL,
	description varchar(512),
	PRIMARY KEY (module_name)
);


CREATE TABLE parameter
(
	module_name varchar(80) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	added_version varchar(30),
	defautl_value varchar(80),
	description varchar(512),
	is_free_form enum('true','false'),
	PRIMARY KEY (module_name, parameter_name)
);



