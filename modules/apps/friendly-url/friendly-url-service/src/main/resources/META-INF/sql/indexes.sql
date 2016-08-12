create index IX_5ADBE453 on FriendlyURL (companyId, groupId, classNameId, classPK, friendlyUrl[$COLUMN_LENGTH:150$]);
create index IX_97417FEE on FriendlyURL (companyId, groupId, classNameId, classPK, main);
create index IX_6EAEBA52 on FriendlyURL (companyId, groupId, classNameId, friendlyUrl[$COLUMN_LENGTH:150$]);
create index IX_BBBC6ADE on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1C7E10E0 on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);