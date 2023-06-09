package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.RecipeHeader;
import com.batchMesComDK.service.*;

@Service
public class RecipeHeaderServiceImpl implements RecipeHeaderService {

	@Autowired
	RecipeHeaderMapper recipeHeaderDao;

	@Override
	public RecipeHeader getByProductParam(String productCode, String productName) {
		// TODO Auto-generated method stub
		return recipeHeaderDao.getByProductParam(productCode, productName);
	}

	@Override
	public RecipeHeader getByRecipeID(String recipeID) {
		// TODO Auto-generated method stub
		return recipeHeaderDao.getByRecipeID(recipeID);
	}
}
