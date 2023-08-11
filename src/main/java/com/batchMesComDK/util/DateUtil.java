package com.batchMesComDK.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
	
	public static final int SECONDS=1;
	public static final int MINUTES=2;
	public static final int DAYS=3;
	
	/**
	 * java处理StepMes为RunStep到期时间
	 */
	public static final String STEP_MES_EXP_DATE="2023-09-09 00:00:00.0";
	public static final String DOSAGE_LAST_EXP_DATE="2023-09-09 00:00:00.0";

	public static int getTimeBetween(String time1, String time2, int flag) {
		//https://blog.csdn.net/qq_41977655/article/details/125248861
		//指定日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        //按照指定格式转化为LocalDate对象
        
        //LocalDateTime localDateTime1 = LocalDateTime.parse("2023-03-09 01:08:01.000",dateTimeFormatter);
        //LocalDateTime localDateTime2 = LocalDateTime.parse("2023-03-09 01:18:01.000",dateTimeFormatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse(time1,dateTimeFormatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(time2,dateTimeFormatter);
        
        //调方法计算两个LocalDate的天数差
        long between = 0;
        switch (flag) {
		case SECONDS:
	        between = ChronoUnit.SECONDS.between(localDateTime1, localDateTime2);
			break;
		case MINUTES:
	        between = ChronoUnit.MINUTES.between(localDateTime1, localDateTime2);
			break;
		case DAYS:
			between = ChronoUnit.DAYS.between(localDateTime1, localDateTime2);
			break;
		}
        return (int)between;
	}
	
	/**
	 * 验证StepMes字段是否过期(mes那边转换runstep字段需要时间，经理说给mes那边一个月时间，这期间暂时由java端转换，到期后java端就不给处理了，依然由mes那边处理)
	 * @param date
	 * @return
	 */
	public static boolean checkStepMesIfExp(String date) {
		int resDays = getTimeBetween(date+".0",STEP_MES_EXP_DATE,DAYS);
		System.out.println("resDays==="+resDays);
		return resDays<=0?true:false;
	}
	
	public static boolean checkDosageLastIfExp(String date) {
		int resDays = getTimeBetween(date+".0",DOSAGE_LAST_EXP_DATE,DAYS);
		System.out.println("resDays==="+resDays);
		return resDays<=0?true:false;
	}
}
