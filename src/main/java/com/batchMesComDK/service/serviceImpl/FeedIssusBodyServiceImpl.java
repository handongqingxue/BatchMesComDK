package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class FeedIssusBodyServiceImpl implements FeedIssusBodyService {

	@Autowired
	private FeedIssusBodyMapper feedIssusBodyDao;

	@Override
	public int add(FeedIssusBody fib) {
		// TODO Auto-generated method stub
		return feedIssusBodyDao.add(fib);
	}
}
