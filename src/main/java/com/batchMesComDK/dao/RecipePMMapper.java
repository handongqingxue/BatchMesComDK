package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface RecipePMMapper {

	int add(RecipePM rpm);

	int addFromList(@Param("rPMList") List<RecipePM> rPMList);

	int deleteByList(@Param("idList") List<String> idList);
	
	List<RecipePM> getListByWorkOrderID(@Param("workOrderID") String workOrderID);

	List<RecipePM> getListByWorkOrderIDList(@Param("workOrderIDList") List<String> workOrderIDList);
	
	List<RecipePM> getManFeedListByWorkOrderID(@Param("workOrderID") String workOrderID);

	int updateDosageByID(@Param("id") Integer id, @Param("dosage") String dosage);

	/**
	 * 根据工单id批量更新配方参数的量
	 * @param rPMList
	 * @param workOrderID
	 * @return
	 */
	int updateDosageByListWOID(@Param("rPMList") List<RecipePM> rPMList, @Param("workOrderID") String workOrderID);

	int clearDosageByIdList(@Param("idList") List<Integer> idList);
}
