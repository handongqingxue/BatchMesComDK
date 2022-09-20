package com.batchMesComDK.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class APIUtil {

	public static final String SERVICE_URL="http://10.10.99.20:8080/ZnczLfyl/gkj/";
	//public static final String SERVICE_URL="http://localhost:8080/ZnczLfyl/gkj/";

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

	public static JSONObject getDingDan(String cph, String ddztMc) {
		Map parames = new HashMap<String, String>();
        parames.put("cph", cph);  
        parames.put("ddztMc", ddztMc);
        JSONObject resultJO = doHttp("getDingDan",parames);
		return resultJO;
	}
}
