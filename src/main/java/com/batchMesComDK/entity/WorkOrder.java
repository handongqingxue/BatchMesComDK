package com.batchMesComDK.entity;

import java.util.List;

public class WorkOrder {
	
	/**
	 * 物料齐套完毕标识
	 */
	public static final int WLQTWB=1;
	/**
	 * 参数确认完毕标识（具备创建BATCH条件）
	 */
	public static final int CSQRWB=2;
	/**
	 * BATCH创建完毕标识（待执行）
	 */
	public static final int BCJWB=3;
	/**
	 * BATCH启动标识
	 */
	public static final int BQD=4;
	/**
	 * BATCH运行标识
	 */
	public static final int BYX=5;
	/**
	 * BATCH取消标识
	 */
	public static final int BQX=6;
	/**
	 * BATCH暂停标识
	 */
	public static final int BZT=7;
	/**
	 * BATCH意外终止标识
	 */
	public static final int BYWZZ=8;
	/**
	 * BATCH结束标识
	 */
	public static final int BJS=10;
	
	/**
	 * 物料齐套完毕名称
	 */
	public static final String WLQTWB_TEXT="物料齐套完毕";//1
	/**
	 * 参数确认完毕名称（具备创建BATCH条件）
	 */
	public static final String CSQRWB_TEXT="参数确认完毕";//2
	/**
	 * BATCH创建完毕名称（待执行）
	 */
	public static final String BCJWB_TEXT="BATCH创建完毕";//3
	/**
	 * BATCH启动名称
	 */
	public static final String BQD_TEXT="BATCH启动";//4
	/**
	 * BATCH运行名称
	 */
	public static final String BYX_TEXT="BATCH运行";//5
	/**
	 * BATCH取消名称
	 */
	public static final String BQX_TEXT="BATCH取消";//6
	/**
	 * BATCH暂停名称
	 */
	public static final String BZT_TEXT="BATCH暂停";//7
	/**
	 * BATCH意外终止名称
	 */
	public static final String BYWZZ_TEXT="BATCH意外终止";//8
	/**
	 * BATCH结束名称
	 */
	public static final String BJS_TEXT="BATCH结束";//0

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
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getTotalOutput() {
		return TotalOutput;
	}
	public void setTotalOutput(String totalOutput) {
		TotalOutput = totalOutput;
	}
	public String getMfgCode() {
		return MfgCode;
	}
	public void setMfgCode(String mfgCode) {
		MfgCode = mfgCode;
	}
	public String getMfgVersion() {
		return MfgVersion;
	}
	public void setMfgVersion(String mfgVersion) {
		MfgVersion = mfgVersion;
	}
	public String getRecipeID() {
		return RecipeID;
	}
	public void setRecipeID(String recipeID) {
		RecipeID = recipeID;
	}
	public String getFormulaId() {
		return FormulaId;
	}
	public void setFormulaId(String formulaId) {
		FormulaId = formulaId;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getUnitID() {
		return UnitID;
	}
	public void setUnitID(String unitID) {
		UnitID = unitID;
	}
	public String getZGID() {
		return ZGID;
	}
	public void setZGID(String zGID) {
		ZGID = zGID;
	}
	public String getLotNo() {
		return LotNo;
	}
	public void setLotNo(String lotNo) {
		LotNo = lotNo;
	}
	public String getWorkcenterId() {
		return WorkcenterId;
	}
	public void setWorkcenterId(String workcenterId) {
		WorkcenterId = workcenterId;
	}
	private String WorkOrderID;
	private String ProductCode;
	private String ProductName;
	private String TotalOutput;
	private String MfgCode;
	private String MfgVersion;
	private String RecipeID;
	private String FormulaId;
	private Integer State;
	private String CreateTime;
	private String UnitID;
	private String ZGID;
	private String LotNo;
	private String WorkcenterId;
	private List<RecipePM> recipePMList;
	public List<RecipePM> getRecipePMList() {
		return recipePMList;
	}
	public void setRecipePMList(List<RecipePM> recipePMList) {
		this.recipePMList = recipePMList;
	}
	
}
