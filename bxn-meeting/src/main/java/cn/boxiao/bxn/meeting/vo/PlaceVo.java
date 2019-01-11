package cn.boxiao.bxn.meeting.vo;

public class PlaceVo {

	private String id;
	private String placeNo;
	private String placeName;
	private String description;
	private String status;
	private String deleted;
	private String createTime;

	@Override
	public String toString() {
		return "PlaceVo [id=" + id + ", placeNo=" + placeNo + ", placeName=" + placeName + ", description="
				+ description + ", status=" + status + ", deleted=" + deleted + ", createTime=" + createTime + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
