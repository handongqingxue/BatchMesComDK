package com.batchMesComDK.dao;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface ManFeedMapper {

	int add(ManFeed mf);

	int editByWorkOrderID(ManFeed mf);

	ManFeed getByWorkOrderID(@Param("workOrderID") String workOrderID);
}
