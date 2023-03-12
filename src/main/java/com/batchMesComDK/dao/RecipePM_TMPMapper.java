package com.batchMesComDK.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePM_TMPMapper {

	List<RecipePM_TMP> getByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);

	int add(RecipePM_TMP rPM_TMP);

	int deleteByList(@Param("idList") List<String> idList);

	int edit(RecipePM_TMP rPM_TMP);

	RecipePM_TMP getById(@Param("id") Integer id);
}
