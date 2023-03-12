package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.RecipeHeader;

public interface RecipeHeaderMapper {

	String getIdentifierByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);

	RecipeHeader getByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);
}
