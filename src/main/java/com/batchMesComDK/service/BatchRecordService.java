package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface BatchRecordService {

	int addFromRecordPM(String workOrderID);

	int deleteByIds(String ids);

	List<BatchRecord> getSendToMesData(List<String> workOrderList);
}
