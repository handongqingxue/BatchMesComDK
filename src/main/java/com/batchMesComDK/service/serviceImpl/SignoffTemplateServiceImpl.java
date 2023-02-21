package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class SignoffTemplateServiceImpl implements SignoffTemplateService {

	@Autowired
	SignoffTemplateMapper signoffTemplateDao;

	@Override
	public int add(SignoffTemplate st) {
		// TODO Auto-generated method stub
		return signoffTemplateDao.add(st);
	}
}
