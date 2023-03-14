package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BatchRecordMapper {

	int add(BatchRecord br);

	int deleteByList(@Param("idList") List<String> idList);

	List<BatchRecord> getListByWorkOrderIDList(@Param("workOrderIDList") List<String> workOrderIDList,@Param("recordTypeList") List<String> recordTypeList);
}
