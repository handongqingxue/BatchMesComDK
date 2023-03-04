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

	@Override
	public int add(ManFeed manFeed) {
		// TODO Auto-generated method stub
		return manFeedDao.add(manFeed);
	}

	@Override
	public int addFromRecipePM(String workOrderID) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> rPMList=recipePMDao.getListByWorkOrderID(workOrderID);
		
		ManFeed mf=null;
		for (int i = 0; i < rPMList.size(); i++) {
			RecipePM rPM=rPMList.get(i);
			
			mf=new ManFeed();
		  	mf.setWorkOrderID(workOrderID);
		  	mf.setMaterialCode(rPM.getPMCode());
		  	mf.setMaterialName(rPM.getPMName());
		  	mf.setFeedPort(rPM.getFeedPort());
		  	mf.setMarkBit("0");
		  	mf.setMaterialSV(rPM.getMaterialSV());
			
			count+=manFeedDao.add(mf);
		}
		
		return count;
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
	public ManFeed getByWorkOrderIDPhaseID(String workOrderID,String phaseID) {
		// TODO Auto-generated method stub
		return manFeedDao.getByWorkOrderIDPhaseID(workOrderID,phaseID);
	}

}
