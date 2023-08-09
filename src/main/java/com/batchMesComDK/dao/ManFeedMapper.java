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

	ManFeed getByWorkOrderIDFeedPort(@Param("workOrderID") String workOrderID,@Param("feedPort") String feedPort);

	int updateMarkBitByParamsList(@Param("mfList") List<ManFeed> mfList);

	List<ManFeed> getListByWorkOrderIDList(@Param("workOrderIDList") List<String> workOrderIDList);

	List<ManFeed> getStepMesListByWOID(@Param("workOrderID") String workOrderID);

	int updateStepMesByList(@Param("mfList") List<ManFeed> mfList);
}
