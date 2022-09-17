package com.batchMesComDK.dao;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface FormulaDtoMapper {

	int add(FormulaDto fd);
	
	List<FormulaDto> getList();

}
