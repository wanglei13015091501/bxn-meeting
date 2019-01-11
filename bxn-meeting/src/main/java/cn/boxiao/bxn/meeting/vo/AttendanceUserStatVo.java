package cn.boxiao.bxn.meeting.vo;

import cn.boxiao.bxn.meeting.data.AttendanceUserStatDo;

public class AttendanceUserStatVo {
	private String id;

	private String userId; // 用户ID
	private String userName; // 用户名称
	private String fullName; // 用户全名
	private String uniqueNo; // 教工号/学号
	private int category; // 用户身份
	private String organizationName; // 用户组名称
	private int orgUserNum; // 用户组成员数

	private int needJoinNum; // 应参会次数
	private int normalNum; // 正常次数
	private int lateNum; // 迟到次数
	private int leaveNum; // 早退次数
	private int leaveLateNum; // 迟到且早退次数
	private int unPunchNum; // 未打卡次数
	private int absenceNum; // 缺勤次数
	private int holidayNum; // 请假次数
	private int missingNum; // 漏卡次数

	@Override
	public String toString() {
		return "AttendanceUserStatVo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", fullName="
				+ fullName + ", uniqueNo=" + uniqueNo + ", category=" + category + ", organizationName="
				+ organizationName + ", orgUserNum=" + orgUserNum + ", needJoinNum=" + needJoinNum + ", normalNum="
				+ normalNum + ", lateNum=" + lateNum + ", leaveNum=" + leaveNum + ", leaveLateNum=" + leaveLateNum
				+ ", unPunchNum=" + unPunchNum + ", absenceNum=" + absenceNum + ", holidayNum=" + holidayNum
				+ ", missingNum=" + missingNum + "]";
	}

	public void copyNumsFromDo(AttendanceUserStatDo userStatDo) {
		if (userStatDo.getNeedJoinNum() != null) {
			this.setNeedJoinNum(userStatDo.getNeedJoinNum().intValue());
		}
		if (userStatDo.getNormalNum() != null) {
			this.setNormalNum(userStatDo.getNormalNum().intValue());
		}
		if (userStatDo.getLateNum() != null) {
			this.setLateNum(userStatDo.getLateNum().intValue());
		}
		if (userStatDo.getLeaveNum() != null) {
			this.setLeaveNum(userStatDo.getLeaveNum().intValue());
		}
		if (userStatDo.getLeaveLateNum() != null) {
			this.setLeaveLateNum(userStatDo.getLeaveLateNum().intValue());
		}
		if (userStatDo.getUnPunchNum() != null) {
			this.setUnPunchNum(userStatDo.getUnPunchNum().intValue());
		}
		if (userStatDo.getAbsenceNum() != null) {
			this.setAbsenceNum(userStatDo.getAbsenceNum().intValue());
		}
		if (userStatDo.getHolidayNum() != null) {
			this.setHolidayNum(userStatDo.getHolidayNum().intValue());
		}
		if (userStatDo.getMissingNum() != null) {
			this.setMissingNum(userStatDo.getMissingNum().intValue());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getOrgUserNum() {
		return orgUserNum;
	}

	public void setOrgUserNum(int orgUserNum) {
		this.orgUserNum = orgUserNum;
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

	public int getAbsenceNum() {
		return absenceNum;
	}

	public void setAbsenceNum(int absenceNum) {
		this.absenceNum = absenceNum;
	}

	public int getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(int missingNum) {
		this.missingNum = missingNum;
	}

	public int getHolidayNum() {
		return holidayNum;
	}

	public void setHolidayNum(int holidayNum) {
		this.holidayNum = holidayNum;
	}

}
