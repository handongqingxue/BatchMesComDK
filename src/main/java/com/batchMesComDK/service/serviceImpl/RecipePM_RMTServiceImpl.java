package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class RecipePM_RMTServiceImpl implements RecipePM_RMTService {

	@Autowired
	RecipePM_RMTMapper recipePM_RMTDao;

	@Override
	public int add(RecipePM_RMT rPM_RMT) {
		// TODO Auto-generated method stub
		return recipePM_RMTDao.add(rPM_RMT);
	}

	@Override
	public int edit(RecipePM_RMT rPM_RMT) {
		// TODO Auto-generated method stub
		return recipePM_RMTDao.edit(rPM_RMT);
	}

	@Override
	public RecipePM_RMT getById(Integer id) {
		// TODO Auto-generated method stub
		return recipePM_RMTDao.getById(id);
	}
}
