
BEGIN;
INSERT INTO `meeting_attendance_rule` 
(`id`,`rule_name`,`rule_value`,`status`,`create_time`) VALUES 
('1', 'normalSign', '20', '0', '2017-03-08 09:55:17'), 
('2', 'normalLogout', '30', '0', '2017-03-08 09:55:37'), 
('3', 'late', '15', '0', '2017-03-08 10:01:26'), 
('4', 'leave', '20', '0', '2017-03-08 10:02:19'), 
('5', 'notice', '10', '0', '2017-03-08 10:03:00'), 
('6', 'noClock', '10', '0', '2017-03-08 10:04:03'), 
('7', 'oneClock', '10', '0', '2017-03-08 10:07:34'), 
('8', 'timeClock', '30', '0', '2017-03-08 10:07:46'),
('9', 'onlySign', '10', '0', '2017-03-08 10:07:56'),
('10', 'onlyLogout', '10', '0', '2017-03-08 10:08:08'), 
('11', 'maxMinuteLate', '30', '0', '2017-03-08 10:08:19'), 
('12', 'maxMinuteLeave', '30', '0', '2017-03-08 10:08:42');
COMMIT;

BEGIN;
INSERT INTO `meeting_type` 
(`id`,`type_name`,`group_id`,`deleted`,`creator_id`,`create_time`,`del_time`) VALUES 
('1', '一卡通考勤会议', '1', '0', '', '2017-03-08 09:55:17',null);
COMMIT;

BEGIN;
INSERT INTO `meeting_group` 
(`id`,`order_no`,`group_name`,`parent_id`,`status`,`deleted`,`creator_id`,`create_time`,`del_time`) VALUES 
('1', '10', '一卡通考勤部门分组', '0', '0', '0', '', '2017-03-08 09:55:17', null);
COMMIT;


