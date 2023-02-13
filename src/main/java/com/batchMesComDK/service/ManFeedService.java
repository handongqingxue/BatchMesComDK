package com.batchMesComDK.service;

import com.batchMesComDK.entity.*;

public interface ManFeedService {

	int editByWorkOrderID(ManFeed mf);

	int add(ManFeed manFeed);

	ManFeed getByWorkOrderID(String workOrderID);

}
