package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.RecipePM;

public interface RecipePMService {

	int addFromTMP(String workOrderID, String productCode, String productName);

	int addFromWORecipePMList(String workOrderID, List<RecipePM> recipePMList);

	int deleteByIds(String ids);

	List<RecipePM> getListByWorkOrderID(String workOrderID);

	int updateDosageByPMParam(List<RecipePM> recipePMList);
}
