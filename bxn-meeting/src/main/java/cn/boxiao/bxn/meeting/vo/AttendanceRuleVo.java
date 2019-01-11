package cn.boxiao.bxn.meeting.vo;

public class AttendanceRuleVo {

	private String id;
	private String ruleName;
	private String ruleValue;
	private String status;
	private String createTime;

	@Override
	public String toString() {
		return "AttendanceRuleVo [id=" + id + ", ruleName=" + ruleName + ", ruleValue=" + ruleValue + ", status="
				+ status + ", createTime=" + createTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
