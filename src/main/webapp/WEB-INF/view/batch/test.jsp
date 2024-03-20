<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<%=basePath %>resource/js/jquery-3.3.1.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
var path='<%=basePath %>';
var timer=null;
var prePhaseIDs;
$(function(){
	//keepWatchOnWorkOrder();
	initUwosStateSel();
	initUbtsStateSel();
	/*
	setInterval(function(){
		keepWatchOnWorkOrderTest();
	},"3000");
	*/
});

function keepWatchOnWorkOrderTest(){
	$.post(path+"batch/keepWatchOnWorkOrderTest",
		function(data){
			
		}
	,"json");
}

function addWorkOrder(){
	var workOrderID=$("#addWorkOrder_div #workOrderID").val();
	var productCode=$("#addWorkOrder_div #productCode").val();
	var productName=$("#addWorkOrder_div #productName").val();
	var totalOutput=$("#addWorkOrder_div #totalOutput").val();
	var mfgCode=$("#addWorkOrder_div #mfgCode").val();
	var mfgVersion=$("#addWorkOrder_div #mfgVersion").val();
	var recipeID=$("#addWorkOrder_div #recipeID").val();
	var formulaId=$("#addWorkOrder_div #formulaId").val();
	var state=$("#addWorkOrder_div #state").val();
	var createTime=$("#addWorkOrder_div #createTime").val();
	var unitID=$("#addWorkOrder_div #unitID").val();
	
	$.post(path+"batch/addWorkOrder",
		{workOrderID:workOrderID,productCode:productCode,productName:productName,totalOutput:totalOutput,mfgCode:mfgCode,
		mfgVersion:mfgVersion,recipeID:recipeID,formulaId:formulaId,state:state,createTime:createTime,unitID:unitID},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function deleteWorkOrder(){
	var ids=$("#deleteWorkOrder_div #ids").val();

	$.post(path+"batch/deleteWorkOrderByIds",
		{ids:ids},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function editWorkOrder(){
	var id=$("#editWorkOrder_div #id").val();
	var workOrderID=$("#editWorkOrder_div #workOrderID").val();
	var productCode=$("#editWorkOrder_div #productCode").val();
	var productName=$("#editWorkOrder_div #productName").val();
	var totalOutput=$("#editWorkOrder_div #totalOutput").val();
	var mfgCode=$("#editWorkOrder_div #mfgCode").val();
	var mfgVersion=$("#editWorkOrder_div #mfgVersion").val();
	var recipeID=$("#editWorkOrder_div #recipeID").val();
	var formulaId=$("#editWorkOrder_div #formulaId").val();
	var state=$("#editWorkOrder_div #state").val();
	var unitID=$("#editWorkOrder_div #unitID").val();
	
	$.post(path+"batch/editWorkOrder",
		{ID:id,workOrderID:workOrderID,productCode:productCode,productName:productName,totalOutput:totalOutput,mfgCode:mfgCode,
		mfgVersion:mfgVersion,recipeID:recipeID,formulaId:formulaId,state:state,unitID:unitID},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function getWorkOrder(){
	var id=$("#editWorkOrder_div #id").val();

	$.post(path+"batch/getWorkOrder",
		{id:id},
		function(data){
			if(data.message=="ok"){
				var workOrder=data.workOrder;
				var workOrderID=workOrder.workOrderID;
				var productCode=workOrder.productCode;
				var productName=workOrder.productName;
				var totalOutput=workOrder.totalOutput;
				var mfgCode=workOrder.mfgCode;
				var mfgVersion=workOrder.mfgVersion;
				var recipeID=workOrder.recipeID;
				var formulaId=workOrder.formulaId;
				var state=workOrder.state;
				var unitID=workOrder.unitID;
				
				$("#editWorkOrder_div #workOrderID").val(workOrderID);
				$("#editWorkOrder_div #productCode").val(productCode);
				$("#editWorkOrder_div #productName").val(productName);
				$("#editWorkOrder_div #totalOutput").val(totalOutput);
				$("#editWorkOrder_div #mfgCode").val(mfgCode);
				$("#editWorkOrder_div #mfgVersion").val(mfgVersion);
				$("#editWorkOrder_div #recipeID").val(recipeID);
				$("#editWorkOrder_div #formulaId").val(formulaId);
				$("#editWorkOrder_div #state").val(state);
				$("#editWorkOrder_div #unitID").val(unitID);
			}
			else{
				$("#editWorkOrder_div #workOrderID").val("");
				$("#editWorkOrder_div #productCode").val("");
				$("#editWorkOrder_div #productName").val("");
				$("#editWorkOrder_div #totalOutput").val("");
				$("#editWorkOrder_div #mfgCode").val("");
				$("#editWorkOrder_div #mfgVersion").val("");
				$("#editWorkOrder_div #recipeID").val("");
				$("#editWorkOrder_div #formulaId").val("");
				$("#editWorkOrder_div #state").val("");
				$("#editWorkOrder_div #unitID").val("");
				alert(data.info);
			}
		}
	,"json");
}

function addRecipePM_TMP(){
	var pMCode=$("#addRecipePM_TMP_div #pMCode").val();
	var pMName=$("#addRecipePM_TMP_div #pMName").val();
	var lot=$("#addRecipePM_TMP_div #lot").val();
	var dosage=$("#addRecipePM_TMP_div #dosage").val();
	var unit=$("#addRecipePM_TMP_div #unit").val();
	var hLimit=$("#addRecipePM_TMP_div #hLimit").val();
	var lLimit=$("#addRecipePM_TMP_div #lLimit").val();
	var pMType=$("#addRecipePM_TMP_div #pMType").val();
	var recipeID=$("#addRecipePM_TMP_div #recipeID").val();
	var cName=$("#addRecipePM_TMP_div #cName").val();
	var feedPort=$("#addRecipePM_TMP_div #feedPort").val();
	var materialSV=$("#addRecipePM_TMP_div #materialSV").val();
	var hH=$("#addRecipePM_TMP_div #hH").val();
	var lL=$("#addRecipePM_TMP_div #lL").val();

	$.post(path+"batch/addRecipePM_TMP",
		{pMCode:pMCode,pMName:pMName,lot:lot,dosage:dosage,unit:unit,hLimit:hLimit,lLimit:lLimit,pMType:pMType,recipeID:recipeID,cName:cName,
		feedPort:feedPort,materialSV:materialSV,hH:hH,lL:lL},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function deleteRecipePM_TMP(){
	var ids=$("#deleteRecipePM_TMP_div #ids").val();

	$.post(path+"batch/deleteRecipePM_TMPByIds",
		{ids:ids},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function editRecipePM_TMP(){
	var id=$("#editRecipePM_TMP_div #id").val();
	var pMCode=$("#editRecipePM_TMP_div #pMCode").val();
	var pMName=$("#editRecipePM_TMP_div #pMName").val();
	var lot=$("#editRecipePM_TMP_div #lot").val();
	var dosage=$("#editRecipePM_TMP_div #dosage").val();
	var unit=$("#editRecipePM_TMP_div #unit").val();
	var hLimit=$("#editRecipePM_TMP_div #hLimit").val();
	var lLimit=$("#editRecipePM_TMP_div #lLimit").val();
	var pMType=$("#editRecipePM_TMP_div #pMType").val();
	var recipeID=$("#editRecipePM_TMP_div #recipeID").val();
	var cName=$("#editRecipePM_TMP_div #cName").val();
	var feedPort=$("#editRecipePM_TMP_div #feedPort").val();
	var materialSV=$("#editRecipePM_TMP_div #materialSV").val();
	var hH=$("#editRecipePM_TMP_div #hH").val();
	var lL=$("#editRecipePM_TMP_div #lL").val();
	
	$.post(path+"batch/editRecipePM_TMP",
		{ID:id,pMCode:pMCode,pMName:pMName,lot:lot,dosage:dosage,unit:unit,hLimit:hLimit,lLimit:lLimit,
		pMType:pMType,recipeID:recipeID,cName:cName,feedPort:feedPort,materialSV:materialSV,hH:hH,lL:lL},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function getRecipePM_TMP(){
	var id=$("#editRecipePM_TMP_div #id").val();

	$.post(path+"batch/getRecipePM_TMP",
		{id:id},
		function(data){
			if(data.message=="ok"){
				var rPM_TMP=data.rPM_TMP;
				console.log(rPM_TMP)
				var pMCode=rPM_TMP.pmcode;
				var pMName=rPM_TMP.pmname;
				var lot=rPM_TMP.lot;
				var dosage=rPM_TMP.dosage;
				var unit=rPM_TMP.unit;
				var hLimit=rPM_TMP.hlimit;
				var lLimit=rPM_TMP.llimit;
				var pMType=rPM_TMP.pmType;
				var recipeID=rPM_TMP.recipeID;
				var cName=rPM_TMP.cname;
				
				$("#editRecipePM_TMP_div #pMCode").val(pMCode);
				$("#editRecipePM_TMP_div #pMName").val(pMName);
				$("#editRecipePM_TMP_div #lot").val(lot);
				$("#editRecipePM_TMP_div #dosage").val(dosage);
				$("#editRecipePM_TMP_div #unit").val(unit);
				$("#editRecipePM_TMP_div #hLimit").val(hLimit);
				$("#editRecipePM_TMP_div #lLimit").val(lLimit);
				$("#editRecipePM_TMP_div #pMType").val(pMType);
				$("#editRecipePM_TMP_div #recipeID").val(recipeID);
				$("#editRecipePM_TMP_div #cName").val(cName);
			}
			else{
				$("#editRecipePM_TMP_div #pMCode").val("");
				$("#editRecipePM_TMP_div #pMName").val("");
				$("#editRecipePM_TMP_div #lot").val("");
				$("#editRecipePM_TMP_div #dosage").val("");
				$("#editRecipePM_TMP_div #unit").val("");
				$("#editRecipePM_TMP_div #hLimit").val("");
				$("#editRecipePM_TMP_div #lLimit").val("");
				$("#editRecipePM_TMP_div #pMType").val("");
				$("#editRecipePM_TMP_div #recipeID").val("");
				$("#editRecipePM_TMP_div #cName").val("");
				alert(data.info);
			}
		}
	,"json");
}

function addRecipePMFromTMP(){
	var workOrderID=$("#arpmftmp_div #workOrderID").val();
	var productCode=$("#arpmftmp_div #productCode").val();
	var productName=$("#arpmftmp_div #productName").val();
	
	$.post(path+"batch/addRecipePMFromTMP",
		{workOrderID:workOrderID,productCode:productCode,productName:productName},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function deleteRecipePM(){
	var ids=$("#deleteRecipePM_div #ids").val();

	$.post(path+"batch/deleteRecipePMByIds",
		{ids:ids},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function addBatchRecordFromRecordPM(){
	var workOrderID=$("#abrfrp_div #workOrderID").val();
	
	$.post(path+"batch/addBatchRecordFromRecordPM",
		{workOrderID:workOrderID},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function deleteBatchRecord(){
	var ids=$("#deleteBatchRecord_div #ids").val();

	$.post(path+"batch/deleteBatchRecordByIds",
		{ids:ids},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function addManFeed(){
	var workOrderID=$("#addManFeed_div #workOrderID").val();
	var materialCode=$("#addManFeed_div #materialCode").val();
	var materialName=$("#addManFeed_div #materialName").val();
	var suttle=$("#addManFeed_div #suttle").val();
	var unit=$("#addManFeed_div #unit").val();
	var feedTime=$("#addManFeed_div #feedTime").val();
	var phaseID=$("#addManFeed_div #phaseID").val();
	var markBit=$("#addManFeed_div #markBit").val();
	var materialSV=$("#addManFeed_div #materialSV").val();
	
	$.post(path+"batch/addManFeed",
		{workOrderID:workOrderID,materialCode:materialCode,materialName:materialName,suttle:suttle,
		unit:unit,feedTime:feedTime,phaseID:phaseID,markBit:markBit,materialSV:materialSV},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function addManFeedFromRecipePM(){
	var workOrderID=$("#amffrp_div #workOrderID").val();
	
	$.post(path+"batch/addManFeedFromRecipePM",
		{workOrderID:workOrderID},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function deleteManFeed(){
	var ids=$("#deleteManFeed_div #ids").val();

	$.post(path+"batch/deleteManFeedByIds",
		{ids:ids},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function editManFeed(){
	var workOrderID=$("#editManFeed_div #workOrderID").val();
	var materialCode=$("#editManFeed_div #materialCode").val();
	var materialName=$("#editManFeed_div #materialName").val();
	var suttle=$("#editManFeed_div #suttle").val();
	var unit=$("#editManFeed_div #unit").val();
	var feedTime=$("#editManFeed_div #feedTime").val();
	var markBit=$("#editManFeed_div #markBit").val();
	var materialSV=$("#editManFeed_div #materialSV").val();
	
	$.post(path+"batch/editManFeed",
		{workOrderID:workOrderID,materialCode:materialCode,materialName:materialName,suttle:suttle,
		unit:unit,feedTime:feedTime,markBit:markBit,materialSV:materialSV},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function getManFeed(){
	var workOrderID=$("#editManFeed_div #workOrderID").val();
	var feedPort=$("#editManFeed_div #feedPort").val();
	$.post(path+"batch/getManFeed",
		{workOrderID:workOrderID,feedPort:feedPort},
		function(data){
			if(data.message=="ok"){
				var manFeed=data.manFeed;
				console.log(manFeed)
				var materialCode=manFeed.materialCode;
				var materialName=manFeed.materialName;
				var suttle=manFeed.suttle;
				var unit=manFeed.unit;
				var feedTime=manFeed.feedTime;
				var feedPort=manFeed.feedPort;
				var markBit=manFeed.markBit;
				var materialSV=manFeed.materialSV;
				
				$("#editManFeed_div #materialCode").val(materialCode);
				$("#editManFeed_div #materialName").val(materialName);
				$("#editManFeed_div #suttle").val(suttle);
				$("#editManFeed_div #unit").val(unit);
				$("#editManFeed_div #feedTime").val(feedTime);
				$("#editManFeed_div #markBit").val(markBit);
				$("#editManFeed_div #materialSV").val(materialSV);
			}
			else{
				$("#editManFeed_div #materialCode").val("");
				$("#editManFeed_div #materialName").val("");
				$("#editManFeed_div #suttle").val("");
				$("#editManFeed_div #unit").val("");
				$("#editManFeed_div #feedTime").val("");
				$("#editManFeed_div #markBit").val("");
				$("#editManFeed_div #materialSV").val("");
				alert(data.info);
			}
		}
	,"json");
}

function addBatchTest(){
	var batchID=$("#addBatchTest_div #batchID").val();
	var recipe=$("#addBatchTest_div #recipe").val();
	var description=$("#addBatchTest_div #description").val();
	var mode=$("#addBatchTest_div #mode").val();
	
	$.post(path+"batch/addBatchTest",
		{batchID:batchID,recipe:recipe,description:description,mode:mode},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function updateWorkOrderStateById(){
	var state=$("#uwos_div #state_sel").val();
	if(state=="")
		alert("请选择状态")
	else{
		var id=$("#uwos_div #id").val();
		$.post(path+"batch/updateWorkOrderStateById",
			{state:state,id:id},
			function(data){
				if(data.message=="ok")
					alert(data.info);
				else{
					alert(data.info);
				}
			}
		,"json");
	}
}

function updateBatchTestStateByCreateID(){
	var state=$("#ubts_div #state_sel").val();
	if(state=="")
		alert("请选择状态")
	else{
		var createID=$("#ubts_div #createID").val();
		$.post(path+"batch/updateBatchTestStateByCreateID",
			{state:state,createID:createID},
			function(data){
				if(data.message=="ok")
					alert(data.info);
				else{
					alert(data.info);
				}
			}
		,"json");
	}
}

function initUwosStateSel(){
	var wlqtwbState=parseInt('${requestScope.wlqtwbState}');
	var csqrwbState=parseInt('${requestScope.csqrwbState}');
	var bcjwbState=parseInt('${requestScope.bcjwbState}');
	var bqdState=parseInt('${requestScope.bqdState}');
	var byxState=parseInt('${requestScope.byxState}');
	var bqxState=parseInt('${requestScope.bqxState}');
	var bztState=parseInt('${requestScope.bztState}');
	var bywzzState=parseInt('${requestScope.bywzzState}');
	var bjsState=parseInt('${requestScope.bjsState}');

	var wlqtwbStateMc='${requestScope.wlqtwbStateMc}';
	var csqrwbStateMc='${requestScope.csqrwbStateMc}';
	var bcjwbStateMc='${requestScope.bcjwbStateMc}';
	var bqdStateMc='${requestScope.bqdStateMc}';
	var byxStateMc='${requestScope.byxStateMc}';
	var bqxStateMc='${requestScope.bqxStateMc}';
	var bztStateMc='${requestScope.bztStateMc}';
	var bywzzStateMc='${requestScope.bywzzStateMc}';
	var bjsStateMc='${requestScope.bjsStateMc}';
	
	var stateSel=$("#uwos_div #state_sel");
	stateSel.append("<option value=\"\">请选择</option>");
	stateSel.append("<option value=\""+wlqtwbState+"\">"+wlqtwbStateMc+"</option>");
	stateSel.append("<option value=\""+csqrwbState+"\">"+csqrwbStateMc+"</option>");
	stateSel.append("<option value=\""+bcjwbState+"\">"+bcjwbStateMc+"</option>");
	stateSel.append("<option value=\""+bqdState+"\">"+bqdStateMc+"</option>");
	stateSel.append("<option value=\""+byxState+"\">"+byxStateMc+"</option>");
	stateSel.append("<option value=\""+bqxState+"\">"+bqxStateMc+"</option>");
	stateSel.append("<option value=\""+bztState+"\">"+bztStateMc+"</option>");
	stateSel.append("<option value=\""+bywzzState+"\">"+bywzzStateMc+"</option>");
	stateSel.append("<option value=\""+bjsState+"\">"+bjsStateMc+"</option>");
}

function initUbtsStateSel(){
	var readyState='${requestScope.readyState}';
	var startState='${requestScope.startState}';
	var runningState='${requestScope.runningState}';
	var stopState='${requestScope.stopState}';
	var stoppedState='${requestScope.stoppedState}';
	var completeState='${requestScope.completeState}';

	var stateSel=$("#ubts_div #state_sel");
	stateSel.append("<option value=\"\">请选择</option>");
	stateSel.append("<option value=\""+readyState+"\">"+readyState+"</option>");
	stateSel.append("<option value=\""+startState+"\">"+startState+"</option>");
	stateSel.append("<option value=\""+runningState+"\">"+runningState+"</option>");
	stateSel.append("<option value=\""+stopState+"\">"+stopState+"</option>");
	stateSel.append("<option value=\""+stoppedState+"\">"+stoppedState+"</option>");
	stateSel.append("<option value=\""+completeState+"\">"+completeState+"</option>");
}

function getItem(){
	if(timer!=null){
		clearInterval(timer);
		timer=null;
	}
	var item=$("#item_sel").val();
	if(item=="CreateIDBatchStepDataList"){
		if(!checkCreateID())
			return false;
		var createID=$("#inpFor_div #createID").val();
		item=item.replace("CreateID",createID);
		getItemByVal(item);
	}
	else if(item=="PhaseDataList"){
		timer=setInterval(function(){
			getItemByVal(item);
		},"3000");
	}
	else if(item=="PhaseIDParms"){
		if(!checkPhaseID())
			return false;
		var phaseID=$("#inpFor_div #phaseID").val();
		item=item.replace("PhaseID",phaseID);
		getItemByVal(item);
	}
	else if(item=="ProcedureIDData"){
		if(!checkProcedureID())
			return false;
		var procedureID=$("#inpFor_div #procedureID").val();
		item=item.replace("ProcedureID",procedureID.replaceAll("\\t","	"));
		alert(item)
		getItemByVal(item);
	}
	else{
		alert(item)
		getItemByVal(item);
	}
}

function getItemByVal(val){
	$.post(path+"batch/getItem",
		{item:val},
		function(result){
			console.log("result==="+JSON.stringify(result));
			var data=JSON.stringify(result.data);
			data=data.substring(1,data.length-1);
			splitData(val,data);
		}
	,"json");
}

function execute(){
	var command=$("#command_sel").val();
	//var command="[REMOVE(Item,batchsvr1\ADMINISTRATOR,7)]";
	if(command.indexOf("[COMMAND(")!=-1){
		if(!checkProcedureID())
			return false;
		if(!checkCmd())
			return false;
		var procedureID=$("#inpFor_div #procedureID").val();
		command=command.replace("ProcedureID",procedureID);
		var cmd=$("#inpFor_div #cmd_sel").val();
		command=command.replace("Cmd",cmd);
	}
	else if(command.indexOf("[REMOVE(")!=-1){
		if(!checkCreateID())
			return false;
		var createID=$("#inpFor_div #createID").val();
		command=command.replace("CreateID",createID);
	}
	else if(command.indexOf("[MESSAGES(")!=-1){
		if(!checkPhaseID())
			return false;
		var phaseID=$("#inpFor_div #phaseID").val();
		command=command.replace("PhaseID",phaseID);
	}
	
	executeByCmd(command);
}

function executeByCmd(command){
	$.post(path+"batch/execute",
		{command:command},
		function(result){
			console.log("result==="+JSON.stringify(result));
		}
	,"json");
}

function checkCreateID(){
	var createID=$("#inpFor_div #createID").val();
	if(createID==""||createID==null){
		alert("请输入CreateID");
		return false;
	}
	else
		return true;
}

//47\tCLS_SWEETCREAM_OP:1
function checkProcedureID(){
	var procedureID=$("#inpFor_div #procedureID").val();
	if(procedureID==""||procedureID==null){
		alert("请输入ProcedureID");
		return false;
	}
	else
		return true;
}

function checkPhaseID(){
	var phaseID=$("#inpFor_div #phaseID").val();
	if(phaseID==""||phaseID==null){
		alert("请输入PhaseID");
		return false;
	}
	else
		return true;
}

//CLEAR_FAILURES
function checkCmd(){
	var cmd=$("#inpFor_div #cmd_sel").val();
	if(cmd==""||cmd==null){
		alert("请选择Cmd");
		return false;
	}
	else
		return true;
}

function splitData(item,data){
	if(item=="Batchlist")
		splitBatchlist(data);
	else if(item=="BatchOverrides")
		splitBatchOverrides(data);
	else if(item=="47BatchStepDataList")
		splitBatchStepDataList(data);
	else if(item=="5EventData")
		splitEventData(data);
	else if(item=="DataServersList")
		splitDataServersList(data);
	else if(item=="DataServerStatistics")
		splitDataServerStatistics(data);
	else if(item=="YES_NOEnumSet")
		splitEnumSetEnumSet(data);
	else if(item=="ErrorMessage")
		splitErrorMessage(data);
	else if(item=="EventDataFiles")
		splitEventDataFiles(data);
	else if(item=="HyperlinkLabels")
		splitHyperlinkLabels(data);
	else if(item=="InfoMessage")
		splitInfoMessage(data);
	else if(item=="OperationDataList")
		splitOperationDataList(data);
	else if(item=="PhaseDataList")
		splitPhaseDataList(data);
	else if(item=="PhaseErrs")
		splitPhaseErrs(data);
	else if(item=="5PhaseData")
		splitPhaseData(data);
	else if(item=="1Units")
		splitUnits(data);
	else if(item=="5Hyperlinks")
		splitHyperlinks(data);
	else if(item=="5Strings")
		splitStrings(data);
	else if(item=="4PhaseBitmaps")
		splitPhaseBitmaps(data);
	else if(item=="4Phases")
		splitPhases(data);
	else if(item=="4Phases2")
		splitPhases2(data);
	else if(item=="4UnitTagData")
		splitUnitTagData(data);
}

function splitBatchlist(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	var prclStartLoc;
	var prclEndLoc;
	var unitStartLoc;
	var unitEndLoc;
	var phaseStartLoc;
	var phaseEndLoc;
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("BatchID==="+itemArr[0]);
		console.log("RecipeName==="+itemArr[1]);
		console.log("BatchDesc==="+itemArr[2]);
		console.log("StartTime==="+itemArr[3]);
		console.log("ElapsedTime==="+itemArr[4]);
		console.log("State==="+itemArr[5]);
		console.log("Mode==="+itemArr[6]);
		console.log("Failures==="+itemArr[7]);
		console.log("CreateID==="+itemArr[8]);
		console.log("CmdMask==="+itemArr[9]);
		console.log("BatchType==="+itemArr[10]);
		
		prclStartLoc=item.indexOf("$PRCL\\t");
		prclEndLoc=item.indexOf("\\t$END",0);
		var procCellList=item.substring(prclStartLoc+7,prclEndLoc);
		var pCellNames=procCellList.split("\\t");
		for(var j=0;j<pCellNames.length;j++){
			console.log("pCellName==="+pCellNames[j]);
		}

		unitStartLoc=item.indexOf("$UNIT\\t");
		unitEndLoc=item.indexOf("\\t$END",prclEndLoc+1);
		var unitList=item.substring(unitStartLoc+7,unitEndLoc);
		var unitNames=unitList.split("\\t");
		for(var j=0;j<unitNames.length;j++){
			console.log("unitName"+i+"==="+unitNames[j]);
		}
		
		phaseStartLoc=item.indexOf("$PHASE\\t");
		phaseEndLoc=item.indexOf(" $END",unitEndLoc+1);
		var phaseList=item.substring(phaseStartLoc+8,phaseEndLoc);
		console.log(phaseList=="")//NullList
	}
}

function splitBatchOverrides(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("CreateID==="+itemArr[0]);
		console.log("NumOverrides==="+itemArr[1]);
	}
}

function splitBatchStepDataList(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	var parmStartLoc;
	var parmEndLoc;
	var repParmStartLoc;
	var repParmEndLoc;
	var ownerIDStartLoc;
	var ownerIDEndLoc;
	var ownerNameStartLoc;
	var ownerNameEndLoc;
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("ID==="+itemArr[0]);
		console.log("Name==="+itemArr[1]);
		console.log("SP88Type==="+itemArr[2]);
		console.log("KeyPName==="+itemArr[3]);
		console.log("KeyValue==="+itemArr[4]);
		console.log("State==="+itemArr[5]);
		console.log("Mode==="+itemArr[6]);
		console.log("UnitName==="+itemArr[7]);
		console.log("Control==="+itemArr[8]);
		console.log("Index==="+itemArr[9]);
		console.log("Paused==="+itemArr[10]);
		console.log("Msg==="+itemArr[11]);
		console.log("Rqst==="+itemArr[12]);
		console.log("Fail==="+itemArr[13]);
		
		parmStartLoc=item.indexOf("$PARM\\t");
		parmEndLoc=item.indexOf("\\t$END",0);
		var parmList=item.substring(parmStartLoc+7,parmEndLoc);
		var parameters=parmList.split("\\t");
		for(var j=0;j<parameters.length;j++){
			console.log("parameter==="+parameters[j]);
		}

		repParmStartLoc=item.indexOf("$REPORT\\t");
		repParmEndLoc=item.indexOf("\\t$END",parmEndLoc+1);
		var repParmList=item.substring(repParmStartLoc+9,repParmEndLoc);
		parameters=repParmList.split("\\t");
		for(var j=0;j<repParmList.length;j++){
			console.log("parameters"+i+"==="+parameters[j]);
		}
		
		ownerIDStartLoc=item.indexOf("\\t",repParmEndLoc+1);
		ownerIDEndLoc=item.indexOf("\\t",ownerIDStartLoc+1);
		//alert(ownerIDStartLoc+","+ownerIDEndLoc)
		var ownerID=item.substring(ownerIDStartLoc+2,ownerIDEndLoc);
		console.log("ownerID==="+ownerID);
		
		/*
		ownerNameStartLoc=item.indexOf("\\t",ownerIDEndLoc+1);
		ownerNameEndLoc=item.indexOf("\\t",ownerNameStartLoc+1);
		alert(ownerNameStartLoc+","+ownerNameEndLoc)
		var ownerName=item.substring(ownerNameStartLoc+2,ownerNameEndLoc);
		console.log("ownerName==="+ownerName);
		*/
	}
}

function splitEventData(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("Time==="+itemArr[0]);
		console.log("BatchID==="+itemArr[1]);
		console.log("RecipePath==="+itemArr[2]);
		console.log("Description==="+itemArr[3]);
		console.log("EventType==="+itemArr[4]);
		console.log("Value==="+itemArr[5]);
		console.log("EngUnits==="+itemArr[6]);
		console.log("Area==="+itemArr[7]);
		console.log("PCell==="+itemArr[8]);
		console.log("Unit==="+itemArr[9]);
		console.log("EQMName==="+itemArr[10]);
		console.log("PhaseType==="+itemArr[11]);
		console.log("UserID==="+itemArr[12]);
		console.log("CreateID==="+itemArr[13]);
	}
}

function splitDataServersList(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("DataServerName==="+itemArr[0]);
		console.log("WatchdogProtocol==="+itemArr[1]);
		console.log("Location==="+itemArr[2]);
		console.log("RemoteNodeName==="+itemArr[3]);
		console.log("Protocol==="+itemArr[4]);
		console.log("ServerIdentifier==="+itemArr[5]);
		console.log("ConfigString1==="+itemArr[6]);
		console.log("ConfigString2==="+itemArr[7]);
		console.log("ConfigString3==="+itemArr[8]);
		console.log("ConfigString4==="+itemArr[9]);
		console.log("LocaleID==="+itemArr[10]);
		console.log("BadValueString==="+itemArr[11]);
	}
}

function splitDataServerStatistics(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("ServerName==="+itemArr[0]);
		console.log("ConfigString1==="+itemArr[1]);
		console.log("ConfigString2==="+itemArr[2]);
		console.log("ConversationStatus==="+itemArr[3]);
		console.log("WatchDog==="+itemArr[4]);
	}
}

function splitEnumSetEnumSet(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("EnumName==="+itemArr[0]);
		console.log("OrdValue==="+itemArr[1]);
	}
}

function splitErrorMessage(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("Time==="+itemArr[0]);
		console.log("Description==="+itemArr[1]);
		console.log("Additional Data==="+itemArr[2]);
	}
}

function splitEventDataFiles(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("BatchID==="+itemArr[0]);
		console.log("CreateID==="+itemArr[1]);
		console.log("Description==="+itemArr[2]);
		console.log("FilePath==="+itemArr[3]);
		console.log("StartTime==="+itemArr[4]);
		console.log("RecipeName==="+itemArr[5]);
	}
}

function splitHyperlinkLabels(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		var item=items[i];
		var itemArr=item.split("\\t");
		var itemName;
		switch(i){
			case 0:
				itemName="PCellLabels.PCL";
				break;
			case 1:
				itemName="UnitLabels.UL";
				break;
			case 2:
				itemName="PhaseLabels.PL";
				break;
			case 3:
				itemName="ShResrcLabels.SRL";
				break;
		}
		for(var j=0;j<itemArr.length-1;j++){
			console.log(itemName+(j+1)+"==="+itemArr[j]);
		}
	}
}

function splitInfoMessage(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("Time==="+itemArr[0]);
		console.log("Description==="+itemArr[1]);
		console.log("Additional Data==="+itemArr[2]);
	}
}

function splitOperationDataList(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("OpSeqID==="+itemArr[0]);
		console.log("OpSeqName==="+itemArr[1]);
		console.log("OpSeqState==="+itemArr[2]);
		console.log("Pausing==="+itemArr[3]);
		console.log("Mode==="+itemArr[4]);
		console.log("ArbMask==="+itemArr[5]);
		console.log("UnitName==="+itemArr[6]);
		console.log("CmdMask==="+itemArr[7]);
		console.log("UnitID==="+itemArr[8]);
		console.log("Owner==="+itemArr[9]);
		console.log("BatchID==="+itemArr[10]);
		console.log("FailMsg==="+itemArr[11]);
		console.log("OperationMsg==="+itemArr[12]);
		console.log("StepIndex==="+itemArr[13]);
		console.log("ValidUList==="+itemArr[14]);
	}
}

function splitPhaseDataList(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	var currPhaseIDs="";
	for(var i=0;i<items.length-1;i++){
		//console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		var phaseID=itemArr[0];
		var phaseState=itemArr[2];
		pdlLogStr="PhaseID==="+phaseID+","+"PhaseName==="+itemArr[1]+","+"PhaseState==="+phaseState;
		pdlLogStr+="Pausing==="+itemArr[3]+","+"Mode==="+itemArr[4]+","+"ArbMask==="+itemArr[5];
		pdlLogStr+="CmdMask==="+itemArr[6]+","+"UnitID==="+itemArr[7]+","+"UnitName==="+itemArr[8];
		pdlLogStr+="Owner==="+itemArr[9]+","+"BatchID==="+itemArr[10]+","+"FailMsg==="+itemArr[11];
		pdlLogStr+="PhaseMsg==="+itemArr[12]+","+"StepIndex==="+itemArr[13]+","+"ValidUList==="+itemArr[14];
		//console.log(pdlLogStr);
		if("RUNNING"==phaseState){
			currPhaseIDs+=","+phaseID;
		}
	}
	if(currPhaseIDs!=""){
		currPhaseIDs=currPhaseIDs.substring(1);
		if(currPhaseIDs!=prePhaseIDs){
			console.log("currPhaseIDs="+currPhaseIDs);
			var currPhaseIDArr=currPhaseIDs.split(",");
			for(var i=0;i<currPhaseIDArr.length;i++){
				var currPhaseID=currPhaseIDArr[i];
				//getItemByVal(currPhaseID+"Parms");
				executeByCmd("[MESSAGES(Item,batchsvr1\ADMINISTRATOR,"+currPhaseID+")]");
			}
		}
		prePhaseIDs=currPhaseIDs;
	}
}

function splitPhaseErrs(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("PhaseID==="+itemArr[0]);
		console.log("PhaseName==="+itemArr[1]);
		console.log("UnitID==="+itemArr[2]);
		console.log("UnitName==="+itemArr[3]);
		console.log("PCellName==="+itemArr[4]);
		console.log("AreaName==="+itemArr[5]);
		console.log("Owner==="+itemArr[6]);
		console.log("BatchID==="+itemArr[7]);
		console.log("PhErrMsg==="+itemArr[8]);
		console.log("State==="+itemArr[9]);
	}
}

function splitPhaseData(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("PhaseID==="+itemArr[0]);
		console.log("PhaseName==="+itemArr[1]);
		console.log("PhaseState==="+itemArr[2]);
		console.log("Pausing==="+itemArr[3]);
		console.log("Mode==="+itemArr[4]);
		console.log("ArbMask==="+itemArr[5]);
		console.log("CmdMask==="+itemArr[6]);
		console.log("UnitID==="+itemArr[7]);
		console.log("UnitName==="+itemArr[8]);
		console.log("Owner==="+itemArr[9]);
		console.log("BatchID==="+itemArr[10]);
		console.log("FailMsg==="+itemArr[11]);
		console.log("PhaseMsg==="+itemArr[12]);
		console.log("StepIndex==="+itemArr[13]);
		var validUListArr=itemArr[14].split("\\t");
		for(var j=0;j<validUListArr.length;j++){
			console.log("UnitName==="+validUListArr[j]);
		}
	}
}

function splitUnits(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("UnitID==="+itemArr[0]);
		console.log("UnitName==="+itemArr[1]);
		console.log("UnitClass==="+itemArr[2]);
		console.log("MaxOwners==="+itemArr[3]);
		console.log("XCord==="+itemArr[4]);
		console.log("YCord==="+itemArr[5]);
		console.log("BitMapIndex==="+itemArr[6]);
		
		var connStartLoc=item.indexOf("$PARM\\t");
		var connEndLoc=item.indexOf("\\t$END",0);
		var connections=item.substring(connStartLoc+7,connEndLoc);
		var connArr=connections.split("\\t");
		for(var j=0;j<connArr.length;j++){
			console.log("UnitName==="+connArr[j]);
			console.log("UnitID==="+connArr[j]);
		}
	}
}

function splitHyperlinks(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("ResourceName==="+itemArr[0]);
		console.log("Hyperlink1==="+itemArr[1]);
		console.log("Hyperlink2==="+itemArr[2]);
		console.log("Hyperlink3==="+itemArr[3]);
		console.log("Hyperlink4==="+itemArr[4]);
		console.log("Hyperlink5==="+itemArr[5]);
	}
}

function splitStrings(data){
	var dataArr=data.split("\\t");
	console.log("ResourceName==="+dataArr[0]);
	console.log("GenericString1==="+dataArr[1]);
	console.log("GenericString2==="+dataArr[2]);
	console.log("GenericString3==="+dataArr[3]);
	console.log("GenericString4==="+dataArr[4]);
	console.log("GenericString5==="+dataArr[5]);
}

function splitPhaseBitmaps(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("BitMapIndex==="+itemArr[0]);
		console.log("FileName==="+itemArr[1]);
	}
}

function splitPhases(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("PhaseID==="+itemArr[0]);
		console.log("PhaseName==="+itemArr[1]);
		console.log("MaxOwners==="+itemArr[2]);
		console.log("XCord==="+itemArr[3]);
		console.log("Ycord==="+itemArr[4]);
		console.log("BitMapIndex==="+itemArr[5]);
	}
}

function splitPhases2(data){
	var items=data.split("\\r\\n");
	console.log("length==="+items.length);
	for(var i=0;i<items.length-1;i++){
		console.log("i==="+i);
		var item=items[i];
		var itemArr=item.split("\\t");
		console.log("PhaseID==="+itemArr[0]);
		console.log("PhaseName==="+itemArr[1]);
		console.log("MaxOwners==="+itemArr[2]);
		console.log("PhaseType==="+itemArr[3]);
		console.log("XCord==="+itemArr[4]);
		console.log("Ycord==="+itemArr[5]);
		console.log("BitMapIndex==="+itemArr[6]);
	}
}

function splitUnitTagData(data){
	var unitNameStartLoc=0;
	var unitNameEndLoc=data.indexOf("\\r\\n",0);
	var UnitName=data.substring(unitNameStartLoc,unitNameEndLoc);
	
	var unitClassNameStartLoc=unitNameEndLoc+4;
	var unitClassNameEndLoc=data.indexOf("\\r\\n",unitClassNameStartLoc);
	var UnitClassName=data.substring(unitClassNameStartLoc,unitClassNameEndLoc);
	var tagsStartLoc=unitClassNameEndLoc+4;
	var tagsEndLoc=data.indexOf("\\r\\n",tagsStartLoc);
	var Tags=data.substring(tagsStartLoc,tagsEndLoc);
	var TagArr=Tags.split("\\t");
	console.log("TagName==="+TagArr[0]);
	console.log("DataServerName==="+TagArr[1]);
	console.log("ConfigString1==="+TagArr[2]);
	console.log("ConfigString2==="+TagArr[3]);
	console.log("ConfigString3==="+TagArr[4]);
	console.log("ConfigString4==="+TagArr[5]);
	console.log("Protocol==="+TagArr[6]);
	console.log("ConfiguredDataType==="+TagArr[7]);
	console.log("OPCAdviseItemDataType==="+TagArr[8]);
	console.log("OPCReadItemDataType==="+TagArr[9]);
	console.log("OPCWriteItemDataType==="+TagArr[10]);
	console.log("AdviseStatus==="+TagArr[11]);
	console.log("LatestAdviseValue==="+TagArr[12]);
	console.log("LatestAdviseValueQuality==="+TagArr[13]);
	console.log("LatestAdviseValueTimestamp==="+TagArr[14]);
	console.log("ReadStatus==="+TagArr[15]);
	console.log("LatestReadValue==="+TagArr[16]);
	console.log("LatestReadValueQuality==="+TagArr[17]);
	console.log("LatestReadValueTimestamp==="+TagArr[18]);
	console.log("WriteStatus==="+TagArr[19]);
	console.log("LastValueWritten==="+TagArr[20]);
	console.log("LastWriteResult==="+TagArr[21]);
	console.log("LastWriteTimestamp==="+TagArr[22]);
}
</script>
</head>
<body>
<div id="inpFor_div">
	<div>
		CreateID:<input type="text" id="createID" size="50"/>
	</div>
	<div>
		ProcedureID:<input type="text" id="procedureID" size="50"/>
	</div>
	<div>
		PhaseID:<input type="text" id="phaseID" size="50"/>
	</div>
	<div>
		Cmd:
		<select id="cmd_sel">
			<option value="">请选择</option>
			<option value="START">START</option>
			<option value="STOP">STOP</option>
			<option value="AUTO-MODE">AUTO-MODE</option>
			<option value="PAUSE">PAUSE</option>
			<option value="HOLD">HOLD</option>
			<option value="RESTART">RESTART</option>
			<option value="MAN-MODE">MAN-MODE</option>
			<option value="RESUME">RESUME</option>
			<option value="ABORT">ABORT</option>
			<option value="RESET">RESET</option>
			<option value="DOWNLOAD">DOWNLOAD</option>
			<option value="CLEAR_FAILURES">CLEAR_FAILURES</option>
		</select>
	</div>
</div>
<div>
	<select id="item_sel">
		<option value="BadTagCount">BadTagCount</option>
		<option value="Batchlist">Batchlist</option>
		<option value="BatchListCt">BatchListCt</option>
		<option value="BatchOverrides">BatchOverrides</option>
		<option value="BLBatchID_1">BLBatchID_1-?</option>
		<option value="BatchOverrides">BLCMDMask_3</option>
		<option value="BLCreateID_1">BLCreateID_1-?</option>
		<option value="BLDESC_3">BLDESC_3-?</option>
		<option value="BLFailure_1">BLFailure_1-?</option>
		<option value="BLMode_3">BLMode_3-?</option>
		<option value="BLRecipe_3">BLRecipe_3-?</option>
		<option value="BLStartTime_3">BLStartTime_3-?</option>
		<option value="BLState_3">BLState_3-?</option>
		<option value="BLType_3">BLType_3-?</option>
		<option value="COMClientCount">COMClientCount</option>
		<option value="CPRVersion">CPRVersion</option>
		<option value="CreateIDBatchStepDataList">CreateIDBatchStepDataList</option>
		<option value="5EventData">5EventData</option>
		<option value="5EventDataFile">5EventDataFile</option>
		<option value="5scale">5scale</option>
		<option value="DataServersList">DataServersList</option>
		<option value="DataServerStatistics">DataServerStatistics</option>
		<option value="DDEClientCount">DDEClientCount</option>
		<option value="DecimalSeparator">DecimalSeparator</option>
		<option value="Domains">Domains</option>
		<option value="YES_NOEnumSet">YES_NOEnumSet</option>
		<option value="EquipmentModel">EquipmentModel</option>
		<option value="EquipmentModelDate">EquipmentModelDate</option>
		<option value="EquipmentModelUpdateVersion">EquipmentModelUpdateVersion</option>
		<option value="ErrorMessage">ErrorMessage</option>
		<option value="EventDataFiles">EventDataFiles</option>
		<option value="15_4_Values">15_4_Values-?</option>
		<option value="9ParamExprValues">9ParamExprValues-?</option>
		<option value="15ReportExprValues">15ReportExprValues-?</option>
		<option value="HyperlinkLabels">HyperlinkLabels</option>
		<option value="InfoMessage">InfoMessage</option>
		<option value="ItemCount">ItemCount</option>
		<option value="JournalDir">JournalDir</option>
		<option value="ListSeparator">ListSeparator</option>
		<option value="Locale">Locale</option>
		<option value="LogDir">LogDir</option>
		<option value="MatSvrStatus">MatSvrStatus</option>
		<option value="OPCClientCount">OPCClientCount</option>
		<option value="OperationDataList">OperationDataList</option>
		<option value="OperationErrs">OperationErrs</option>
		<option value="OperationErrsCt">OperationErrsCt</option>
		<option value="PhaseDataList">PhaseDataList</option>
		<option value="PhaseErrs">PhaseErrs</option>
		<option value="PhaseErrsCt">PhaseErrsCt</option>
		<option value="5BatchID">5BatchID</option>
		<option value="5Failure">5Failure</option>
		<option value="5Info">5Info</option>
		<option value="5Message">5Message</option>
		<option value="PhaseIDParms">PhaseIDParms</option>
		<option value="5Pause">5Pause</option>
		<option value="5Pausing">5Pausing</option>
		<option value="5PhaseData">5PhaseData</option>
		<option value="5Requests">5Requests</option>
		<option value="5SS">5SS</option>
		<option value="5State">5State</option>
		<option value="5stepindex">5stepindex</option>
		<option value="5Unit">5Unit</option>
		<option value="PhasesList">PhasesList</option>
		<option value="PhasesList2">PhasesList2</option>
		<option value="PhasesList3">PhasesList3</option>
		<option value="ProcedureIDData">ProcedureIDData</option>
		<option value="5Data2">5Data2</option>
		<option value="5Data3">5Data3</option>
		<option value="5Status">5Status</option>
		<option value="5UnitRequirements">5UnitRequirements</option>
		<option value="ProcessCellBitMaps">ProcessCellBitMaps</option>
		<option value="1Units">1Units</option>
		<option value="ProcessCellsList">ProcessCellsList</option>
		<option value="ProcessCellsList2">ProcessCellsList2</option>
		<option value="RecipeDir">RecipeDir</option>
		<option value="RecipeList">RecipeList</option>
		<option value="5EqData">5EqData</option>
		<option value="5Hyperlinks">5Hyperlinks</option>
		<option value="5Owned">5Owned</option>
		<option value="5Owners">5Owners</option>
		<option value="5Requested">5Requested</option>
		<option value="5Requesters">5Requesters</option>
		<option value="5Strings">5Strings</option>
		<option value="ResourcesList">ResourcesList</option>
		<option value="RunMode">RunMode</option>
		<option value="RunWithoutMatSvr">RunWithoutMatSvr</option>
		<option value="StartTime">StartTime</option>
		<option value="StepsList">StepsList</option>
		<option value="StringLabels">StringLabels</option>
		<option value="TagVerStatus">TagVerStatus</option>
		<option value="TagVerStatusOrd">TagVerStatusOrd</option>
		<option value="ThousandsSeparator">ThousandsSeparator</option>
		<option value="Time">Time</option>
		<option value="Time2">Time2</option>
		<option value="4TimerData">4TimerData-?</option>
		<option value="4Status">4Status-?</option>
		<option value="TimerSteps">TimerSteps</option>
		<option value=""></option>
		<option value=""></option>
		<option value=""></option>
		<option value=""></option>
		<option value="TotalTagCount">TotalTagCount</option>
		<option value="UEArea_2">UEArea_2-?</option>
		<option value="UEBatchID_1">UEBatchID_1-?</option>
		<option value="UEDefault_1">UEDefault_1-?</option>
		<option value="UEDesc_1">UEDesc_1-?</option>
		<option value="UEEU_2">UEEU_2-?</option>
		<option value="UEEvent_2">UEEvent_2-?</option>
		<option value="UEEventID_2">UEEventID_2-?</option>
		<option value="UEHigh_1">UEHigh_1-?</option>
		<option value="UELow_2">UELow_2-?</option>
		<option value="UEPhase_2">UEPhase_2-?</option>
		<option value="UEProcCell_2">UEProcCell_2-?</option>
		<option value="UERecipe_1">UERecipe_1-?</option>
		<option value="UERespType_1">UERespType_1-?</option>
		<option value="UETime_1">UETime_1-?</option>
		<option value="UEUnit_1">UEUnit_1-?</option>
		<option value="UEValue_1">UEValue_1-?</option>
		<option value="UnAcknowledgedEvents">UnAcknowledgedEvents</option>
		<option value="UnacknowledgedEventsCt">UnacknowledgedEventsCt</option>
		<option value="UnitBitMaps">UnitBitMaps</option>
		<option value="4BatchID">4BatchID</option>
		<option value="4Operations">4Operations</option>
		<option value="4PhaseBitmaps">4PhaseBitmaps</option>
		<option value="4Phases">4Phases</option>
		<option value="4Phases2">4Phases2</option>
		<option value="4UnitTagData">4UnitTagData</option>
		<option value="4BindingPreferences">4BindingPreferences</option>
		<option value="4BindingRequirements">4BindingRequirements</option>
		<option value="UnitsList">UnitsList</option>
		<option value="VerifiedTagCount">VerifiedTagCount</option>
		<option value="Version">Version</option>
		<option value="WarningMessage">WarningMessage</option>
	</select>
	<input type="button" value="send" onclick="getItem()"/>
</div>
<div>
	<select id="command_sel">
	<!-- 
		<option value="[BATCH(Item,batchsvr1\ADMINISTRATOR,CLS_FRENCHVANILLA.BPC,BATCH_ID,100,FRENCHVANILLA PREMIUM -CLASSBASED,FREEZER,4,MIXER,2,PARMS,CREAM_AMOUNT,2001,EGG_AMOUNT,200,FLAVOR_AMOUNT,50,MILK_AMOUNT,1999,SUGAR_AMOUNT, 750)]">Batch</option>
		 -->
		<option value="[BATCH(Item,batchsvr1\ADMINISTRATOR,MX4_CLASS.BPC,BATCH_ID,100,,UP_CLASS:1,103,PARMS)]">Batch</option>
		<option value="[COMMAND(Item,batchsvr1\ADMINISTRATOR,ProcedureID,Cmd)]">COMMAND</option>
		<option value="[REMOVE(Item,batchsvr1\ADMINISTRATOR,CreateID)]">REMOVE</option>
		<option value="[FORMULATIONS_DATA(<Item>,batchsvr1\ADMINISTRATOR,CLS_FRENCHVANILLA.BPC,Premium)]">Formulations_Data</option>
		<option value="[FORMULATIONS_INFO(<Item>,batchsvr1\ADMINISTRATOR,CLS_FRENCHVANILLA.BPC)]">Formulations_Info</option>
		<option value="[INFO2(<Item>,batchsvr1\ADMINISTRATOR,PRODUCT_X.BPC)]">Info2</option>
		<option value="[MESSAGES(Item,batchsvr1\ADMINISTRATOR,PhaseID)]">Messages</option>
		<option value="[MTRL_INFO(Item,batchsvr1\ADMINISTRATOR,PRODUCT_X.BPC)]">Mtrl_Info</option>
		<option value="[PARMS(Item,batchsvr1\ADMINISTRATOR,5)]">Parms</option>
		<option value="[PARMS2(Item,batchsvr1\ADMINISTRATOR,5)]">Parms2</option>
		<option value="[RECIPELIST(Item,batchsvr1\ADMINISTRATOR)]">RecipeList</option>
	</select>
	<input type="button" value="send" onclick="execute()"/>
</div>

<div id="addWorkOrder_div" style="margin-top: 10px;">
	添加工单数据:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID" value="20230208"/>
	</div>
	<div>
		ProductCode:<input type="text" size="50" id="productCode"/>
	</div>
	<div>
		ProductName:<input type="text" size="50" id="productName"/>
	</div>
	<div>
		TotalOutput:<input type="text" size="50" id="totalOutput"/>
	</div>
	<div>
		MfgCode:<input type="text" size="50" id="mfgCode"/>
	</div>
	<div>
		MfgVersion:<input type="text" size="50" id="mfgVersion"/>
	</div>
	<div>
		RecipeID:<input type="text" size="50" id="recipeID" value="CLS_FRENCHVANILLA.BPC"/>
	</div>
	<div>
		FormulaId:<input type="text" size="50" id="formulaId"/>
	</div>
	<div>
		State:<input type="text" size="50" id="state" value="1"/>
	</div>
	<div>
		CreateTime:<input type="text" size="50" id="createTime" value="2023-02-08"/>
	</div>
	<div>
		UnitID:<input type="text" size="50" id="unitID"/>
	</div>
	<input type="button" value="发送" onclick="addWorkOrder()"/>
</div>

<div id="editWorkOrder_div" style="margin-top: 10px;">
	修改工单数据:
	<div>
		ID:<input type="text" size="50" id="id"/>
		<input type="button" value="查询" onclick="getWorkOrder()"/>
	</div>
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
	</div>
	<div>
		ProductCode:<input type="text" size="50" id="productCode"/>
	</div>
	<div>
		ProductName:<input type="text" size="50" id="productName"/>
	</div>
	<div>
		TotalOutput:<input type="text" size="50" id="totalOutput"/>
	</div>
	<div>
		MfgCode:<input type="text" size="50" id="mfgCode"/>
	</div>
	<div>
		MfgVersion:<input type="text" size="50" id="mfgVersion"/>
	</div>
	<div>
		RecipeID:<input type="text" size="50" id="recipeID"/>
	</div>
	<div>
		FormulaId:<input type="text" size="50" id="formulaId"/>
	</div>
	<div>
		State:<input type="text" size="50" id="state" value="1"/>
	</div>
	<div>
		CreateTime:<input type="text" size="50" id="createTime"/>
	</div>
	<div>
		UnitID:<input type="text" size="50" id="unitID"/>
	</div>
	<input type="button" value="提交" onclick="editWorkOrder()"/>
</div>

<div id="deleteWorkOrder_div" style="margin-top: 10px;">
	删除工单数据:
	<div>
		IDs:<input type="text" size="50" id="ids"/>
		<input type="button" value="删除" onclick="deleteWorkOrder()"/>
	</div>
</div>

<div id="addRecipePM_TMP_div" style="margin-top: 10px;">
	添加远程配方参数数据:
	<div>
		PMCode:<input type="text" size="50" id="pMCode"/>
	</div>
	<div>
		PMName:<input type="text" size="50" id="pMName"/>
	</div>
	<div>
		Lot:<input type="text" size="50" id="lot"/>
	</div>
	<div>
		Dosage:<input type="text" size="50" id="dosage"/>
	</div>
	<div>
		Unit:<input type="text" size="50" id="unit"/>
	</div>
	<div>
		HLimit:<input type="text" size="50" id="hLimit"/>
	</div>
	<div>
		LLimit:<input type="text" size="50" id="lLimit"/>
	</div>
	<div>
		PMType:<input type="text" size="50" id="pMType"/>
	</div>
	<div>
		RecipeID:<input type="text" size="50" id="recipeID"/>
	</div>
	<div>
		CName:<input type="text" size="50" id="cName"/>
	</div>
	<div>
		FeedPort:<input type="text" size="50" id="feedPort"/>
	</div>
	<div>
		MaterialSV:<input type="text" size="50" id="materialSV"/>
	</div>
	<div>
		HH:<input type="text" size="50" id="hH"/>
	</div>
	<div>
		LL:<input type="text" size="50" id="lL"/>
	</div>
	<input type="button" value="发送" onclick="addRecipePM_TMP()"/>
</div>

<div id="editRecipePM_TMP_div" style="margin-top: 10px;">
	修改远程配方参数数据:
	<div>
		ID:<input type="text" size="50" id="id"/>
		<input type="button" value="查询" onclick="getRecipePM_TMP()"/>
	</div>
	<div>
		PMCode:<input type="text" size="50" id="pMCode"/>
	</div>
	<div>
		PMName:<input type="text" size="50" id="pMName"/>
	</div>
	<div>
		Lot:<input type="text" size="50" id="lot"/>
	</div>
	<div>
		Dosage:<input type="text" size="50" id="dosage"/>
	</div>
	<div>
		Unit:<input type="text" size="50" id="unit"/>
	</div>
	<div>
		HLimit:<input type="text" size="50" id="hLimit"/>
	</div>
	<div>
		LLimit:<input type="text" size="50" id="lLimit"/>
	</div>
	<div>
		PMType:<input type="text" size="50" id="pMType"/>
	</div>
	<div>
		RecipeID:<input type="text" size="50" id="recipeID"/>
	</div>
	<div>
		CName:<input type="text" size="50" id="cName"/>
	</div>
	<div>
		FeedPort:<input type="text" size="50" id="feedPort"/>
	</div>
	<div>
		MaterialSV:<input type="text" size="50" id="materialSV"/>
	</div>
	<div>
		HH:<input type="text" size="50" id="hH"/>
	</div>
	<div>
		LL:<input type="text" size="50" id="lL"/>
	</div>
	<input type="button" value="提交" onclick="editRecipePM_TMP()"/>
</div>

<div id="deleteRecipePM_TMP_div" style="margin-top: 10px;">
	删除远程配方参数数据:
	<div>
		IDs:<input type="text" size="50" id="ids"/>
		<input type="button" value="删除" onclick="deleteRecipePM_TMP()"/>
	</div>
</div>

<div id="arpmftmp_div" style="margin-top: 10px;">
	从远程配方参数表添加配方参数:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID" value="20230209"/>
	</div>
	<div>
		ProductCode:<input type="text" size="50" id="productCode"/>
	</div>
	<div>
		ProductName:<input type="text" size="50" id="productName"/>
	</div>
	<input type="button" value="发送" onclick="addRecipePMFromTMP()"/>
</div>

<div id="deleteRecipePM_div" style="margin-top: 10px;">
	删除配方参数数据:
	<div>
		IDs:<input type="text" size="50" id="ids"/>
		<input type="button" value="删除" onclick="deleteRecipePM()"/>
	</div>
</div>

<div id="abrfrp_div" style="margin-top: 10px;">
	从配方参数表添加批记录数据:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
	</div>
	<input type="button" value="发送" onclick="addBatchRecordFromRecordPM()"/>
</div>

<div id="deleteBatchRecord_div" style="margin-top: 10px;">
	删除批记录数据:
	<div>
		IDs:<input type="text" size="50" id="ids"/>
		<input type="button" value="删除" onclick="deleteBatchRecord()"/>
	</div>
</div>

<div id="addManFeed_div" style="margin-top: 10px;">
	添加人工投料信息:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
	</div>
	<div>
		MaterialCode:<input type="text" size="50" id="materialCode"/>
	</div>
	<div>
		MaterialName:<input type="text" size="50" id="materialName"/>
	</div>
	<div>
		Suttle:<input type="text" size="50" id="suttle"/>
	</div>
	<div>
		Unit:<input type="text" size="50" id="unit"/>
	</div>
	<div>
		FeedTime:<input type="text" size="50" id="feedTime"/>
	</div>
	<div>
		PhaseID:<input type="text" size="50" id="phaseID"/>
	</div>
	<div>
		MarkBit:<input type="text" size="50" id="markBit"/>
	</div>
	<div>
		MaterialSV:<input type="text" size="50" id="materialSV"/>
	</div>
	<input type="button" value="发送" onclick="addManFeed()"/>
</div>

<div id="amffrp_div" style="margin-top: 10px;">
	从配方参数表添加人工投料数据:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
	</div>
	<input type="button" value="发送" onclick="addManFeedFromRecipePM()"/>
</div>

<div id="editManFeed_div" style="margin-top: 10px;">
	修改人工投料信息:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
		FeedPort:<input type="text" size="50" id="feedPort"/>
		<input type="button" value="查询" onclick="getManFeed()"/>
	</div>
	<div>
		MaterialCode:<input type="text" size="50" id="materialCode"/>
	</div>
	<div>
		MaterialName:<input type="text" size="50" id="materialName"/>
	</div>
	<div>
		Suttle:<input type="text" size="50" id="suttle"/>
	</div>
	<div>
		Unit:<input type="text" size="50" id="unit"/>
	</div>
	<div>
		FeedTime:<input type="text" size="50" id="feedTime"/>
	</div>
	<div>
		MarkBit:<input type="text" size="50" id="markBit"/>
	</div>
	<div>
		MaterialSV:<input type="text" size="50" id="materialSV"/>
	</div>
	<input type="button" value="提交" onclick="editManFeed()"/>
</div>

<div id="deleteManFeed_div" style="margin-top: 10px;">
	删除人工投料信息:
	<div>
		IDs:<input type="text" size="50" id="ids"/>
		<input type="button" value="删除" onclick="deleteManFeed()"/>
	</div>
</div>

<div id="addBatchTest_div" style="margin-top: 10px;">
	添加batch模拟数据:
	<div>
		BatchID:<input type="text" size="50" id="batchID"/>
	</div>
	<div>
		Recipe:<input type="text" size="50" id="recipe"/>
	</div>
	<div>
		Description:<input type="text" size="50" id="description"/>
	</div>
	<div>
		Mode:<input type="text" size="50" id="mode"/>
	</div>
	<input type="button" value="发送" onclick="addBatchTest()"/>
</div>

<div id="uwos_div" style="margin-top: 10px;">
	更新工单状态:
	<div>
		状态:
		<select id="state_sel"></select>
	</div>
	<div>
		ID:<input type="text" size="50" id="id"/>
	</div>
	<input type="button" value="发送" onclick="updateWorkOrderStateById()"/>
</div>

<div id="ubts_div" style="margin-top: 10px;">
	更新BatchTest状态:
	<div>
		状态:
		<select id="state_sel"></select>
	</div>
	<div>
		CreateID:<input type="text" size="50" id="createID"/>
	</div>
	<input type="button" value="发送" onclick="updateBatchTestStateByCreateID()"/>
</div>

<div style="margin-top: 10px;">
	<input type="button" value="巡回监视" onclick="keepWatchOnWorkOrder()"/>
</div>
</body>
</html>