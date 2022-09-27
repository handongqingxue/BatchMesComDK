package com.batchMesComDK.entity;

import java.util.List;

public class WorkOrderBody {

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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTotalOutput() {
		return totalOutput;
	}
	public void setTotalOutput(String totalOutput) {
		this.totalOutput = totalOutput;
	}
	public String getMfgCode() {
		return mfgCode;
	}
	public void setMfgCode(String mfgCode) {
		this.mfgCode = mfgCode;
	}
	public String getMfgVersion() {
		return mfgVersion;
	}
	public void setMfgVersion(String mfgVersion) {
		this.mfgVersion = mfgVersion;
	}
	public String getFormulaId() {
		return formulaId;
	}
	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	private String workOrder;
	private String productCode;
	private String productName;
	private String totalOutput;
	private String mfgCode;
	private String mfgVersion;
	private String formulaId;
	private Integer state;
	private List<OrderMateriaBody> orderMaterialList;
	public List<OrderMateriaBody> getOrderMaterialList() {
		return orderMaterialList;
	}
	public void setOrderMaterialList(List<OrderMateriaBody> orderMaterialList) {
		this.orderMaterialList = orderMaterialList;
	}
}
