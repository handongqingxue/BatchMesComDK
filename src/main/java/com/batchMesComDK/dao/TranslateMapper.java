package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface TranslateMapper {

	int add(Translate t);

	int getCount(@Param("tabName") String tabName, @Param("colName") String colName, @Param("foreignKey") String foreignKey);
}
