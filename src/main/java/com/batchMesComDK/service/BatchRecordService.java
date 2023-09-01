package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface BatchRecordService {

	int addFromRecordPM(String workOrderID);

	int deleteByIds(String ids);

	List<BatchRecord> getSendToMesData(List<String> workOrderList);

	int addTechFromBHBatchHis(List<String> sendToMesWOIDList);

	int addMaterialFromBHBatchHis(List<String> workOrderIDList);

	int addPhaseFromBHBatchHis(List<String> workOrderIDList);

	int addBatchFromBHBatch(List<String> workOrderIDList);
}
