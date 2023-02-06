package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface WorkOrderMapper {

	int add(WorkOrder wo);

	int updateStateById(@Param("state") Integer state, @Param("id") Integer id);
}
