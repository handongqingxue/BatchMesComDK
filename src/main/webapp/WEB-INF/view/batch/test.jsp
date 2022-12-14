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
});

function createBatch(){
	var parms=$("#batch_parms").val();
	$.post("http://10.0.3.150:8080/BatchMesComDK/batch/create",
		{parms:parms},
		function(data){
			alert(data);
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
<div>
mes????????????java????????????:
<input type="text" size="50" id="batch_parms"/>
	<input type="button" value="send" onclick="createBatch()"/>
</div>
</body>
</html>