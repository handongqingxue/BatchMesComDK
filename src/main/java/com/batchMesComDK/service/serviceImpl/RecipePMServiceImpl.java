package com.batchMesComDK.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
	public int addFromTMP(String workOrderID, String productCode, String productName) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM_TMP> rPMTmpList=recipePM_TMPDao.getByProductParam(productCode,productName);
		
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
			rPM.setFeedPort(rPMTmp.getFeedPort());
			rPM.setMaterialSV(rPMTmp.getMaterialSV());
			rPM.setHH(rPMTmp.getHH());
			rPM.setLL(rPMTmp.getLL());
			rPM.setPMSort(rPMTmp.getPMSort());
			rPM.setStep(rPMTmp.getStep());
			
			count+=recipePMDao.add(rPM);
		}
		
		return count;
	}

	@Override
	public int addFromWORecipePMList(String workOrderID, List<RecipePM> recipePMList) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			RecipePM rPM=null;
			for (int i=0;i<recipePMList.size();i++) {
				RecipePM recipePM = recipePMList.get(i);
				rPM=new RecipePM();
				String pMCode = recipePM.getPMCode();
				
				String pMName1=null;
				String cName = recipePM.getCName();
				if("山梨醇进料量".equals(cName))
					pMName1="AM_SL";
				else if("摩擦型SIO2进料量".equals(cName))
					pMName1="AM_SIO22";
				else if("PM1真空度".equals(cName))
					pMName1="VD_PM1";
				recipePM.setPMName(pMName1);
				
				String pMName = recipePM.getPMName();
				rPM.setPMCode(pMCode);
				rPM.setPMName(pMName);
				if(!StringUtils.isEmpty(pMCode))
					rPM.setLot("B"+pMCode.substring(1));
				rPM.setDosage(recipePM.getDosage());
				String unit=null;
				if("T1".equals(pMName))
					unit="Min";
				else if("VD1".equals(pMName))
					unit="Mpa";
				else
					unit="KG";
				rPM.setUnit(unit);
				
				String hLimit=null;
				if("SIO2".equals(pMName))
					hLimit="120";
				else if("GL".equals(pMName))
					hLimit="220";
				else if("T1".equals(pMName))
					hLimit="320";
				else if("VD1".equals(pMName))
					hLimit="0";
				else if("DCHP".equals(pMName))
					hLimit="206";
				rPM.setHLimit(hLimit);
				
				String lLimit=null;
				if("SIO2".equals(pMName))
					lLimit="80";
				else if("GL".equals(pMName))
					lLimit="180";
				else if("T1".equals(pMName))
					lLimit="280";
				else if("VD1".equals(pMName))
					lLimit="-0.2";
				else if("DCHP".equals(pMName))
					lLimit="198";
				rPM.setLLimit(lLimit);
				
				String pMType=null;
				if("SIO2".equals(pMName)||"GL".equals(pMName)||"DCHP".equals(pMName))
					pMType="1";
				else if("T1".equals(pMName)||"VD1".equals(pMName))
					pMType="2";
				rPM.setPMType(pMType);
				rPM.setWorkOrderID(workOrderID);
				
				/*
				String cName=null;
				if("SIO2".equals(pMName))
					cName="二氧化硅进料量";
				else if("GL".equals(pMName))
					cName="甘油";
				else if("T1".equals(pMName))
					cName="分散时间";
				else if("VD1".equals(pMName))
					cName="真空度";
				else if("DCHP_2".equals(pMName))
					cName="DCHP_2";
				else if("DCHP_3".equals(pMName))
					cName="DCHP_3";
				else if("DCHP".equals(pMName))
					cName="DCHP进料量";
				else if("ML".equals(pMName))
					cName="香精";
				else
					cName=pMName;
				*/
				rPM.setCName(cName);
				
				count+=recipePMDao.add(rPM);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@Override
	public List<RecipePM> getDLListByWorkOrderID(String workOrderID) {
		// TODO Auto-generated method stub
		List<RecipePM> dlRecipePMList = new ArrayList<RecipePM>();
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderID(workOrderID);
		for (RecipePM recipePM : recipePMList) {
			String feedPort = recipePM.getFeedPort();
			if(StringUtils.isEmpty(feedPort))
				dlRecipePMList.add(recipePM);
		}
		return dlRecipePMList;
	}

	@Override
	public int updateDosageByPMParam(String workOrderID, List<RecipePM> wodRecipePMList) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderID(workOrderID);
		Map<String, HashMap<String, Object>> recPMCountMap=initRecPMCountMap(recipePMList);
		for (RecipePM recipePM : recipePMList) {
			int id = recipePM.getID();
			String pMCode = recipePM.getPMCode();
			String dosage = recipePM.getDosage();
			String pMName = recipePM.getPMName();
			for (RecipePM wodRecipePM : wodRecipePMList) {
				String wodPMCode = wodRecipePM.getPMCode();
				String wodDosage = wodRecipePM.getDosage();
				if(pMCode.equals(wodPMCode)) {
					if(pMName.startsWith("AM_")) {
						HashMap<String, Object> recPMMap = recPMCountMap.get(wodPMCode);
						int rPMcount=Integer.valueOf(recPMMap.get("count").toString());
						if(rPMcount==1) {
							if(dosage!=wodDosage) {
								count+=recipePMDao.updateDosageByID(id,wodDosage);
								recPMMap.put("update", true);
								break;
							}
						}
						else {
							float dosageSum=Float.valueOf(recPMMap.get("dosage").toString());
							if(dosageSum!=Float.valueOf(wodDosage)) {
								boolean update = Boolean.valueOf(recPMMap.get("update").toString());
								if(!update) {
									int firstId=Integer.valueOf(recPMMap.get("firstId").toString());
									count+=recipePMDao.updateDosageByID(firstId,wodDosage+"x");
									recPMMap.put("update", true);
									break;
								}
							}
						}
					}
					else {
						if(dosage!=wodDosage)
							count+=recipePMDao.updateDosageByID(id,wodDosage);
					}
				}
			}
		}
		return count;
	}
	
	private Map<String, HashMap<String, Object>> initRecPMCountMap(List<RecipePM> recipePMList) {
		Map<String, HashMap<String, Object>> recPMCountMap=new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> recPMMap=null;
		for (RecipePM recipePM : recipePMList) {
			String pMName = recipePM.getPMName();
			if(!pMName.startsWith("AM_"))
				continue;
			String pMCode = recipePM.getPMCode();
			float dosage = Float.valueOf(recipePM.getDosage());
			HashMap<String, Object> pMCodeMap = recPMCountMap.get(pMCode);
			if(pMCodeMap==null) {
				Integer id = recipePM.getID();
				
				recPMMap=new HashMap<String, Object>();
				recPMMap.put("firstId", id);
				recPMMap.put("count", 1);
				recPMMap.put("dosage", dosage);
				recPMMap.put("update", false);
				
				recPMCountMap.put(pMCode, recPMMap);
			}
			else {
				int count=Integer.valueOf(pMCodeMap.get("count").toString());
				//System.out.println("count==="+count);
				count++;
				pMCodeMap.put("count", count);
				
				float preDosage=Float.valueOf(pMCodeMap.get("dosage").toString());
				//System.out.println("dosage==="+dosage);
				preDosage+=dosage;
				pMCodeMap.put("dosage", preDosage);
			}
		}
		return recPMCountMap;
	}
}
