package com.batchMesComDK.entity;

public class FeedIssusBody {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}
	public String getFeedportCode() {
		return feedportCode;
	}
	public void setFeedportCode(String feedportCode) {
		this.feedportCode = feedportCode;
	}
	public String getFeedTime() {
		return feedTime;
	}
	public void setFeedTime(String feedTime) {
		this.feedTime = feedTime;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSuttle() {
		return suttle;
	}
	public void setSuttle(String suttle) {
		this.suttle = suttle;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	private String workOrder;
	private String feedportCode;
	private String feedTime;
	private String materialCode;
	private String suttle;
	private String unit;
	private String sort;
}
