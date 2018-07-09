create unique index IX_8971EF10 on AssetAutoTaggerEntry (assetEntryId);
create index IX_357626B3 on AssetAutoTaggerEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9FAEBDF5 on AssetAutoTaggerEntry (uuid_[$COLUMN_LENGTH:75$], groupId);