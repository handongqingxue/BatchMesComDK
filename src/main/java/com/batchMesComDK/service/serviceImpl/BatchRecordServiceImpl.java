package com.batchMesComDK.service.serviceImpl;

import java.util.ArrayList;
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
	@Autowired
	BHBatchHisMapper bHBatchHisDao;
	@Autowired
	BHBatchMapper bHBatchDao;

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
		String recordTypes = BatchRecord.WLCSJL+","+BatchRecord.PCJL+","+BatchRecord.PGCJL+","+BatchRecord.PCGCJL;
		String[] recordTypeArr = recordTypes.split(",");
		List<String> recordTypeList = Arrays.asList(recordTypeArr);
		return batchRecordDao.getListByWorkOrderIDList(workOrderIDList,recordTypeList);
	}

	@Override
	public int addMaterialFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//his 140
		BatchRecord batchRecord=null;
		List<BHBatchHis> materialList = bHBatchHisDao.getMaterialListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : materialList) {
			batchRecord=new BatchRecord();
			String workOrderID = bhBatchHis.getWorkOrderID();
			String materialID = bhBatchHis.getMaterialID();
			String materialName = bhBatchHis.getMaterialName();
			String lotName = bhBatchHis.getLotName();
			String pValue = bhBatchHis.getPValue();
			String eu = bhBatchHis.getEU();
			String cName = bhBatchHis.getCName();
			
			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMCode(materialID);
			batchRecord.setPMName(materialName);
			batchRecord.setLotNo(lotName);
			batchRecord.setRecordEvent("原料进料记录");
			batchRecord.setRecordContent(pValue);
			batchRecord.setUnit(eu);
			batchRecord.setRecordType("2");
			batchRecord.setPMCName(cName);
			
			count+=batchRecordDao.add(batchRecord);
		}
		
		return count;
	}

	@Override
	public int addPhaseFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//his 3420
		BatchRecord batchRecord=null;
		List<BHBatchHis> phaseList = bHBatchHisDao.getPhaseListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : phaseList) {
			batchRecord=new BatchRecord();
			String workOrderID = bhBatchHis.getWorkOrderID();
			String materialName = bhBatchHis.getMaterialName();
			String eu = bhBatchHis.getEU();
			String pMDisc = bhBatchHis.getPMDisc();
			String phaseDisc = bhBatchHis.getPhaseDisc();
			String recordID = bhBatchHis.getRecordID();

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMName(materialName);
			batchRecord.setRecordEvent("PHASE过程记录");
			batchRecord.setUnit(eu);
			batchRecord.setRecordType("8");
			batchRecord.setPMCName(pMDisc);
			batchRecord.setPhaseDisc(phaseDisc);
			batchRecord.setPhaseID(recordID);
			
			count+=batchRecordDao.add(batchRecord);
		}
		return count;
	}

	@Override
	public int addBatchFromBHBatch(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//uniqueid 98
		BatchRecord batchRecord=null;
		List<BHBatch> batchList = bHBatchDao.getListByWOIDList(workOrderIDList);
		for (BHBatch batch : batchList) {
			batchRecord=new BatchRecord();
			String workOrderID = batch.getWorkOrderID();

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setRecordEvent("批次过程记录");
			batchRecord.setRecordType("9");
			
			count+=batchRecordDao.add(batchRecord);
		}
		return 0;
	}
}
