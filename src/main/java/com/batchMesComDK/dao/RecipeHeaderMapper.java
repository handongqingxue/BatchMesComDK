package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipeHeaderMapper {

	RecipeHeader getByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);

	RecipeHeader getByRecipeID(@Param("recipeID") String recipeID);

	RecipeHeader getByWorkOrderID(@Param("workOrderID") String workOrderID);
}
