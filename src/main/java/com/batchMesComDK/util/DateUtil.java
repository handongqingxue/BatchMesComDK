package com.batchMesComDK.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {

	public static int getTimeBetween() {
		//指定日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        //按照指定格式转化为LocalDate对象
        LocalDate time1 = LocalDate.parse("2023-03-09 01:08:01.000",dateTimeFormatter);
        LocalDate time2 = LocalDate.parse("2023-03-19 01:08:01.000",dateTimeFormatter);
        //调方法计算两个LocalDate的天数差
        long between = ChronoUnit.DAYS.between(time1, time2);
        return (int)between;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getTimeBetween());
	}
}
