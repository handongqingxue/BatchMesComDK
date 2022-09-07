package com.batchMesComDK.entity;

public class PasteWorkingNumBody {

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
	public String getCreamCode() {
		return creamCode;
	}
	public void setCreamCode(String creamCode) {
		this.creamCode = creamCode;
	}
	public String getCreamWaterNo() {
		return creamWaterNo;
	}
	public void setCreamWaterNo(String creamWaterNo) {
		this.creamWaterNo = creamWaterNo;
	}
	private String workOrder;
	private String creamCode;
	private String creamWaterNo;
}
