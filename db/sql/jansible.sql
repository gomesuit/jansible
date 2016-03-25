SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS apply_history;
DROP TABLE IF EXISTS available_module;
DROP TABLE IF EXISTS choice;
DROP TABLE IF EXISTS environment_variable;
DROP TABLE IF EXISTS role_relation;
DROP TABLE IF EXISTS server_relation;
DROP TABLE IF EXISTS service_group_variable;
DROP TABLE IF EXISTS service_group;
DROP TABLE IF EXISTS environment;
DROP TABLE IF EXISTS file;
DROP TABLE IF EXISTS global_role_file;
DROP TABLE IF EXISTS global_role_relation;
DROP TABLE IF EXISTS global_role_tag_variable;
DROP TABLE IF EXISTS global_role_tag;
DROP TABLE IF EXISTS global_role_template;
DROP TABLE IF EXISTS global_role_variable;
DROP TABLE IF EXISTS global_task_conditional;
DROP TABLE IF EXISTS global_task_detail;
DROP TABLE IF EXISTS global_task;
DROP TABLE IF EXISTS global_role;
DROP TABLE IF EXISTS parameter;
DROP TABLE IF EXISTS task_conditional;
DROP TABLE IF EXISTS task_detail;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS module;
DROP TABLE IF EXISTS role_variable;
DROP TABLE IF EXISTS template;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS server_parameter;
DROP TABLE IF EXISTS server_variable;
DROP TABLE IF EXISTS server;
DROP TABLE IF EXISTS project;




/* Create Tables */

CREATE TABLE apply_history
(
	apply_histroy_id int NOT NULL AUTO_INCREMENT,
	project_name varchar(50) NOT NULL,
	environment_name varchar(80),
	group_name varchar(80),
	server_name varchar(80),
	tag_name varchar(120) NOT NULL,
	tag_comment varchar(256),
	apply_time datetime NOT NULL,
	PRIMARY KEY (apply_histroy_id, project_name)
);


CREATE TABLE available_module
(
	module_name varchar(80) NOT NULL,
	PRIMARY KEY (module_name)
);


CREATE TABLE choice
(
	module_name varchar(80) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	choice_value varchar(80) NOT NULL,
	PRIMARY KEY (module_name, parameter_name, choice_value)
);


CREATE TABLE environment
(
	jenkins_port varchar(10) NOT NULL,
	environment_name varchar(80) NOT NULL,
	PRIMARY KEY (jenkins_port, environment_name)
);


CREATE TABLE environment_variable
(
	jenkins_port varchar(10) NOT NULL,
	environment_name varchar(80) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (jenkins_port, environment_name, variable_name)
);


CREATE TABLE file
(
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	file_name varchar(128) NOT NULL,
	PRIMARY KEY (project_name, role_name, file_name)
);


CREATE TABLE global_role
(
	role_name varchar(30) NOT NULL,
	repository_url varchar(100) NOT NULL,
	PRIMARY KEY (role_name)
);


CREATE TABLE global_role_file
(
	role_name varchar(30) NOT NULL,
	file_name varchar(128) NOT NULL,
	PRIMARY KEY (role_name, file_name)
);


CREATE TABLE global_role_relation
(
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	tag_name varchar(120) NOT NULL,
	PRIMARY KEY (project_name, role_name)
);


CREATE TABLE global_role_tag
(
	role_name varchar(30) NOT NULL,
	tag_name varchar(120) NOT NULL,
	tag_comment varchar(256),
	PRIMARY KEY (role_name, tag_name)
);


CREATE TABLE global_role_tag_variable
(
	role_name varchar(30) NOT NULL,
	tag_name varchar(120) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (role_name, tag_name, variable_name)
);


CREATE TABLE global_role_template
(
	role_name varchar(30) NOT NULL,
	template_name varchar(128) NOT NULL,
	PRIMARY KEY (role_name, template_name)
);


