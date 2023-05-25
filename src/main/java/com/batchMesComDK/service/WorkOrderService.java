package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);

	int updateStateByWorkOrderID(Integer state, String workOrderID);

	int updateStateByFormulaId(Integer state, String formulaId);

	List<WorkOrder> getKeepWatchList();

	List<WorkOrder> getFinishedList();

	int edit(WorkOrder wo);

	WorkOrder getById(Integer id);

	int deleteByIds(String ids);

	int updateZGIDByWorkOrder(String zGID, String workOrder);

	String createFormulaIdByDateYMD(String identifier);

	String getFormulaIdByWOID(String workOrderID);

	int updateStateByWOIDs(Integer state, String workOrders);

	Integer getCountByByWOIDs(String workOrders);

	List<Integer> getStateListByWOIDs(String workOrders);

	/**
	 * 根据状态和主机id检查是否存在其他工单
	 * @param state
	 * @param workOrderID
	 * @param unitID
	 * @return
	 */
	boolean checkExistOtherByStateUnitID(int state, String workOrderID, String unitID);

	/**
	 * 根据主机id修改本机上其他工单状态
	 * @param state
	 * @param workOrderID
	 * @param unitID
	 * @return
	 */
	int updateOtherStateByUnitID(int state, String workOrderID, String unitID);
}
