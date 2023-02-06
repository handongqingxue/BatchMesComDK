package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class BatchRecordServiceImpl implements BatchRecordService {

	@Autowired
	BatchRecordMapper batchRecordDao;
	@Autowired
	RecipePMMapper recipePMDao;

	@Override
	public int addFromRecordPM(String recipeID) {
		// TODO Auto-generated method stub
		RecipePM rPM=recipePMDao.getByRecipeID(recipeID);
		
		BatchRecord br=new BatchRecord();
		br.setID(rPM.getID());
		br.setWorkOrderID(rPM.getWorkOrderID());
		br.setPMCode(rPM.getPMCode());
		br.setPMName(rPM.getPMName());
		br.setLotNo(rPM.getLot());
		//br.setRecordEvent(recordEvent);
		//br.setRecordContent(recordContent);
		br.setUnit(rPM.getUnit());
		
		rPM.setPMType(rPMRmt.getPMType());
		rPM.setCName(rPMRmt.getCName());
		
		private String DeviationType;
		private String RecordType;
		private String EquipmentCode;
		private String PMCName;
		private String PhaseDisc;
		private String PhaseID;
		private String WMark;
		
		return 0;
	}
}