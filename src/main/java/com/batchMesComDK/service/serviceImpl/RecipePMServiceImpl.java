package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class RecipePMServiceImpl implements RecipePMService {

	@Autowired
	RecipePM_RMTMapper recipePM_RMTDao;
	@Autowired
	RecipePMMapper recipePMDao;

	@Override
	public int addFromRMT(String workOrderID, String recipeID) {
		// TODO Auto-generated method stub
		RecipePM_RMT rPMRmt=recipePM_RMTDao.getByRecipeID(recipeID);
		
		RecipePM rPM=new RecipePM();
		rPM.setID(rPMRmt.getID());
		rPM.setPMCode(rPMRmt.getPMCode());
		rPM.setPMName(rPMRmt.getPMName());
		rPM.setLot(rPMRmt.getLot());
		rPM.setDosage(rPMRmt.getDosage());
		rPM.setUnit(rPMRmt.getUnit());
		rPM.setHLimit(rPMRmt.getHLimit());
		rPM.setLLimit(rPMRmt.getLLimit());
		rPM.setPMType(rPMRmt.getPMType());
		rPM.setWorkOrderID(workOrderID);
		rPM.setCName(rPMRmt.getCName());
		
		return recipePMDao.add(rPM);
	}
}