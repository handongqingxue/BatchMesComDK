package com.batchMesComDK.service.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class ManFeedServiceImpl implements ManFeedService {

	@Autowired
	ManFeedMapper manFeedDao;

	@Override
	public int add(ManFeed manFeed) {
		// TODO Auto-generated method stub
		return manFeedDao.add(manFeed);
	}

	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		String[] idArr = ids.split(",");
		List<String> idList = Arrays.asList(idArr);
		return manFeedDao.deleteByList(idList);
	}
	
	@Override
	public int editByWorkOrderID(ManFeed mf) {
		// TODO Auto-generated method stub
		return manFeedDao.editByWorkOrderID(mf);
	}

	@Override
	public ManFeed getByWorkOrderID(String workOrderID) {
		// TODO Auto-generated method stub
		return manFeedDao.getByWorkOrderID(workOrderID);
	}

}
