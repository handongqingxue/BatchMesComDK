package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

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
	public int addFromRecordPM(String workOrderID) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> rPMList=recipePMDao.getListByWorkOrderID(workOrderID);
		
		BatchRecord br=null;
		for (int i = 0; i < rPMList.size(); i++) {
			RecipePM rPM=rPMList.get(i);
			br=new BatchRecord();
			br.setWorkOrderID(workOrderID);
			br.setPMCode(rPM.getPMCode());
			br.setPMName(rPM.getPMName());
			br.setLotNo(rPM.getLot());
			//br.setRecordEvent(recordEvent);
			//br.setRecordContent(recordContent);
			br.setUnit(rPM.getUnit());
			Integer recordTypeInt=null;
			String pMType=rPM.getPMType();
			Integer pMTypeInt = Integer.valueOf(pMType);
			switch (pMTypeInt) {
			case RecipePM.WLCS:
				recordTypeInt=BatchRecord.WLCSJL;
				break;
			case RecipePM.GYCS:
				recordTypeInt=BatchRecord.GCCSJL;
				break;
			}
			br.setRecordType(recordTypeInt+"");
			//br.setEquipmentCode(equipmentCode);
			br.setPMCName(rPM.getCName());
			//br.setPhaseDisc(phaseDisc);
			//br.setPhaseID(phaseID);
			//br.setWMark(wMark);
			
			count+=batchRecordDao.add(br);
		}
		
		return count;
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return batchRecordDao.deleteByList(idList);
	}

	@Override
	public List<BatchRecord> getSendToMesData(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		String recordTypes = BatchRecord.PCJL+","+BatchRecord.PGCJL+","+BatchRecord.PCGCJL;
		String[] recordTypeArr = recordTypes.split(",");
		List<String> recordTypeList = Arrays.asList(recordTypeArr);
		return batchRecordDao.getListByWorkOrderIDList(workOrderIDList,recordTypeList);
	}
}
