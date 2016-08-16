create table FriendlyURL (
	uuid_ VARCHAR(75) null,
	friendlyUrlId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	urlTitle VARCHAR(150) null,
	main BOOLEAN
);