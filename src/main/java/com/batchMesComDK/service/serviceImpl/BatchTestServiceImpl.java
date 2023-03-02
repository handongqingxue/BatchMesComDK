package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;
import com.batchMesComDK.util.*;

@Service
public class BatchTestServiceImpl implements BatchTestService {

	@Autowired
	BatchTestMapper batchTestDao;

	@Override
	public int add(BatchTest bt) {
		// TODO Auto-generated method stub
		return batchTestDao.add(bt);
	}

	@Override
	public String getItem(String item) {
		// TODO Auto-generated method stub
		String result=null;
		if(Constant.ITEM_BATCH_LIST_CT.equals(item)) {
			int batchCount=batchTestDao.getCount();
			result=batchCount+Constant.END_SUCCESS;
		}
		return result;
	}

	@Override
	public String getBLKey_x(String key, int rowNumber) {
		// TODO Auto-generated method stub
		System.out.println("key==="+key);
		System.out.println("rowNumber==="+rowNumber);
		return batchTestDao.getBLCol_x(key,rowNumber);
	}

	@Override
	public int updateStateByCreateID(String state, Integer createID) {
		// TODO Auto-generated method stub
		String state1=null;
		if(BatchTest.START.equals(state))
			state1=BatchTest.RUNNING;
		else if(BatchTest.STOP.equals(state))
			state1=BatchTest.STOPPED;
		return batchTestDao.updateStateByCreateID(state1,createID);
	}
}
