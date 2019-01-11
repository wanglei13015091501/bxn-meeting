package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class AttendanceRuleDo extends BaseDo {

	private String ruleName;
	private String ruleValue;

	private String status;

	@Override
	public String toString() {
		return "AttendanceRuleDo [ruleName=" + ruleName + ", ruleValue=" + ruleValue + ", status=" + status + "]";
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

}
