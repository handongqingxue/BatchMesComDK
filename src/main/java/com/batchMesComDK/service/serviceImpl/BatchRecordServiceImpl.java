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
			
			//大料、小料在进料或投料时都可能产生偏差
			for (RecipePM recipePM : recipePMList) {//遍历物料参数，看看是否有偏差记录需要填充物料编码
				String rPMWorkOrderID = recipePM.getWorkOrderID();
				String pMCode = recipePM.getPMCode();
				String cName = recipePM.getCName();
				if(!StringUtils.isEmpty(cName)) {
					String cNamePre = cName.substring(0, cName.indexOf("_"));//wincc端生成的物料参数偏差记录不管是第几次后面都不带_,要截取物料参数名_之前的名称部分才能在批记录表里把物料编码更新进去
					if(brWorkOrderID.equals(rPMWorkOrderID)&&cNamePre.equals(brPMCName)&&String.valueOf(BatchRecord.PCJL).equals(batchRecord.getRecordType())) {
						batchRecordDao.updateDevPMCode(pMCode,cNamePre,rPMWorkOrderID);
						batchRecord.setPMCode(pMCode);
						break;
					}
				}
			}
			
			for (ManFeed manFeed : manFeedList) {//遍历人工投料信息，看看是否有偏差记录需要填充物料编码
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
	public int addTechFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;
		List<BatchRecord> batchRecordList=new ArrayList<>();
		BatchRecord batchRecord=null;
		List<BHBatchHis> techList = bHBatchHisDao.getTechListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : techList) {
			batchRecord=new BatchRecord();
			
			String workOrderID = bhBatchHis.getWorkOrderID();
			String lclTime = bhBatchHis.getLclTime();
			String descript = bhBatchHis.getDescript();
			String batchID = bhBatchHis.getBatchID();
			String pValue = bhBatchHis.getPValue();
			String phaseDisc = bhBatchHis.getPhaseDisc();
			String pMDisc = bhBatchHis.getPMDisc();

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMName(descript);//Descript BatchRecordTr
			batchRecord.setLotNo(batchID);//pro开头的批次号
			batchRecord.setRecordEvent(BatchRecord.GCCSJL_TEXT);
			batchRecord.setRecordContent(pValue);//phase batch是时间跨度
			batchRecord.setRecordStartTime(lclTime);
			batchRecord.setRecordEndTime(lclTime);
			batchRecord.setRecordType(BatchRecord.GCCSJL+"");
			batchRecord.setPMCName(phaseDisc+pMDisc);
			
			batchRecordList.add(batchRecord);
		}
		
		if(batchRecordList.size()>0) {
			if(batchRecordDao.getExistBRCountByRE(workOrderIDList, BatchRecord.GCCSJL_TEXT)>0)
				batchRecordDao.delExistBRListByRE(workOrderIDList,BatchRecord.GCCSJL_TEXT);
			count=batchRecordDao.addFromList(batchRecordList);
		}
		return count;
	}
	
	@Override
	public int addMaterialFromBHBatchHis(List<String> workOrderIDList) {
		// TODO Auto-generated method stub
		int count=0;
		List<BatchRecord> batchRecordList=new ArrayList<>();
		BatchRecord batchRecord=null;
		List<BHBatchHis> materialList = bHBatchHisDao.getMaterialListByWOIDList(workOrderIDList);
		List<BHBatchHis> materialCompleteList = bHBatchHisDao.getMaterialCompleteListByWOIDList(workOrderIDList);
		for (BHBatchHis bhBatchHis : materialList) {
			batchRecord=new BatchRecord();
			
			String workOrderID = bhBatchHis.getWorkOrderID();
			String recordStartTime = bhBatchHis.getLclTime();
			String pMCode = bhBatchHis.getPMCode();
			String descript = bhBatchHis.getDescript();
			String batchID = bhBatchHis.getBatchID();
			String pValue = bhBatchHis.getPValue();
			String eu = bhBatchHis.getEU();
			String cNameMes = bhBatchHis.getCNameMes();
			String feedPort = bhBatchHis.getFeedPort();
			String phase = bhBatchHis.getPhase();
			String recipe = bhBatchHis.getRecipe();
			String recordEndTime=getRecordEndTime(workOrderID,recipe,phase,materialCompleteList);
			
			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setPMCode(pMCode);
			batchRecord.setPMName(descript);//Descript BatchRecordTr
			batchRecord.setLotNo(batchID);//pro开头的批次号
			batchRecord.setRecordEvent(BatchRecord.WLCSJL_TEXT);
			batchRecord.setRecordContent(pValue);//phase batch是时间跨度
			batchRecord.setUnit(eu);
			batchRecord.setRecordStartTime(recordStartTime);
			batchRecord.setRecordEndTime(recordEndTime);
			batchRecord.setRecordType(BatchRecord.WLCSJL+"");
			batchRecord.setPMCName(cNameMes);
			batchRecord.setFeedPort(feedPort);
			
			batchRecordList.add(batchRecord);
			
			//count+=batchRecordDao.add(batchRecord);
		}
		
		System.out.println("batchRecordList.size()==="+batchRecordList.size());
		if(batchRecordList.size()>0) {
			if(batchRecordDao.getExistBRCountByRE(workOrderIDList, BatchRecord.WLCSJL_TEXT)>0)
				batchRecordDao.delExistBRListByRE(workOrderIDList,BatchRecord.WLCSJL_TEXT);
			count=batchRecordDao.addFromList(batchRecordList);
		}
		return count;
	}
	
	public String getRecordEndTime(String workOrderID, String recipe, String phase, List<BHBatchHis> materialCompleteList) {
		String recordEndTime=null;
		
		for (BHBatchHis materialComplete : materialCompleteList) {
			String mcWorkOrderID=materialComplete.getWorkOrderID(); 
			String mcRecipe=materialComplete.getRecipe();
			String mcPhase=materialComplete.getPhase();
			if(workOrderID.equals(mcWorkOrderID)&&recipe.equals(mcRecipe)&&phase.equals(mcPhase)) {
				recordEndTime = materialComplete.getLclTime();
				break;
			}
		}
		return recordEndTime;
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
			Integer phaseStep = phase.getPhaseStep();
			String lclStartTime = phase.getLclStartTime();
			String lclCompleteTime = phase.getLclCompleteTime();
			String recipe = phase.getRecipe();
			String recordContent = null;
			if(!StringUtils.isEmpty(lclStartTime)&&!StringUtils.isEmpty(lclCompleteTime))
				recordContent = DateUtil.getTimeBetween(lclStartTime,lclCompleteTime,DateUtil.SECONDS)+"S";

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setLotNo(batchID);
			batchRecord.setRecordEvent(BatchRecord.PGCJL_TEXT);
			batchRecord.setRecordContent(recordContent);
			batchRecord.setUnit(eu);
			batchRecord.setRecordType(BatchRecord.PGCJL+"");
			if(phaseID.startsWith("TB"))
				batchRecord.setPhaseDisc(phaseDisc+recipe.substring(recipe.lastIndexOf(":")+1, recipe.lastIndexOf("-")));
			else
				batchRecord.setPhaseDisc(phaseDisc);
			batchRecord.setPhaseID(phaseID);
			batchRecord.setPhaseStep(phaseStep);
			batchRecord.setRecordStartTime(lclStartTime);
			batchRecord.setRecordEndTime(lclCompleteTime);
			
			batchRecordList.add(batchRecord);
			
			//count+=batchRecordDao.add(batchRecord);
		}
		System.out.println("batchRecordListSize==="+batchRecordList.size());
		if(batchRecordList.size()>0) {
			if(batchRecordDao.getExistBRCountByRE(workOrderIDList, BatchRecord.PGCJL_TEXT)>0) {
				int c = batchRecordDao.delExistBRListByRE(workOrderIDList,BatchRecord.PGCJL_TEXT);
				System.out.println("c==="+c);
			}
			count=batchRecordDao.addFromList(batchRecordList);
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
			String batchid = batch.getBatchid();
			String starttimebatchlist = batch.getStarttimebatchlist();
			String endtimebatchlist = batch.getEndtimebatchlist();
			String recordContent = null;
			if(!StringUtils.isEmpty(starttimebatchlist)&&!StringUtils.isEmpty(endtimebatchlist))
				recordContent = DateUtil.getTimeBetween(starttimebatchlist,endtimebatchlist,DateUtil.SECONDS)+"S";

			batchRecord.setWorkOrderID(workOrderID);
			batchRecord.setLotNo(batchid);
			batchRecord.setRecordEvent(BatchRecord.PCGCJL_TEXT);
			batchRecord.setRecordContent(recordContent);
			batchRecord.setRecordType(BatchRecord.PCGCJL+"");
			batchRecord.setRecordStartTime(starttimebatchlist);
			batchRecord.setRecordEndTime(endtimebatchlist);
			
			count+=batchRecordDao.add(batchRecord);
		}
		return count;
	}
}
