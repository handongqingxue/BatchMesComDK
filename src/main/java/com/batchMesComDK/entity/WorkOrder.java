package com.batchMesComDK.entity;

public class WorkOrder {
	
	/**
	 * 物料齐套完毕
	 */
	public static final int WLQTWB=1;
	/**
	 * 参数确认完毕（具备创建BATCH条件）
	 */
	public static final int CSQRWB=2;
	/**
	 * BATCH创建完毕（待执行）
	 */
	public static final int BCJWB=3;
	/**
	 * BATCH启动
	 */
	public static final int BQD=4;
	/**
	 * BATCH运行
	 */
	public static final int BYX=5;
	/**
	 * BATCH取消
	 */
	public static final int BQX=6;
	/**
	 * BATCH暂停
	 */
	public static final int BZT=7;
	/**
	 * BATCH意外终止
	 */
	public static final int BYWZZ=8;
	/**
	 * BATCH结束
	 */
	public static final int BJS=0;

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
	
}
