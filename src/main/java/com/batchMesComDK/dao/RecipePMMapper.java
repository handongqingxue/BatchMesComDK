package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePMMapper {

	int add(RecipePM rpm);

	int deleteByList(@Param("idList") List<String> idList);
	
	List<RecipePM> getListByWorkOrderID(@Param("workOrderID") String workOrderID);

	int updateDosageByPMParam(@Param("pMCode") String pMCode, @Param("pMName") String pMName, @Param("dosage") String dosage);
}
