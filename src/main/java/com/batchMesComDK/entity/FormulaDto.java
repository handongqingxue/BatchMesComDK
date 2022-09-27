package com.batchMesComDK.entity;

import java.util.List;

public class FormulaDto {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getWorkcenterId() {
		return workcenterId;
	}
	public void setWorkcenterId(String workcenterId) {
		this.workcenterId = workcenterId;
	}
	public String getRefQuantity() {
		return refQuantity;
	}
	public void setRefQuantity(String refQuantity) {
		this.refQuantity = refQuantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBeginValidDate() {
		return beginValidDate;
	}
	public void setBeginValidDate(String beginValidDate) {
		this.beginValidDate = beginValidDate;
	}
	public String getEndValidDate() {
		return endValidDate;
	}
	public void setEndValidDate(String endValidDate) {
		this.endValidDate = endValidDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getOriginalFormulaCode() {
		return originalFormulaCode;
	}
	public void setOriginalFormulaCode(String originalFormulaCode) {
		this.originalFormulaCode = originalFormulaCode;
	}
	public String getOrigineVersion() {
		return origineVersion;
	}
	public void setOrigineVersion(String origineVersion) {
		this.origineVersion = origineVersion;
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
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getStageType() {
		return stageType;
	}
	public void setStageType(String stageType) {
		this.stageType = stageType;
	}
	public String getIsStandBom() {
		return isStandBom;
	}
	public void setIsStandBom(String isStandBom) {
		this.isStandBom = isStandBom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getDosageType() {
		return dosageType;
	}
	public void setDosageType(String dosageType) {
		this.dosageType = dosageType;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getWeightEquivalent() {
		return weightEquivalent;
	}
	public void setWeightEquivalent(String weightEquivalent) {
		this.weightEquivalent = weightEquivalent;
	}
	public String getWeightEquivalentUnit() {
		return weightEquivalentUnit;
	}
	public void setWeightEquivalentUnit(String weightEquivalentUnit) {
		this.weightEquivalentUnit = weightEquivalentUnit;
	}
	public String getRefQuantityUnit() {
		return refQuantityUnit;
	}
	public void setRefQuantityUnit(String refQuantityUnit) {
		this.refQuantityUnit = refQuantityUnit;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getVerifyUser() {
		return verifyUser;
	}
	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}
	public String getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}
	public String getCancelAt() {
		return cancelAt;
	}
	public void setCancelAt(String cancelAt) {
		this.cancelAt = cancelAt;
	}
	public String getCancelUser() {
		return cancelUser;
	}
	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	private String code;
	private String version;
	private String name;
	private String factory;
	private String workcenterId;
	private String refQuantity;
	private String unit;
	private String beginValidDate;
	private String endValidDate;
	private String status;
	private String createdAt;
	private String updatedAt;
	private String createUser;
	private String updateUser;
	private String originalFormulaCode;
	private String origineVersion;
	private String productCode;
	private String productName;
	private String productDesc;
	private String stageType;
	private String isStandBom;
	private String type;
	private String project;
	private String dosageType;
	private String dosage;
	private String form;
	private String folder;
	private String unitType;
	private String weightEquivalent;
	private String weightEquivalentUnit;
	private String refQuantityUnit;
	private String message;
	private String approveUser;
	private String approveDate;
	private String verifyUser;
	private String verifyDate;
	private String cancelAt;
	private String cancelUser;
	private String zoneCode;
	private List<FormulaMaterialDto> formulaMaterialList;
	public List<FormulaMaterialDto> getFormulaMaterialList() {
		return formulaMaterialList;
	}
	public void setFormulaMaterialList(List<FormulaMaterialDto> formulaMaterialList) {
		this.formulaMaterialList = formulaMaterialList;
	}
}
