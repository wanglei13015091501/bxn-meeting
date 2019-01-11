package cn.boxiao.bxn.meeting.data;

public class AttendanceUserStatDo {
	private String id;

	private String userId; // 用户ID
	private String userName; // 用户名称
	private String fullName; // 用户全名
	private String uniqueNo; // 教工号/学号
	private int category; // 用户身份

	private Integer needJoinNum; // 应参会次数
	private Integer normalNum; // 正常次数
	private Integer lateNum; // 迟到次数
	private Integer leaveNum; // 早退次数
	private Integer leaveLateNum; // 迟到且早退次数
	private Integer unPunchNum; // 未打卡次数
	private Integer absenceNum; // 缺勤次数
	private Integer holidayNum; // 请假次数
	private Integer missingNum; // 漏卡次数

	@Override
	public String toString() {
		return "AttendanceUserStatDo [id=" + id + ", userId=" + userId + ", userName=" + userName + ", fullName="
				+ fullName + ", uniqueNo=" + uniqueNo + ", category=" + category + ", needJoinNum=" + needJoinNum
				+ ", normalNum=" + normalNum + ", lateNum=" + lateNum + ", leaveNum=" + leaveNum + ", leaveLateNum="
				+ leaveLateNum + ", unPunchNum=" + unPunchNum + ", absenceNum=" + absenceNum + ", holidayNum="
				+ holidayNum + ", missingNum=" + missingNum + "]";
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Integer getNeedJoinNum() {
		return needJoinNum;
	}

	public void setNeedJoinNum(Integer needJoinNum) {
		this.needJoinNum = needJoinNum;
	}

	public Integer getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getLateNum() {
		return lateNum;
	}

	public void setLateNum(Integer lateNum) {
		this.lateNum = lateNum;
	}

	public Integer getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(Integer leaveNum) {
		this.leaveNum = leaveNum;
	}

	public Integer getLeaveLateNum() {
		return leaveLateNum;
	}

	public void setLeaveLateNum(Integer leaveLateNum) {
		this.leaveLateNum = leaveLateNum;
	}

	public Integer getUnPunchNum() {
		return unPunchNum;
	}

	public void setUnPunchNum(Integer unPunchNum) {
		this.unPunchNum = unPunchNum;
	}

	public Integer getAbsenceNum() {
		return absenceNum;
	}

	public void setAbsenceNum(Integer absenceNum) {
		this.absenceNum = absenceNum;
	}

	public Integer getHolidayNum() {
		return holidayNum;
	}

	public void setHolidayNum(Integer holidayNum) {
		this.holidayNum = holidayNum;
	}

	public Integer getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(Integer missingNum) {
		this.missingNum = missingNum;
	}

}
