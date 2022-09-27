package com.batchMesComDK.entity;

public class MaterialCheckOverIssusBody {

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
	public String getCheckOverTime() {
		return checkOverTime;
	}
	public void setCheckOverTime(String checkOverTime) {
		this.checkOverTime = checkOverTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String workOrder;
	private String checkOverTime;
	private String status;
}
