package com.batchMesComDK.dao;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface FormulaMaterialDtoMapper {

	int add(FormulaMaterialDto fmd);

	List<FormulaMaterialDto> getList();
}
