package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);

	List<WorkOrder> getKeepWatchList();
}
