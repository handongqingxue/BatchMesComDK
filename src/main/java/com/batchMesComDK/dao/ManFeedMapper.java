package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface ManFeedMapper {

	int add(ManFeed mf);

	int deleteByList(@Param("idList") List<String> idList);

	int editByWorkOrderIDFeedPort(ManFeed mf);

	ManFeed getByWorkOrderIDPhaseID(@Param("workOrderID") String workOrderID,@Param("phaseID") String phaseID);
}
