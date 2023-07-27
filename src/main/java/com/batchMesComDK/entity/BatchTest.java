package com.batchMesComDK.entity;

public class BatchTest {
	
	public static final String READY="READY";
	public static final String START="START";
	public static final String RESTART="RESTART";
	public static final String RUNNING="RUNNING";
	public static final String HOLD="HOLD";
	public static final String HELD="HELD";
	public static final String STOP="STOP";
	public static final String STOPPED="STOPPED";
	public static final String ABORT="ABORT";
	public static final String ABORTED="ABORTED";
	public static final String COMPLETE="COMPLETE";
	
	/**
	 * Can't pass in null Dispatch object
	 */
	public static final String CANT_PASS_IN_NULL_DISPATCH_OBJECT="Can't pass in null Dispatch object";
	/**
	 * Can't map name to dispid: GetItem
	 */
	public static final String CANT_MAP_NAME_TO_DISPID_GETITEM="Can't map name to dispid: GetItem";

	private Integer CreateID;
	public Integer getCreateID() {
		return CreateID;
	}
	public void setCreateID(Integer createID) {
		CreateID = createID;
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
