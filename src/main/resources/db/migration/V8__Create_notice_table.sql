
create table NOTICE
(
	ID INTEGER auto_increment,
	ACCOUNT_ID VARCHAR(100),
	QUESTION_ID INTEGER,
	VISITED BOOLEAN
);

create unique index NOTICE_ID_uindex
	on NOTICE (ID);

alter table NOTICE
	add constraint NOTICE_pk
		primary key (ID);

