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
	//getItem();
});

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
		<option value="68BatchID">68BatchID-?</option>
		<option value="68Failure">68Failure-?</option>
		<option value="68Info">68Info-?</option>
		<option value="68Message">68Message-?</option>
		<option value="68Parms">68Parms-?</option>
		<option value="68Pause">68Pause-?</option>
		<option value="68Pausing">68Pausing-?</option>
		<option value="68PhaseData">68PhaseData-?</option>
		<option value="68Requests">68Requests-?</option>
		<option value="68SS">68SS-?</option>
		<option value="68State">68State-?</option>
		<option value="12stepindex">12stepindex</option>
		<option value="68Unit">68Unit-?</option>
		<option value="PhasesList">PhasesList</option>
		<option value="PhasesList2">PhasesList2</option>
		<option value="PhasesList3">PhasesList3</option>
		<option value="17Data">17Data-?</option>
		<option value="17\tMCLS_SWEETCREAM_UP:1\tMCLS_SWEETCREAM_OP:1DATA">17\tMCLS_SWEETCREAM_UP:1\tMCLS_SWEETCREAM_OP:1DATA-?</option>
		<option value="17Status">17Status-?</option>
		<option value="17\tMCLS_SWEETCREAM_UP:1\tMCLS_SWEETCREAM_OP:1Status">17\t MCLS_SWEETCREAM_UP:1\tMCLS_SWEETCREAM_OP:1Status-?</option>
		<option value="5UnitRequirements">5UnitRequirements</option>
		<option value="ProcessCellBitMaps">ProcessCellBitMaps</option>
		<option value="54Units">54Units-?</option>
		<option value="ProcessCellsList">ProcessCellsList</option>
		<option value="ProcessCellsList2">ProcessCellsList2</option>
		<option value="RecipeDir">RecipeDir</option>
		<option value="RecipeList">RecipeList</option>
		<option value="68EqData">68EqData-?</option>
		<option value="68Hyperlinks">68Hyperlinks-?</option>
		<option value="68Owned">68Owned-?</option>
		<option value="119Owners">119Owners-?</option>
		<option value="68Requested">68Requested-?</option>
		<option value="119Requesters">119Requesters-?</option>
		<option value="68Strings">68Strings-?</option>
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
		<option value="55BatchID">55BatchID-?</option>
		<option value="55Operations">55Operations-?</option>
		<option value="55PhaseBitmaps">55PhaseBitmaps-?</option>
		<option value="55Phases">55Phases-?</option>
		<option value="55Phases2">55Phases2-?</option>
		<option value="55UnitTagData">55UnitTagData-?</option>
		<option value="13BindingPreferences">13BindingPreferences-?</option>
		<option value="13BindingRequirements">13BindingRequirements-?</option>
		<option value="UnitsList">UnitsList</option>
		<option value="VerifiedTagCount">VerifiedTagCount</option>
		<option value="Version">Version</option>
		<option value="WarningMessage">WarningMessage</option>
	</select>
	<input type="button" value="send" onclick="getItem()"/>
</div>
<div>
	<select id="command_sel">
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
</body>
</html>