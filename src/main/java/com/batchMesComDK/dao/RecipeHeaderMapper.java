package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

public interface RecipeHeaderMapper {

	String getNamePreByProductParam(@Param("productCode") String productCode, @Param("productName") String productName);
}
