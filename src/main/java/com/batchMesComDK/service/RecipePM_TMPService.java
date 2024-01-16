package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface RecipePM_TMPService {

	int add(RecipePM_TMP rPM_TMP);

	int deleteByIds(String ids);

	int edit(RecipePM_TMP rPM_TMP);

	RecipePM_TMP getById(Integer id);

	/**
	 * 根据配方id验证是否存在
	 * @param recipeID
	 * @return
	 */
	boolean checkIfExistByRecipeID(String recipeID);

}
