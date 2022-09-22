package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class TranslateServiceImpl implements TranslateService {

	@Autowired
	private TranslateMapper translateDao;

	@Override
	public int add(Translate translate) {
		// TODO Auto-generated method stub
		int count=0;
		if(translateDao.getCount(translate.getTabName(),translate.getColName(),translate.getForeignKey())==0)
			count=translateDao.add(translate);
		return count;
	}
}
