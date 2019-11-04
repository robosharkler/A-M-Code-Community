create table REPLY
(
	ID INTEGER not null,
	QUESTION_ID INTEGER,
	ACCOUNT_ID VARCHAR(100),
	GMT_CREATE BIGINT,
	DESCRIPTION CLOB
);

create unique index REPLY_ID_uindex
	on REPLY (ID);

alter table REPLY
	add constraint REPLY_pk
		primary key (ID);