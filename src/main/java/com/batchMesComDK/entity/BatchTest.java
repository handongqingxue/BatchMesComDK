package com.batchMesComDK.entity;

public class BatchTest {
	
	public static final String READY="READY";
	public static final String RUNNING="RUNNING";
	public static final String STOPPED="STOPPED";
	public static final String COMPLETE="COMPLETE";

	private Integer ID;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getBatchID() {
		return BatchID;
	}
	public void setBatchID(String batchID) {
		BatchID = batchID;
	}
	public String getRecipe() {
		return Recipe;
	}
	public void setRecipe(String recipe) {
		Recipe = recipe;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getMode() {
		return Mode;
	}
	public void setMode(String mode) {
		Mode = mode;
	}
	private String BatchID;
	private String Recipe;
	private String Description;
	private String StartTime;
	private String State;
	private String Mode;
}
