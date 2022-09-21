package com.batchMesComDK.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;
import com.batchMesComDK.util.APIUtil;
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
		
		//List<FormulaDto> fdList=formulaDtoService.getList();
		//System.out.println("size==="+fdList.size());
		//List<FormulaMaterialDto> fmdList=formulaMaterialDtoService.getList();
		//System.out.println("size==="+fmdList.size());
		//ActiveXTest.printVersion("BatchViewHMI.BatchesList");
		
		//List<BHBatch> bhbList=bHBatchService.getList();
		//System.out.println("size==="+bhbList.size());
		
		return MODULE_NAME+"/test";
	}

	
	@RequestMapping(value="/addDataToDB")
	@ResponseBody
	public Map<String, Object> addDataToDB(String tabName,String resultType) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			boolean success=false;
			int count=0;
			if("FeedIssusBody".equals(tabName)) {
				JSONObject jo = APIUtil.getTabTestItem(tabName);
				FeedIssusBody fib=(FeedIssusBody)jo.get("FeedIssusBody");
				count=feedIssusBodyService.add(fib);
				if(count>0)
					success=true;
			}
			else if("FormulaDto".equals(tabName)) {
				JSONObject jo =null;
				if(APIUtil.ITEM_RESULT.equals(resultType)) {
					jo = APIUtil.getTabTestItem(tabName);
					FormulaDto fd=(FormulaDto)jo.get("FormulaDto");
					count=formulaDtoService.add(fd);
					if(count>0)
						success=true;
				}
				else if(APIUtil.LIST_RESULT.equals(resultType)) {
					jo =APIUtil.getTabTestList(tabName);
					List<FormulaDto> fdList=(List<FormulaDto>)jo.get("FormulaDtoList");
					int fdListSize = fdList.size();
					for(int i=0;i<fdListSize;i++) {
						count+=formulaDtoService.add(fdList.get(i));
					}
					if(count==fdListSize)
						success=true;
				}
			}
			else if("FormulaMaterialDto".equals(tabName)) {
				JSONObject jo = APIUtil.getTabTestItem(tabName);
				FormulaMaterialDto fmd=(FormulaMaterialDto)jo.get("FormulaMaterialDto");
				count=formulaMaterialDtoService.add(fmd);
			}
			else if("OrderMateriaBody".equals(tabName)) {
				JSONObject jo = APIUtil.getTabTestItem(tabName);
				OrderMateriaBody omb=(OrderMateriaBody)jo.get("OrderMateriaBody");
				count=orderMateriaBodyService.add(omb);
			}
			else if("PasteWorkingNumBody".equals(tabName)) {
				JSONObject jo = APIUtil.getTabTestItem(tabName);
				PasteWorkingNumBody pwnb=(PasteWorkingNumBody)jo.get("PasteWorkingNumBody");
				count=pasteWorkingNumBodyService.add(pwnb);
			}
			else if("WorkOrderBody".equals(tabName)) {
				JSONObject jo = APIUtil.getTabTestItem(tabName);
				WorkOrderBody wob=(WorkOrderBody)jo.get("WorkOrderBody");
				count=workOrderBodyService.add(wob);
			}
			
			if(success) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加数据成功！");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加数据失败！");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		List<Map<String,Object>> codeDosageList=formulaDtoService.getCodeMaterialDosage();
		
		if(codeDosageList.size()>0) {
			jsonMap.put("message", "ok");
			jsonMap.put("codeDosageList", codeDosageList);
		}
		else {
			jsonMap.put("message", "no");
			jsonMap.put("info", "暂无信息！");
		}
		return jsonMap;
	}
}
