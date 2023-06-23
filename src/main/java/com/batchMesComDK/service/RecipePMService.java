package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.RecipePM;

public interface RecipePMService {

	int addFromTMP(String workOrderID, String productCode, String productName);

	/**
	 * 测试时没有提供工艺指导书，就暂时将下单报文里的物料参数信息直接插入到配方参数表里
	 * @param workOrderID
	 * @param recipePMList
	 * @return
	 */
	int addFromWORecipePMList(String workOrderID, List<RecipePM> recipePMList);

	int deleteByIds(String ids);

	List<RecipePM> getListByWorkOrderID(String workOrderID);

	List<RecipePM> getDLListByWorkOrderID(String workOrderID);

	int updateDosageXByPMParam(String workOrderID, List<RecipePM> wodRecipePMList);

	int updateDosageLastByPMParam(String workOrderID, List<RecipePM> wodRecipePMList);
}
