use bxn;

/* 系统权限 */
insert  into sys_permission(id,comments,permNameEn,permNameZh,bizsystemName,createdDate,lastUpdated,category) values
(28401,'会议考勤系统管理权限','PERM_MEETING_ADMIN','会议考勤系统管理权限','BXN-MEETING',Now(),Now(),'会议考勤系统'),
(28402,'会议考勤系统组织者权限','PERM_MEETING_OWNER','会议考勤系统组织者权限','BXN-MEETING',Now(),Now(),'会议考勤系统');

/* 系统角色 */
insert into sys_role (id,comments,roleNameEn,roleNameZh,systemRole,createdDate,lastUpdated) values
(28401,'会议考勤系统管理角色','PERM_MEETING_ADMIN','角色适用于赋予用户会议考勤系统管理权限',1,Now(),Now()),
(28402,'会议考勤系统组织者角色','PERM_MEETING_OWNER','角色适用于赋予用户会议考勤系统组织者权限',1,Now(),Now());

/* 系统角色权限 */
insert  into sys_role_permission(id,createdDate,lastUpdated,role_id,permission_id) values
(28401,Now(),Now(),28401,28401),
(28402,Now(),Now(),28402,28402);
