package com.batchMesComDK.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.batchMesComDK.entity.*;
import com.batchMesComDK.service.*;
import com.batchMesComDK.util.APIUtil;
import com.batchMesComDK.util.ActiveXTest;
import com.batchMesComDK.util.Constant;
import com.batchMesComDK.util.DesUtil;
import com.batchMesComDK.util.JsonUtil;
import com.batchMesComDK.util.PinyinUtil;
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
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private RecipePMService recipePMService;
	@Autowired
	private RecipePM_RMTService recipePM_RMTService;
	@Autowired
	private BatchRecordService batchRecordService;
	@Autowired
	private TranslateService translateService;
	@Autowired
	private MaterialCheckOverIssusBodyService materialCheckOverIssusBodyService;
	public static final String MODULE_NAME="batch";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//http://localhost:8080/BatchMesComDK/batch/test
	@RequestMapping(value="/test")
	public String goTest(HttpServletRequest request) {
		
		//List<FormulaDto> fdList=formulaDtoService.getList();
		//System.out.println("size==="+fdList.size());
		//List<FormulaMaterialDto> fmdList=formulaMaterialDtoService.getList();
		//System.out.println("size==="+fmdList.size());
		//ActiveXTest.printVersion("BatchViewHMI.BatchesList");
		
		//List<BHBatch> bhbList=bHBatchService.getList();
		//System.out.println("size==="+bhbList.size());
		Constant.setWorkOrderStateInRequest(request);
		
		return MODULE_NAME+"/test";
	}

	@RequestMapping(value="/keepWatchOnWorkOrder")
	public void keepWatchOnWorkOrder() {
		
		try {
			List<WorkOrder> woList=workOrderService.getKeepWatchList();
			System.out.println("woListSize==="+woList.size());
			String batchIDs="";
			String batchCountResultStr = getItem("BatchListCt");
			System.out.println("batchCountResultStr==="+batchCountResultStr);
			JSONObject batchCountResultJO = new JSONObject(batchCountResultStr);
			String data = batchCountResultJO.getString("data");
			int batchCount = Integer.valueOf(data);
			System.out.println("batchCount==="+batchCount);
			for (int i = 0; i < woList.size(); i++) {
				WorkOrder wo = woList.get(i);
				Integer state = wo.getState();
				System.out.println("state==="+state);
				switch (state) {
				case WorkOrder.CSQRWB:
					//调用创建batch接口创建batch
					Integer id = wo.getID();
					String workOrderID = wo.getWorkOrderID();				
					String recipeID = wo.getRecipeID();
					System.out.println("id==="+id);
					System.out.println("workOrderID==="+workOrderID);
					System.out.println("recipeID==="+recipeID);
					createBatch(id,workOrderID,recipeID);
					break;
				case WorkOrder.BQD:
					//启动执行配方
					for (int j = 1; j <= batchCount; j++) {
						String idStr = wo.getID().toString();
						String batchIDVal = BLKey_x("BatchID",j);
						if(idStr.equals(batchIDVal)) {
							String createIDVal = BLKey_x("CreateID",j);
							System.out.println("createIDVal==="+createIDVal);
							
							StringBuilder commandBQDSB=new StringBuilder();
							commandBQDSB.append("[COMMAND(Item,");
							commandBQDSB.append(Constant.USERID);
							commandBQDSB.append(",");
							commandBQDSB.append(createIDVal);
							commandBQDSB.append(",START)]");
							execute(commandBQDSB.toString());
							
							String stateVal = BLKey_x("State",j);
							if("RUNNING".equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYX, Integer.valueOf(idStr));
							}
						}
					}
					break;
				case WorkOrder.BQX:
				case WorkOrder.BZT:
					//调用batch command接口
					for (int j = 1; j <= batchCount; j++) {
						String idStr = wo.getID().toString();
						String batchIDVal = BLKey_x("BatchID",j);
						if(idStr.equals(batchIDVal)) {
							String createIDVal = BLKey_x("CreateID",j);
							System.out.println("createIDVal==="+createIDVal);
							
							StringBuilder commandQXZTSB=new StringBuilder();
							commandQXZTSB.append("[COMMAND(Item,");
							commandQXZTSB.append(Constant.USERID);
							commandQXZTSB.append(",");
							commandQXZTSB.append(createIDVal);
							commandQXZTSB.append(",STOP)]");
							execute(commandQXZTSB.toString());

							String stateVal = BLKey_x("State",j);
							if("STOPPED".equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYWZZ, Integer.valueOf(idStr));
							}
						}
					}
					break;
				}
				
				if(state>5) {
					//把状态大于5的工单id拼接起来
					batchIDs+=","+wo.getWorkOrderID();
				}
			}

			/*
			if(StringUtils.isEmpty(batchIDs)) {
				String[] batchIDArr = batchIDs.split(",");
				for (int i = 0; i < batchIDArr.length; i++) {
					String batchID = batchIDArr[i];
					for (int j = 1; j <= batchCount; j++) {
						String batchIDVal = BLKey_x("BatchID",j);
						if(batchID.equals(batchIDVal)) {
							String stateVal = BLKey_x("State",j);
							if("COMPLATE".equals(stateVal)) {
								//workOrderService.updateStateById(WorkOrder.BJS, id);
							}
							else if("STOPPED".equals(stateVal)) {
								//workOrderService.updateStateById(WorkOrder.BYWZZ, id);
							}
						}
					}
				}
			}
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String BLKey_x(String key, int rowNumber) {
		String value=null;
		try {
			String resultJOStr = getItem("Batchlist");
			JSONObject resultJO = new JSONObject(resultJOStr);
			String dataJAStr = resultJO.getString("data");
			//System.out.println("dataJAStr==="+dataJAStr);
			String[] batchStrArr = dataJAStr.split("\\r\\n");
			String batchStr = batchStrArr[rowNumber-1];
			System.out.println("batchStr==="+batchStr);
			String[] valueArr = batchStr.split("\\t");
			int valueLoc=-1;
			if("BatchID".equals(key))
				valueLoc=0;
			else if("RecipeName".equals(key))
				valueLoc=1;
			else if("BatchDesc".equals(key))
				valueLoc=2;
			else if("StartTime".equals(key))
				valueLoc=3;
			else if("ElapsedTime".equals(key))
				valueLoc=4;
			else if("State".equals(key))
				valueLoc=5;
			else if("Mode".equals(key))
				valueLoc=6;
			else if("Failures".equals(key))
				valueLoc=7;
			else if("CreateID".equals(key))
				valueLoc=8;
			else if("CmdMask".equals(key))
				valueLoc=9;
			else if("BatchType".equals(key))
				valueLoc=10;
			
			if(valueLoc!=-1) {
				value=valueArr[valueLoc];
			}
			System.out.println("value==="+value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return value;
		}
	}
	
	private void createBatch(Integer id, String workOrderID, String recipeID) {
		StringBuilder commandSB=new StringBuilder();
		commandSB.append("[BATCH(Item,");
		commandSB.append(Constant.USERID);
		commandSB.append(",");
		commandSB.append(recipeID);
		commandSB.append(",");
		commandSB.append(id);//BatchID
		commandSB.append("");
		commandSB.append(",100,FRENCHVANILLA PREMIUM -CLASSBASED,FREEZER,4,MIXER,2,PARMS,");
		//commandSB.append("CREAM_AMOUNT,2001,EGG_AMOUNT,200,FLAVOR_AMOUNT,50,MILK_AMOUNT,1999,SUGAR_AMOUNT, 750");
		
		List<RecipePM> rPMList=recipePMService.getListByWorkOrderID(workOrderID);
		StringBuilder rPMSB=new StringBuilder();
		for (int i = 0; i < rPMList.size(); i++) {
			RecipePM rPM = rPMList.get(i);
			String pMName = rPM.getPMName();
			String dosage = rPM.getDosage();
			rPMSB.append(",");
			rPMSB.append(pMName);
			rPMSB.append(",");
			rPMSB.append(dosage);
		}
		String rPMStr = rPMSB.toString();
		rPMStr=rPMStr.substring(1);
		
		commandSB.append(rPMStr);
		commandSB.append(")]");
		String commandSBStr=commandSB.toString();
		System.out.println("commandSBStr==="+commandSBStr);
		execute(commandSBStr);
	}

	@RequestMapping(value="/addWorkOrder")
	@ResponseBody
	public Map<String, Object> addWorkOrder(WorkOrder wo) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=workOrderService.add(wo);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加工单成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加工单失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addRecipePM_RMT")
	@ResponseBody
	public Map<String, Object> addRecipePM_RMT(RecipePM_RMT rPM_RMT) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePM_RMTService.add(rPM_RMT);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加远程配方参数成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加远程配方参数失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addRecipePMFromRMT")
	@ResponseBody
	public Map<String, Object> addRecipePMFromRMT(String workOrderID, String recipeID) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePMService.addFromRMT(workOrderID, recipeID);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加配方参数成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加配方参数失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addBatchRecordFromRecordPM")
	@ResponseBody
	public Map<String, Object> addBatchRecordFromRecordPM(String workOrderID) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=batchRecordService.addFromRecordPM(workOrderID);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加批记录成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加批记录失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/updateWorkOrderStateById")
	@ResponseBody
	public Map<String, Object> updateWorkOrderStateById(Integer state, Integer id) {
		
		System.out.println("state==="+state);
		System.out.println("id==="+id);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			int count=workOrderService.updateStateById(state,id);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "修改工单状态成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "修改工单状态失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
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
				List<Translate> translateList=new ArrayList<Translate>();
				if(APIUtil.ITEM_RESULT.equals(resultType)) {
					jo = APIUtil.getTabTestItem(tabName);
					FormulaDto fd=(FormulaDto)jo.get("FormulaDto");
					
					String nameChinese = fd.getName();
					String namePinYin = PinyinUtil.getPinYinString(nameChinese);
					fd.setName(namePinYin);
					
					String productNameChinese = fd.getProductName();
					String productNamePinYin = PinyinUtil.getPinYinString(productNameChinese);
					fd.setProductName(productNamePinYin);
					
					count=formulaDtoService.add(fd);
					
					Translate translate=new Translate();
					translate.setTabName(tabName);
					translate.setColName("name");
					translate.setChinese(nameChinese);
					translate.setPinYin(namePinYin);
					translate.setForeignKey(fd.getId());
					translateList.add(translate);
					
					translate=new Translate();
					translate.setTabName(tabName);
					translate.setColName("productName");
					translate.setChinese(productNameChinese);
					translate.setPinYin(productNamePinYin);
					translate.setForeignKey(fd.getId());
					translateList.add(translate);
					
					if(count>0)
						success=true;
				}
				else if(APIUtil.LIST_RESULT.equals(resultType)) {
					jo =APIUtil.getTabTestList(tabName);
					List<FormulaDto> fdList=(List<FormulaDto>)jo.get("FormulaDtoList");
					int fdListSize = fdList.size();
					for(int i=0;i<fdListSize;i++) {
						FormulaDto fd=fdList.get(i);
						
						String nameChinese = fd.getName();
						String namePinYin = PinyinUtil.getPinYinString(nameChinese);
						fd.setName(namePinYin);
						
						String productNameChinese = fd.getProductName();
						String productNamePinYin = PinyinUtil.getPinYinString(productNameChinese);
						fd.setProductName(productNamePinYin);
						
						count+=formulaDtoService.add(fd);
						
						Translate translate=new Translate();
						translate.setTabName(tabName);
						translate.setColName("name");
						translate.setChinese(nameChinese);
						translate.setPinYin(namePinYin);
						translate.setForeignKey(fd.getId());
						translateList.add(translate);
						
						translate=new Translate();
						translate.setTabName(tabName);
						translate.setColName("productName");
						translate.setChinese(productNameChinese);
						translate.setPinYin(productNamePinYin);
						translate.setForeignKey(fd.getId());
						translateList.add(translate);
					}
					if(count==fdListSize)
						success=true;
				}
				
				for (int i = 0; i < translateList.size(); i++) {
					Translate translate = translateList.get(i);
					translateService.add(translate);
				}
			}
			else if("FormulaMaterialDto".equals(tabName)) {
				JSONObject jo = null;
				if(APIUtil.ITEM_RESULT.equals(resultType)) {
					jo = APIUtil.getTabTestItem(tabName);
					FormulaMaterialDto fmd=(FormulaMaterialDto)jo.get("FormulaMaterialDto");
					count=formulaMaterialDtoService.add(fmd);
					if(count>0)
						success=true;
				}
				else if(APIUtil.LIST_RESULT.equals(resultType)) {
					jo =APIUtil.getTabTestList(tabName);
					List<FormulaMaterialDto> fmdList=(List<FormulaMaterialDto>)jo.get("FormulaMaterialDtoList");
					int fmdListSize = fmdList.size();
					for(int i=0;i<fmdListSize;i++) {
						count+=formulaMaterialDtoService.add(fmdList.get(i));
					}
					if(count==fmdListSize)
						success=true;
				}
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
				jsonMap.put("info", "������ݳɹ���");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "�������ʧ�ܣ�");
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
		//TODO ��Է���Ķ�̬����ʵʱ��������
		PlanResult plan=new PlanResult();
		String json=null;
		String result=null;
		try {
			System.out.println("item==="+item);
			result = BatchComBridge.getInstance().callGetItem(item);
			System.out.println("result==="+result);
			plan.setStatus(1);
			plan.setMsg("Success");
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
			System.out.println("------------------------------------------------------------------------------------------");
			System.out.println("DateTime==="+sdf.format(new Date()));
			System.out.println("Command==="+command);
			System.out.println("------------------------------------------------------------------------------------------");
			result = BatchComBridge.getInstance().callExecute(command);
			System.out.println("Result==="+result);
			System.out.println(" ");
			plan.setStatus(1);
			plan.setMsg("Success");
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
			jsonMap.put("info", "������Ϣ��");
		}
		return jsonMap;
	}
	
	/**
	 * 4.3 配方数据推送
	 * @param bodyEnc
	 */
	@RequestMapping(value="/formulaPush", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult formulaPush(@RequestBody String bodyEnc) {
		
		PlanResult plan=new PlanResult();
		
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		List<FormulaDto> fdList=new ArrayList<FormulaDto>();
		net.sf.json.JSONArray fdJA = net.sf.json.JSONArray.fromObject(bodyDec);
		for (int i = 0; i < fdJA.size(); i++) {
			net.sf.json.JSONObject fdJO = (net.sf.json.JSONObject)fdJA.get(i);
			FormulaDto fd=(FormulaDto)net.sf.json.JSONObject.toBean(fdJO, FormulaDto.class);
			System.out.println("id==="+fd.getId());
			fdList.add(fd);
		}
		int c=formulaDtoService.add(fdList.get(0));
		if(c>0) {
			plan.setSuccess(true);
			plan.setStatus(1);
			plan.setMsg("成功");
		}
		else {
			plan.setSuccess(false);
			plan.setStatus(0);
			plan.setMsg("失败");
		}
		return plan;
	}

	/**
	 * 4.4 工单下达
	 * @param bodyEnc
	 */
	@RequestMapping(value="/workOrderDown", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult workOrderDown(@RequestBody String bodyEnc) {

		PlanResult plan=new PlanResult();
		
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		/*
		List<WorkOrderBody> wobList=new ArrayList<WorkOrderBody>();
		net.sf.json.JSONArray wobJA = net.sf.json.JSONArray.fromObject(bodyDec);
		for (int i = 0; i < wobJA.size(); i++) {
			net.sf.json.JSONObject wobJO = (net.sf.json.JSONObject)wobJA.get(i);
			WorkOrderBody wob=(WorkOrderBody)net.sf.json.JSONObject.toBean(wobJO, WorkOrderBody.class);
			System.out.println("id==="+wob.getId());
			wobList.add(wob);
		}
		int c=workOrderBodyService.add(wobList.get(0));
		*/

		List<WorkOrder> woList=new ArrayList<WorkOrder>();
		net.sf.json.JSONArray woJA = net.sf.json.JSONArray.fromObject(bodyDec);
		for (int i = 0; i < woJA.size(); i++) {
			net.sf.json.JSONObject woJO = (net.sf.json.JSONObject)woJA.get(i);
			WorkOrder wo=(WorkOrder)net.sf.json.JSONObject.toBean(woJO, WorkOrder.class);
			System.out.println("ID==="+wo.getID());
			woList.add(wo);
		}
		WorkOrder wo = woList.get(0);
		int c=workOrderService.add(wo);
		if(c>0) {
			String workOrderID = wo.getWorkOrderID();
			String recipeID = wo.getRecipeID();
			c=recipePMService.addFromRMT(workOrderID, recipeID);
			if(c>0) {
				Integer id = wo.getID();
				c=workOrderService.updateStateById(WorkOrder.WLQTWB,id);
			}
			
			plan.setSuccess(true);
			plan.setStatus(1);
			plan.setMsg("成功");
		}
		else {
			plan.setSuccess(false);
			plan.setStatus(0);
			plan.setMsg("失败");
		}
		return plan;
	}

	/**
	 * 4.5 工单取消
	 * @param bodyEnc
	 */
	@RequestMapping(value="/workOrderCannel", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult workOrderCannel(@RequestBody String bodyEnc) {

		PlanResult plan=new PlanResult();
		
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		
		plan.setSuccess(true);
		plan.setStatus(1);
		plan.setMsg("成功");
		return plan;
	}
	
	/**
	 * 4.6 投料信息下发
	 * @param bodyEnc
	 * @return
	 */
	@RequestMapping(value="/feedIssusDown", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult feedIssusDown(@RequestBody String bodyEnc) {

		PlanResult plan=new PlanResult();

		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		net.sf.json.JSONObject fibJO = net.sf.json.JSONObject.fromObject(bodyDec);
		FeedIssusBody fib=(FeedIssusBody)net.sf.json.JSONObject.toBean(fibJO, FeedIssusBody.class);
		int c=feedIssusBodyService.add(fib);
		if(c>0) {
			plan.setSuccess(true);
			plan.setStatus(1);
			plan.setMsg("成功");
		}
		else {
			plan.setSuccess(false);
			plan.setStatus(0);
			plan.setMsg("失败");
		}
		return plan;
	}
	
	/**
	 * 4.7 制膏编号下发
	 * @param bodyEnc
	 * @return
	 */
	@RequestMapping(value="/pasteWorkingNumDown", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult pasteWorkingNumDown(@RequestBody String bodyEnc) {

		PlanResult plan=new PlanResult();
		
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		List<PasteWorkingNumBody> pwnbList=new ArrayList<PasteWorkingNumBody>();
		net.sf.json.JSONArray pwnbJA = net.sf.json.JSONArray.fromObject(bodyDec);
		for (int i = 0; i < pwnbJA.size(); i++) {
			net.sf.json.JSONObject pwnbJO = (net.sf.json.JSONObject)pwnbJA.get(i);
			PasteWorkingNumBody pwnb=(PasteWorkingNumBody)net.sf.json.JSONObject.toBean(pwnbJO, PasteWorkingNumBody.class);
			System.out.println("id==="+pwnb.getId());
			pwnbList.add(pwnb);
		}
		int c=pasteWorkingNumBodyService.add(pwnbList.get(0));
		if(c>0) {
			plan.setSuccess(true);
			plan.setStatus(1);
			plan.setMsg("成功");
		}
		else {
			plan.setSuccess(false);
			plan.setStatus(0);
			plan.setMsg("失败");
		}
		return plan;
	}
	
	@RequestMapping(value="/materialCheckOverIssusDown", method = RequestMethod.POST)
	@ResponseBody
	public PlanResult materialCheckOverIssusDown(@RequestBody String bodyEnc) {

		PlanResult plan=new PlanResult();
		
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		net.sf.json.JSONObject mcoibJO = net.sf.json.JSONObject.fromObject(bodyDec);
		MaterialCheckOverIssusBody mcoib=(MaterialCheckOverIssusBody)net.sf.json.JSONObject.toBean(mcoibJO, MaterialCheckOverIssusBody.class);
		int c=materialCheckOverIssusBodyService.add(mcoib);
		if(c>0) {
			plan.setSuccess(true);
			plan.setStatus(1);
			plan.setMsg("成功");
		}
		else {
			plan.setSuccess(false);
			plan.setStatus(0);
			plan.setMsg("失败");
		}
		return plan;
	}
	
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/changeOrderStatus 工单状态变更
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/batchProcessParameters 工艺参数采集
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/devicationRecord 偏差数据采集
}
