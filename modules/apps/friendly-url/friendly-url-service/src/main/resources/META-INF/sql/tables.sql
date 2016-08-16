create table FriendlyURL (
	uuid_ VARCHAR(75) null,
	friendlyUrlId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	urlTitle VARCHAR(150) null,
	main BOOLEAN
);