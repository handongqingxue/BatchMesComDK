package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface RecipePM_RMTService {

	int add(RecipePM_RMT rPM_RMT);

	int edit(RecipePM_RMT rPM_RMT);

	RecipePM_RMT getById(Integer id);

}
