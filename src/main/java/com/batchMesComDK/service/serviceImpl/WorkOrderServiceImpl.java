package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
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
	public List<WorkOrder> getKeepWatchList() {
		// TODO Auto-generated method stub
		String states = WorkOrder.CSQRWB+","+WorkOrder.BQD+","+WorkOrder.BQX+","+WorkOrder.BZT;
		String[] stateArr = states.split(",");
		List<String> stateList = Arrays.asList(stateArr);
		return workOrderDao.getListByStateList(stateList);
	}
}
