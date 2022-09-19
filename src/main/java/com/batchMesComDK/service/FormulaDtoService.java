package com.batchMesComDK.service;

import java.util.List;
import java.util.Map;

import com.batchMesComDK.entity.*;

public interface FormulaDtoService {

	int add(FormulaDto fd);
	
	List<FormulaDto> getList();

	List<Map<String, Object>> getCodeMaterialDosage();

}
