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

import com.batchMesComDK.entity.FeedIssusBody;
import com.batchMesComDK.entity.FormulaDto;
import com.batchMesComDK.entity.FormulaMaterialDto;
import com.batchMesComDK.entity.OrderMateriaBody;
import com.batchMesComDK.entity.PasteWorkingNumBody;
import com.batchMesComDK.entity.WorkOrderBody;

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
	
	public static JSONObject getTabTestItem(String tabName) {
		JSONObject jo=new JSONObject();
		try {
			if("FeedIssusBody".equals(tabName)) {
				FeedIssusBody fib=new FeedIssusBody();
			    fib.setId("123456");
			    fib.setWorkOrder("aaaaaaaaaaaaa");
			    fib.setFeedportCode("dfgrgrgergr");
			    fib.setFeedTime("1997-07-01");
			    fib.setMaterialCode("fdfertrgg");
			    fib.setSuttle("fgfhgth");
			    fib.setUnit("kg");
			    fib.setSort("1");
				jo.put("FeedIssusBody", fib);
			}
			else if("FormulaDto".equals(tabName)) {
				FormulaDto fd=new FormulaDto();
				fd.setId("cl310l32l09en0a693iho2ezr");
				fd.setCode("F000001");
				fd.setVersion("0");
				fd.setName("冷酸灵抗敏感牙膏90克配方");
				fd.setFactory(null);
				fd.setWorkcenterId(null);
				fd.setRefQuantity(null);
				fd.setUnit("");
				fd.setBeginValidDate(null);
				fd.setEndValidDate(null);
				fd.setStatus("V");
				fd.setCreatedAt("2022-05-11T03:20:42.955");
				fd.setUpdatedAt("2022-07-01T09:17:01.342");
				fd.setCreateUser("wang");
				fd.setUpdateUser("wang");
				fd.setOriginalFormulaCode("F000001");
				fd.setOrigineVersion("0");
				fd.setProductCode("1010051");
				fd.setProductName("冷酸灵抗敏感牙膏90克配方");
				fd.setProductDesc("");
				fd.setStageType("G");
				fd.setIsStandBom("1");
				fd.setType("T");
				fd.setProject("");
				fd.setDosageType("");
				fd.setDosage("");
				fd.setForm(null);
				fd.setFolder("");
				fd.setUnitType("Q");
				fd.setWeightEquivalent("0E-30");
				fd.setWeightEquivalentUnit("");
				fd.setRefQuantityUnit("un");
				fd.setMessage(null);
				fd.setApproveUser("");
				fd.setApproveDate(null);
				fd.setVerifyUser("wang");
				fd.setVerifyDate("2022-07-01T09:17:01");
				fd.setCancelAt(null);
				fd.setCancelUser("");
				fd.setZoneCode(null);
				jo.put("FormulaDto", fd);
			}
			else if("FormulaMaterialDto".equals(tabName)) {
				FormulaMaterialDto fmd=new FormulaMaterialDto();
				fmd.setId("cl38bsxwr0fd10a69oqs63267");
				fmd.setCreatedAt("2022-05-16T06:09:08.521");
				fmd.setUpdatedAt("2022-05-16T06:09:08.521");
				fmd.setCreateUser("wang");
				fmd.setUpdateUser("wang");
				fmd.setPhase("0001");
				fmd.setPhaseDes("");
				fmd.setStep("0001");
				fmd.setIsUniqueLot("0");
				fmd.setTolerance(null);
				fmd.setMaterialCode("4030038");
				fmd.setMaterialName("冷酸灵抗敏感牙膏90克大箱");
				fmd.setIsCompensateur("N");
				fmd.setQty("1.000000000000000000000000000000");
				fmd.setUnit("un");
				fmd.setIsWeightingWeight("1");
				fmd.setIsMfgWeight("1");
				fmd.setIsPackWeight("1");
				fmd.setDosage("1");
				fmd.setIsPotency("0");
				fmd.setPotency("0E-30");
				fmd.setIsFixedQty("0");
				fmd.setIsMasterMaterial("0");
				fmd.setAttribute1(null);
				fmd.setIsTailSemiFinishedPrd("0");
				fmd.setFormula("cl310l32l09en0a693iho2ezr");
				fmd.setWeightStationCode(null);
				fmd.setBeginValidDate(null);
				fmd.setEndValidDate(null);
				jo.put("FormulaMaterialDto", fmd);
			}
			else if("OrderMateriaBody".equals(tabName)) {
				OrderMateriaBody omb=new OrderMateriaBody();
				omb.setId("123456");
				omb.setMaterialCode("fdfertrgg");
				omb.setMaterialName("嬲");
				omb.setLot("aaa");
				omb.setDosage("bbb");
				omb.setUnit("kg");
				omb.setStep("1");
				omb.setPhase("ccc");
				jo.put("OrderMateriaBody", omb);
			}
			else if("PasteWorkingNumBody".equals(tabName)) {
				PasteWorkingNumBody pwnb=new PasteWorkingNumBody();
				pwnb.setId("123456");
				pwnb.setWorkOrder("aaaaaaaaaaaaa");
				pwnb.setCreamCode("fdsffgfdgf");
				pwnb.setCreamWaterNo("dfertrgtrgt");
				jo.put("PasteWorkingNumBody", pwnb);
			}
			else if("WorkOrderBody".equals(tabName)) {
				WorkOrderBody wob=new WorkOrderBody();
				wob.setId("123456");
				wob.setWorkOrder("aaaaaaaaaaaaa");
				wob.setProductcode("fdfgffgfg");
				wob.setProductName("嬲");
				wob.setTotalOutput("ccc");
				wob.setMfgCode("ddd");
				wob.setMfgVersion("1.0");
				wob.setFormulaId("1");
				jo.put("WorkOrderBody", wob);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return jo;
		}
	}
}
