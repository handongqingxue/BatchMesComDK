package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface BatchTestService {

	int add(BatchTest bt);

	String getItem(String item);

	String getBLKey_x(String key, int rowNumber);

	int updateStateByCreateID(String state, Integer createID);
}
