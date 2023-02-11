package com.batchMesComDK.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePM_RMTMapper {

	List<RecipePM_RMT> getByRecipeID(@Param("recipeID") String recipeID);

	int add(RecipePM_RMT rPM_RMT);

	int edit(RecipePM_RMT rPM_RMT);

	RecipePM_RMT getById(@Param("id") Integer id);
}
