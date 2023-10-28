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
	public static final String RESET="RESET";
	public static final String COMPLETE="COMPLETE";
	public static final String CLEAR_FAILURES="CLEAR_FAILURES";
	
	public static final String BATCH_LIST_CT="BatchListCt";
	public static final String BATCH_LIST="BatchList";
	public static final String BL_BATCH_ID="BLBatchID_";
	public static final String BL_CREATE_ID="BLCreateID_";
	public static final String BL_STATE="BLState_";
	public static final String BL_FAILURE="BLFailure_";
	
	public static final String SUCCESS_RESULT="SUCCESS:";
	
	/**
	 * 行与行之间的分隔符(虽然文档里写着是crlf,但java端解析就成了\r\n)
	 */
	public static final String CRLF_SPACE_SIGN="\\r\\n";
	
	public static final String T_SPACE_SIGN="\\t";
	
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
