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
	ManFeedMapper manFeedDao;
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
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderIDList(workOrderIDList);
		List<ManFeed> manFeedList = manFeedDao.getListByWorkOrderIDList(workOrderIDList);
		List<BatchRecord> batchRecordList = batchRecordDao.getListByWorkOrderIDList(workOrderIDList,recordTypeList);
		
		for (BatchRecord batchRecord : batchRecordList) {
			String brWorkOrderID = batchRecord.getWorkOrderID();
			String brPMCName=batchRecord.getPMCName();
			
			for (RecipePM recipePM : recipePMList) {
				String rPMWorkOrderID = recipePM.getWorkOrderID();
				String pMCode = recipePM.getPMCode();
				String cName = recipePM.getCName();
				if(!StringUtils.isEmpty(cName)) {
					if(brWorkOrderID.equals(rPMWorkOrderID)&&cName.equals(brPMCName)&&String.valueOf(BatchRecord.PCJL).equals(batchRecord.getRecordType())) {
						batchRecordDao.updateDevPMCode(pMCode,cName,rPMWorkOrderID);
						batchRecord.setPMCode(pMCode);
						break;
					}
				}
			}
			
			for (ManFeed manFeed : manFeedList) {
				String mfWorkOrderID = manFeed.getWorkOrderID();
				String materialCode = manFeed.getMaterialCode();
				String materialName = manFeed.getMaterialName();
				if(!StringUtils.isEmpty(materialName)) {
					if(brWorkOrderID.equals(mfWorkOrderID)&&materialName.equals(brPMCName)&&String.valueOf(BatchRecord.PCJL).equals(batchRecord.getRecordType())) {
						batchRecordDao.updateDevPMCode(materialCode,materialName,mfWorkOrderID);
						batchRecord.setPMCode(materialCode);
						break;
					}
				}
			}
		}
		return batchRecordList;
	}

	@Override
	public int addMaterialFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//his 140
		List<BatchRecord> batchRecordList=new ArrayList<>();
		BatchRecord batchRecord=null;
		List<BHBatchHis> materialList = bHBatchHisDao.getMaterialListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : materialList) {
			batchRecord=new BatchRecord();
			
			String workOrderID = bhBatchHis.getWorkOrderID();
			String lclTime = bhBatchHis.getLclTime();
			String pMCode = bhBatchHis.getPMCode();
			String descript = bhBatchHis.getDescript();
			String batchID = bhBatchHis.getBatchID();
			String pValue = bhBatchHis.getPValue();
			String eu = bhBatchHis.getEU();
			String cName = bhBatchHis.getCName();
			String feedPort = bhBatchHis.getFeedPort();
			
			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMCode(pMCode);
			batchRecord.setPMName(descript);//Descript BatchRecordTr
			batchRecord.setLotNo(batchID);//pro开头的批次号
			batchRecord.setRecordEvent("原料进料记录");
			batchRecord.setRecordContent(pValue);//phase batch是时间跨度
			batchRecord.setUnit(eu);
			batchRecord.setRecordStartTime(lclTime);
			batchRecord.setRecordEndTime(lclTime);
			batchRecord.setRecordType("2");
			batchRecord.setPMCName(cName);
			batchRecord.setFeedPort(feedPort);
			
			batchRecordList.add(batchRecord);
			
			//count+=batchRecordDao.add(batchRecord);
		}
		
		System.out.println("batchRecordList.size()==="+batchRecordList.size());
		if(batchRecordList.size()>0)
			count=batchRecordDao.addFromList(batchRecordList);
		return count;
	}

	@Override
	public int addPhaseFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;//his 3420
		List<BatchRecord> batchRecordList=new ArrayList<>();
		BatchRecord batchRecord=null;
		List<BHBatchHis> phaseList = new ArrayList<>();
		List<BHBatchHis> bhBatchHisList = bHBatchHisDao.getPhaseListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : bhBatchHisList) {
			String recipe = bhBatchHis.getRecipe();
			String phaseID=recipe.substring(recipe.lastIndexOf("\\")+1, recipe.lastIndexOf(":"));
			//if(checkIfExistInList(phaseID,phaseList))
				//continue;
			//else {
				bhBatchHis.setPhaseID(phaseID);
				phaseList.add(bhBatchHis);
			//}
		}
		
		List<BHBatchHis> lclTimePhaseList=bHBatchHisDao.getPhaseLclTimeListByWOIDList(workOrderIDList);
		for (int i = 0; i < phaseList.size(); i++) {
			BHBatchHis phase = phaseList.get(i);
			for (int j = 0; j < lclTimePhaseList.size(); j++) {
				BHBatchHis lclTimePhase = lclTimePhaseList.get(j);
				
				if(lclTimePhase.getRecipe().equals(phase.getRecipe())
				 &&lclTimePhase.getPhase().equals(phase.getPhase())
				 &&lclTimePhase.getDescript().startsWith("State Changed:")) {
					String pValue = lclTimePhase.getPValue();
					if("STARTING".equals(pValue)) {
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
			String recipe = phase.getRecipe();
			String recordContent = null;
			if(!StringUtils.isEmpty(lclStartTime)&&!StringUtils.isEmpty(lclCompleteTime))
				recordContent = DateUtil.getTimeBetween(lclStartTime,lclCompleteTime,DateUtil.SECONDS)+"S";

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setLotNo(batchID);
			batchRecord.setRecordEvent("PHASE过程记录");
			batchRecord.setRecordContent(recordContent);
			batchRecord.setUnit(eu);
			batchRecord.setRecordType("8");
			if(phaseID.startsWith("TB"))
				batchRecord.setPhaseDisc(phaseDisc+recipe.substring(recipe.lastIndexOf(":")+1, recipe.lastIndexOf("-")));
			else
				batchRecord.setPhaseDisc(phaseDisc);
			batchRecord.setPhaseID(phaseID);
			batchRecord.setRecordStartTime(lclStartTime);
			batchRecord.setRecordEndTime(lclCompleteTime);
			
			batchRecordList.add(batchRecord);
			
			//count+=batchRecordDao.add(batchRecord);
		}
		return batchRecordDao.addFromList(batchRecordList);
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
			String batchid = batch.getBatchid();
			String starttimebatchlist = batch.getStarttimebatchlist();
			String endtimebatchlist = batch.getEndtimebatchlist();
			String recordContent = null;
			if(!StringUtils.isEmpty(starttimebatchlist)&&!StringUtils.isEmpty(endtimebatchlist))
				recordContent = DateUtil.getTimeBetween(starttimebatchlist,endtimebatchlist,DateUtil.SECONDS)+"S";

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setLotNo(batchid);
			batchRecord.setRecordEvent("批次过程记录");
			batchRecord.setRecordContent(recordContent);
			batchRecord.setRecordType("9");
			batchRecord.setRecordStartTime(starttimebatchlist);
			batchRecord.setRecordEndTime(endtimebatchlist);
			
			count+=batchRecordDao.add(batchRecord);
		}
		return count;
	}
}
