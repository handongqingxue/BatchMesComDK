package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface ManFeedService {

	int add(ManFeed manFeed);

	int deleteByIds(String ids);

	int editByWorkOrderID(ManFeed mf);

	ManFeed getByWorkOrderID(String workOrderID);

}
