package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class MaterialCheckOverIssusBodyServiceImpl implements MaterialCheckOverIssusBodyService {

	@Autowired
	private MaterialCheckOverIssusBodyMapper materialCheckOverIssusBodyDao;

	@Override
	public int add(MaterialCheckOverIssusBody materialCheckOverIssusBody) {
		// TODO Auto-generated method stub
		return materialCheckOverIssusBodyDao.add(materialCheckOverIssusBody);
	}
}
