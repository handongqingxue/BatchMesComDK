package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface ManFeedMapper {

	int add(ManFeed mf);

	int addFromList(@Param("mfList") List<ManFeed> mfList);

	int deleteByList(@Param("idList") List<String> idList);

	int editByWorkOrderIDFeedPort(ManFeed mf);

	ManFeed getByWorkOrderIDFeedPort(@Param("workOrderID") String workOrderID,@Param("feedPort") String feedPort);

	int updateMarkBitByParamsList(@Param("mfList") List<ManFeed> mfList);
}
