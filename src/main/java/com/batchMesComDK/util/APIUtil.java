package com.batchMesComDK.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.batchMesComDK.entity.*;

//import net.sf.json.JSONArray;

public class APIUtil {

	public static final String SERVICE_URL="http://localhost:8080/ZnczLfyl/gkj/";
	//public static final String SERVICE_URL_MES="https://385n683i90.imdo.co/mesPlatform/api/remote/batch/";
	
	public static final String SERVICE_URL_MES="http://10.0.3.135:80/mesPlatform/api/remote/batch/";
	//public static final String SERVICE_URL_MES="http://10.0.3.136:19888/mesPlatform/api/remote/batch/";
	//public static final String SERVICE_URL_MES="http://172.25.10.13:19888/mesPlatform/api/remote/batch/";
	
	public static final String ITEM_RESULT="item";
	public static final String LIST_RESULT="list";
	
	public static final String SUCCESS="success";
	public static final boolean SUCCESS_TRUE=true;
	public static final boolean SUCCESS_FALSE=false;
	
	public static final String STATE="state";
	/**
	 * 001正常状态(001正常 002数据格式有误 003数据不完整)
	 */
	public static final String STATE_001="001";
	/**
	 * 002数据格式有误状态(001正常 002数据格式有误 003数据不完整)
	 */
	public static final String STATE_002="002";
	/**
	 * 003数据不完整状态(001正常 002数据格式有误 003数据不完整)
	 */
	public static final String STATE_003="003";
	
	public static final String MSG="msg";
	/**
	 * 正常
	 */
	public static final String MSG_NORMAL="正常";
	/**
	 * 数据格式有误
	 */
	public static final String MSG_DATA_FORMAT_ERROR="数据格式有误";
	
	public static final String MESSAGE="message";
	public static final String MESSAGE_OK="ok";

	//https://www.cnblogs.com/aeolian/p/7746158.html
	//https://www.cnblogs.com/bobc/p/8809761.html
	public static JSONObject doHttp(String method, Map<String, Object> params) {
		JSONObject resultJO = null;
		try {
			// 构建请求参数  
			StringBuffer paramsSB = new StringBuffer();
			if (params != null) {  
			    for (Entry<String, Object> e : params.entrySet()) {
			    	paramsSB.append(e.getKey());  
			    	paramsSB.append("=");  
			    	paramsSB.append(e.getValue());  
			    	paramsSB.append("&");  
			    }  
			    paramsSB.substring(0, paramsSB.length() - 1);  
			}  
			
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			URL url = new URL(SERVICE_URL+method);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");//请求post方式
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//header内的的参数在这里set    
			//connection.setRequestProperty("key", "value");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect(); 
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
			//OutputStream writer = connection.getOutputStream(); 
			writer.write(paramsSB.toString());
			writer.flush();
			InputStream is = connection.getInputStream(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead); 
				sbf.append("\r\n"); 
			}
			reader.close();
			
			connection.disconnect();
			String result = sbf.toString();
			System.out.println("result==="+result);
			resultJO = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject doHttpMes(String method, JSONObject bodyParamJO) {
		JSONObject resultJO = null;
		try {
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			URL url = new URL(SERVICE_URL_MES+method);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");//请求post方式
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//header内的的参数在这里set    
			//connection.setRequestProperty("key", "value");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect(); 
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
			//body参数放这里
			String bodyParamStr = bodyParamJO.toString();
			//System.out.println("bodyParamStr==="+bodyParamStr);
			writer.write(bodyParamStr);
			writer.flush();
			InputStream is = connection.getInputStream(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead); 
				sbf.append("\r\n"); 
			}
			reader.close();
			
			connection.disconnect();
			String result = sbf.toString();
			System.out.println("result==="+result);
			resultJO = new JSONObject(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}
	
	public static JSONObject doHttpMes(String method, JSONArray bodyParamJA) {
		JSONObject resultJO = null;
		try {
			StringBuffer sbf = new StringBuffer(); 
			String strRead = null; 
			URL url = new URL(SERVICE_URL_MES+method);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");//请求post方式
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//header内的的参数在这里set    
			//connection.setRequestProperty("key", "value");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect(); 
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 
			//body参数放这里
			String bodyParamStr = bodyParamJA.toString();
			//System.out.println("bodyParamStr==="+bodyParamStr);
			writer.write(bodyParamStr);
			writer.flush();
			InputStream is = connection.getInputStream(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead); 
				sbf.append("\r\n"); 
			}
			reader.close();
			
			connection.disconnect();
			String result = sbf.toString();
			System.out.println("result==="+result);
			resultJO = new JSONObject(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return resultJO;
		}
	}

	public static JSONObject getDingDan(String cph, String ddztMc) {
		Map parames = new HashMap<String, String>();
        parames.put("cph", cph);  
        parames.put("ddztMc", ddztMc);
        JSONObject resultJO = doHttp("getDingDan",parames);
		return resultJO;
	}
	
	public static JSONObject getTabTestList(String tabName) {
		//https://blog.csdn.net/LC_Liangchao/article/details/121793583
		JSONObject jo=new JSONObject();
		StringBuilder jaSB=new StringBuilder();
		if("FeedIssusBody".equals(tabName)) {
			
		}
		return jo;
	}
}
