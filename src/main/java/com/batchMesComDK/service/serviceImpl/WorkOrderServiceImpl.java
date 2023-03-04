package com.batchMesComDK.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	public List<WorkOrder> getKeepWatchList() {
		// TODO Auto-generated method stub
		String states = WorkOrder.CSQRWB+","+WorkOrder.BQD+","+WorkOrder.BYX+","+WorkOrder.BQX+","+WorkOrder.BZT;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		return workOrderDao.getListByStateList(stateList);
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
	public int updateCreamCodeByWorkOrder(String creamCode, String workOrder) {
		// TODO Auto-generated method stub
		return workOrderDao.updateFormulaIdByWorkOrderID(creamCode,workOrder);
	}

	@Override
	public String createFormulaIdByDateYMD(String productCode, String productName) {
		// TODO Auto-generated method stub
		String namePre = recipeHeaderDao.getNamePreByProductParam(productCode, productName);
		String formulaIdDate = namePre+"_BATCH"+formulaIdSDF.format(new Date());
		Integer count=workOrderDao.getMaxFormulaIdNumByFormulaIdDate(formulaIdDate);
		if(count==null)
			count=0;
		String formulaIdXhStr=null;
		int formulaIdXh=count+1;
		if(formulaIdXh<10)
			formulaIdXhStr="00"+formulaIdXh;
		else if(formulaIdXh<100)
			formulaIdXhStr="0"+formulaIdXh;
		return formulaIdDate+formulaIdXhStr;
	}
}
