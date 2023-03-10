package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface ManFeedService {

	int add(ManFeed manFeed);

	int addFromRecipePM(String workOrderID);

	int deleteByIds(String ids);

	int editByWorkOrderIDFeedPort(ManFeed mf);

	int editByWorkOrderIDFeedPortList(List<ManFeed> mfList);

	ManFeed getByWorkOrderIDFeedPort(String workOrderID,String feedPort);

}
