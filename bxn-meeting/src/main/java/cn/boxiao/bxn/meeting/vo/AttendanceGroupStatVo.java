/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.vo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月24日
 */
public class AttendanceGroupStatVo {

	private String groupId;
	private String groupName;
	private int needJoinNum; // 参会次数
	private int normalNum; // 正常次数
	private String normalRates; // 全勤率

	@Override
	public String toString() {
		return "AttendanceGroupStatVo [groupId=" + groupId + ", groupName=" + groupName + ", needJoinNum=" + needJoinNum
				+ ", normalNum=" + normalNum + ", normalRates=" + normalRates + "]";
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getNeedJoinNum() {
		return needJoinNum;
	}

	public void setNeedJoinNum(int needJoinNum) {
		this.needJoinNum = needJoinNum;
	}

	public String getNormalRates() {
		return normalRates;
	}

	public void setNormalRates(String normalRates) {
		this.normalRates = normalRates;
	}

	public int getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(int normalNum) {
		this.normalNum = normalNum;
	}

}
