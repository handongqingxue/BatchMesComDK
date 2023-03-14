package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class TestLogServiceImpl implements TestLogService {
	
	@Autowired
	private TestLogMapper testLogDao;

	@Override
	public int add(TestLog testLog) {
		// TODO Auto-generated method stub
		return testLogDao.add(testLog);
	}

}
