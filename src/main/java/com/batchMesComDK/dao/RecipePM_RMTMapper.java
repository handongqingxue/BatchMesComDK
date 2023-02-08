package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePM_RMTMapper {

	RecipePM_RMT getByRecipeID(@Param("recipeID") String recipeID);

	int add(RecipePM_RMT rPM_RMT);
}
