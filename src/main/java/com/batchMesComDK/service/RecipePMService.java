package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.RecipePM;

public interface RecipePMService {

	int addFromRMT(String workOrderID, String recipeID);

	int deleteByIds(String ids);

	List<RecipePM> getListByWorkOrderID(String workOrderID);
}
