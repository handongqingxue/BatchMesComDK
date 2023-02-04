package com.batchMesComDK.entity;

public class RecipePM {

    private Integer ID;
  	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getPMCode() {
		return PMCode;
	}
	public void setPMCode(String pMCode) {
		PMCode = pMCode;
	}
	public String getPMName() {
		return PMName;
	}
	public void setPMName(String pMName) {
		PMName = pMName;
	}
	public String getLot() {
		return Lot;
	}
	public void setLot(String lot) {
		Lot = lot;
	}
	public String getDosage() {
		return Dosage;
	}
	public void setDosage(String dosage) {
		Dosage = dosage;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getHLimit() {
		return HLimit;
	}
	public void setHLimit(String hLimit) {
		HLimit = hLimit;
	}
	public String getLLimit() {
		return LLimit;
	}
	public void setLLimit(String lLimit) {
		LLimit = lLimit;
	}
	public String getPMType() {
		return PMType;
	}
	public void setPMType(String pMType) {
		PMType = pMType;
	}
  	public String getWorkOrderID() {
		return WorkOrderID;
	}
	public void setWorkOrderID(String workOrderID) {
		WorkOrderID = workOrderID;
	}
	public String getCName() {
		return CName;
	}
	public void setCName(String cName) {
		CName = cName;
	}
	private String PMCode;
  	private String PMName;
  	private String Lot;
  	private String Dosage;
  	private String Unit;
  	private String HLimit;
  	private String LLimit;
  	private String PMType;
  	private String WorkOrderID;
	private String CName;
}
