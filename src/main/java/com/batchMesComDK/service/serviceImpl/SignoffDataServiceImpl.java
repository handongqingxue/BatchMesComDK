package com.batchMesComDK.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batchMesComDK.dao.*;
import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;

@Service
public class SignoffDataServiceImpl implements SignoffDataService {

	@Autowired
	SignoffTemplateMapper signoffTemplateDao;
	@Autowired
	SignoffDataMapper signoffDataDao;

	@Override
	public int addFromTemplate(String workOrderID) {
		// TODO Auto-generated method stub
		int count=0;
		List<SignoffTemplate> stList=signoffTemplateDao.getByWorkOrderID(workOrderID);

		SignoffData sd=null;
		for(int i=0;i<stList.size();i++) {
			SignoffTemplate st = stList.get(i);
			sd=new SignoffData();
			sd.setID(st.getID());
			sd.setSignoffID(st.getSignoffID());
			sd.setPMName(st.getPMName());
			sd.setPMValue(st.getPMValue());
			sd.setUnit(st.getUnit());
			sd.setH(st.getH());
			sd.setL(st.getL());
			sd.setHH(st.getHH());
			sd.setLL(st.getLL());
			sd.setDevType(st.getDevType());
			sd.setWorkOrderID(st.getWorkOrderID());
			sd.setRecipeName(st.getRecipeName());
			sd.setSignTime(st.getSignTime());
			sd.setOprUser(st.getOprUser());
			sd.setOprSign(st.getOprSign());
			sd.setOprComment(st.getOprComment());
			sd.setAppUser(st.getAppUser());
			sd.setAppSign(st.getAppSign());
			sd.setAppComment(st.getAppComment());
			
			count+=signoffDataDao.add(sd);
		}
		
		return count;
	}
}
