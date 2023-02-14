package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BatchRecordMapper {

	int add(BatchRecord br);

	int deleteByList(@Param("idList") List<String> idList);

	List<BatchRecord> getListByWorkOrderID(@Param("workOrderID") String workOrderID);
}
