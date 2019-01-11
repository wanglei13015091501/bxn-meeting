package cn.boxiao.bxn.meeting.data;

import java.util.Date;

public class AttendanceGroupDo {
	private String id;

	private String meetingId; // 会议ID
	private String groupId; // 部门ID

	private int needJoinNum; // 应参会次数
	private int normalNum; // 正常次数
	private int lateNum; // 迟到次数
	private int leaveNum; // 早退次数
	private int leaveLateNum; // 迟到且早退次数
	private int unPunchNum; // 未打卡次数
	private int absenceNum; // 缺勤次数
	private int holidayPubNum; // 请假次数(公假)
	private int holidayPriNum; // 请假次数(私假)
	private int holidaySickNum; // 请假次数(病假)
	private int missingNum; // 漏卡次数

	private String deleted; // 是否删除标识
	private Date createTime; // 创建时间

	@Override
	public String toString() {
		return "AttendanceGroupDo [id=" + id + ", meetingId=" + meetingId + ", groupId=" + groupId + ", needJoinNum="
				+ needJoinNum + ", normalNum=" + normalNum + ", lateNum=" + lateNum + ", leaveNum=" + leaveNum
				+ ", leaveLateNum=" + leaveLateNum + ", unPunchNum=" + unPunchNum + ", absenceNum=" + absenceNum
				+ ", holidayPubNum=" + holidayPubNum + ", holidayPriNum=" + holidayPriNum + ", holidaySickNum="
				+ holidaySickNum + ", missingNum=" + missingNum + ", deleted=" + deleted + ", createTime=" + createTime
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getNeedJoinNum() {
		return needJoinNum;
	}

	public void setNeedJoinNum(int needJoinNum) {
		this.needJoinNum = needJoinNum;
	}

	public int getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(int normalNum) {
		this.normalNum = normalNum;
	}

	public int getLateNum() {
		return lateNum;
	}

	public void setLateNum(int lateNum) {
		this.lateNum = lateNum;
	}

	public int getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(int leaveNum) {
		this.leaveNum = leaveNum;
	}

	public int getLeaveLateNum() {
		return leaveLateNum;
	}

	public void setLeaveLateNum(int leaveLateNum) {
		this.leaveLateNum = leaveLateNum;
	}

	public int getUnPunchNum() {
		return unPunchNum;
	}

	public void setUnPunchNum(int unPunchNum) {
		this.unPunchNum = unPunchNum;
	}

	public int getAbsenceNum() {
		return absenceNum;
	}

	public void setAbsenceNum(int absenceNum) {
		this.absenceNum = absenceNum;
	}

	public int getHolidayPubNum() {
		return holidayPubNum;
	}

	public void setHolidayPubNum(int holidayPubNum) {
		this.holidayPubNum = holidayPubNum;
	}

	public int getHolidayPriNum() {
		return holidayPriNum;
	}

	public void setHolidayPriNum(int holidayPriNum) {
		this.holidayPriNum = holidayPriNum;
	}

	public int getHolidaySickNum() {
		return holidaySickNum;
	}

	public void setHolidaySickNum(int holidaySickNum) {
		this.holidaySickNum = holidaySickNum;
	}

	public int getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(int missingNum) {
		this.missingNum = missingNum;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
