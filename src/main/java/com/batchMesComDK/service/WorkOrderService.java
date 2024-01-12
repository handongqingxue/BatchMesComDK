package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);

	int updateStateByWorkOrderID(Integer state, String workOrderID);

	int updateStateByFormulaId(Integer state, String formulaId);

	int updateClearFaultByFormulaId(Integer clearFault, String formulaId);

	List<WorkOrder> getKeepWatchList(List<String> woEndIDList);

	List<WorkOrder> getSendToMesList();

	/**
	 * 这个方法供调试用，手动创建工单号集合，防止出现越界情况
	 * @param workOrderIDList
	 * @return
	 */
	List<WorkOrder> getSendToMesListTest(List<String> workOrderIDList, Integer hoursAgo);

	int edit(WorkOrder wo);

	WorkOrder getById(Integer id);

	int deleteByIds(String ids);

	int updateZGIDByWorkOrder(String zGID, String workOrder);

	String createFormulaIdByDateYMD(String identifier);

	String getFormulaIdByWOID(String workOrderID);

	int updateStateByWOIDs(Integer state, String workOrders);

	Integer getCountByByWOIDs(String workOrders);

	List<Integer> getStateListByWOIDs(String workOrders);

	int updateBatchCreatedById(boolean batchCreated, Integer id);

	Boolean getBatchCreatedById(Integer id);

	int updateApiFailDataById(String apiFailData, Integer id);

	String getApiFailDataById(Integer id);

	int updateSendBRToMesByWorkOrderID(boolean sendBRToMes, String workOrderID);

	List<String> getUnSendBRToMesWOIDList();

}
