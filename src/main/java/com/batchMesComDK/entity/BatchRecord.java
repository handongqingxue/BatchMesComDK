package com.batchMesComDK.entity;

public class BatchRecord {
	
	/**
	 * 阶段过程记录
	 */
	public static final int JDGCJL=0;
	/**
	 * 过程(工艺)参数记录
	 */
	public static final int GCCSJL=1;
	/**
	 * 过程(工艺)参数记录
	 */
	public static final String GCCSJL_TEXT="工艺参数记录";
	/**
	 * 物料参数记录(原料进料记录)
	 */
	public static final int WLCSJL=2;
	/**
	 * 物料参数记录(原料进料记录)
	 */
	public static final String WLCSJL_TEXT="原料进料记录";
	/**
	 * 偏差记录
	 */
	public static final int PCJL=3;
	/**
	 * PHASE过程记录
	 */
	public static final int PGCJL=8;
	/**
	 * PHASE过程记录
	 */
	public static final String PGCJL_TEXT="PHASE过程记录";
	/**
	 * 批次过程记录
	 */
	public static final int PCGCJL=9;
	/**
	 * 批次过程记录
	 */
	public static final String PCGCJL_TEXT="批次过程记录";
	
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
	public String getLotNo() {
		return LotNo;
	}
	public void setLotNo(String lotNo) {
		LotNo = lotNo;
	}
	public String getRecordEvent() {
		return RecordEvent;
	}
	public void setRecordEvent(String recordEvent) {
		RecordEvent = recordEvent;
	}
	public String getRecordContent() {
		return RecordContent;
	}
	public void setRecordContent(String recordContent) {
		RecordContent = recordContent;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getRecordStartTime() {
		return RecordStartTime;
	}
	public void setRecordStartTime(String recordStartTime) {
		RecordStartTime = recordStartTime;
	}
	public String getRecordEndTime() {
		return RecordEndTime;
	}
	public void setRecordEndTime(String recordEndTime) {
		RecordEndTime = recordEndTime;
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
	public String getDeviationType() {
		return DeviationType;
	}
	public void setDeviationType(String deviationType) {
		DeviationType = deviationType;
	}
	public String getRecordType() {
		return RecordType;
	}
	public void setRecordType(String recordType) {
		RecordType = recordType;
	}
	public String getEquipmentCode() {
		return EquipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		EquipmentCode = equipmentCode;
	}
	public String getPMCName() {
		return PMCName;
	}
	public void setPMCName(String pMCName) {
		PMCName = pMCName;
	}
	public String getPhaseDisc() {
		return PhaseDisc;
	}
	public void setPhaseDisc(String phaseDisc) {
		PhaseDisc = phaseDisc;
	}
	public String getPhaseID() {
		return PhaseID;
	}
	public void setPhaseID(String phaseID) {
		PhaseID = phaseID;
	}
	public String getFeedPort() {
		return FeedPort;
	}
	public void setFeedPort(String feedPort) {
		FeedPort = feedPort;
	}
	public Integer getPhaseStep() {
		return PhaseStep;
	}
	public void setPhaseStep(Integer phaseStep) {
		PhaseStep = phaseStep;
	}
	private String WorkOrderID;
	private String PMCode;
	private String PMName;
	private String LotNo;
	private String RecordEvent;
	private String RecordContent;
	private String Unit;
	private String RecordStartTime;
	private String RecordEndTime;
	private String HLimit;
	private String LLimit;
	private String DeviationType;
	private String RecordType;
	private String EquipmentCode;
	private String PMCName;
	private String PhaseDisc;
	private String PhaseID;
	private String FeedPort;//投料口
	private Integer PhaseStep;//phase是第几次执行
}
