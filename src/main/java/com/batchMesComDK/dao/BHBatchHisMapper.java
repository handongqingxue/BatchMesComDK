package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BHBatchHisMapper {
	
	List<BHBatchHis> getMaterialListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);
}
