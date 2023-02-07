package com.batchMesComDK.entity;

public class ManFeed {

    private Integer ID;
  	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getWorkOrderID() {
		return WorkOrderID;
	}
	public void setWorkOrderID(String workOrderID) {
		WorkOrderID = workOrderID;
	}
	public String getMaterialCode() {
		return MaterialCode;
	}
	public void setMaterialCode(String materialCode) {
		MaterialCode = materialCode;
	}
	public String getMaterialName() {
		return MaterialName;
	}
	public void setMaterialName(String materialName) {
		MaterialName = materialName;
	}
	public String getSuttle() {
		return Suttle;
	}
	public void setSuttle(String suttle) {
		Suttle = suttle;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getFeedTime() {
		return FeedTime;
	}
	public void setFeedTime(String feedTime) {
		FeedTime = feedTime;
	}
	public String getPhaseID() {
		return PhaseID;
	}
	public void setPhaseID(String phaseID) {
		PhaseID = phaseID;
	}
	public String getMarkBit() {
		return MarkBit;
	}
	public void setMarkBit(String markBit) {
		MarkBit = markBit;
	}
	private String WorkOrderID;
	private String MaterialCode;
  	private String MaterialName;
  	private String Suttle;
  	private String Unit;
  	private String FeedTime;
  	private String PhaseID;
  	private String MarkBit;
}
