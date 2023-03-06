package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.RecipePM;

public interface RecipePMService {

	int addFromRMT(String workOrderID, Integer pMType, String productCode, String productName);

	int deleteByIds(String ids);

	List<RecipePM> getListByWorkOrderID(String workOrderID);
}
