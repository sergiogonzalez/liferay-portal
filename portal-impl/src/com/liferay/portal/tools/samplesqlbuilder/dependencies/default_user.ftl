<#-- Default user -->

${sampleSQLBuilder.insertUser(null, null, dataFactory.defaultUser)}

<#-- Guest user -->

<#assign user = dataFactory.guestUser>

<#assign userGroup = dataFactory.newGroup(user)>

${sampleSQLBuilder.insertGroup(userGroup, [])}

<#assign groupIds = [dataFactory.guestGroup.groupId]>
<#assign roleIds = [dataFactory.administratorRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#-- Sample user -->

<#assign user = dataFactory.sampleUser>

<#assign sampleUserId = user.userId>

<#assign userGroup = dataFactory.newGroup(user)>

${sampleSQLBuilder.insertGroup(userGroup, [dataFactory.newLayout(userGroup.groupId, "home", "", "33,")])}

<#assign groupIds = 1..maxGroupCount>
<#assign roleIds = [dataFactory.administratorRole.roleId, dataFactory.powerUserRole.roleId, dataFactory.userRole.roleId]>

${sampleSQLBuilder.insertUser(groupIds, roleIds, user)}

<#list groupIds as groupId>
	<#assign blogsStatsUser = dataFactory.newBlogsStatsUser(groupId)>

	insert into BlogsStatsUser values (${blogsStatsUser.statsUserId}, ${blogsStatsUser.groupId}, ${blogsStatsUser.companyId}, ${blogsStatsUser.userId}, ${blogsStatsUser.entryCount}, '${dataFactory.getDateString(blogsStatsUser.lastPostDate)}', ${blogsStatsUser.ratingsTotalEntries}, ${blogsStatsUser.ratingsTotalScore}, ${blogsStatsUser.ratingsAverageScore});

	<#assign mbStatsUser = dataFactory.newMBStatsUser(groupId)>

	insert into MBStatsUser values (${mbStatsUser.statsUserId}, ${mbStatsUser.groupId}, ${mbStatsUser.userId}, ${mbStatsUser.messageCount}, '${dataFactory.getDateString(mbStatsUser.lastPostDate)}');
</#list>