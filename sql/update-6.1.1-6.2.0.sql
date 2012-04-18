alter table Contact_ add classNameId LONG;
alter table Contact_ add classPK LONG;
alter table Contact_ add emailAddress VARCHAR(75) null;

drop table Groups_Permissions;

drop table OrgGroupPermission;

drop table Permission_;

drop table Resource_;

drop table ResourceCode;

drop table Roles_Permissions;

alter table SocialActivityCounter add active_ BOOLEAN;

COMMIT_TRANSACTION;

update SocialActivityCounter set active_ = TRUE;

create table TrashEntry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	typeSettings TEXT null,
	status INTEGER
);

drop table Users_Permissions;