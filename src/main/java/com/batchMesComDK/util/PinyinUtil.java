package com.batchMesComDK.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;

//https://blog.csdn.net/weixin_42288943/article/details/121765494
/**
 * ƴ��ת��������
 */
public class PinyinUtil {

	/**
     * ������ת��Ϊƴ��
     */
    public static String getPinYinString(String string) {
        if (null != string) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char word = string.charAt(i);
                //https://www.cnblogs.com/sunny3158/p/16110063.html
                if(checkIfChinese(String.valueOf(word))) {//������֤�Ƿ�Ϊ���ģ����ǵĻ�����ת��ƴ����ֱ����ӽ�ȥ(����������ϵ�demo��ԭ��û�У����Լ����ϵ�)
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
     * ��ȡ���ĵ���Ϊ���ַ�
     */
    public static String getPinYinFirstChar(String string) {
        if (null != string) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char word = string.charAt(i);
                if(checkIfChinese(String.valueOf(word))) {//������֤�Ƿ�Ϊ���ģ����ǵĻ�����ת��ƴ����ֱ����ӽ�ȥ(����������ϵ�demo��ԭ��û�У����Լ����ϵ�)
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
     * ��֤�Ƿ�������
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
        String string="�����鿹��������90���䷽";
        String pinYinFirstChar1 = getPinYinString(string);
        String pinYinFirstChar2 = getPinYinFirstChar(string);
        System.out.println("------->"+pinYinFirstChar1);
        System.out.println("------->"+pinYinFirstChar2);
    }
}
