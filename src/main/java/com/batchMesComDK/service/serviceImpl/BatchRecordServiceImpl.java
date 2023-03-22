package com.batchMesComDK.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;
import com.batchMesComDK.util.DateUtil;

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
			String pMCode = bhBatchHis.getPMCode();
			String descript = bhBatchHis.getDescript();
			String batchID = bhBatchHis.getBatchID();
			String pValue = bhBatchHis.getPValue();
			String eu = bhBatchHis.getEU();
			String pMDisc = bhBatchHis.getPMDisc();
			String feedPort = bhBatchHis.getFeedPort();
			
			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMCode(pMCode);
			batchRecord.setPMName(descript);//Descript BatchRecordTr
			batchRecord.setLotNo(batchID);//pro开头的批次号
			batchRecord.setRecordEvent("原料进料记录");
			batchRecord.setRecordContent(pValue);//phase batch是时间跨度
			batchRecord.setUnit(eu);
			batchRecord.setRecordType("2");
			batchRecord.setPMCName(pMDisc);
			batchRecord.setFeedPort(feedPort);
			
			count+=batchRecordDao.add(batchRecord);
		}
		
		return count;
	}

	@Override
	public int addPhaseFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//his 3420
		BatchRecord batchRecord=null;
		List<BHBatchHis> phaseList = new ArrayList<>();
		List<BHBatchHis> bhBatchHisList = bHBatchHisDao.getPhaseListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : bhBatchHisList) {
			String recipe = bhBatchHis.getRecipe();
			String phaseID=recipe.substring(recipe.lastIndexOf("\\")+1, recipe.lastIndexOf(":"));
			if(checkIfExistInList(phaseID,phaseList))
				continue;
			else {
				bhBatchHis.setPhaseID(phaseID);
				phaseList.add(bhBatchHis);
			}
		}
		
		List<BHBatchHis> lclTimePhaseList=bHBatchHisDao.getPhaseLclTimeListByWOIDList(workOrderIDList);
		for (int i = 0; i < phaseList.size(); i++) {
			BHBatchHis phase = phaseList.get(i);
			for (int j = 0; j < lclTimePhaseList.size(); j++) {
				BHBatchHis lclTimePhase = lclTimePhaseList.get(j);
				
				if(lclTimePhase.getPhase().equals(phase.getPhase())&&"ADD_PM1_51".equals(phase.getPhase())) {
					String pValue = lclTimePhase.getPValue();
					if("START".equals(pValue)) {
						phase.setLclStartTime(lclTimePhase.getLclTime());
						//break;
					}
					else if("COMPLETE".equals(pValue)) {
						phase.setLclCompleteTime(lclTimePhase.getLclTime());
						//break;
					}
				}
			}
		}
		
		for (BHBatchHis phase : phaseList) {
			batchRecord=new BatchRecord();
			String workOrderID = phase.getWorkOrderID();
			String batchID = phase.getBatchID();
			String eu = phase.getEU();
			String phaseDisc = phase.getPhaseDisc();
			String phaseID = phase.getPhaseID();
			String lclStartTime = phase.getLclStartTime();
			String lclCompleteTime = phase.getLclCompleteTime();
			String recordContent = null;
			if(!StringUtils.isEmpty(lclStartTime)&&!StringUtils.isEmpty(lclCompleteTime))
				recordContent = DateUtil.getTimeBetween(lclStartTime,lclCompleteTime,DateUtil.SECONDS)+"S";

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setLotNo(batchID);
			batchRecord.setRecordEvent("PHASE过程记录");
			batchRecord.setRecordContent(recordContent);
			batchRecord.setUnit(eu);
			batchRecord.setRecordType("8");
			batchRecord.setPhaseDisc(phaseDisc);
			batchRecord.setPhaseID(phaseID);
			batchRecord.setRecordStartTime(lclStartTime);
			batchRecord.setRecordEndTime(lclCompleteTime);
			count+=batchRecordDao.add(batchRecord);
		}
		return count;
	}
	
	private boolean checkIfExistInList(String phaseID, List<BHBatchHis> phaseList) {
		boolean exist = false;
		for (BHBatchHis phase : phaseList) {
			if(phase.getPhaseID().equals(phaseID)) {
				exist=true;
				break;
			}
		}
		return exist;
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
