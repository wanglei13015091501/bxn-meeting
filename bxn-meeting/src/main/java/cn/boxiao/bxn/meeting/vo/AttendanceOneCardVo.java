package cn.boxiao.bxn.meeting.vo;

public class AttendanceOneCardVo {

	private String id;
	private String userName;
	private String fullName;
	private String uniqueNo;
	private String category;
	private String recognition;
	private String deviceId;
	private String recordDate;

	@Override
	public String toString() {
		return "AttendanceCardVo [id=" + id + ", userName=" + userName + ", fullName=" + fullName + ", uniqueNo="
				+ uniqueNo + ", category=" + category + ", recognition=" + recognition + ", deviceId=" + deviceId
				+ ", recordDate=" + recordDate + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

}
