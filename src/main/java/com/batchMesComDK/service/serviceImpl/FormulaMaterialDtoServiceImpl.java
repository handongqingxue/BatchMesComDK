package com.batchMesComDK.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.FormulaMaterialDto;
import com.batchMesComDK.service.*;

@Service
public class FormulaMaterialDtoServiceImpl implements FormulaMaterialDtoService {

	@Autowired
	private FormulaMaterialDtoMapper formulaMaterialDtoDao;

	@Override
	public int add(FormulaMaterialDto fmd) {
		// TODO Auto-generated method stub
		return formulaMaterialDtoDao.add(fmd);
	}

	@Override
	public List<FormulaMaterialDto> getList() {
		// TODO Auto-generated method stub
		return formulaMaterialDtoDao.getList();
	}
}
