package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BatchTestMapper {

	int add(BatchTest bt);

	int getCount();

	String getBLCol_x(@Param("key") String key, @Param("rowNum") int rowNum);

	int updateStateByCreateID(@Param("state") String state, @Param("createID") Integer createID);
}
