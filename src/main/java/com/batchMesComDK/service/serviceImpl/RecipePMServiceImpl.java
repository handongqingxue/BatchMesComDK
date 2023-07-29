package com.batchMesComDK.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public int addFromTMP(String workOrderID, String recipeID) {
		// TODO Auto-generated method stub
		//System.out.println("recipeIDlength==="+recipeID.length());
		//System.out.println("recipeID==="+recipeID);
		int count=0;
		//List<RecipePM_TMP> rPMTmpList=recipePM_TMPDao.getByProductParam(productCode,productName);
		List<RecipePM_TMP> rPMTmpList=recipePM_TMPDao.getByRecipeID(recipeID);
		//System.out.println("rPMTmpListsize==="+rPMTmpList.size());
		
		List<RecipePM> rPMList=new ArrayList<>();
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
			rPM.setHH(rPMTmp.getHH());
			rPM.setLL(rPMTmp.getLL());
			rPM.setPMSort(rPMTmp.getPMSort());
			rPM.setStep(rPMTmp.getStep());
			
			rPMList.add(rPM);
			//count+=recipePMDao.add(rPM);
		}
		
		count=recipePMDao.addFromList(rPMList);
		
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
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderID(workOrderID);
		return recipePMList;
	}

	@Override
	public int updateDosageXByPMParam(String workOrderID, List<RecipePM> wodRecipePMList) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderID(workOrderID);
		Map<String, HashMap<String, Object>> recPMCodeMap=initRecPMCodeMap(recipePMList);
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
						HashMap<String, Object> recPMMap = recPMCodeMap.get(wodPMCode);
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

	@Override
	public int updateDosageLastByPMParam(String workOrderID, List<RecipePM> wodRecipePMList) {
		// TODO Auto-generated method stub
		int count=0;
		List<RecipePM> recipePMList = recipePMDao.getListByWorkOrderID(workOrderID);
		Map<String, HashMap<String, Object>> recPMCodeMap=initRecPMCodeMap(recipePMList);
		
		Map<String,HashMap<String,Integer>> recPMAddCountIdMap=initAddCountIdMap(recipePMList);
		Map<String,Integer> recPMCurAddCountMap=initCurAddCountMap(recPMCodeMap);
		Map<String,Float> preDosageSumMap=initPreDosageSumMap(recPMCodeMap);
		Map<String,Boolean> addFinishMap=initAddFinishMap(recPMCodeMap);
		
		for (RecipePM recipePM : recipePMList) {
			int id = recipePM.getID();
			String pMCode = recipePM.getPMCode();
			String dosage = recipePM.getDosage();
			String pMName = recipePM.getPMName();
			String pMType = recipePM.getPMType();
			for (RecipePM wodRecipePM : wodRecipePMList) {
				String wodPMCode = wodRecipePM.getPMCode();
				String wodDosage = wodRecipePM.getDosage();
				if(pMCode.equals(wodPMCode)) {
					//if(pMName.startsWith("AM_")) {
					if("1".equals(pMType)) {
						HashMap<String, Object> recPMMap = recPMCodeMap.get(wodPMCode);
						int rPMcount=Integer.valueOf(recPMMap.get("count").toString());//判断每种物料的加料次数
						if(rPMcount==1) {//只加一次直接更新重量
							if(dosage!=wodDosage) {
								count+=recipePMDao.updateDosageByID(id,wodDosage);
								break;
							}
						}
						else {//加多次情况下执行下面逻辑
							float dosageSum=Float.valueOf(recPMMap.get("dosage").toString());//获取标准配方里组态的加料总量
							if(dosageSum!=Float.valueOf(wodDosage)) {//与下单时该物料的总量对比，有变化的话走这里的逻辑
								Boolean addFinish = addFinishMap.get(pMCode);//根据物料编码获取是否更新数量完成
								if(addFinish)//若已更新过最后一次加料的数量，就不往下执行，直接进入下一轮循环
									continue;
								
								int recPMCurAddCount = recPMCurAddCountMap.get(wodPMCode);//根据物料编码，获取当前加料次数
								recPMCurAddCount++;
								float preDosageSum = preDosageSumMap.get(wodPMCode);//获取该物料当前已加了多少总量
								if(recPMCurAddCount==rPMcount) {//若当前加料次数等于需要加的总次数，说明是最后一次加料，就把剩余需要加的重量更新在最后一次的重量里
									float lastDosage = Float.valueOf(wodDosage)-preDosageSum;//下单里的总量-前面几次加的总量和就是最后一次需要更新的重量
									System.out.println("lastDosage==="+lastDosage);
									count+=recipePMDao.updateDosageByID(id,lastDosage+"");
								}
								else {//在未循环到最后一次加料的情况下，执行下面逻辑
									float nxtDosageSum = preDosageSum+Float.valueOf(dosage);//之前的总量+这次要加的重量=这次的总量
									if(Float.valueOf(wodDosage)>preDosageSum&&Float.valueOf(wodDosage)<=nxtDosageSum) {//若下单里的物料总量>之前的总量,<这次的总量,说明加到这次就加完了
										if(Float.valueOf(wodDosage)<nxtDosageSum) {
											float lastDosage = Float.valueOf(wodDosage)-preDosageSum;
											count+=recipePMDao.updateDosageByID(id,lastDosage+"");
										}
										if(recPMCurAddCount<rPMcount) {//若当前的投料次数<加料总次数,说明这次不是最后一次加料。但料已加完,后面就不加了,后面的重量都为0
											clearAfterDosage(recPMCurAddCount,rPMcount,pMCode,recPMAddCountIdMap);
										}
										addFinishMap.put(pMCode, true);//加完料就更新加料完成标识，下次循环到该物料就不执行加料逻辑了
									}
									else {
										preDosageSumMap.put(wodPMCode, nxtDosageSum);
									}
								}
								
								recPMCurAddCountMap.put(wodPMCode, recPMCurAddCount);//更新当前是第几次加料
								break;
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

	private void clearAfterDosage(int recPMCurAddCount, int rPMcount, String pMCode, Map<String, HashMap<String, Integer>> recPMAddCountIdMap) {
		List<Integer> addCountIdList=new ArrayList<Integer>();
		HashMap<String, Integer> pMCodeMap = recPMAddCountIdMap.get(pMCode);
		for (int i = recPMCurAddCount+1; i <= rPMcount; i++) {
			Integer addCountId = pMCodeMap.get("addCountId"+i);
			addCountIdList.add(addCountId);
		}
		int i=recipePMDao.clearDosageByIdList(addCountIdList);
	}

	/**
	 * 初始化同一个工单里每种物料的加料总量(初始化是0，每次加料时总量会发生变化)
	 * @param recPMCodeMap
	 * @return
	 */
	public Map<String,Float> initPreDosageSumMap(Map<String, HashMap<String, Object>> recPMCodeMap) {
		Map<String,Float> preDosageSumMap=new HashMap<String, Float>();
		Set<String> recPMKeySet = recPMCodeMap.keySet();
		for (String recPMKey : recPMKeySet) {
			preDosageSumMap.put(recPMKey, (float)0.0);
		}
		return preDosageSumMap;
	}
	
	/**
	 * 初始化同一个工单里每种物料是否添加完成的Map
	 * @param recPMCodeMap
	 * @return
	 */
	private Map<String, Boolean> initAddFinishMap(Map<String, HashMap<String, Object>> recPMCodeMap) {
		// TODO Auto-generated method stub
		Map<String,Boolean> addFinishMap=new HashMap<String, Boolean>();
		Set<String> recPMKeySet = recPMCodeMap.keySet();
		for (String recPMKey : recPMKeySet) {
			addFinishMap.put(recPMKey, false);
		}
		return addFinishMap;
	}
	
	/**
	 * 初始化同一个工单里每种物料每次添加时对应的参数记录id
	 * @param recipePMList
	 * @return
	 */
	private Map<String, HashMap<String, Integer>> initAddCountIdMap(List<RecipePM> recipePMList) {
		// TODO Auto-generated method stub
		Map<String, HashMap<String, Integer>> recPMAddCountIdMap=new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> addCountIdMap=null;
		for (RecipePM recipePM : recipePMList) {
			String pMCode = recipePM.getPMCode();
			HashMap<String, Integer> pMCodeMap = recPMAddCountIdMap.get(pMCode);
			if(pMCodeMap==null) {
				addCountIdMap=new HashMap<String, Integer>();
				addCountIdMap.put("addCount", 1);
				addCountIdMap.put("addCountId1", recipePM.getID());

				recPMAddCountIdMap.put(pMCode, addCountIdMap);
			}
			else {
				int addCount=Integer.valueOf(pMCodeMap.get("addCount").toString());
				//System.out.println("addCount==="+addCount);
				addCount++;
				pMCodeMap.put("addCount", addCount);
				
				pMCodeMap.put("addCountId"+addCount, recipePM.getID());
			}
		}
		return recPMAddCountIdMap;
	}
	
	/**
	 * 初始化同一个工单里每种物料的当前添加次数(最初是0，用到的时候才记录是第几次添加)
	 * @param recPMCodeMap
	 * @return
	 */
	public Map<String,Integer> initCurAddCountMap(Map<String, HashMap<String, Object>> recPMCodeMap) {
		Map<String,Integer> recPMCurAddCountMap=new HashMap<String, Integer>();
		Set<String> recPMKeySet = recPMCodeMap.keySet();
		for (String recPMKey : recPMKeySet) {
			System.out.println("recPMKey==="+recPMKey);
			recPMCurAddCountMap.put(recPMKey, 0);
		}
		return recPMCurAddCountMap;
	}
	
	/**
	 * 初始化同一个工单里每种物料的数量、重量之和
	 * @param recipePMList
	 * @return
	 */
	private Map<String, HashMap<String, Object>> initRecPMCodeMap(List<RecipePM> recipePMList) {
		Map<String, HashMap<String, Object>> recPMCountMap=new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> recPMMap=null;
		for (RecipePM recipePM : recipePMList) {
			String pMName = recipePM.getPMName();
			String pMType = recipePM.getPMType();
			//if(!pMName.startsWith("AM_"))
			if(!"1".equals(pMType))
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
