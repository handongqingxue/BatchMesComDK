package com.batchMesComDK.service;

import java.util.List;

import com.batchMesComDK.entity.*;

public interface ManFeedService {

	int add(ManFeed manFeed);

	int addFromRecipePM(String workOrderID, String productCode, String productName);

	int deleteByIds(String ids);

	int editByWorkOrderIDFeedPort(ManFeed mf);

	int editByWorkOrderIDFeedPortList(List<ManFeed> mfList);

	ManFeed getByWorkOrderIDFeedPort(String workOrderID,String feedPort);

	/**
	 * 直接把下单时带的小料数据插入人工投料表
	 * @param mfList
	 * @return
	 */
	int addFromList(List<ManFeed> mfList);

	int updateStepMesByWOID(String workOrderID);

}
