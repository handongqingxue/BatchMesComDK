package com.batchMesComDK.util;

import java.util.List;

import org.json.JSONObject;

import com.batchMesComDK.controller.BatchController;

public class SendRecThre implements Runnable {
	
	/**
	 * 偏差记录常量
	 */
	public static final int DEV=1;
	/**
	 * 批记录常量
	 */
	public static final int BR=2;
	private BatchController batchController;
	/**
	 * 存放偏差记录body参数的集合
	 */
	private List<JSONObject> bodyParamDevJOList;
	private JSONObject bodyParamBRJO;
	private int sendType;
	/**
	 * 重发休眠时间(单位:毫秒)
	 */
	private long sleepTime=60000;

	/**
	 * 偏差记录构造函数
	 * @param batchController
	 * @param bodyParamDevJOList
	 * @param sendType
	 */
	public SendRecThre(BatchController batchController, List<JSONObject> bodyParamDevJOList, int sendType) {
		// TODO Auto-generated constructor stub
		this.batchController=batchController;
		this.bodyParamDevJOList=bodyParamDevJOList;
		this.sendType=sendType;
	}

	/**
	 * 批记录构造函数
	 * @param batchController
	 * @param bodyParamBRJO
	 * @param sendType
	 */
	public SendRecThre(BatchController batchController,JSONObject bodyParamBRJO, int sendType) {
		// TODO Auto-generated constructor stub
		this.batchController=batchController;
		this.bodyParamBRJO=bodyParamBRJO;
		this.sendType=sendType;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch (sendType) {
		case DEV:
			//因为偏差记录是单条推送给mes,每一条对应一个线程太耗资源,就用一个线程,把所有记录放集合里分开推送
			for (JSONObject bodyParamDevJO : bodyParamDevJOList) {
				sendDevRecord(bodyParamDevJO,false);
			}
			break;
		case BR:
			sendBatchRecord(false);
			break;
		}
	}
	
	/**
	 * 推送偏差记录
	 * @param bodyParamDevJO
	 * @param reSend
	 */
	private void sendDevRecord(JSONObject bodyParamDevJO, boolean reSend) {
		try {
			String bodyParamDevJOStr = bodyParamDevJO.toString();
			//System.out.println("bodyParamDevJOStr==="+bodyParamDevJOStr);
			JSONObject dhmJO = APIUtil.doHttpMes("devicationRecord",bodyParamDevJO);
			boolean success = dhmJO.getBoolean("success");
			String msg = dhmJO.getString("msg");
			int state = dhmJO.getInt("state");
			
			batchController.addTestLog(batchController.createTestLogByParams("bodyParamDevJOStr",success+"",state+"",msg+bodyParamDevJOStr));
			
			if(!success&&!reSend) {
				Thread.sleep(sleepTime);
				sendDevRecord(bodyParamDevJO, true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 推送批记录
	 * @param reSend
	 */
	private void sendBatchRecord(boolean reSend) {
		try {
			String bodyParamBRJOStr = bodyParamBRJO.toString();
			//System.out.println("bodyParamBRJOStr==="+bodyParamBRJOStr);
			JSONObject dhmJO = APIUtil.doHttpMes("electronicBatchRecord",bodyParamBRJO);
			boolean success = dhmJO.getBoolean("success");
			String apiMsg = dhmJO.getString("msg");
			int state = dhmJO.getInt("state");
			
			JSONObject msgJO = new JSONObject();
			msgJO.put("apiMsg", apiMsg);
			msgJO.put("bodyMsg", bodyParamBRJOStr);
			
			batchController.addTestLog(batchController.createTestLogByParams("bodyParamBRJOStr",success+"",state+"",msgJO.toString()));
			
			if(!success&&!reSend) {//在发送失败时,需间隔一段时间重发
				Thread.sleep(sleepTime);
				sendBatchRecord(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
