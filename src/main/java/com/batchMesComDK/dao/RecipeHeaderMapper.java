package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipeHeaderMapper {

	/**
	 * 根据产品编码和名称查询配方（一个配方可能对应两个产品：满锅、零锅，这个方法暂时不用了，用下面的方法查询配方）
	 * @param productCode
	 * @param productName
	 * @return
	 */
	RecipeHeader getByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);

	/**
	 * 根据配方id查询配方头表信息
	 * @param recipeID
	 * @return
	 */
	RecipeHeader getByRecipeID(@Param("recipeID") String recipeID);

	/**
	 * 根据配方名称查询配方
	 * @param identifier
	 * @return
	 */
	RecipeHeader getByIdentifier(@Param("identifier") String identifier);

	RecipeHeader getByWorkOrderID(@Param("workOrderID") String workOrderID);
}
