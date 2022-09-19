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
	private FormulaMaterialDtoService formulaMaterialDtoService;
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
		
		List<FormulaDto> fdList=formulaDtoService.getList();
		System.out.println("size==="+fdList.size());
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
			fd.setName("冷酸灵抗敏感牙膏90克配方");
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
			fd.setProductName("冷酸灵抗敏感牙膏90克配方");
			fd.setProductDesc("");
			fd.setStageType("G");
			fd.setIsStandBom("1");
			fd.setType("T");
			fd.setProject("");
			fd.setDosageType("");
			fd.setDosage("");
			fd.setForm(null);
			fd.setFolder("");
			fd.setUnitType("Q");
			fd.setWeightEquivalent("0E-30");
			fd.setWeightEquivalentUnit("");
			fd.setRefQuantityUnit("un");
			fd.setMessage(null);
			fd.setApproveUser("");
			fd.setApproveDate(null);
			fd.setVerifyUser("wang");
			fd.setVerifyDate("2022-07-01T09:17:01");
			fd.setCancelAt(null);
			fd.setCancelUser("");
			fd.setZoneCode(null);
			count=formulaDtoService.add(fd);
		}
		else if("FormulaMaterialDto".equals(tabName)) {
			FormulaMaterialDto fmd=new FormulaMaterialDto();
			fmd.setId("cl38bsxwr0fd10a69oqs63267");
			fmd.setCreatedAt("2022-05-16T06:09:08.521");
			fmd.setUpdatedAt("2022-05-16T06:09:08.521");
			fmd.setCreateUser("wang");
			fmd.setUpdateUser("wang");
			fmd.setPhase("0001");
			fmd.setPhaseDes("");
			fmd.setStep("0001");
			fmd.setIsUniqueLot("0");
			fmd.setTolerance(null);
			fmd.setMaterialCode("4030038");
			fmd.setMaterialName("冷酸灵抗敏感牙膏90克大箱");
			fmd.setIsCompensateur("N");
			fmd.setQty("1.000000000000000000000000000000");
			fmd.setUnit("un");
			fmd.setIsWeightingWeight("1");
			fmd.setIsMfgWeight("1");
			fmd.setIsPackWeight("1");
			fmd.setDosage("1");
			fmd.setIsPotency("0");
			fmd.setPotency("0E-30");
			fmd.setIsFixedQty("0");
			fmd.setIsMasterMaterial("0");
			fmd.setAttribute1(null);
			fmd.setIsTailSemiFinishedPrd("0");
			fmd.setFormula("cl310l32l09en0a693iho2ezr");
			fmd.setWeightStationCode(null);
			fmd.setBeginValidDate(null);
			fmd.setEndValidDate(null);
			count=formulaMaterialDtoService.add(fmd);
		}
		else if("OrderMateriaBody".equals(tabName)) {
			OrderMateriaBody omb=new OrderMateriaBody();
			omb.setId("123456");
			omb.setMaterialCode("fdfertrgg");
			omb.setMaterialName("嬲");
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
			wob.setProductName("嬲");
			wob.setTotalOutput("ccc");
			wob.setMfgCode("ddd");
			wob.setMfgVersion("1.0");
			wob.setFormulaId("1");
			count=workOrderBodyService.add(wob);
		}
		
		if(count>0) {
			jsonMap.put("message", "ok");
			jsonMap.put("info", "添加数据成功！");
		}
		else {
			jsonMap.put("message", "no");
			jsonMap.put("info", "添加数据失败！");
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
		//TODO 针对分类的动态进行实时调整更新
		PlanResult plan=new PlanResult();
		String json=null;
		String result=null;
		try {
			System.out.println("item==="+item);
			result = BatchComBridge.getInstance().callGetItem(item);
			System.out.println("result==="+result);
			plan.setStatus(1);
			plan.setMsg("成功");
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
		//TODO 针对分类的动态进行实时调整更新
		PlanResult plan=new PlanResult();
		String json=null;
		String result=null;
		try {
			System.out.println("command==="+command);
			result = BatchComBridge.getInstance().callExecute(command);
			System.out.println("result==="+result);
			plan.setStatus(1);
			plan.setMsg("成功");
			plan.setData(result);
			json=JsonUtil.getJsonFromObject(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value="/getFormulaCodeMaterialDosage")
	@ResponseBody
	public Map<String, Object> getFormulaCodeMaterialDosage() {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		//List<Map<String,Object>> codeDosageList=formulaDtoService.getCodeMaterialDosage();
		List<FormulaDto> list = formulaDtoService.getList();
		
		if(list.size()>0) {
			jsonMap.put("message", "ok");
			jsonMap.put("codeDosageList", list);
		}
		else {
			jsonMap.put("message", "no");
			jsonMap.put("info", "暂无信息！");
		}
		return jsonMap;
	}
}
