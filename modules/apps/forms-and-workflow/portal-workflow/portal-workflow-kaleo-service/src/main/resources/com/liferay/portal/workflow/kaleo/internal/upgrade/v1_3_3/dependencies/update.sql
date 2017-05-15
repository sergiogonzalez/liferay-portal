drop table KaleoDefinitionVersion;

create table KaleoDefinitionVersion (
	kaleoDefinitionVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(200) null,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(200) null,
	title STRING null,
	description STRING null,
	content TEXT null,
	version VARCHAR(75) null,
	status INTEGER
);

create unique index IX_AE02DCC on KaleoDefinitionVersion (companyId, name[$COLUMN_LENGTH:200$], version[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;