package com.batchMesComDK.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.service.*;

@Service
public class FormulaMaterialDtoServiceImpl implements FormulaMaterialDtoService {

	@Autowired
	private FormulaMaterialDtoMapper formulaMaterialDtoDao;
}
