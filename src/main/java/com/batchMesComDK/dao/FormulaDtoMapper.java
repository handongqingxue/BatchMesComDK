package com.batchMesComDK.dao;

import java.util.List;
import java.util.Map;

import com.batchMesComDK.entity.*;

public interface FormulaDtoMapper {

	int add(FormulaDto fd);
	
	List<FormulaDto> getList();

	List<Map<String, Object>> getCodeMaterialDosage();

}
