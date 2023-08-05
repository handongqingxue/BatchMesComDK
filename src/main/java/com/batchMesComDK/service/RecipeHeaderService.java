package com.batchMesComDK.service;

import com.batchMesComDK.entity.RecipeHeader;

public interface RecipeHeaderService {

	/**
	 * 根据产品编码和产品名称查询配方头表信息（后来方案变了，一个产品可能有两个配方（满锅和半锅），因此产品编码和名称在配方头表里可能出现重复，这个方法暂时不用了）
	 * @param productCode
	 * @param productName
	 * @return
	 */
	RecipeHeader getByProductParam(String productCode, String productName);

	/**
	 * 根据配方id查询配方头表信息（即使一个产品有两个配方，配方id是唯一的，就通过配方id查询了）
	 * @param recipeID
	 * @return
	 */
	RecipeHeader getByRecipeID(String recipeID);
	
	/**
	 * 根据配方名称查询配方
	 * @param identifier
	 * @return
	 */
	RecipeHeader getByIdentifier(String identifier);

	/**
	 * 根据工单id查询配方头表信息
	 * @param workOrderID
	 * @return
	 */
	RecipeHeader getByWorkOrderID(String workOrderID);

}
