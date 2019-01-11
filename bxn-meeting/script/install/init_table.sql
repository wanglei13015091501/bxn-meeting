CREATE DATABASE IF NOT EXISTS meeting DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

use meeting;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `meeting_attendance_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_meeting`;
CREATE TABLE `meeting_attendance_meeting` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `meeting_name` varchar(256) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议名称',
  `place_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议地点id',
  `description` varchar(512) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注/描述',
  `meeting_type_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议类型的id',
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `cycling` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '循环标记(0：不循环，1：每天， 2:每周， 3:每月)',
  `creator_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '创建人id',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(0-未开始,1-已开始,2-已结束)',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议明细表';

-- ----------------------------
--  Table structure for `meeting_attendance_organizer`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_organizer`;
CREATE TABLE `meeting_attendance_organizer` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `meeting_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议ID',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户ID',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议组织人员明细表';

-- ----------------------------
--  Table structure for `meeting_attendance_place`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_place`;
CREATE TABLE `meeting_attendance_place` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `place_no` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议室编码',
  `place_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '会议室名称',
  `description` varchar(512) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '描述',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(0-启用,1-禁用)',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议地点表';

-- ----------------------------
--  Table structure for `meeting_type`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_type`;
CREATE TABLE `meeting_type` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `type_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '考勤类型名称',
  `group_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '部门分类',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `creator_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议类型表';

-- ----------------------------
--  Table structure for `meeting_group`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_group`;
CREATE TABLE `meeting_group` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `order_no` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '排序号',
  `group_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '部门名称或部门类型名称',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '上级部门标识(0:该记录为部门类型，其他值:该记录是所属部门类型的ID)',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(0-启用,1-禁用)',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `creator_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部门类型/部门表';

-- ----------------------------
--  Table structure for `meeting_group_user`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_group_user`;
CREATE TABLE `meeting_group_user` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户id',
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名称',
  `group_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '所属部门ID',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `creator_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部门用户表';

-- ----------------------------
--  Table structure for `meeting_attendance_rule`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_rule`;
CREATE TABLE `meeting_attendance_rule` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `rule_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规则名称',
  `rule_value` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规则的值',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(0-启用,1-禁用)',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='考勤规则表';

-- ----------------------------
--  Table structure for `meeting_attendance_log`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_log`;
CREATE TABLE `meeting_attendance_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名称',
  `meeting_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '会议ID',
  `device_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '打卡设备ID',
  `clock_time` datetime DEFAULT NULL COMMENT '打卡时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议考勤日志表';

-- ----------------------------
--  Table structure for `meeting_attendance_user`
-- ----------------------------
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_user`;
CREATE TABLE `meeting_attendance_user` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `meeting_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '会议ID',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户id',
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名称',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间',
  `logout_time` datetime DEFAULT NULL COMMENT '签退时间',
  `modify_sign_time` datetime DEFAULT NULL COMMENT '修改后的签到时间',
  `modify_logout_time` datetime DEFAULT NULL COMMENT '修改后的签退时间',
  `description` varchar(512) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注',
  `status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)',
  `modify_status` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户修改后的状态(0-正常,1-迟到,2-早退,3-缺勤,4-漏卡,5-公假,6-私假,7-病假)',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议考勤人员明细表';

-- ----------------------------
--  Table structure for `meeting_attendance_group`
-- ----------------------------
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attendance_group`;
CREATE TABLE `meeting_attendance_group` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `meeting_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '会议ID',
  `group_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '部门id',
  `need_join_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '应到人数',
  `normal_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '正常人数',
  `late_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '迟到人数',
  `leave_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '早退人数',
  `leave_late_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '迟到且早退人数',
  `un_punch_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '未打卡人数',
  `absence_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '缺勤人数',
  `holiday_pub_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '请假次数(私假)',
  `holiday_pri_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '请假次数(公假)',
  `holiday_sick_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '请假次数(病假)',
  `missing_num` int COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '漏卡次数',
  `deleted` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '删除标记(0：正常，1：删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会议考勤部门统计表';