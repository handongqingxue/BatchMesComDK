package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);

	int updateStateByWorkOrderID(Integer state, String workOrderID);

	int updateStateByFormulaId(Integer state, String formulaId);

	List<WorkOrder> getKeepWatchList();

	List<WorkOrder> getSendToMesList();

	int edit(WorkOrder wo);

	WorkOrder getById(Integer id);

	int deleteByIds(String ids);

	int updateZGIDByWorkOrder(String zGID, String workOrder);

	String createFormulaIdByDateYMD(String identifier);

	String getFormulaIdByWOID(String workOrderID);

	int updateStateByWOIDs(Integer state, String workOrders);

	Integer getCountByByWOIDs(String workOrders);

	List<Integer> getStateListByWOIDs(String workOrders);

}
