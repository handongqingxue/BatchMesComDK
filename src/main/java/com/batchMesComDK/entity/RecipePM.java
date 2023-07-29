package com.batchMesComDK.entity;

public class RecipePM {
	
	/**
	 * 物料参数
	 */
	public static final int WLCS=1;
	/**
	 * 工艺参数
	 */
	public static final int GYCS=2;
	/**
	 * 人工投料参数
	 */
	public static final int RGTLCS=3;

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
	public String getHH() {
		return HH;
	}
	public void setHH(String hH) {
		HH = hH;
	}
	public String getLL() {
		return LL;
	}
	public void setLL(String lL) {
		LL = lL;
	}
	public Integer getPMSort() {
		return PMSort;
	}
	public void setPMSort(Integer pMSort) {
		PMSort = pMSort;
	}
	public Integer getStep() {
		return Step;
	}
	public void setStep(Integer step) {
		Step = step;
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
	private String HH;
	private String LL;
	private Integer PMSort;
	private Integer Step;
}
