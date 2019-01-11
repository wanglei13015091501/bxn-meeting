package cn.boxiao.bxn.meeting.data;

import cn.boxiao.bxn.meeting.base.model.BaseDo;

public class PlaceDo extends BaseDo {

	private String placeNo;

	private String placeName;
	private String description;

	private String status;

	@Override
	public String toString() {
		return "PlaceDo [placeNo=" + placeNo + ", placeName=" + placeName + ", description=" + description + ", status="
				+ status + "]";
	}

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
