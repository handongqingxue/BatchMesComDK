package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class OrderMateriaBodyServiceImpl implements OrderMateriaBodyService {

	@Autowired
	private OrderMateriaBodyMapper orderMateriaBodyDao;

	@Override
	public int add(OrderMateriaBody omb) {
		// TODO Auto-generated method stub
		return orderMateriaBodyDao.add(omb);
	}
}
