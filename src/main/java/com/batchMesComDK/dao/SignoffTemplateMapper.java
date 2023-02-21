package com.batchMesComDK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.batchMesComDK.entity.*;

public interface SignoffTemplateMapper {

	List<SignoffTemplate> getByWorkOrderID(@Param("workOrderID") String workOrderID);

	int add(SignoffTemplate st);

}
