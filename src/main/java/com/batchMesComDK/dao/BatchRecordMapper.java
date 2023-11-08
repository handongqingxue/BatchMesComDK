package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BatchRecordMapper {

	int add(BatchRecord br);

	int addFromList(@Param("brList") List<BatchRecord> brList);

	int deleteByList(@Param("idList") List<String> idList);

	List<BatchRecord> getListByWorkOrderIDList(@Param("workOrderIDList") List<String> workOrderIDList,@Param("recordTypeList") List<String> recordTypeList);
	
	/**
	 * 根据记录事件判断是否存在批记录
	 * @param workOrderIDList
	 * @param recordEvent
	 * @return
	 */
	int getExistBRCountByRE(@Param("workOrderIDList") List<String> workOrderIDList, @Param("recordEvent") String recordEvent);
	
	/**
	 * 根据记录事件删除批记录(用于补发批记录给mes时，删除之前重复的批记录)
	 * @param workOrderIDList
	 * @param recordEvent
	 * @return
	 */
	int delExistBRListByRE(@Param("workOrderIDList") List<String> workOrderIDList, @Param("recordEvent") String recordEvent);

	int updateDevPMCode(@Param("pMCode") String pMCode, @Param("pMCName") String pMCName, @Param("workOrderID") String workOrderID);

}
