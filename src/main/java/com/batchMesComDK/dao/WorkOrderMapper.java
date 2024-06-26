package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface WorkOrderMapper {

	int add(WorkOrder wo);

	int updateStateById(@Param("state") Integer state, @Param("id") Integer id);

	int updateStateByWorkOrderID(@Param("state") Integer state, @Param("workOrderID") String workOrderID);

	int updateStateByFormulaId(@Param("state") Integer state, @Param("formulaId") String formulaId);

	int updateClearFaultByFormulaId(@Param("clearFault") Integer clearFault, @Param("formulaId") String formulaId);

	List<WorkOrder> getListByStateList(@Param("stateList") List<String> stateList);

	List<WorkOrder> getListByWOEndIDStateList(@Param("woEndIDList") List<String> woEndIDList, @Param("stateList") List<String> stateList);

	List<WorkOrder> getSendToMesListByStateList(@Param("stateList") List<String> stateList);

	List<WorkOrder> getSendToMesListByStateListTest(@Param("workOrderIDList") List<String> workOrderIDList, @Param("stateList") List<String> stateList, @Param("hoursAgo") Integer hoursAgo);

	/**
	 * 获得已结束(终止或已完成)的工单列表
	 * @param stateList
	 * @return
	 */
	List<WorkOrder> getEndListByStateList(@Param("stateList") List<String> stateList);

	int edit(WorkOrder wo);

	WorkOrder getById(@Param("id") Integer id);

	int deleteByList(@Param("idList") List<String> idList);

	int updateZGIDByWorkOrderID(@Param("zGID") String zGID, @Param("workOrderID") String workOrderID);

	String getMaxFormulaIdNumByFormulaIdDate(@Param("formulaIdDate") String formulaIdDate);

	String getFormulaIdByWOID(@Param("workOrderID") String workOrderID);

	int updateStateByWOIDList(@Param("state") Integer state, @Param("workOrderIDList") List<String> workOrderIDList);

	Integer getCountByByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<Integer> getStateListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	int updateBatchCreatedById(@Param("batchCreated") boolean batchCreated, @Param("id") Integer id);

	Boolean getBatchCreatedById(@Param("id") Integer id);

	int updateApiFailDataById(@Param("apiFailData") String apiFailData, @Param("id") Integer id);

	String getApiFailDataById(@Param("id") Integer id);

	int updateSendBRToMesByWorkOrderID(@Param("sendBRToMes") int sendBRToMes, @Param("workOrderID") String workOrderID);

	List<String> getUnSendBRToMesWOIDList(@Param("stateList") List<String> stateList);

	int updateBatchEndTypeById(@Param("batchEndType") int batchEndType, @Param("id") Integer id);

	int updateBatchEndTypeByFormulaId(@Param("batchEndType") int batchEndType, @Param("formulaId") String formulaId);

	int updateReFeedInfoById(@Param("reFeedPort") String reFeedPort, @Param("reFeedStepMes") Integer reFeedStepMes, @Param("id") Integer id);

	int getSendingBRToMesWOCount(@Param("stateList") List<String> stateList);

	int restoreToUnSend(@Param("stateList") List<String> stateList);

	int getCountByWOID(@Param("workOrderID") String workOrderID);

}
