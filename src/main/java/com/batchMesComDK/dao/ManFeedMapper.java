package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface ManFeedMapper {

	int add(ManFeed mf);

	int addFromList(@Param("mfList") List<ManFeed> mfList);

	int deleteByList(@Param("idList") List<String> idList);

	/**
	 * 单个更新投料信息
	 * @param mf
	 * @return
	 */
	int editByWorkOrderIDFeedPort(ManFeed mf);

	/**
	 * 批量更新投料信息
	 * @param mfList
	 * @return
	 */
	int editByList(@Param("mfList") List<ManFeed> mfList);

	int reEditByList(@Param("mfList") List<ManFeed> mfList);

	ManFeed getByWOIDFeedPort(@Param("workOrderID") String workOrderID,@Param("feedPort") String feedPort);

	int updateMarkBitByParamsList(@Param("mfList") List<ManFeed> mfList);

	List<ManFeed> getListByWorkOrderIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	/**
	 * 根据工单号获取投料步骤(mes和batch端的投料步骤都有)
	 * @param workOrderID
	 * @return
	 */
	List<ManFeed> getStepListByWOID(@Param("workOrderID") String workOrderID);

	/**
	 * 根据投料信息集合里的工单号、物料编码，更新batch端的投料步骤号
	 * @param mfList
	 * @return
	 */
	int updateRunStepByList(@Param("mfList") List<ManFeed> mfList);
}
