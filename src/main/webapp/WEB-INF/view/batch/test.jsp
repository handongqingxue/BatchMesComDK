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
$(function(){
	//addDataToDB();
	//getFormulaCodeMaterialDosage();
	//keepWatchOnWorkOrder();
	initUwosStateSel();
});

function keepWatchOnWorkOrder(){
	$.post(path+"batch/keepWatchOnWorkOrder",
		function(data){
			
		}
	,"json");
}

function getFormulaCodeMaterialDosage(){
	$.post(path+"batch/getFormulaCodeMaterialDosage",
		function(data){
			if(data.message){
				var codeDosageList=data.codeDosageList;
				for(var i=0;i<codeDosageList.length;i++){
					var codeDosage=codeDosageList[i];
					console.log("code==="+codeDosage.code+",dosage==="+codeDosage.dosage);
				}
			}
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
		{id:id,workOrderID:workOrderID,productCode:productCode,productName:productName,totalOutput:totalOutput,mfgCode:mfgCode,
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

function addRecipePM_RMT(){
	var pMCode=$("#addRecipePM_RMT_div #pMCode").val();
	var pMName=$("#addRecipePM_RMT_div #pMName").val();
	var lot=$("#addRecipePM_RMT_div #lot").val();
	var dosage=$("#addRecipePM_RMT_div #dosage").val();
	var unit=$("#addRecipePM_RMT_div #unit").val();
	var hLimit=$("#addRecipePM_RMT_div #hLimit").val();
	var lLimit=$("#addRecipePM_RMT_div #lLimit").val();
	var pMType=$("#addRecipePM_RMT_div #pMType").val();
	var recipeID=$("#addRecipePM_RMT_div #recipeID").val();
	var cName=$("#addRecipePM_RMT_div #cName").val();

	$.post(path+"batch/addRecipePM_RMT",
		{pMCode:pMCode,pMName:pMName,lot:lot,dosage:dosage,unit:unit,hLimit:hLimit,lLimit:lLimit,pMType:pMType,recipeID:recipeID,cName:cName},
		function(data){
			if(data.message=="ok")
				alert(data.info);
			else{
				alert(data.info);
			}
		}
	,"json");
}

function addRecipePMFromRMT(){
	var workOrderID=$("#arpmfrmt_div #workOrderID").val();
	var recipeID=$("#arpmfrmt_div #recipeID").val();
	
	$.post(path+"batch/addRecipePMFromRMT",
		{workOrderID:workOrderID,recipeID:recipeID},
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

function addDataToDB(){
	var tabName="FormulaDto";
	$.post(path+"batch/addDataToDB",
		{tabName:tabName,resultType:"list"},
		function(data){
			alert(data.info);
		}
	,"json");
}

function getItem(){
	var item=$("#item_sel").val();
	$.post(path+"batch/getItem",
		{item:item},
		function(result){
			console.log("result==="+JSON.stringify(result));
			var data=JSON.stringify(result.data);
			data=data.substring(1,data.length-1);
			splitData(item,data);
		}
	,"json");
}

function execute(){
	var command=$("#command_sel").val();
	//var command="[REMOVE(Item,batchsvr1\ADMINISTRATOR,7)]";
	$.post(path+"batch/execute",
		{command:command},
		function(result){
			console.log("result==="+JSON.stringify(result));
		}
	,"json");
}

function splitData(item,data){
	if(item=="Batchlist")
		splitBatchlist(data);
	else if(item=="BatchOverrides")
		splitBatchOverrides(data);
	else if(item=="5BatchStepDataList")
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
	for(var i=0;i<items.length-1;i++){
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
		console.log("ValidUList==="+itemArr[14]);
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
		<option value="BLFailure_3">BLFailure_3-?</option>
		<option value="BLMode_3">BLMode_3-?</option>
		<option value="BLRecipe_3">BLRecipe_3-?</option>
		<option value="BLStartTime_3">BLStartTime_3-?</option>
		<option value="BLState_3">BLState_3-?</option>
		<option value="BLType_3">BLType_3-?</option>
		<option value="COMClientCount">COMClientCount</option>
		<option value="CPRVersion">CPRVersion</option>
		<option value="5BatchStepDataList">5BatchStepDataList</option>
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
		<option value="5Parms">5Parms</option>
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
		<option value="5Data">5Data</option>
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
		<option value="[BATCH(Item,batchsvr1\ADMINISTRATOR,CLS_FRENCHVANILLA.BPC,BATCH_ID,100,FRENCHVANILLA PREMIUM -CLASSBASED,FREEZER,4,MIXER,2,PARMS,CREAM_AMOUNT,2001,EGG_AMOUNT,200,FLAVOR_AMOUNT,50,MILK_AMOUNT,1999,SUGAR_AMOUNT, 750)]">Batch</option>
		<option value="[REMOVE(Item,batchsvr1\ADMINISTRATOR,7)]">REMOVE</option>
		<option value="[FORMULATIONS_INFO(<Item>,batchsvr1\ADMINISTRATOR,PRODUCT_X.BPC)]">Formulations_Info</option>
		<option value="[INFO2(<Item>,batchsvr1\ADMINISTRATOR,PRODUCT_X.BPC)]">Info2</option>
		<option value="[MESSAGES(Item,batchsvr1\ADMINISTRATOR,5)]">Messages</option>
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

<div id="addRecipePM_RMT_div" style="margin-top: 10px;">
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
	<input type="button" value="发送" onclick="addRecipePM_RMT()"/>
</div>

<div id="arpmfrmt_div" style="margin-top: 10px;">
	从远程配方参数表添加配方参数:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID" value="20230209"/>
	</div>
	<div>
		RecipeID:<input type="text" size="50" id="recipeID"/>
	</div>
	<input type="button" value="发送" onclick="addRecipePMFromRMT()"/>
</div>

<div id="abrfrp_div" style="margin-top: 10px;">
	从配方参数表添加批记录数据:
	<div>
		WorkOrderID:<input type="text" size="50" id="workOrderID"/>
	</div>
	<input type="button" value="发送" onclick="addBatchRecordFromRecordPM()"/>
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

<div style="margin-top: 10px;">
	<input type="button" value="巡回监视" onclick="keepWatchOnWorkOrder()"/>
</div>
</body>
</html>