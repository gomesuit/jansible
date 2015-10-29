SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS choice;
DROP TABLE IF EXISTS role_relation;
DROP TABLE IF EXISTS server;
DROP TABLE IF EXISTS service_group;
DROP TABLE IF EXISTS environment;
DROP TABLE IF EXISTS file;
DROP TABLE IF EXISTS parameter;
DROP TABLE IF EXISTS task_detail;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS module;
DROP TABLE IF EXISTS template;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS variable;
DROP TABLE IF EXISTS project;




/* Create Tables */

CREATE TABLE choice
(
	module_name varchar(80) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	choice_value varchar(80) NOT NULL,
	PRIMARY KEY (module_name, parameter_name, choice_value)
);


CREATE TABLE environment
(
	project_name varchar(80) NOT NULL,
	environment_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name, environment_name)
);


CREATE TABLE file
(
	project_name varchar(80) NOT NULL,
	role_name varchar(80) NOT NULL,
	file_name varchar(256) NOT NULL,
	PRIMARY KEY (project_name, role_name, file_name)
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
	required enum('yes','no'),
	defautl_value varchar(80),
	description varchar(512),
	is_free_form enum('true','false'),
	PRIMARY KEY (module_name, parameter_name)
);


CREATE TABLE project
(
	project_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name)
);


CREATE TABLE role
(
	project_name varchar(80) NOT NULL,
	role_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name, role_name)
);


CREATE TABLE role_relation
(
	project_name varchar(80),
	environment_name varchar(80),
	group_name varchar(80),
	role_name varchar(80),
	sort int
);


CREATE TABLE server
(
	project_name varchar(80) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	server_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name, environment_name, group_name, server_name)
);


CREATE TABLE service_group
(
	project_name varchar(80) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name, environment_name, group_name)
);


CREATE TABLE task
(
	task_id int NOT NULL AUTO_INCREMENT,
	project_name varchar(80) NOT NULL,
	role_name varchar(80) NOT NULL,
	module_name varchar(80) NOT NULL,
	description varchar(512),
	free_form varchar(512),
	sort int NOT NULL,
	PRIMARY KEY (task_id, project_name, role_name)
);


CREATE TABLE task_detail
(
	task_id int NOT NULL,
	project_name varchar(80) NOT NULL,
	role_name varchar(80) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	parameter_value varchar(256),
	PRIMARY KEY (task_id, project_name, role_name, parameter_name)
);


CREATE TABLE template
(
	project_name varchar(80) NOT NULL,
	role_name varchar(80) NOT NULL,
	template_name varchar(256) NOT NULL,
	PRIMARY KEY (project_name, role_name, template_name)
);


CREATE TABLE variable
(
	project_name varchar(80) NOT NULL,
	target enum('project','environment','server_group','server','role') NOT NULL,
	target_name varchar(80) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (project_name, target, target_name, variable_name)
);



