package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface BHBatchMapper {

	List<BHBatch> getList();

	List<BHBatch> getListByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);

}
