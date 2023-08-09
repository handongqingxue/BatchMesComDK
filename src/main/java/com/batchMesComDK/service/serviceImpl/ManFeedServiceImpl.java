package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class ManFeedServiceImpl implements ManFeedService {

	@Autowired
	ManFeedMapper manFeedDao;
	@Autowired
	RecipePMMapper recipePMDao;
	@Autowired
	RecipeHeaderMapper recipeHeaderDao;

	@Override
	public int add(ManFeed manFeed) {
		// TODO Auto-generated method stub
		return manFeedDao.add(manFeed);
	}

	@Override
	public int addFromRecipePM(String workOrderID, String productCode, String productName) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> rPMList=recipePMDao.getManFeedListByWorkOrderID(workOrderID);
		RecipeHeader rh=recipeHeaderDao.getByProductParam(productCode,productName);
		String dev1 = rh.getDev1();
		String dev2 = rh.getDev2();
		
		ManFeed mf=null;
		for (int i = 0; i < rPMList.size(); i++) {
			RecipePM rPM=rPMList.get(i);
			
			mf=new ManFeed();
		  	mf.setWorkOrderID(workOrderID);
		  	mf.setMaterialCode(rPM.getPMCode());
		  	mf.setMaterialName(rPM.getPMName());
		  	mf.setUnit(rPM.getUnit());
		  	mf.setMarkBit(ManFeed.WJS+"");
		  	mf.setMaterialSV(rPM.getDosage());
		  	mf.setDev1(dev1);
		  	mf.setDev2(dev2);
			
			count+=manFeedDao.add(mf);
		}
		
		return count;
	}

	@Override
	public int addFromList(List<ManFeed> mfList) {
		// TODO Auto-generated method stub
		return manFeedDao.addFromList(mfList);
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return manFeedDao.deleteByList(idList);
	}

	@Override
	public int editByWorkOrderIDFeedPort(ManFeed mf) {
		// TODO Auto-generated method stub
		return manFeedDao.editByWorkOrderIDFeedPort(mf);
	}
	
	@Override
	public int editByWorkOrderIDFeedPortList(List<ManFeed> mfList) {
		// TODO Auto-generated method stub
		int count=0;
		/*
		for (ManFeed mf : mfList) {
			count+=manFeedDao.editByWorkOrderIDFeedPort(mf);
		}
		*/
		count=manFeedDao.editByList(mfList);
		manFeedDao.updateMarkBitByParamsList(mfList);
		
		return count;
	}

	@Override
	public ManFeed getByWorkOrderIDFeedPort(String workOrderID,String feedPort) {
		// TODO Auto-generated method stub
		return manFeedDao.getByWorkOrderIDFeedPort(workOrderID,feedPort);
	}

	@Override
	public int updateStepMesByWOID(String workOrderID) {
		// TODO Auto-generated method stub
		int count=0;
		List<ManFeed> manFeedList=manFeedDao.getStepMesListByWOID(workOrderID);
		count=manFeedDao.updateStepMesByList(manFeedList);
		return count;
	}

}
