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
	RecipePM_RMTMapper recipePM_RMTDao;
	@Autowired
	RecipePMMapper recipePMDao;

	@Override
	public int addFromRMT(String workOrderID, String productCode, String productName) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM_RMT> rPMRmtList=recipePM_RMTDao.getByProductParam(productCode,productName);
		
		RecipePM rPM=null;
		for(int i=0;i<rPMRmtList.size();i++) {
			RecipePM_RMT rPMRmt = rPMRmtList.get(i);
			rPM=new RecipePM();
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
			rPM.setCName(rPMRmt.getCName());
			rPM.setFeedPort(rPMRmt.getFeedPort());
			rPM.setMaterialSV(rPMRmt.getMaterialSV());
			rPM.setHH(rPMRmt.getHH());
			rPM.setLL(rPMRmt.getLL());
			
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
