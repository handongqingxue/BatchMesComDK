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
		}
	,"json");
}
</script>
</head>
<body>
<select id="item_sel">
	<option value="BadTagCount">BadTagCount</option>
	<option value="Batchlist">Batchlist</option>
	<option value="BatchListCt">BatchListCt</option>
	<option value="BatchOverrides">BatchOverrides</option>
	<option value="BLBatchID_3">BLBatchID_3-?</option>
	<option value="BatchOverrides">BLCMDMask_3</option>
	<option value="BLCreateID_3">BLCreateID_3-?</option>
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
	<option value="17UnitRequirements">17UnitRequirements-?</option>
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
</body>
</html>