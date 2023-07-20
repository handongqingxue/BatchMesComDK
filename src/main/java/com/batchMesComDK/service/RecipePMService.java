package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.RecipePM;

public interface RecipePMService {

	int addFromTMP(String workOrderID, String recipeID);

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

	/**
	 * 对比大料加入量，若有变化则把每种大料的总量更新在第一次加料量里，并在后面加x(这个方法暂时这么用，以后可能会用其他的逻辑)
	 * @param workOrderID
	 * @param wodRecipePMList
	 * @return
	 */
	int updateDosageXByPMParam(String workOrderID, List<RecipePM> wodRecipePMList);

	/**
	 * 对比大料加入量，若有变化则把每种大料总量里多出或少去的部分更新在最后一次加料量里(这个方法以后会用)
	 * @param workOrderID
	 * @param wodRecipePMList
	 * @return
	 */
	int updateDosageLastByPMParam(String workOrderID, List<RecipePM> wodRecipePMList);
}
