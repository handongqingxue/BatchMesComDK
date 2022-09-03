package com.batchMesComDK.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class BHBatchServiceImpl implements BHBatchService {

	@Autowired
	private BHBatchMapper bHBatchDao;

	@Override
	public List<BHBatch> getList() {
		// TODO Auto-generated method stub
		return bHBatchDao.getList();
	}
}
