package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface FormulaDtoService {

	int add(FormulaDto fd);
	
	List<FormulaDto> getList();

}
