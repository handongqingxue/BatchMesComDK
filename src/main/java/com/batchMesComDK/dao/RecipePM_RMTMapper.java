package com.batchMesComDK.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePM_RMTMapper {

	List<RecipePM_RMT> getByProductParam(@Param("pMType") Integer pMType,@Param("productCode") String productCode, @Param("productName") String productName);

	int add(RecipePM_RMT rPM_RMT);

	int deleteByList(@Param("idList") List<String> idList);

	int edit(RecipePM_RMT rPM_RMT);

	RecipePM_RMT getById(@Param("id") Integer id);
}
