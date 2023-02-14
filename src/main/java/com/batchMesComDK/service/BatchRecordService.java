package com.batchMesComDK.service;

public interface BatchRecordService {

	int addFromRecordPM(String workOrderID);

	int deleteByIds(String ids);
}
