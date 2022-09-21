package com.batchMesComDK.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;

//https://blog.csdn.net/weixin_42288943/article/details/121765494
/**
 * 拼音转化工具类
 */
public class PinyinUtil {

	/**
     * 将中文转化为拼音
     */
    public static String getPinYinString(String string) {
        if (null != string) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char word = string.charAt(i);
                //https://www.cnblogs.com/sunny3158/p/16110063.html
                if(checkIfChinese(String.valueOf(word))) {//这里验证是否为中文，不是的话不用转换拼音，直接添加进去(这个方法网上的demo里原来没有，是自己加上的)
	                String[] strings = PinyinHelper.toHanyuPinyinStringArray(word);
	                if (null != string) {
	                    builder.append(strings[0]);
	                } else {
	                    builder.append(word);
	                }
                }
                else
                	builder.append(word);
            }
            return builder.toString();
        }
        return "";
    }
    
    /**
     * 获取中文的因为首字符
     */
    public static String getPinYinFirstChar(String string) {
        if (null != string) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char word = string.charAt(i);
                if(checkIfChinese(String.valueOf(word))) {//这里验证是否为中文，不是的话不用转换拼音，直接添加进去(这个方法网上的demo里原来没有，是自己加上的)
	                String[] strings = PinyinHelper.toHanyuPinyinStringArray(word);
	                if (null != string) {
	                    builder.append(strings[0].charAt(0));
	                } else {
	                    builder.append(word);
	                }
                }
                else
                	builder.append(word);
            }
            return builder.toString();
        }
        return "";
    }
    
    /**
     * 验证是否是中文
     * @param str
     * @return
     */
    public static boolean checkIfChinese(String str) {
    	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    	Matcher m = p.matcher(str);
    	if (m.find()) {
    		return true;
    	}
    	return false;
	}
    
    public static void main(String[] args) {
        String string="冷酸灵抗敏感牙膏90克配方";
        String pinYinFirstChar1 = getPinYinString(string);
        String pinYinFirstChar2 = getPinYinFirstChar(string);
        System.out.println("------->"+pinYinFirstChar1);
        System.out.println("------->"+pinYinFirstChar2);
    }
}
