package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface WorkOrderService {

	int add(WorkOrder wo);

	int updateStateById(Integer state, Integer id);
}
