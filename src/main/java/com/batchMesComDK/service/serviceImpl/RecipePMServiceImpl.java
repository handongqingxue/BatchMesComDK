package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

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
				rPM.setCName(cName);
				System.out.println("11111111111111");
				
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
	public int updateDosageByPMParam(List<RecipePM> recipePMList) {
		// TODO Auto-generated method stub
		int count=0;
		for (RecipePM recipePM : recipePMList) {
			String pMCode = recipePM.getPMCode();
			String pMName = recipePM.getPMName();
			String dosage = recipePM.getDosage();
			count+=recipePMDao.updateDosageByPMParam(pMCode,pMName,dosage);
		}
		return count;
	}
}
