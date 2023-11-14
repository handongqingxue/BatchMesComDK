package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BHBatchHisMapper {
	
	/**
	 * 获得原料记录(这个方法以前给mes推送batch端的参数中文名时用，现在给mes推送mes那边发的中文名暂时不用这个方法了，用下面的方法)
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getMaterialListByWOIDListOld(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 获得原料记录
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getMaterialListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 获得原料记录开始时间
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getMaterialConnectingListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 获得工艺参数记录
	 * @param workOrderIDList
	 * @return
	 */
	List<BHBatchHis> getTechListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<BHBatchHis> getPhaseListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<BHBatchHis> getPhaseLclTimeListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);
}
