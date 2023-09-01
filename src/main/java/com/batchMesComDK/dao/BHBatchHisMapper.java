package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BHBatchHisMapper {
	
	/**
	 * 获得原料记录
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getMaterialListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 获得原料记录完成时间
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getMaterialCompleteListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 获得工艺参数记录
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getTechListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<BHBatchHis> getPhaseListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<BHBatchHis> getPhaseLclTimeListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);
}
