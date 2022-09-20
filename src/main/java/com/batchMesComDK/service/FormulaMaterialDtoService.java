package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface FormulaMaterialDtoService {

	int add(FormulaMaterialDto fmd);

	List<FormulaMaterialDto> getList();

}
