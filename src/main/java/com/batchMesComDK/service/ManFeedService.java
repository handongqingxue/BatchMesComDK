package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface ManFeedService {

	int add(ManFeed manFeed);

	int addFromRecipePM(String workOrderID);

	int deleteByIds(String ids);

	int editByWorkOrderIDFeedPort(ManFeed mf);

	ManFeed getByWorkOrderIDFeedPort(String workOrderID,String feedPort);

}
