package com.batchMesComDK.util;

import javax.servlet.http.HttpServletRequest;

import com.batchMesComDK.entity.*;

public class Constant {

	public static final String USERID="batchsvr1/ADMINISTRATOR";
	public static final String ITEM_BATCH_LIST_CT="BatchListCt";
	public static final String END_SUCCESS="/nSuccess";
	public static final String RESOURCES_DIR="D:/resource/BatchMesComDK";

	public static void setWorkOrderStateInRequest(HttpServletRequest request) {
		
		request.setAttribute("wlqtwbState", WorkOrder.WLQTWB);//物料齐套完毕
		request.setAttribute("csqrwbState", WorkOrder.CSQRWB);//参数确认完毕（具备创建BATCH条件）
		request.setAttribute("bcjwbState", WorkOrder.BCJWB);//BATCH创建完毕（待执行）
		request.setAttribute("bqdState", WorkOrder.BQD);//BATCH启动
		request.setAttribute("byxState", WorkOrder.BYX);//BATCH运行
		request.setAttribute("bqxState", WorkOrder.BQX);//BATCH取消
		request.setAttribute("bztState", WorkOrder.BZT);//BATCH暂停
		request.setAttribute("bywzzState", WorkOrder.BYWZZ);//BATCH意外终止
		request.setAttribute("bjsState", WorkOrder.BJS);//BATCH结束
		
		request.setAttribute("wlqtwbStateMc", WorkOrder.WLQTWB_TEXT);//物料齐套完毕
		request.setAttribute("csqrwbStateMc", WorkOrder.CSQRWB_TEXT);//参数确认完毕（具备创建BATCH条件）
		request.setAttribute("bcjwbStateMc", WorkOrder.BCJWB_TEXT);//BATCH创建完毕（待执行）
		request.setAttribute("bqdStateMc", WorkOrder.BQD_TEXT);//BATCH启动
		request.setAttribute("byxStateMc", WorkOrder.BYX_TEXT);//BATCH运行
		request.setAttribute("bqxStateMc", WorkOrder.BQX_TEXT);//BATCH取消
		request.setAttribute("bztStateMc", WorkOrder.BZT_TEXT);//BATCH暂停
		request.setAttribute("bywzzStateMc", WorkOrder.BYWZZ_TEXT);//BATCH意外终止
		request.setAttribute("bjsStateMc", WorkOrder.BJS_TEXT);//BATCH结束
		
	}

	public static void setBatchTestStateInRequest(HttpServletRequest request) {
		
		request.setAttribute("readyState", BatchTest.READY);
		request.setAttribute("startState", BatchTest.START);
		request.setAttribute("runningState", BatchTest.RUNNING);
		request.setAttribute("stopState", BatchTest.STOP);
		request.setAttribute("stoppedState", BatchTest.STOPPED);
		request.setAttribute("completeState", BatchTest.COMPLETE);
	}
}
