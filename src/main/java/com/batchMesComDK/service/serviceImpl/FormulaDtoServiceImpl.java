package com.batchMesComDK.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.service.*;
import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;

@Service
public class FormulaDtoServiceImpl implements FormulaDtoService {

	@Autowired
	private FormulaDtoMapper formulaDtoDao;

	@Override
	public List<FormulaDto> getList() {
		// TODO Auto-generated method stub
		return formulaDtoDao.getList();
	}

	@Override
	public int add(FormulaDto fd) {
		// TODO Auto-generated method stub
		return formulaDtoDao.add(fd);
	}

}
