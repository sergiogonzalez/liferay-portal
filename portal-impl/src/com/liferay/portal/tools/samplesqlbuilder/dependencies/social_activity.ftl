<#setting number_format = "0">

insert into SocialActivity values (${socialActivity.activityId}, ${socialActivity.groupId}, ${socialActivity.companyId}, ${socialActivity.userId}, ${stringUtil.valueOf(dateUtil.newTime())}, 0, ${socialActivity.classNameId}, ${socialActivity.classPK}, 1, '', 0);