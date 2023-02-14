package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface WorkOrderMapper {

	int add(WorkOrder wo);

	int updateStateById(@Param("state") Integer state, @Param("id") Integer id);

	List<WorkOrder> getListByStateList(@Param("stateList") List<String> stateList);

	int edit(WorkOrder wo);

	WorkOrder getById(@Param("id") Integer id);

	int deleteByList(@Param("idList") List<String> idList);
}
