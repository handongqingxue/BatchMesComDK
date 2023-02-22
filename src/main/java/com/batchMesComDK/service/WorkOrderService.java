package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);

	int updateStateByWorkOrderID(Integer state, String workOrderID);

	List<WorkOrder> getKeepWatchList();

	int edit(WorkOrder wo);

	WorkOrder getById(Integer id);

	int deleteByIds(String ids);

	int updateCreamCodeByWorkOrder(String creamCode, String workOrder);
}