CREATE TABLE global_role_variable
(
	role_name varchar(30) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (role_name, variable_name)
);


CREATE TABLE global_task
(
	task_id int NOT NULL AUTO_INCREMENT,
	role_name varchar(30) NOT NULL,
	module_name varchar(80) NOT NULL,
	description varchar(512),
	sort int NOT NULL,
	PRIMARY KEY (task_id, role_name)
);


CREATE TABLE global_task_conditional
(
	task_id int NOT NULL,
	role_name varchar(30) NOT NULL,
	conditional_name varchar(80) NOT NULL,
	conditional_value varchar(256) NOT NULL,
	PRIMARY KEY (task_id, role_name, conditional_name)
);


CREATE TABLE global_task_detail
(
	task_id int NOT NULL,
	role_name varchar(30) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	parameter_value varchar(256),
	PRIMARY KEY (task_id, role_name, parameter_name)
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
	project_name varchar(50) NOT NULL,
	repository_url varchar(100) NOT NULL,
	jenkins_ip_address varchar(50),
	jenkins_port varchar(10),
	jenkins_job_name varchar(20),
	PRIMARY KEY (project_name)
);


CREATE TABLE role
(
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	PRIMARY KEY (project_name, role_name)
);


CREATE TABLE role_relation
(
	project_name varchar(50) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	role_name varchar(30) NOT NULL,
	role_type enum('project','global') NOT NULL,
	sort int,
	PRIMARY KEY (project_name, environment_name, group_name, role_name)
);


CREATE TABLE role_variable
(
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (project_name, role_name, variable_name)
);


CREATE TABLE server
(
	project_name varchar(50) NOT NULL,
	server_name varchar(80) NOT NULL,
	environment_name varchar(80) NOT NULL,
	PRIMARY KEY (project_name, server_name)
);


CREATE TABLE server_parameter
(
	server_name varchar(80) NOT NULL,
	project_name varchar(50) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	parameter_value varchar(256),
	PRIMARY KEY (server_name, project_name, parameter_name)
);


CREATE TABLE server_relation
(
	jenkins_port varchar(10) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	server_name varchar(80) NOT NULL,
	PRIMARY KEY (jenkins_port, environment_name, group_name, server_name)
);


CREATE TABLE server_variable
(
	server_name varchar(80) NOT NULL,
	project_name varchar(50) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (server_name, project_name, variable_name)
);


CREATE TABLE service_group
(
	jenkins_port varchar(10) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	description varchar(512),
	PRIMARY KEY (jenkins_port, environment_name, group_name)
);


CREATE TABLE service_group_variable
(
	jenkins_port varchar(10) NOT NULL,
	environment_name varchar(80) NOT NULL,
	group_name varchar(80) NOT NULL,
	variable_name varchar(80) NOT NULL,
	value varchar(80),
	PRIMARY KEY (jenkins_port, environment_name, group_name, variable_name)
);


CREATE TABLE task
(
	task_id int NOT NULL AUTO_INCREMENT,
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	module_name varchar(80) NOT NULL,
	description varchar(512),
	sort int NOT NULL,
	PRIMARY KEY (task_id, project_name, role_name)
);


CREATE TABLE task_conditional
(
	task_id int NOT NULL,
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	conditional_name varchar(80) NOT NULL,
	conditional_value varchar(256) NOT NULL,
	PRIMARY KEY (task_id, project_name, role_name, conditional_name)
);


CREATE TABLE task_detail
(
	task_id int NOT NULL,
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	parameter_name varchar(80) NOT NULL,
	parameter_value varchar(256),
	PRIMARY KEY (task_id, project_name, role_name, parameter_name)
);


CREATE TABLE template
(
	project_name varchar(50) NOT NULL,
	role_name varchar(30) NOT NULL,
	template_name varchar(128) NOT NULL,
	PRIMARY KEY (project_name, role_name, template_name)
);



