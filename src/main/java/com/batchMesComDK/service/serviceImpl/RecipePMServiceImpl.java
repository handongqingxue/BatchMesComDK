package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class RecipePMServiceImpl implements RecipePMService {

	@Autowired
	RecipePM_TMPMapper recipePM_TMPDao;
	@Autowired
	RecipePMMapper recipePMDao;

	@Override
	public int addFromTMP(String workOrderID, Integer pMType, String productCode, String productName) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM_TMP> rPMTmpList=recipePM_TMPDao.getByProductParam(pMType,productCode,productName);
		
		RecipePM rPM=null;
		for(int i=0;i<rPMTmpList.size();i++) {
			RecipePM_TMP rPMTmp = rPMTmpList.get(i);
			rPM=new RecipePM();
			rPM.setPMCode(rPMTmp.getPMCode());
			rPM.setPMName(rPMTmp.getPMName());
			rPM.setLot(rPMTmp.getLot());
			rPM.setDosage(rPMTmp.getDosage());
			rPM.setUnit(rPMTmp.getUnit());
			rPM.setHLimit(rPMTmp.getHLimit());
			rPM.setLLimit(rPMTmp.getLLimit());
			rPM.setPMType(rPMTmp.getPMType());
			rPM.setWorkOrderID(workOrderID);
			rPM.setCName(rPMTmp.getCName());
			rPM.setCName(rPMTmp.getCName());
			rPM.setFeedPort(rPMTmp.getFeedPort());
			rPM.setMaterialSV(rPMTmp.getMaterialSV());
			rPM.setHH(rPMTmp.getHH());
			rPM.setLL(rPMTmp.getLL());
			
			count+=recipePMDao.add(rPM);
		}
		
		return count;
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return recipePMDao.deleteByList(idList);
	}

	@Override
	public List<RecipePM> getListByWorkOrderID(String workOrderID) {
		// TODO Auto-generated method stub
		return recipePMDao.getListByWorkOrderID(workOrderID);
	}
}
