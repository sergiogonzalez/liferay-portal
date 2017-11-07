create table ReadingTimeEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	readingTimeInSeconds LONG
);