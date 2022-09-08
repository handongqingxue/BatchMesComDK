package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class PasteWorkingNumBodyServiceImpl implements PasteWorkingNumBodyService {

	@Autowired
	private PasteWorkingNumBodyMapper pasteWorkingNumBodyDao;

	@Override
	public int add(PasteWorkingNumBody pwnb) {
		// TODO Auto-generated method stub
		return pasteWorkingNumBodyDao.add(pwnb);
	}
}
