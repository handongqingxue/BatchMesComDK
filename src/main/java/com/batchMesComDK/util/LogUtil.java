package com.batchMesComDK.util;

import java.io.*;
import java.text.*;
import java.util.Date;

public class LogUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 记录日志
	 * @param msg
	 */
	public static void writeInLog(String msg) {
		FileInputStream fis = null;
		FileOutputStream fos=null;
		try {
			File eventFile=new File(Constant.RESOURCES_DIR+"/TestLog/event.txt");
			if(!eventFile.exists())
				eventFile.createNewFile();
			
			StringBuffer preSB=new StringBuffer();
			fis = new FileInputStream(eventFile);
            int data;
            while ((data = fis.read()) != -1) {
            	preSB.append((char)data);
            }
            
            // 关闭输入流
            fis.close();
            
            String preSBStr = preSB.toString();
            
            StringBuffer currMsgSB=new StringBuffer();
            currMsgSB.append(preSBStr);
            currMsgSB.append(sdf.format(new Date()));
            currMsgSB.append(":");
            currMsgSB.append(msg);
            currMsgSB.append("\r\n");
            
            String currMsg = currMsgSB.toString();
			
			byte bytes[]=new byte[512];
			bytes=currMsg.getBytes();
			int b=bytes.length; //是字节的长度，不是字符串的长度
			fos=new FileOutputStream(eventFile);
			fos.write(bytes,0,b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 添加工单批记录日志
	 * @param content
	 * @param workOrderID
	 * @throws IOException 
	 */
	public static void addBRLog(String content, String workOrderID) throws IOException {
		String testLogDirStr=Constant.RESOURCES_DIR+"/TestLog/";
		File testLogDir = new File(testLogDirStr);
		if(!testLogDir.exists())
			testLogDir.mkdir();
		File workOrderBRFile=new File(testLogDirStr+workOrderID+".txt");
		workOrderBRFile.createNewFile();
		
		byte bytes[]=new byte[512];
		bytes=content.getBytes();
		int b=bytes.length; //是字节的长度，不是字符串的长度
		FileOutputStream fos=new FileOutputStream(workOrderBRFile);
		fos.write(bytes,0,b);
		fos.close();
	}
}
