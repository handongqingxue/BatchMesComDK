package com.batchMesComDK.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	@Autowired
	WorkOrderMapper workOrderDao;
	@Autowired
	RecipeHeaderMapper recipeHeaderDao;
	private SimpleDateFormat formulaIdSDF = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int add(WorkOrder wo) {
		// TODO Auto-generated method stub
		return workOrderDao.add(wo);
	}

	@Override
	public int updateStateById(Integer state,Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.updateStateById(state,id);
	}

	@Override
	public int updateStateByWorkOrderID(Integer state,String workOrderID) {
		// TODO Auto-generated method stub
		return workOrderDao.updateStateByWorkOrderID(state,workOrderID);
	}

	@Override
	public int updateStateByFormulaId(Integer state, String formulaId) {
		// TODO Auto-generated method stub
		return workOrderDao.updateStateByFormulaId(state,formulaId);
	}

	@Override
	public int updateClearFaultByFormulaId(Integer clearFault, String formulaId) {
		// TODO Auto-generated method stub
		return workOrderDao.updateClearFaultByFormulaId(clearFault,formulaId);
	}

	@Override
	public List<WorkOrder> getKeepWatchList(List<String> woEndIDList) {
		// TODO Auto-generated method stub
		String states = WorkOrder.CSQRWB+","+WorkOrder.BQD+","+WorkOrder.BYX+","+WorkOrder.BQX+","+WorkOrder.BZT+","+WorkOrder.GDLXZXQX+","+WorkOrder.GDSGCJ+","+WorkOrder.CXTL;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		List<WorkOrder> kwwoList=null;
		if(woEndIDList==null)//若工单上次id集合没有，则调用这个方法
			kwwoList=workOrderDao.getListByStateList(stateList);
		else
			kwwoList=workOrderDao.getListByWOEndIDStateList(woEndIDList,stateList);
		return kwwoList;
	}

	@Override
	public List<WorkOrder> getSendToMesList() {
		// TODO Auto-generated method stub
		String states = WorkOrder.BYWZZ+","+WorkOrder.BJS;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		return workOrderDao.getSendToMesListByStateList(stateList);
	}

	@Override
	public List<WorkOrder> getSendToMesListTest(List<String> workOrderIDList, Integer hoursAgo) {
		// TODO Auto-generated method stub
		String states = WorkOrder.BYWZZ+","+WorkOrder.BJS;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		return workOrderDao.getSendToMesListByStateListTest(workOrderIDList,stateList,hoursAgo);
	}

	@Override
	public List<WorkOrder> getEndList() {
		// TODO Auto-generated method stub
		String states = WorkOrder.BYWZZ+","+WorkOrder.BJS;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		return workOrderDao.getEndListByStateList(stateList);
	}

	@Override
	public int edit(WorkOrder wo) {
		// TODO Auto-generated method stub
		return workOrderDao.edit(wo);
	}

	@Override
	public WorkOrder getById(Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.getById(id);
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return workOrderDao.deleteByList(idList);
	}

	@Override
	public int updateZGIDByWorkOrder(String zGID, String workOrder) {
		// TODO Auto-generated method stub
		return workOrderDao.updateZGIDByWorkOrderID(zGID,workOrder);
	}

	@Override
	public String createFormulaIdByDateYMD(String identifier) {
		// TODO Auto-generated method stub
		String identifierPre = "";
		if(!StringUtils.isEmpty(identifier)) {
			identifierPre=identifier.substring(0, 3);
		}
		String formulaIdDate = identifierPre+"_BATCH"+formulaIdSDF.format(new Date());
		String countStr = workOrderDao.getMaxFormulaIdNumByFormulaIdDate(formulaIdDate);
		System.out.println("countStr==="+countStr);
		Integer count;
		if(StringUtils.isEmpty(countStr)) {
			count=0;
		}
		else
			count=Integer.valueOf(countStr);
		
		String formulaIdXhStr=null;
		int formulaIdXh=count+1;
		if(formulaIdXh<10)
			formulaIdXhStr="00"+formulaIdXh;
		else if(formulaIdXh<100)
			formulaIdXhStr="0"+formulaIdXh;
		return formulaIdDate+formulaIdXhStr;
	}

	@Override
	public String getFormulaIdByWOID(String workOrderID) {
		// TODO Auto-generated method stub
		return workOrderDao.getFormulaIdByWOID(workOrderID);
	}

	@Override
	public int updateStateByWOIDs(Integer state, String workOrders) {
		// TODO Auto-generated method stub
		String[] workOrderArr = workOrders.split(",");
		List<String> workOrderIDList = Arrays.asList(workOrderArr);
		return workOrderDao.updateStateByWOIDList(state,workOrderIDList);
	}

	@Override
	public Integer getCountByByWOIDs(String workOrders) {
		// TODO Auto-generated method stub
		String[] workOrderArr = workOrders.split(",");
		List<String> workOrderIDList = Arrays.asList(workOrderArr);
		return workOrderDao.getCountByByWOIDList(workOrderIDList);
	}

	@Override
	public List<Integer> getStateListByWOIDs(String workOrders) {
		// TODO Auto-generated method stub
		String[] workOrderArr = workOrders.split(",");
		List<String> workOrderIDList = Arrays.asList(workOrderArr);
		return workOrderDao.getStateListByWOIDList(workOrderIDList);
	}

	@Override
	public int updateBatchCreatedById(boolean batchCreated, Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.updateBatchCreatedById(batchCreated,id);
	}

	@Override
	public Boolean getBatchCreatedById(Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.getBatchCreatedById(id);
	}

	@Override
	public int updateApiFailDataById(String apiFailData, Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.updateApiFailDataById(apiFailData,id);
	}

	@Override
	public String getApiFailDataById(Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.getApiFailDataById(id);
	}

	@Override
	public int updateSendBRToMesByWorkOrderID(int sendBRToMes, String workOrderID) {
		// TODO Auto-generated method stub
		return workOrderDao.updateSendBRToMesByWorkOrderID(sendBRToMes,workOrderID);
	}

	@Override
	public List<String> getUnSendBRToMesWOIDList() {
		// TODO Auto-generated method stub
		String states = WorkOrder.BYWZZ+","+WorkOrder.BJS;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		if(workOrderDao.getSendingBRToMesWOCount(stateList)>0)
			workOrderDao.restoreToUnSend(stateList);
		return workOrderDao.getUnSendBRToMesWOIDList(stateList);
	}

	@Override
	public int updateBatchEndTypeById(int batchEndType, Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.updateBatchEndTypeById(batchEndType,id);
	}

	@Override
	public int updateBatchEndTypeByFormulaId(int batchEndType, String formulaId) {
		// TODO Auto-generated method stub
		return workOrderDao.updateBatchEndTypeByFormulaId(batchEndType,formulaId);
	}

	@Override
	public int updateReFeedInfoById(String reFeedPort, Integer reFeedStepMes, Integer id) {
		// TODO Auto-generated method stub
		return workOrderDao.updateReFeedInfoById(reFeedPort,reFeedStepMes,id);
	}

	@Override
	public boolean checkIfExistByWOID(String workOrderID) {
		// TODO Auto-generated method stub
		return workOrderDao.getCountByWOID(workOrderID)==0?false:true;
	}
}
