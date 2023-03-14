package com.batchMesComDK.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
	private RecipePM_TMPService recipePM_TMPService;
	@Autowired
	private ManFeedService manFeedService;
	@Autowired
	private BatchRecordService batchRecordService;
	@Autowired
	private BatchTestService batchTestService;
	@Autowired
	private TranslateService translateService;
	@Autowired
	private SignoffTemplateService signoffTemplateService;
	@Autowired
	private SignoffDataService signoffDataService;
	@Autowired
	private MaterialCheckOverIssusBodyService materialCheckOverIssusBodyService;
	public static final String MODULE_NAME="batch";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<Map<String, Object>> woPreStateList=new ArrayList<>();
	
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
		Constant.setBatchTestStateInRequest(request);
		
		return MODULE_NAME+"/test";
	}

	/**
	 * 巡回检索工单状态变化
	 */
	@RequestMapping(value="/keepWatchOnWorkOrder")
	public void keepWatchOnWorkOrder() {
		
		try {
			List<WorkOrder> woList=workOrderService.getKeepWatchList();
			System.out.println("woListSize==="+woList.size());
			String formulaIds="";
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
					String formulaId = wo.getFormulaId();
					String workOrderID = wo.getWorkOrderID();				
					String recipeID = wo.getRecipeID();
					System.out.println("formulaId==="+formulaId);
					System.out.println("workOrderID==="+workOrderID);
					System.out.println("recipeID==="+recipeID);
					createBatch(formulaId,workOrderID,recipeID);
					
					addManFeedFromRecipePM(workOrderID,null,null);//工单创建时，从配方参数表里取数据，放入人工投料表
					break;
				case WorkOrder.BQD:
					//启动执行配方
					for (int j = 1; j <= batchCount; j++) {
						String formulaIdStr = wo.getFormulaId().toString();
						//String batchIDVal = BLKey_x("BatchID",j);
						String batchIDVal = getItem("BLBatchID_"+j);
						batchIDVal = batchIDVal.substring(0, batchIDVal.indexOf(Constant.END_SUCCESS));
						if(formulaIdStr.equals(batchIDVal)) {
							//String createIDVal = BLKey_x("CreateID",j);
							String createIDVal = getItem("BLCreateID_"+j);
							createIDVal = createIDVal.substring(0, createIDVal.indexOf(Constant.END_SUCCESS));
							System.out.println("createIDVal==="+createIDVal);
							
							StringBuilder commandBQDSB=new StringBuilder();
							commandBQDSB.append("[COMMAND(Item,");
							commandBQDSB.append(Constant.USERID);
							commandBQDSB.append(",");
							commandBQDSB.append(createIDVal);
							commandBQDSB.append(",");
							commandBQDSB.append(BatchTest.START);
							commandBQDSB.append(")]");
							execute(commandBQDSB.toString());
							
							//String stateVal = BLKey_x("State",j);
							String stateVal = getItem("BLState_"+j);
							stateVal = stateVal.substring(0, stateVal.indexOf(Constant.END_SUCCESS));
							if(BatchTest.RUNNING.equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYX, wo.getID());
							}
						}
					}
					break;
				case WorkOrder.BQX:
				case WorkOrder.BZT:
					//调用batch command接口
					for (int j = 1; j <= batchCount; j++) {
						String formulaIdStr = wo.getFormulaId().toString();
						//String batchIDVal = BLKey_x("BatchID",j);
						String batchIDVal = getItem("BLBatchID_"+j);
						batchIDVal = batchIDVal.substring(0, batchIDVal.indexOf(Constant.END_SUCCESS));
						if(formulaIdStr.equals(batchIDVal)) {
							//String createIDVal = BLKey_x("CreateID",j);
							String createIDVal = getItem("BLCreateID_"+j);
							createIDVal = createIDVal.substring(0, createIDVal.indexOf(Constant.END_SUCCESS));
							System.out.println("createIDVal==="+createIDVal);
							
							StringBuilder commandQXZTSB=new StringBuilder();
							commandQXZTSB.append("[COMMAND(Item,");
							commandQXZTSB.append(Constant.USERID);
							commandQXZTSB.append(",");
							commandQXZTSB.append(createIDVal);
							commandQXZTSB.append(",");
							commandQXZTSB.append(BatchTest.STOP);
							commandQXZTSB.append(")]");
							execute(commandQXZTSB.toString());

							//String stateVal = BLKey_x("State",j);
							String stateVal = getItem("BLState_"+j);
							stateVal = stateVal.substring(0, stateVal.indexOf(Constant.END_SUCCESS));
							if(BatchTest.STOPPED.equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYWZZ, wo.getID());
							}
						}
					}
					break;
				}
				
				if(state==WorkOrder.BYX||state==WorkOrder.BQX||state==WorkOrder.BZT) {
					//把状态大于5的工单的可执行配方id拼接起来，可执行配方id对应batchID
					formulaIds+=","+wo.getFormulaId();
				}
			}

			if(StringUtils.isEmpty(formulaIds)) {
				String[] formulaIdArr = formulaIds.substring(1).split(",");
				for (int i = 0; i < formulaIdArr.length; i++) {
					String formulaId = formulaIdArr[i];
					for (int j = 1; j <= batchCount; j++) {
						//String batchIDVal = BLKey_x("BatchID",j);
						String batchIDVal = getItem("BLBatchID_"+j);
						batchIDVal = batchIDVal.substring(0, batchIDVal.indexOf(Constant.END_SUCCESS));
						if(formulaId.equals(batchIDVal)) {
							//String stateVal = BLKey_x("State",j);
							String stateVal = getItem("BLState_"+j);
							stateVal = stateVal.substring(0, stateVal.indexOf(Constant.END_SUCCESS));
							if(BatchTest.COMPLETE.equals(stateVal)) {
								workOrderService.updateStateByFormulaId(WorkOrder.BJS, formulaId);
							}
							else if(BatchTest.STOPPED.equals(stateVal)) {
								workOrderService.updateStateByFormulaId(WorkOrder.BYWZZ, formulaId);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 巡回检索工单状态变化(模拟虚拟机测试用)
	 */
	@RequestMapping(value="/keepWatchOnWorkOrderTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> keepWatchOnWorkOrderTest() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<WorkOrder> woList=workOrderService.getKeepWatchList();
			System.out.println("woListSize==="+woList.size());
			String workOrderIDs="";
			String formulaIds="";
			String startWorkOrderIDs="";
			String blcResult = batchTestService.getItem(Constant.ITEM_BATCH_LIST_CT);
			System.out.println("blcResult==="+blcResult);
			String batchListCt = blcResult.substring(0, blcResult.indexOf(Constant.END_SUCCESS));
			int batchCount = Integer.valueOf(batchListCt);
			System.out.println("batchCount==="+batchCount);
			for (int i = 0; i < woList.size(); i++) {
				WorkOrder wo = woList.get(i);
				Integer state = wo.getState();
				System.out.println("state==="+state);
				switch (state) {
				case WorkOrder.CSQRWB:
					//调用创建batch接口创建batch
					String formulaId = wo.getFormulaId();
					String workOrderID = wo.getWorkOrderID();				
					String recipeID = wo.getRecipeID();
					String productCode = wo.getProductCode();
					String productName = wo.getProductName();
					System.out.println("formulaId==="+formulaId);
					System.out.println("workOrderID==="+workOrderID);
					System.out.println("recipeID==="+recipeID);
					System.out.println("productCode==="+productCode);
					System.out.println("productName==="+productName);
					
					/*
					 * 内部测试先屏蔽掉这个逻辑
					addManFeedFromRecipePM(workOrderID,productCode,productName);//工单创建时，从配方参数表里取数据，放入人工投料表
					*/
					
					BatchTest bt=new BatchTest();
					bt.setRecipe(recipeID);
					bt.setBatchID(formulaId);
					bt.setDescription("FRENCHVANILLA PREMIUM -CLASSBASED");
					addBatchTest(bt);
					
					workOrderService.updateStateById(WorkOrder.BCJWB, wo.getID());
					
					StringBuilder qrwbSB=new StringBuilder();
					qrwbSB.append("[{");
					qrwbSB.append("\"workOrder\":\"");
					qrwbSB.append(wo.getWorkOrderID());
					qrwbSB.append("\",\"orderExecuteStatus\":\"CREATED\",");
					qrwbSB.append("\"updateTime\":\"2022-1-13 12:14:13\",\"updateBy\":\"OPR2\"}]");
					changeOrderStatus(qrwbSB.toString());
					
					addWOPreStateInList(WorkOrder.BCJWB,wo.getWorkOrderID());
					break;
				case WorkOrder.BQD:
					//启动执行配方
					for (int j = 1; j <= batchCount; j++) {
						String workOrderIDStr = wo.getWorkOrderID().toString();
						String formulaIdStr = wo.getFormulaId().toString();
						String batchIDVal = batchTestService.getBLKey_x("BatchID",j);
						if(formulaIdStr.equals(batchIDVal)) {
							String createIDVal = batchTestService.getBLKey_x("CreateID",j);
							System.out.println("createIDVal==="+createIDVal);
							
							batchTestService.updateStateByCreateID(BatchTest.START,Integer.valueOf(createIDVal));
							
							String stateVal = batchTestService.getBLKey_x("State",j);
							if(BatchTest.RUNNING.equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYX, wo.getID());
								
								StringBuilder qdSB=new StringBuilder();
								qdSB.append("[{");
								qdSB.append("\"workOrder\":\"");
								qdSB.append(wo.getWorkOrderID());
								qdSB.append("\",\"orderExecuteStatus\":\"COMMENCED\",");
								qdSB.append("\"updateTime\":\"2022-1-13 12:14:13\",\"updateBy\":\"OPR2\"}]");
								changeOrderStatus(qdSB.toString());
								
								addWOPreStateInList(WorkOrder.BYX,workOrderIDStr);
							}
						}
					}
					break;
				case WorkOrder.BQX:
				//case WorkOrder.BZT://电子签名时需要暂停工单，因此这里不需要意外终止，待签名结束后工单仍然继续执行
					//调用batch command接口
					for (int j = 1; j <= batchCount; j++) {
						String formulaIdStr = wo.getFormulaId().toString();
						String batchIDVal = batchTestService.getBLKey_x("BatchID",j);
						System.out.println("batchIDVal==="+batchIDVal);
						if(formulaIdStr.equals(batchIDVal)) {
							String createIDVal = batchTestService.getBLKey_x("CreateID",j);
							System.out.println("createIDVal==="+createIDVal);
							
							batchTestService.updateStateByCreateID(BatchTest.STOP,Integer.valueOf(createIDVal));

							String stateVal = BLKey_x("State",j);
							if(BatchTest.STOPPED.equals(stateVal)) {
								workOrderService.updateStateById(WorkOrder.BYWZZ, wo.getID());
								
								StringBuilder qxSB=new StringBuilder();
								qxSB.append("[{");
								qxSB.append("\"workOrder\":\"");
								qxSB.append(wo.getWorkOrderID());
								qxSB.append("\",\"orderExecuteStatus\":\"CANCEL\",");
								qxSB.append("\"updateTime\":\"2022-1-13 12:14:13\",\"updateBy\":\"OPR2\"}]");
								changeOrderStatus(qxSB.toString());
								
								addWOPreStateInList(WorkOrder.BYWZZ,wo.getWorkOrderID());
							}
						}
					}
					break;
				}
				
				if(state==WorkOrder.BYX||state==WorkOrder.BQX||state==WorkOrder.BZT) {
					//把状态大于5的工单的可执行配方id拼接起来，可执行配方id对应batchID
					formulaIds+=","+wo.getFormulaId();
					workOrderIDs+=","+wo.getWorkOrderID();
				}
			}

			if(!StringUtils.isEmpty(formulaIds)) {
				String[] formulaIdArr = formulaIds.substring(1).split(",");
				String[] workOrderIDArr = workOrderIDs.substring(1).split(",");
				for (int i = 0; i < formulaIdArr.length; i++) {
					String formulaId = formulaIdArr[i];
					String workOrderID = workOrderIDArr[i];
					for (int j = 1; j <= batchCount; j++) {
						String batchIDVal = batchTestService.getBLKey_x("BatchID",j);
						if(formulaId.equals(batchIDVal)) {
							String stateVal = batchTestService.getBLKey_x("State",j);
							if(BatchTest.COMPLETE.equals(stateVal)) {
								workOrderService.updateStateByFormulaId(WorkOrder.BJS, formulaId);
								
								StringBuilder jsSB=new StringBuilder();
								jsSB.append("[{");
								jsSB.append("\"workOrder\":\"");
								jsSB.append(workOrderID);
								jsSB.append("\",\"orderExecuteStatus\":\"COMPLETE\",");
								jsSB.append("\"updateTime\":\"2022-1-13 12:14:13\",\"updateBy\":\"OPR2\"}]");
								changeOrderStatus(jsSB.toString());
								
								addWOPreStateInList(WorkOrder.BJS,workOrderID);
							}
							else if(BatchTest.STOPPED.equals(stateVal)) {
								workOrderService.updateStateByFormulaId(WorkOrder.BYWZZ, formulaId);

								StringBuilder jsSB=new StringBuilder();
								jsSB.append("[{");
								jsSB.append("\"workOrder\":\"");
								jsSB.append(workOrderID);
								jsSB.append("\",\"orderExecuteStatus\":\"PRODUCTBREAK\",");
								jsSB.append("\"updateTime\":\"2022-1-13 12:14:13\",\"updateBy\":\"OPR2\"}]");
								changeOrderStatus(jsSB.toString());
								
								addWOPreStateInList(WorkOrder.BYWZZ,workOrderID);
							}
						}
					}
				}
			}
			if(!StringUtils.isEmpty(startWorkOrderIDs)) {
				formulaIds=formulaIds.substring(1);
				JSONArray bodyParamJA=new JSONArray();
				JSONObject bodyParamJO=new JSONObject();
				bodyParamJO.put("workOrder", "WO48qn5e9go9");
				bodyParamJO.put("orderExecuteStatus", "PRODUCTION");
				bodyParamJO.put("updateTime", "2023-03-13 15:40:13");
				bodyParamJO.put("updateBy", "OPR2");
				bodyParamJA.put(bodyParamJO);
				APIUtil.doHttpMes("changeOrderStatus", bodyParamJA);
				
				StringBuilder commandSB=new StringBuilder();
				commandSB.append("");
				//changeOrderStatus();
				//aaaaaaaaa
			}
			
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");//001正常 002数据格式有误 003数据不完整
			jsonMap.put("msg", "正常");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}
	
	private void addWOPreStateInList(Integer state, String workOrderID) {
		System.out.println("addWOPreStateInList...."+workOrderID);
		boolean exist=checkWOIfExistInPreStateList(workOrderID);
		System.out.println("exist==="+exist);
		if(exist) {
			for (Map<String, Object> woPreStateMap : woPreStateList) {
				String preWorkOrderID = woPreStateMap.get("workOrderID").toString();
				if(workOrderID.equals(preWorkOrderID)) {
					System.out.println("state==="+state);
					woPreStateMap.put("state", state);
				}
			}
		}
		else {
			Map<String,Object> preStateMap=new HashMap<>();
			preStateMap.put("workOrderID", workOrderID);
			preStateMap.put("state", state);
			woPreStateList.add(preStateMap);
		}
	}
	
	private boolean checkWOIfExistInPreStateList(String workOrderID) {
		boolean exist=false;
		for (Map<String, Object> woPreStateMap : woPreStateList) {
			String preWorkOrderID = woPreStateMap.get("workOrderID").toString();
			if(workOrderID.equals(preWorkOrderID)) {
				exist=true;
				break;
			}
		}
		return exist;
	}
	
	/**
	 * 这个方法是以前调用batch虚拟机里BLState_x方法不通时模拟调用的，现在虚拟机端那个方法通了，这个方法暂时不用了
	 * @param key
	 * @param rowNumber
	 * @return
	 */
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
	
	private void createBatch(String batchID, String workOrderID, String recipeID) {
		StringBuilder commandSB=new StringBuilder();
		commandSB.append("[BATCH(Item,");
		commandSB.append(Constant.USERID);
		commandSB.append(",");
		commandSB.append(recipeID);
		commandSB.append(",");
		commandSB.append(batchID);
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

	@RequestMapping(value="/deleteWorkOrderByIds")
	@ResponseBody
	public Map<String, Object> deleteWorkOrderByIds(String ids) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=workOrderService.deleteByIds(ids);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "删除工单成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "删除工单失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/editWorkOrder")
	@ResponseBody
	public Map<String, Object> editWorkOrder(WorkOrder wo) {
		
		System.out.println("id==="+wo.getID());

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=workOrderService.edit(wo);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "修改工单成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "修改工单失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/getWorkOrder")
	@ResponseBody
	public Map<String, Object> getWorkOrder(Integer id) {
		
		System.out.println("id==="+id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			WorkOrder wo=workOrderService.getById(id);
			if(wo==null) {
				jsonMap.put("message", "no");
				jsonMap.put("info", "查询工单失败");
			}
			else {
				jsonMap.put("message", "ok");
				jsonMap.put("workOrder", wo);
				jsonMap.put("info", "查询工单成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	/**
	 * 这个接口仅供测试用，真正添加远程配方参数是在上位机端操作
	 * @param rPM_TMP
	 * @return
	 */
	@RequestMapping(value="/addRecipePM_TMP")
	@ResponseBody
	public Map<String, Object> addRecipePM_TMP(RecipePM_TMP rPM_TMP) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePM_TMPService.add(rPM_TMP);
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

	@RequestMapping(value="/deleteRecipePM_TMPByIds")
	@ResponseBody
	public Map<String, Object> deleteRecipePM_TMPByIds(String ids) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePM_TMPService.deleteByIds(ids);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "删除远程配方参数成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "删除远程配方参数失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/editRecipePM_TMP")
	@ResponseBody
	public Map<String, Object> editRecipePM_TMP(RecipePM_TMP rPM_TMP) {
		
		System.out.println("id==="+rPM_TMP.getID());
		System.out.println("pMCode==="+rPM_TMP.getPMCode());
		System.out.println("pMName==="+rPM_TMP.getPMName());
		System.out.println("lot==="+rPM_TMP.getLot());
		System.out.println("dosage==="+rPM_TMP.getDosage());
		System.out.println("unit==="+rPM_TMP.getUnit());
		System.out.println("hLimit==="+rPM_TMP.getHLimit());
		System.out.println("lLimit==="+rPM_TMP.getLLimit());
		System.out.println("pMType==="+rPM_TMP.getPMType());
		System.out.println("recipeID==="+rPM_TMP.getRecipeID());
		System.out.println("cName==="+rPM_TMP.getCName());
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePM_TMPService.edit(rPM_TMP);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "修改远程配方参数成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "修改远程配方参数失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonMap.put("message", "no");
			jsonMap.put("info", "修改远程配方参数失败");
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/getRecipePM_TMP")
	@ResponseBody
	public Map<String, Object> getRecipePM_TMP(Integer id) {
		
		System.out.println("id==="+id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			RecipePM_TMP rPM_TMP=recipePM_TMPService.getById(id);
			if(rPM_TMP==null) {
				jsonMap.put("message", "no");
				jsonMap.put("info", "查询远程配方参数失败");
			}
			else {
				jsonMap.put("message", "ok");
				jsonMap.put("rPM_TMP", rPM_TMP);
				jsonMap.put("info", "查询远程配方参数成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addRecipePMFromTMP")
	@ResponseBody
	public Map<String, Object> addRecipePMFromTMP(String workOrderID, String productCode, String productName) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePMService.addFromTMP(workOrderID, productCode, productName);
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

	@RequestMapping(value="/deleteRecipePMByIds")
	@ResponseBody
	public Map<String, Object> deleteRecipePMByIds(String ids) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=recipePMService.deleteByIds(ids);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "删除配方参数成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "删除配方参数失败");
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

	@RequestMapping(value="/deleteBatchRecordByIds")
	@ResponseBody
	public Map<String, Object> deleteBatchRecordByIds(String ids) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=batchRecordService.deleteByIds(ids);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "删除批记录成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "删除批记录失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addManFeed")
	@ResponseBody
	public Map<String, Object> addManFeed(ManFeed manFeed) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=manFeedService.add(manFeed);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加人工投料信息成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加人工投料信息失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addManFeedFromRecipePM")
	@ResponseBody
	public Map<String, Object> addManFeedFromRecipePM(String workOrderID, String productCode, String productName) {

		//后面两个参数在jsp页面还没加
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=manFeedService.addFromRecipePM(workOrderID,productCode,productName);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加人工投料信息成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加人工投料信息失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/deleteManFeedByIds")
	@ResponseBody
	public Map<String, Object> deleteManFeedByIds(String ids) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=manFeedService.deleteByIds(ids);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "删除人工投料信息成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "删除人工投料信息失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/editManFeed")
	@ResponseBody
	public Map<String, Object> editManFeed(ManFeed mf) {
		
		System.out.println("workOrderID==="+mf.getWorkOrderID());

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=manFeedService.editByWorkOrderIDFeedPort(mf);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "修改人工投料信息成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "修改人工投料信息失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/getManFeed")
	@ResponseBody
	public Map<String, Object> getManFeed(String workOrderID, String feedPort) {
		
		System.out.println("workOrderID==="+workOrderID);
		System.out.println("feedPort==="+feedPort);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			ManFeed mf=manFeedService.getByWorkOrderIDFeedPort(workOrderID,feedPort);
			if(mf==null) {
				jsonMap.put("message", "no");
				jsonMap.put("info", "查询人工投料信息失败");
			}
			else {
				jsonMap.put("message", "ok");
				jsonMap.put("manFeed", mf);
				jsonMap.put("info", "查询人工投料信息成功");
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

	@RequestMapping(value="/updateBatchTestStateByCreateID")
	@ResponseBody
	public Map<String, Object> updateBatchTestStateByCreateID(String state, Integer createID) {
		
		System.out.println("state==="+state);
		System.out.println("createID==="+createID);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			int count=batchTestService.updateStateByCreateID(state,createID);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "修改BatchTest状态成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "修改BatchTest状态失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addSignoffFromTemplate")
	@ResponseBody
	public Map<String, Object> addSignoffFromTemplate(String workOrderID) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=signoffDataService.addFromTemplate(workOrderID);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加电子签名成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加电子签名失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}
	@RequestMapping(value="/addSignoffTemplate")
	@ResponseBody
	public Map<String, Object> addSignoffTemplate(SignoffTemplate st) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=signoffTemplateService.add(st);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加电子签名模板成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加电子签名模板失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}

	@RequestMapping(value="/addBatchTest")
	@ResponseBody
	public Map<String, Object> addBatchTest(BatchTest bt) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			int count=batchTestService.add(bt);
			if(count>0) {
				jsonMap.put("message", "ok");
				jsonMap.put("info", "添加batch模拟数据成功");
			}
			else {
				jsonMap.put("message", "no");
				jsonMap.put("info", "添加batch模拟数据失败");
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
	 * 4.1 工单状态变更
	 * @param bodyEnc
	 * @return
	 */
	@RequestMapping(value="/changeOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeOrderStatus(@RequestBody String bodyEnc) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			System.out.println("bodyEnc==="+bodyEnc);
			String bodyDec = bodyEnc;
			JSONArray bodyParamJA=new JSONArray();
			JSONObject bodyParamJO=null;
			//net.sf.json.JSONArray cosJA = net.sf.json.JSONArray.fromObject(bodyDec);
			JSONArray cosJA=new JSONArray(bodyDec);
			for (int i = 0; i < cosJA.length(); i++) {
				bodyParamJO=new JSONObject();
				JSONObject cosJO = (JSONObject)cosJA.get(i);
				String workOrder = cosJO.getString("workOrder");
				String orderExecuteStatus = cosJO.getString("orderExecuteStatus");
				String updateTime = cosJO.getString("updateTime");
				String updateBy = cosJO.getString("updateBy");
				System.out.println("workOrder==="+workOrder);
				System.out.println("orderExecuteStatus==="+orderExecuteStatus);
				System.out.println("updateTime==="+updateTime);
				System.out.println("updateBy==="+updateBy);
				
				/*
				bodyParamJO.put("workOrder", "WO48qn5e9go9");
				bodyParamJO.put("orderExecuteStatus", "PRODUCTION");
				bodyParamJO.put("updateTime", "2023-03-13 15:40:13");
				bodyParamJO.put("updateBy", "OPR2");
				*/
				bodyParamJO.put("workOrder", workOrder);
				bodyParamJO.put("orderExecuteStatus", orderExecuteStatus);
				bodyParamJO.put("updateTime", updateTime);
				bodyParamJO.put("updateBy", updateBy);
				bodyParamJA.put(bodyParamJO);
			}
			
			
			JSONObject resultJO = APIUtil.doHttpMes("changeOrderStatus", bodyParamJA);
			boolean success = resultJO.getBoolean("success");
			if(success) {
				jsonMap.put("success", "true");
				jsonMap.put("state", "001");//001正常 002数据格式有误 003数据不完整
				jsonMap.put("msg", "正常");
			}
			else {
				jsonMap.put("success", "false");
				jsonMap.put("state", "002");
				jsonMap.put("msg", "数据格式有误");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}
	
	/**
	 * 4.3 配方数据推送
	 * @param bodyEnc
	 */
	@RequestMapping(value="/formulaPush", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> formulaPush(@RequestBody String bodyEnc) {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		System.out.println("bodyEnc==="+bodyEnc);
		/*
		String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		List<FormulaDto> fdList=new ArrayList<FormulaDto>();
		net.sf.json.JSONArray fdJA = net.sf.json.JSONArray.fromObject(bodyDec);
		for (int i = 0; i < fdJA.size(); i++) {
			net.sf.json.JSONObject fdJO = (net.sf.json.JSONObject)fdJA.get(i);
			FormulaDto fd=(FormulaDto)net.sf.json.JSONObject.toBean(fdJO, FormulaDto.class);
			System.out.println("id==="+fd.getId());
			fdList.add(fd);
		}
		*/
		int c=1;//formulaDtoService.add(fdList.get(0));
		if(c>0) {
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");
			jsonMap.put("msg", "正常");
		}
		else {
			jsonMap.put("success", "true");
			jsonMap.put("state", "002");
			jsonMap.put("msg", "数据格式有误");
		}
		
		return jsonMap;
	}

	/**
	 * 4.4 工单下达
	 * @param bodyEnc
	 */
	@RequestMapping(value="/workOrderDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workOrderDown(@RequestBody String bodyEnc) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = bodyEnc;
		//String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
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

		WorkOrder wo = convertMesWorkOrderDownToJava(bodyEnc);
		int c=workOrderService.add(wo);
		if(c>0) {
			String workOrderID = wo.getWorkOrderID();
			String productCode = wo.getProductCode();
			String productName = wo.getProductName();
			//c=recipePMService.addFromTMP(workOrderID, productCode, productName);
			c=recipePMService.addFromWORecipePMList(workOrderID, wo.getRecipePMList());
			if(c>0) {
				/*
				 * 为了测试这块先屏蔽掉
				 */
				//c=recipePMService.updateDosageByPMParam(wo.getRecipePMList());
				c=workOrderService.updateStateByWorkOrderID(WorkOrder.WLQTWB,workOrderID);
			}
			
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");
			jsonMap.put("msg", "正常");
		}
		else {
			jsonMap.put("success", "true");
			jsonMap.put("state", "002");
			jsonMap.put("msg", "数据格式有误");
		}
		return jsonMap;
	}
	
	private WorkOrder convertMesWorkOrderDownToJava(String mesBody) {

		net.sf.json.JSONObject wodMesJO = net.sf.json.JSONObject.fromObject(mesBody);
		//WorkOrder wo=(WorkOrder)net.sf.json.JSONObject.toBean(woJO, WorkOrder.class);
		String recipeID = wodMesJO.getString("formulaId");//mes那边发来的formulaId对应java端的recipeID
		//String id = wodMesJO.getString("id");
		String lotNo = wodMesJO.getString("lotNo");
		String planStartTime = wodMesJO.getString("planStartTime");
		String productName = wodMesJO.getString("productName");
		String productcode = wodMesJO.getString("productcode");
		String qty = wodMesJO.getString("qty");
		String unit = wodMesJO.getString("unit");
		String workOrder = wodMesJO.getString("workOrder");
		String materialListStr = wodMesJO.getString("materialList");
		List<RecipePM> recipePMList=convertMesMaterialListStrToRecipePMList(materialListStr);
		
		WorkOrder wo=new WorkOrder();
		String formulaId=workOrderService.createFormulaIdByDateYMD(productcode,productName);
		wo.setFormulaId(formulaId);
		wo.setRecipeID(recipeID);
		//wo.setID(Integer.valueOf(id));
		wo.setCreateTime(planStartTime);
		wo.setProductName(productName);
		wo.setProductCode(productcode);
		wo.setTotalOutput(qty);
		wo.setWorkOrderID(workOrder);
		wo.setRecipePMList(recipePMList);
		
		return wo;
	}
	
	private List<RecipePM> convertMesMaterialListStrToRecipePMList(String materialListStr){
		List<RecipePM> recipePMList=new ArrayList<>();
		net.sf.json.JSONArray materialListJA = net.sf.json.JSONArray.fromObject(materialListStr);
		int materialListJASize = materialListJA.size();
		RecipePM recipePM=null;
		for (int i = 0; i < materialListJASize; i++) {
			net.sf.json.JSONObject materialListJO = (net.sf.json.JSONObject)materialListJA.get(i);
			String materialCode = materialListJO.getString("materialCode");
			String materialName = materialListJO.getString("materialName");
			String qty = materialListJO.getString("qty");
			
			recipePM=new RecipePM();
			recipePM.setPMCode(materialCode);
			recipePM.setPMName(materialName);
			recipePM.setDosage(qty);
			
			recipePMList.add(recipePM);
		}
		return recipePMList;
	}
	
	private List<ManFeed> convertMesFeedIssusDownToJava(String mesBody) {

		List<ManFeed> mfList=new ArrayList<ManFeed>();
		ManFeed mf=null;
		net.sf.json.JSONArray fidMesJA = net.sf.json.JSONArray.fromObject(mesBody);
		int fidMesJASize = fidMesJA.size();
		for(int i=0;i<fidMesJASize;i++) {
			net.sf.json.JSONObject fidMesJO = (net.sf.json.JSONObject)fidMesJA.get(i);
			String workOrder = fidMesJO.getString("workOrder");
			String feedportCode = fidMesJO.getString("feedportCode");
			String feedTime = fidMesJO.getString("feedTime");
			String materialCode = fidMesJO.getString("materialCode");
			String materialName = fidMesJO.getString("materialName");
			Float suttle = Float.valueOf(fidMesJO.getString("suttle"));
			String unit = fidMesJO.getString("unit");
			
			mf=new ManFeed();
			mf.setWorkOrderID(workOrder);
			mf.setFeedPort(feedportCode);
			mf.setFeedTime(feedTime);
			mf.setMaterialCode(materialCode);
			mf.setMaterialName(materialName);
			mf.setSuttle(suttle);
			mf.setUnit(unit);
			mfList.add(mf);
		}
		
		return mfList;
	}

	/**
	 * 4.5 工单取消
	 * @param bodyEnc
	 */
	@RequestMapping(value="/workOrderCannel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workOrderCannel(@RequestBody String bodyEnc) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			System.out.println("bodyEnc==="+bodyEnc);
			//String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
			net.sf.json.JSONArray wocMesJA = net.sf.json.JSONArray.fromObject(bodyEnc);
			//WorkOrder wo=(WorkOrder)net.sf.json.JSONObject.toBean(woJO, WorkOrder.class);
			int wocMesJASize = wocMesJA.size();
			boolean workOrderBool=true;
			boolean statusBool=true;
			String workOrders = "";
			for(int i=0;i<wocMesJASize;i++) {
				net.sf.json.JSONObject wocMesJO=(net.sf.json.JSONObject)wocMesJA.get(i);
				String workOrder = wocMesJO.getString("workOrder");
				String orderExecuteStatus = wocMesJO.getString("orderExecuteStatus");
				System.out.println("workOrder==="+workOrder);
				System.out.println("orderExecuteStatus==="+orderExecuteStatus);
				if("CANCEL".equals(orderExecuteStatus)) {
					workOrders+=","+workOrder;
				}
				else {
					statusBool=false;
				}
				
				/*
				String batchID=workOrderService.getFormulaIdByWOID(workOrder);
				
				String batchCountResultStr = getItem("BatchListCt");
				System.out.println("batchCountResultStr==="+batchCountResultStr);
				JSONObject batchCountResultJO = new JSONObject(batchCountResultStr);
				String data = batchCountResultJO.getString("data");
				int batchCount = Integer.valueOf(data);
				System.out.println("batchCount==="+batchCount);
				for(int j=0;j<batchCount;j++) {
					String batchIDVal = getItem("BLBatchID_"+j);
					if(batchID.equals(batchIDVal)){
						String createIDVal = getItem("BLCreateID_"+j);

						StringBuilder commandABORTSB=new StringBuilder();
						commandABORTSB.append("[COMMAND(Item,");
						commandABORTSB.append(Constant.USERID);
						commandABORTSB.append(",");
						commandABORTSB.append(createIDVal);
						commandABORTSB.append(",");
						commandABORTSB.append(BatchTest.ABORT);
						commandABORTSB.append(")]");
						execute(commandABORTSB.toString());

						StringBuilder removeSB=new StringBuilder();
						removeSB.append("[REMOVE(Item,");
						removeSB.append(Constant.USERID);
						removeSB.append(",");
						removeSB.append(createIDVal);
						removeSB.append(")]");
						execute(removeSB.toString());
						break;
					}
					
				}
				*/
			}
			
			if(statusBool) {
				workOrders=workOrders.substring(1);
				//Integer workOrderCount=workOrderService.getCountByByWOIDs(workOrders);
				
				int updateCount=workOrderService.updateStateByWOIDs(WorkOrder.BQX, workOrders);
				if(updateCount>0) {
					jsonMap.put("success", "true");
					jsonMap.put("state", "001");
					jsonMap.put("msg", "正常");
				}
				else {
					jsonMap.put("success", "false");
					jsonMap.put("state", "002");
					jsonMap.put("msg", "工单号不存在");
				}
			}
			else {
				jsonMap.put("success", "false");
				jsonMap.put("state", "003");
				jsonMap.put("msg", "状态有误");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}
	
	/**
	 * 4.6 投料信息下发
	 * @param bodyEnc
	 * @return
	 */
	@RequestMapping(value="/feedIssusDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> feedIssusDown(@RequestBody String bodyEnc) {

		/*
		 * 除了ManFeed表里加设定值，RecipePM表里也要加设定值。工单创建时，要从RecipePM表里根据工单id获取相关的配方参数，这些属于物料参数，放入ManFeed表里。
		 * 放入后在操作员扫码之前这个阶段只有物料名、FeedPort（投料口）、MarkBit（是否投料结束为0）、MaterialSV这些字段有数据，其他字段要在操作员扫码填入数后才能填充进去。
		           当操作员扫码时，填入数量、单位，根据系统时间作进料时间。mes端调用人工投料接口，把填入的数据根据工单id和FeedPort（投料口）两个字段，从人工投料表里查询出符合条件的数据，
		           把数量、单位那些之前为空的数据填充进去。填充完毕，markbit置1，wincc端就不再读取了，又将markbit置2，便于继续投料时与新的投料信息区分开。
		 * */

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		System.out.println("bodyEnc==="+bodyEnc);
		String bodyDec = bodyEnc;
		//String bodyDec = DesUtil.decrypt(bodyEnc,DesUtil.SECRET_KEY);
		//net.sf.json.JSONArray fibJA = net.sf.json.JSONArray.fromObject(bodyDec);
		/*
		FeedIssusBody fib=(FeedIssusBody)net.sf.json.JSONObject.toBean(fibJO, FeedIssusBody.class);
		int c=feedIssusBodyService.add(fib);
		*/

		//ManFeed mf=(ManFeed)net.sf.json.JSONObject.toBean(fibJO, ManFeed.class);
		List<ManFeed> mfList=convertMesFeedIssusDownToJava(bodyEnc);
		
		/*
		 * 为了测试暂时屏蔽掉
		int c=manFeedService.editByWorkOrderIDFeedPortList(mfList);
		*/
		int c=manFeedService.addTestFromList(mfList);
		if(c>0) {
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");
			jsonMap.put("msg", "正常");
		}
		else {
			jsonMap.put("success", "true");
			jsonMap.put("state", "002");
			jsonMap.put("msg", "数据格式有误");
		}
		return jsonMap;
	}
	
	/**
	 * 4.7 制膏编号下发
	 * @param bodyEnc
	 * @return
	 */
	@RequestMapping(value="/pasteWorkingNumDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pasteWorkingNumDown(@RequestBody String bodyEnc) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		System.out.println("bodyEnc==="+bodyEnc);
		/*
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
		*/

		int c=0;
		net.sf.json.JSONArray pwndJA = net.sf.json.JSONArray.fromObject(bodyEnc);
		for (int i = 0; i < pwndJA.size(); i++) {
			net.sf.json.JSONObject pwndJO = (net.sf.json.JSONObject)pwndJA.get(i);
			String workOrder = pwndJO.getString("workOrder");
			String creamCode=pwndJO.getString("creamCode");
			System.out.println("workOrder==="+workOrder);
			System.out.println("creamCode==="+creamCode);
			//这里的逻辑写的有点问题，制膏编号不是formulaId，需要在数据库表里单独加个制膏编号字段
			c+=workOrderService.updateZGIDByWorkOrder(creamCode, workOrder);
		}
		if(c>0) {
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");
			jsonMap.put("msg", "正常");
		}
		else {
			jsonMap.put("success", "true");
			jsonMap.put("state", "002");
			jsonMap.put("msg", "数据格式有误");
		}
		return jsonMap;
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

	@RequestMapping(value="/getSendToMesBRData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSendToMesBRData() {

		//batchrecord 批次过程记录  deviationrecord 偏差记录    processrecord 工艺参数记录
		//哦，1 3 8 9 89合成一次发，89 3 1 2这样顺序发。哎，之前写好的逻辑不是按这顺序的，看来我还得改改才行。刚才看群里那帮人的信息感觉焦头烂额的，马上改改逻辑
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<WorkOrder> sendToMesWOList=new ArrayList<>();
			List<String> sendToMesWOIDList=new ArrayList<>();
			List<WorkOrder> woList=workOrderService.getFinishedList();
			for (int i = 0; i < woList.size(); i++) {
				WorkOrder wo = woList.get(i);
				String workOrderID=wo.getWorkOrderID();
				Integer state = wo.getState();
				for (int j = 0; j < woPreStateList.size(); j++) {
					Map<String, Object> woPreStateMap = woPreStateList.get(j);
					String preWorkOrderID = woPreStateMap.get("workOrderID").toString();
					Integer preState = Integer.valueOf(woPreStateMap.get("state").toString());
					if(workOrderID.equals(preWorkOrderID)&&state==preState) {
						sendToMesWOIDList.add(workOrderID);
						sendToMesWOList.add(wo);
					}
				}
			}
			
			List<BatchRecord> brList=batchRecordService.getSendToMesData(sendToMesWOIDList);
			System.out.println("sendToMesWOIDListSize==="+sendToMesWOIDList.size());
			
			if(sendToMesWOIDList.size()>0) {
				for (int i = 0; i < sendToMesWOIDList.size(); i++) {
					WorkOrder sendToMesWO = sendToMesWOList.get(i);
					Integer id = sendToMesWO.getID();
					String workOrderID=sendToMesWO.getWorkOrderID();
					String productCode = sendToMesWO.getProductCode();
					String productName = sendToMesWO.getProductName();
					String recipeID = sendToMesWO.getRecipeID();
					
					JSONObject bodyParamBRJO=new JSONObject();
					bodyParamBRJO.put("id", id);
					bodyParamBRJO.put("workOrder", workOrderID);
					bodyParamBRJO.put("procuctCode", productCode);
					bodyParamBRJO.put("procuctName", productName);
					bodyParamBRJO.put("lotNo", "");
					bodyParamBRJO.put("formulaId", recipeID);
					bodyParamBRJO.put("formulaName", productName);
					bodyParamBRJO.put("workcenterId", "");
					bodyParamBRJO.put("recordType", "batchRecord");
					
					JSONObject bodyParamDevJO=new JSONObject();
					bodyParamDevJO.put("id", id);
					bodyParamDevJO.put("workOrder", workOrderID);
					bodyParamDevJO.put("procuctCode", productCode);
					bodyParamDevJO.put("procuctName", productName);
					bodyParamDevJO.put("lotNo", "");
					bodyParamDevJO.put("formulaId", recipeID);
					bodyParamDevJO.put("formulaName", productName);
					bodyParamDevJO.put("workcenterId", "");
					bodyParamDevJO.put("recordType", "deviationRecord");
					
					JSONObject bodyParamProJO=new JSONObject();
					bodyParamProJO.put("id", id);
					bodyParamProJO.put("workOrder", workOrderID);
					bodyParamProJO.put("procuctCode", productCode);
					bodyParamProJO.put("procuctName", productName);
					bodyParamProJO.put("lotNo", "");
					bodyParamProJO.put("formulaId", recipeID);
					bodyParamProJO.put("formulaName", productName);
					bodyParamProJO.put("workcenterId", "");
					bodyParamProJO.put("recordType", "processRecord");

					JSONArray electtonBatchRecordBRJA=new JSONArray();
					JSONArray electtonBatchRecordDevJA=new JSONArray();
					JSONArray electtonBatchRecordProJA=new JSONArray();
					for (int j = 0; j < brList.size(); j++) {
						BatchRecord sendToMesBR = brList.get(j);
						String sendToMesBRWorkOrderID = sendToMesBR.getWorkOrderID();
						if(workOrderID.equals(sendToMesBRWorkOrderID)) {
							String recordType = sendToMesBR.getRecordType();
							if("8".equals(recordType)||"9".equals(recordType)) {
								JSONObject electtonBatchRecordJO=new JSONObject();
								electtonBatchRecordJO.put("recordContent", sendToMesBR.getRecordContent());
								electtonBatchRecordJO.put("isOver", "是");
								electtonBatchRecordJO.put("isDeviation", "否");
								electtonBatchRecordJO.put("recordValue", sdf.format(new Date()));
								electtonBatchRecordJO.put("valueDecribe", sendToMesBR.getRecordEvent());//RecordEvent
								
								electtonBatchRecordBRJA.put(electtonBatchRecordJO);
							}
							else if("3".equals(recordType)) {
								JSONObject electtonBatchRecordJO=new JSONObject();
								electtonBatchRecordJO.put("recordContent", sendToMesBR.getRecordContent());
								electtonBatchRecordJO.put("isOver", "是");
								electtonBatchRecordJO.put("isDeviation", "是");
								electtonBatchRecordJO.put("recordValue", sdf.format(new Date()));
								electtonBatchRecordJO.put("valueDecribe", sendToMesBR.getRecordEvent());
								electtonBatchRecordJO.put("deviationTop", sendToMesBR.getHLimit());
								electtonBatchRecordJO.put("deviationBottom", sendToMesBR.getLLimit());
								electtonBatchRecordJO.put("deviationName", sendToMesBR.getDeviationType());
								electtonBatchRecordJO.put("actualNum", sendToMesBR.getRecordEvent());
								electtonBatchRecordJO.put("occurTime", sendToMesBR.getRecordStartTime());
								electtonBatchRecordJO.put("workOrder", sendToMesBRWorkOrderID);
								
								electtonBatchRecordDevJA.put(electtonBatchRecordJO);
							}
							if("1".equals(recordType)) {
								JSONObject electtonBatchRecordJO=new JSONObject();
								electtonBatchRecordJO.put("recordContent", sendToMesBR.getRecordContent());
								electtonBatchRecordJO.put("isOver", "是");
								electtonBatchRecordJO.put("isDeviation", "否");
								electtonBatchRecordJO.put("recordValue", sdf.format(new Date()));
								electtonBatchRecordJO.put("valueDecribe", sendToMesBR.getRecordEvent());
								
								electtonBatchRecordProJA.put(electtonBatchRecordJO);
							}
						}
					}
					
					bodyParamBRJO.put("electtonBatchRecord", electtonBatchRecordBRJA);
					APIUtil.doHttpMes("electronicBatchRecord",bodyParamBRJO);
					
					bodyParamDevJO.put("electtonBatchRecord", electtonBatchRecordDevJA);
					APIUtil.doHttpMes("electronicBatchRecord",bodyParamDevJO);
					
					bodyParamDevJO.put("electtonBatchRecord", electtonBatchRecordProJA);
					APIUtil.doHttpMes("electronicBatchRecord",bodyParamProJO);
				}
				//System.out.println("brListSize==="+brList.size());
				
				
			}

			/*
			JSONObject bodyParamJO=new JSONObject();
			bodyParamJO.put("id", "1634004927641407490");
			bodyParamJO.put("workOrder", workOrderID);
			bodyParamJO.put("procuctCode", "3010003");
			bodyParamJO.put("procuctName", "防菌抗敏牙膏膏体");
			bodyParamJO.put("lotNo", "LOTdjrev3");
			bodyParamJO.put("formulaId", "1628998712578641921");
			bodyParamJO.put("formulaName", "防菌抗敏牙膏膏体");
			bodyParamJO.put("workcenterId", "MX31");
			
			JSONArray electtonBatchRecordJA=new JSONArray();
			JSONObject electtonBatchRecordJO=new JSONObject();
			electtonBatchRecordJO.put("recordContent", "程序启动");
			electtonBatchRecordJO.put("isOver", "是");
			electtonBatchRecordJO.put("isDeviation", "否");
			electtonBatchRecordJO.put("recordValue", "2022-12-15 12:12:12");
			electtonBatchRecordJO.put("valueDecribe", "开机");
			electtonBatchRecordJA.put(electtonBatchRecordJO);
			bodyParamJO.put("electtonBatchRecord", electtonBatchRecordJA);
			
			APIUtil.doHttpMes("electronicBatchRecord",bodyParamJO);
			
			jsonMap.put("success", "true");
			jsonMap.put("state", "001");
			jsonMap.put("msg", "正常");
			net.sf.json.JSONArray brListJA = net.sf.json.JSONArray.fromObject(brList);
			jsonMap.put("data", brListJA.toString());
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jsonMap;
		}
	}
	
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/changeOrderStatus 工单状态变更
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/batchProcessParameters 工艺参数采集
	//http://1.1.4.14:19888/mesPlatform/api/remote/batch/devicationRecord 偏差数据采集
}
