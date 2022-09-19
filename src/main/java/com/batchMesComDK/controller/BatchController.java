package com.batchMesComDK.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;
import com.batchMesComDK.util.ActiveXTest;
import com.batchMesComDK.util.JsonUtil;
import com.batchMesComDK.util.PlanResult;
import com.thingworx.sdk.batch.BatchComBridge;

//https://blog.csdn.net/SHBWeiXiao/article/details/78392382
@Controller
@RequestMapping("/batch")
public class BatchController {

	@Autowired
	private FormulaDtoService formulaDtoService;
	@Autowired
	private BHBatchService bHBatchService;
	@Autowired
	private FeedIssusBodyService feedIssusBodyService;
	@Autowired
	private OrderMateriaBodyService orderMateriaBodyService;
	@Autowired
	private PasteWorkingNumBodyService pasteWorkingNumBodyService;
	@Autowired
	private WorkOrderBodyService workOrderBodyService;
	public static final String MODULE_NAME="batch";
	
	@RequestMapping(value="/test")
	public String goTest(HttpServletRequest request) {
		
		//List<FormulaDto> fdList=formulaDtoService.getList();
		//System.out.println("size==="+fdList.size());
		//ActiveXTest.printVersion("BatchViewHMI.BatchesList");
		
		//List<BHBatch> bhbList=bHBatchService.getList();
		//System.out.println("size==="+bhbList.size());
		
		return MODULE_NAME+"/test";
	}

	
	@RequestMapping(value="/addDataToDB")
	@ResponseBody
	public Map<String, Object> addDataToDB(String tabName) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		int count=0;
		if("FeedIssusBody".equals(tabName)) {
			FeedIssusBody fib=new FeedIssusBody();
		    fib.setId("123456");
		    fib.setWorkOrder("aaaaaaaaaaaaa");
		    fib.setFeedportCode("dfgrgrgergr");
		    fib.setFeedTime("1997-07-01");
		    fib.setMaterialCode("fdfertrgg");
		    fib.setSuttle("fgfhgth");
		    fib.setUnit("kg");
		    fib.setSort("1");
			count=feedIssusBodyService.add(fib);
		}
		else if("FormulaDto".equals(tabName)) {
			FormulaDto fd=new FormulaDto();
			fd.setId("cl310l32l09en0a693iho2ezr");
			fd.setCode("F000001");
			fd.setVersion("0");
			fd.setName("�����鿹��������90���䷽");
			fd.setFactory(null);
			fd.setWorkcenterId(null);
			fd.setRefQuantity(null);
			fd.setUnit("");
			fd.setBeginValidDate(null);
			fd.setEndValidDate(null);
			fd.setStatus("V");
			fd.setCreatedAt("2022-05-11T03:20:42.955");
			fd.setUpdatedAt("2022-07-01T09:17:01.342");
			fd.setCreateUser("wang");
			fd.setUpdateUser("wang");
			fd.setOriginalFormulaCode("F000001");
			fd.setOrigineVersion("0");
			fd.setProductCode("1010051");
			fd.setProductName("�����鿹��������90���䷽");
			fd.setProductDesc("");
			fd.setStageType("G");
			fd.setIsStandBom("1");
			fd.setType("T");
			/*
			private String project;
			private String dosageType;
			private String dosage;
			private String form;
			private String folder;
			private String unitType;
			private String weightEquivalent;
			private String weightEquivalentUnit;
			private String refQuantityUnit;
			private String message;
			private String approveUser;
			private String approveDate;
			private String verifyUser;
			private String verifyDate;
			private String cancelAt;
			private String cancelUser;
			private String zoneCode;
			*/
			count=formulaDtoService.add(fd);
		}
		else if("OrderMateriaBody".equals(tabName)) {
			OrderMateriaBody omb=new OrderMateriaBody();
			omb.setId("123456");
			omb.setMaterialCode("fdfertrgg");
			omb.setMaterialName("��");
			omb.setLot("aaa");
			omb.setDosage("bbb");
			omb.setUnit("kg");
			omb.setStep("1");
			omb.setPhase("ccc");
			count=orderMateriaBodyService.add(omb);
		}
		else if("PasteWorkingNumBody".equals(tabName)) {
			PasteWorkingNumBody pwnb=new PasteWorkingNumBody();
			pwnb.setId("123456");
			pwnb.setWorkOrder("aaaaaaaaaaaaa");
			pwnb.setCreamCode("fdsffgfdgf");
			pwnb.setCreamWaterNo("dfertrgtrgt");
			count=pasteWorkingNumBodyService.add(pwnb);
		}
		else if("WorkOrderBody".equals(tabName)) {
			WorkOrderBody wob=new WorkOrderBody();
			wob.setId("123456");
			wob.setWorkOrder("aaaaaaaaaaaaa");
			wob.setProductcode("fdfgffgfg");
			wob.setProductName("��");
			wob.setTotalOutput("ccc");
			wob.setMfgCode("ddd");
			wob.setMfgVersion("1.0");
			wob.setFormulaId("1");
			count=workOrderBodyService.add(wob);
		}
		
		if(count>0) {
			jsonMap.put("message", "ok");
			jsonMap.put("info", "������ݳɹ���");
		}
		else {
			jsonMap.put("message", "no");
			jsonMap.put("info", "�������ʧ�ܣ�");
		}
		return jsonMap;
	}
	
	/**
	 * @param item
	 * @return
	 */
	@RequestMapping(value="/getItem",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getItem(String item) {
		//TODO ��Է���Ķ�̬����ʵʱ��������
		PlanResult plan=new PlanResult();
		String json=null;
		String result=null;
		try {
			System.out.println("item==="+item);
			result = BatchComBridge.getInstance().callGetItem(item);
			System.out.println("result==="+result);
			plan.setStatus(1);
			plan.setMsg("�ɹ�");
			plan.setData(result);
			json=JsonUtil.getJsonFromObject(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value="/execute",produces="application/json;charset=utf-8")
	@ResponseBody
	public String execute(String command) {
		//TODO ��Է���Ķ�̬����ʵʱ��������
		PlanResult plan=new PlanResult();
		String json=null;
		String result=null;
		try {
			System.out.println("command==="+command);
			result = BatchComBridge.getInstance().callExecute(command);
			System.out.println("result==="+result);
			plan.setStatus(1);
			plan.setMsg("�ɹ�");
			plan.setData(result);
			json=JsonUtil.getJsonFromObject(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
