package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class RecipePM_TMPServiceImpl implements RecipePM_TMPService {

	@Autowired
	RecipePM_TMPMapper recipePM_TMPDao;

	@Override
	public int add(RecipePM_TMP rPM_TMP) {
		// TODO Auto-generated method stub
		return recipePM_TMPDao.add(rPM_TMP);
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return recipePM_TMPDao.deleteByList(idList);
	}

	@Override
	public int edit(RecipePM_TMP rPM_TMP) {
		// TODO Auto-generated method stub
		return recipePM_TMPDao.edit(rPM_TMP);
	}

	@Override
	public RecipePM_TMP getById(Integer id) {
		// TODO Auto-generated method stub
		return recipePM_TMPDao.getById(id);
	}

	@Override
	public boolean checkIfExistByRecipeID(String recipeID) {
		// TODO Auto-generated method stub
		return recipePM_TMPDao.getCountByRecipeID(recipeID)==0?false:true;
	}
}
