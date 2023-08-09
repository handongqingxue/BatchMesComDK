package com.batchMesComDK.entity;

public class ManFeed {
	
	/**
	 * 未结束
	 */
	public static final int WJS=0;
	/**
	 * 已结束
	 */
	public static final int YJS=1;
	/**
	 * 已确认(该投料口下次可以继续投料)
	 */
	public static final int YQR=2;

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
	public Float getSuttle() {
		return Suttle;
	}
	public void setSuttle(Float suttle) {
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
	public String getFeedPort() {
		return FeedPort;
	}
	public void setFeedPort(String feedPort) {
		FeedPort = feedPort;
	}
	public String getMarkBit() {
		return MarkBit;
	}
	public void setMarkBit(String markBit) {
		MarkBit = markBit;
	}
	public String getMaterialSV() {
		return MaterialSV;
	}
	public void setMaterialSV(String materialSV) {
		MaterialSV = materialSV;
	}
	public String getDev1() {
		return Dev1;
	}
	public void setDev1(String dev1) {
		Dev1 = dev1;
	}
	public String getDev2() {
		return Dev2;
	}
	public void setDev2(String dev2) {
		Dev2 = dev2;
	}
	public Integer getRunStep() {
		return RunStep;
	}
	public void setRunStep(Integer runStep) {
		RunStep = runStep;
	}
	public Integer getStepMes() {
		return StepMes;
	}
	public void setStepMes(Integer stepMes) {
		StepMes = stepMes;
	}
	private String WorkOrderID;
	private String MaterialCode;
  	private String MaterialName;
  	private Float Suttle;
  	private String Unit;
  	private String FeedTime;
  	private String FeedPort;
  	private String MarkBit;
  	private String MaterialSV;
  	private String Dev1;
  	private String Dev2;
  	private Integer RunStep;
  	private Integer StepMes;
}
