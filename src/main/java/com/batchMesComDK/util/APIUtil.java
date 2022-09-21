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

import org.json.JSONException;
import org.json.JSONObject;

import com.batchMesComDK.entity.*;

//import net.sf.json.JSONArray;

public class APIUtil {

	public static final String SERVICE_URL="http://10.10.99.20:8080/ZnczLfyl/gkj/";
	//public static final String SERVICE_URL="http://localhost:8080/ZnczLfyl/gkj/";
	public static final String ITEM_RESULT="item";
	public static final String LIST_RESULT="list";;

	//https://www.cnblogs.com/aeolian/p/7746158.html
	//https://www.cnblogs.com/bobc/p/8809761.html
	public static JSONObject doHttp(String method, Map<String, Object> params) {
		JSONObject resultJO = null;
		try {
			// ππΩ®«Î«Û≤Œ ˝  
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
			connection.setRequestMethod("POST");//«Î«Ûpost∑Ω Ω
			connection.setDoInput(true); 
			connection.setDoOutput(true); 
			//headerƒ⁄µƒµƒ≤Œ ˝‘⁄’‚¿Ôset    
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
				fd.setName("¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ≈‰∑Ω");
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
				fd.setProductName("¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ≈‰∑Ω");
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
				fmd.setMaterialName("¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ¥Ûœ‰");
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
				omb.setMaterialName("Ê’");
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
				wob.setProductName("Ê’");
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
	
	public static JSONObject getTabTestList(String tabName) {
		//https://blog.csdn.net/LC_Liangchao/article/details/121793583
		JSONObject jo=new JSONObject();
		StringBuilder jaSB=new StringBuilder();
		try {
			if("FeedIssusBody".equals(tabName)) {
				
			}
			else if("FormulaDto".equals(tabName)) {
				jaSB.append("[");
					jaSB.append("{");
						jaSB.append("\"approveUser\":\"\",");
						jaSB.append("\"cancelUser\":\"\",");
						jaSB.append("\"code\":\"F000001\",");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":1652210442955,");
						jaSB.append("\"dosage\":\"\",");
						jaSB.append("\"dosageType\":\"\",");
						jaSB.append("\"folder\":\"\",");
						jaSB.append("\"id\":\"cl310l32l09en0a693iho2ezr\",");
						jaSB.append("\"isStandBom\":1,");
						jaSB.append("\"name\":\"¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ≈‰∑Ω\",");
						jaSB.append("\"originalFormulaCode\":\"F000001\",");
						jaSB.append("\"origineVersion\":0,");
						jaSB.append("\"productCode\":\"1010051\",");
						jaSB.append("\"productDesc\":\"\",");
						jaSB.append("\"productName\":\"¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ≈‰∑Ω\",");
						jaSB.append("\"project\":\"\",");
						jaSB.append("\"refQuantityUnit\":\"un\",");
						jaSB.append("\"stageType\":\"G\",");
						jaSB.append("\"status\":\"V\",");
						jaSB.append("\"type\":\"T\",");
						jaSB.append("\"unit\":\"\",");
						jaSB.append("\"unitType\":\"Q\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":1656638221342,");
						jaSB.append("\"verifyDate\":\"2022-07-01 09:17:01\",");
						jaSB.append("\"verifyUser\":\"wang\",");
						jaSB.append("\"version\":\"0\",");
						jaSB.append("\"weightEquivalent\":\"0E-30\",");
						jaSB.append("\"weightEquivalentUnit\":\"\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"approveUser\":\"\",");
						jaSB.append("\"cancelUser\":\"\",");
						jaSB.append("\"code\":\"F000016\",");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652823369218\",");
						jaSB.append("\"dosage\":\"\",");
						jaSB.append("\"dosageType\":\"\",");
						jaSB.append("\"folder\":\"\",");
						jaSB.append("\"id\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"isStandBom\":1,");
						jaSB.append("\"name\":\"∆’¿‰—¿∏‡∏‡ÃÂ≈‰∑Ω\",");
						jaSB.append("\"originalFormulaCode\":\"F000016\",");
						jaSB.append("\"origineVersion\":0,");
						jaSB.append("\"productCode\":\"3010001\",");
						jaSB.append("\"productDesc\":\"\",");
						jaSB.append("\"productName\":\"∆’¿‰—¿∏‡∏‡ÃÂ\",");
						jaSB.append("\"project\":\"\",");
						jaSB.append("\"refQuantityUnit\":\"kg\",");
						jaSB.append("\"stageType\":\"Z\",");
						jaSB.append("\"status\":\"V\",");
						jaSB.append("\"type\":\"T\",");
						jaSB.append("\"unit\":\"\",");
						jaSB.append("\"unitType\":\"Q\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":1652837139583,");
						jaSB.append("\"verifyDate\":\"2022-05-18 09:25:39\",");
						jaSB.append("\"verifyUser\":\"wang\",");
						jaSB.append("\"version\":\"0\",");
						jaSB.append("\"weightEquivalent\":\"0E-30\",");
						jaSB.append("\"weightEquivalentUnit\":\"\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"approveUser\":\"\",");
						jaSB.append("\"cancelUser\":\"\",");
						jaSB.append("\"code\":\"F000029\",");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1653418782720\",");
						jaSB.append("\"dosage\":\"\",");
						jaSB.append("\"dosageType\":\"\",");
						jaSB.append("\"folder\":\"\",");
						jaSB.append("\"id\":\"cl3l000010sbk0a695c5nyk2v\",");
						jaSB.append("\"isStandBom\":\"1\",");
						jaSB.append("\"name\":\"øπ√Ù√¿∞◊¥Ÿœ˙≈‰∑Ω\",");
						jaSB.append("\"originalFormulaCode\":\"F000029\",");
						jaSB.append("\"origineVersion\":\"0\",");
						jaSB.append("\"productCode\":\"1010891\",");
						jaSB.append("\"productDesc\":\"\",");
						jaSB.append("\"productName\":\"¿‰À·¡È◊®—–øπ√Ù—¿∏‡140ÀÕ¿‰À·¡È√¿∞◊60øÀ\",");
						jaSB.append("\"project\":\"\",");
						jaSB.append("\"refQuantityUnit\":\"un\",");
						jaSB.append("\"stageType\":\"C\",");
						jaSB.append("\"status\":\"V\",");
						jaSB.append("\"type\":\"T\",");
						jaSB.append("\"unit\":\"\",");
						jaSB.append("\"unitType\":\"Q\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1653418821307\",");
						jaSB.append("\"verifyDate\":\"2022-05-25 03:00:21\",");
						jaSB.append("\"verifyUser\":\"wang\",");
						jaSB.append("\"version\":\"0\",");
						jaSB.append("\"weightEquivalent\":\"0E-30\",");
						jaSB.append("\"weightEquivalentUnit\":\"\"");
					jaSB.append("}");
				jaSB.append("]");
				
				List<FormulaDto> fdList=new ArrayList<FormulaDto>();
				net.sf.json.JSONArray fdJA = net.sf.json.JSONArray.fromObject(jaSB.toString());
				for (int i = 0; i < fdJA.size(); i++) {
					net.sf.json.JSONObject fdJO = (net.sf.json.JSONObject)fdJA.get(i);
					FormulaDto fd=(FormulaDto)net.sf.json.JSONObject.toBean(fdJO, FormulaDto.class);
					System.out.println("id==="+fd.getId());
					fdList.add(fd);
				}
				
				jo.put("FormulaDtoList", fdList);
			}
			else if("FormulaMaterialDto".equals(tabName)) {
				jaSB.append("[");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652652548521\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl310l32l09en0a693iho2ezr\",");
						jaSB.append("\"id\":\"cl38bsxwr0fd10a69oqs63267\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"4030038\",");
						jaSB.append("\"materialName\":\"¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ¥Ûœ‰\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"1.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0001\",");
						jaSB.append("\"unit\":\"un\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652652548521\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652652570950\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl310l32l09en0a693iho2ezr\",");
						jaSB.append("\"id\":\"cl38btf7s0fdj0a693fr8dsp0\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"4010023\",");
						jaSB.append("\"materialName\":\"¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀª®∫–\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"72.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0002\",");
						jaSB.append("\"unit\":\"un\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652652570950\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652652585446\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl310l32l09en0a693iho2ezr\",");
						jaSB.append("\"id\":\"cl38btqeh0fdx0a69q9tjxz2s\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"4020028\",");
						jaSB.append("\"materialName\":\"¿‰À·¡Èøπ√Ù∏–—¿∏‡90øÀ»Ìπ‹\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"72.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0003\",");
						jaSB.append("\"unit\":\"un\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652652585446\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652652605979\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl310l32l09en0a693iho2ezr\",");
						jaSB.append("\"id\":\"cl38bu68t0fep0a6905xysp16\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"1\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"3010001\",");
						jaSB.append("\"materialName\":\"∆’¿‰—¿∏‡∏‡ÃÂ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"6.480000000000000000000000000000\",");
						jaSB.append("\"step\":\"0004\",");
						jaSB.append("\"unit\":\"kg\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652652605979\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824504504\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b66jzu0ist0a69p7szrr7t\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"2010012\",");
						jaSB.append("\"materialName\":\"…Ω¿ÊÃ«¥º\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"30.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0001\",");
						jaSB.append("\"unit\":\"l\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824504504\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824552047\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b67koh0itd0a69poqcirpk\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"9010101\",");
						jaSB.append("\"materialName\":\"ÀÆ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"50.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0002\",");
						jaSB.append("\"unit\":\"kg\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824552047\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824587911\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b68ccp0itp0a69ze3snrnt\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"2010014\",");
						jaSB.append("\"materialName\":\"∂˛—ıªØπË\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"15.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0003\",");
						jaSB.append("\"unit\":\"kg\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824587911\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824605415\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b68pux0iu10a695galj5dw\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"2010017\",");
						jaSB.append("\"materialName\":\"ø®¿≠Ω∫\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"10.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0004\",");
						jaSB.append("\"unit\":\"kg\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824605415\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824631371\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b699vy0iul0a6916hum6em\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"2010040\",");
						jaSB.append("\"materialName\":\"œıÀ·ºÿ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"5.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0005\",");
						jaSB.append("\"unit\":\"kg\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824631371\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1652824645699\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3b5i8030ip70a69c1seqy6y\",");
						jaSB.append("\"id\":\"cl3b69kxx0iv20a69b4kbtkwt\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"2010031\",");
						jaSB.append("\"materialName\":\"¿∂…´—’¡œ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"1.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0006\",");
						jaSB.append("\"unit\":\"g\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1652824645699\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1653418796132\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3l000010sbk0a695c5nyk2v\",");
						jaSB.append("\"id\":\"cl3l00acm0sc60a69bo0ncc42\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"3030101\",");
						jaSB.append("\"materialName\":\"¿‰À·¡Èøπ√Ù—¿∏‡140øÀ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"1.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0001\",");
						jaSB.append("\"unit\":\"un\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1653418796132\"");
					jaSB.append("},");
					jaSB.append("{");
						jaSB.append("\"createUser\":\"wang\",");
						jaSB.append("\"createdAt\":\"1653418814887\",");
						jaSB.append("\"dosage\":\"1\",");
						jaSB.append("\"formula\":\"cl3l000010sbk0a695c5nyk2v\",");
						jaSB.append("\"id\":\"cl3l00otk0scu0a69m6thdw8o\",");
						jaSB.append("\"isCompensateur\":\"N\",");
						jaSB.append("\"isFixedQty\":\"0\",");
						jaSB.append("\"isMasterMaterial\":\"0\",");
						jaSB.append("\"isMfgWeight\":\"1\",");
						jaSB.append("\"isPackWeight\":\"1\",");
						jaSB.append("\"isPotency\":\"0\",");
						jaSB.append("\"isTailSemiFinishedPrd\":\"0\",");
						jaSB.append("\"isUniqueLot\":\"0\",");
						jaSB.append("\"isWeightingWeight\":\"1\",");
						jaSB.append("\"materialCode\":\"3030801\",");
						jaSB.append("\"materialName\":\"¿‰À·¡È√¿∞◊60øÀ\",");
						jaSB.append("\"phase\":\"0001\",");
						jaSB.append("\"phaseDes\":\"\",");
						jaSB.append("\"potency\":\"0E-30\",");
						jaSB.append("\"qty\":\"1.000000000000000000000000000000\",");
						jaSB.append("\"step\":\"0002\",");
						jaSB.append("\"unit\":\"un\",");
						jaSB.append("\"updateUser\":\"wang\",");
						jaSB.append("\"updatedAt\":\"1653418814887\"");
					jaSB.append("}");
				jaSB.append("]");
				jaSB.append("");
				jaSB.append("");
				
				List<FormulaMaterialDto> fmdList=new ArrayList<FormulaMaterialDto>();
				net.sf.json.JSONArray fmdJA = net.sf.json.JSONArray.fromObject(jaSB.toString());
				for (int i = 0; i < fmdJA.size(); i++) {
					net.sf.json.JSONObject fmdJO = (net.sf.json.JSONObject)fmdJA.get(i);
					FormulaMaterialDto fmd=(FormulaMaterialDto)net.sf.json.JSONObject.toBean(fmdJO, FormulaMaterialDto.class);
					System.out.println("id==="+fmd.getId());
					fmdList.add(fmd);
				}
				
				jo.put("FormulaMaterialDtoList", fmdList);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}
}
