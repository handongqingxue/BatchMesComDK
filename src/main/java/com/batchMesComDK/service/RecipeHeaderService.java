package com.batchMesComDK.service;

import com.batchMesComDK.entity.RecipeHeader;

public interface RecipeHeaderService {

	RecipeHeader getByProductParam(String productCode, String productName);

	RecipeHeader getByRecipeID(String recipeID);

	RecipeHeader getByWorkOrderID(String workOrderID);

}
