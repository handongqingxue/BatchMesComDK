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
	 * 工单连续执行取消
	 */
	public static final int GDLXZXQX=12;
	/**
	 * 工单手工创建
	 */
	public static final int GDSGCJ=13;
	/**
	 * BATCH创建失败标识（待重新创建）
	 */
	public static final int BCJSB=15;
	/**
	 * 重新投料标识
	 */
	public static final int CXTL=16;
	
	/**
	 * 无故障
	 */
	public static final int NO_FAULT=0;
	/**
	 * 故障
	 */
	public static final int FAULT=1;
	
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
	public static final String BJS_TEXT="BATCH结束";//10
	
	/**
	 * 生产中
	 */
	public static final String PRODUCTION="PRODUCTION";
	/**
	 * 完工
	 */
	public static final String COMPLETE="COMPLETE";
	/**
	 * 制膏完成
	 */
	public static final String CREAMFINISH="CREAMFINISH";
	/**
	 * 生产终止
	 */
	public static final String PRODUCTBREAK="PRODUCTBREAK";
	
	/**
	 * 工单号不存在
	 */
	public static final String WOID_NO_EXIST="工单号不存在";
	/**
	 * 执行中的工单不允许取消
	 */
	public static final String RUN_WO_NO_ALLOW_CANNEL="执行中的工单不允许取消";
	/**
	 * 状态有误
	 */
	public static final String STATE_ERROR="状态有误";
	
	/**
	 * mes下单
	 */
	public static final int MES_DOWN=1;
	/**
	 * 手动创建
	 */
	public static final int HAND_CREATE=2;
	
	/**
	 * 已创建
	 */
	public static final boolean CREATED=true;
	/**
	 * 未创建
	 */
	public static final boolean UN_CREATE=false;

	/**
	 * 未发送
	 */
	public static final int UN_SEND=1;
	/**
	 * 发送中
	 */
	public static final int SENDING=2;
	/**
	 * 已发送
	 */
	public static final int SENDED=3;
	
	/**
	 * batch未结束
	 */
	public static final int BATCH_UN_END=0;
	/**
	 * batch已完成
	 */
	public static final int BATCH_FINISHED=1;
	/**
	 * batch已终止
	 */
	public static final int BATCH_STOPPED=2;

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
	public String getIdentifier() {
		return Identifier;
	}
	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}
	public String getUpdateUser() {
		return UpdateUser;
	}
	public void setUpdateUser(String updateUser) {
		UpdateUser = updateUser;
	}
	public String getFormulaIdMes() {
		return FormulaIdMes;
	}
	public void setFormulaIdMes(String formulaIdMes) {
		FormulaIdMes = formulaIdMes;
	}
	public Integer getClearFault() {
		return ClearFault;
	}
	public void setClearFault(Integer clearFault) {
		ClearFault = clearFault;
	}
	public Integer getCreateType() {
		return CreateType;
	}
	public void setCreateType(Integer createType) {
		CreateType = createType;
	}
	public Boolean getBatchCreated() {
		return BatchCreated;
	}
	public void setBatchCreated(Boolean batchCreated) {
		BatchCreated = batchCreated;
	}
	public String getApiFailData() {
		return ApiFailData;
	}
	public void setApiFailData(String apiFailData) {
		ApiFailData = apiFailData;
	}
	public Integer getSendBRToMes() {
		return SendBRToMes;
	}
	public void setSendBRToMes(Integer sendBRToMes) {
		SendBRToMes = sendBRToMes;
	}
	public String getLastSendTime() {
		return LastSendTime;
	}
	public void setLastSendTime(String lastSendTime) {
		LastSendTime = lastSendTime;
	}
	public Integer getBatchEndType() {
		return BatchEndType;
	}
	public void setBatchEndType(Integer batchEndType) {
		BatchEndType = batchEndType;
	}
	public String getReFeedPort() {
		return ReFeedPort;
	}
	public void setReFeedPort(String reFeedPort) {
		ReFeedPort = reFeedPort;
	}
	public Integer getReFeedStepMes() {
		return ReFeedStepMes;
	}
	public void setReFeedStepMes(Integer reFeedStepMes) {
		ReFeedStepMes = reFeedStepMes;
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
	private String Identifier;
	private String UpdateUser;
	private String FormulaIdMes;
	private Integer ClearFault;
	private Integer CreateType;
	private Boolean BatchCreated;
	private String ApiFailData;
	private Integer SendBRToMes;
	private String LastSendTime;
	private Integer BatchEndType;//记录BatchView里结束时的类型(已完成、已终止),用于执行配方从BatchView里移除后判断是否已结束
  	private String ReFeedPort;//重新投料的投料口
  	private Integer ReFeedStepMes;//重新投料的第几步
	private List<RecipePM> recipePMList;//用来存放mes那边下发的大料和工艺参数
	private List<ManFeed> manFeedList;//用来存放mes那边下发的小料参数
	public List<RecipePM> getRecipePMList() {
		return recipePMList;
	}
	public void setRecipePMList(List<RecipePM> recipePMList) {
		this.recipePMList = recipePMList;
	}
	public List<ManFeed> getManFeedList() {
		return manFeedList;
	}
	public void setManFeedList(List<ManFeed> manFeedList) {
		this.manFeedList = manFeedList;
	}
	
}
