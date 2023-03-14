package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface WorkOrderMapper {

	int add(WorkOrder wo);

	int updateStateById(@Param("state") Integer state, @Param("id") Integer id);

	int updateStateByWorkOrderID(@Param("state") Integer state, @Param("workOrderID") String workOrderID);

	int updateStateByFormulaId(@Param("state") Integer state, @Param("formulaId") String formulaId);

	List<WorkOrder> getListByStateList(@Param("stateList") List<String> stateList);

	int edit(WorkOrder wo);

	WorkOrder getById(@Param("id") Integer id);

	int deleteByList(@Param("idList") List<String> idList);

	int updateZGIDByWorkOrderID(@Param("zGID") String zGID, @Param("workOrderID") String workOrderID);

	Integer getMaxFormulaIdNumByFormulaIdDate(@Param("formulaIdDate") String formulaIdDate);

	String getFormulaIdByWOID(@Param("workOrderID") String workOrderID);

	int updateStateByWOIDList(@Param("state") Integer state, @Param("workOrderIDList") List<String> workOrderIDList);

	Integer getCountByByWOIDList(@Param("workOrderIDList") List<String> workOrderIDList);
}
