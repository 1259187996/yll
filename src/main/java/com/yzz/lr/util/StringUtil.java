package com.yzz.lr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger LOG = LoggerFactory.getLogger(StringUtil.class);
    
	public static Boolean isBlank(Object... os) {
		for (Object o : os) {
			if (o == null || "".equals(o))
				return true;
		}
		return false;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
    /**
     * 结果为100分制
     * 
     * @param name:数据库名
     * @param data：比较名
     * @return
     * @author wellin
     */
    public static double getCnSimilary(String name, String data) {
        double s = 0.0;
        char[] cs = name.toCharArray();
        for (char c : cs) {
            if (data.contains(c + "")) {
                s++;
            }
        }
        s = s / name.length() * 100;
        double s2 = 0.0;
        for (int i = 0; i < cs.length - 1; i++) {
            if (data.contains(cs[i] + "" + cs[i + 1])) {
                s2++;
            }
        }
        s2 = s2 / (cs.length - 0.99) * 100;
        s2 = Math.min(s2, 100.0);
        s = (s + s2) / 2;
        return s;
    }
    
    /**
     * 将list中的字符串用分割符连接作为一整个字符串
     * 
     * @param strList
     * @param separator
     * @return String
     * @author Sian
     */
    public static String listToString(List<String> strList, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (String str : strList) {
//            if (str.matches("\\s*"))
//                continue;
            if (flag) {
                sb.append(separator);
            } else {
                flag = true;
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * 如果转不了，默认当前日期,可以转换的格式yyyy-MM-dd HH:mm:ss
     */
    public static Date string2DateDefaultNow(String dateStr) {
        Date date = new Date();
        try {
            if(dateStr==null){
                return date;
            }
            date = sdf.parse(dateStr); 
            return date;
        } catch (ParseException e) {
            LOG.error("字符串:" + dateStr + "转换日期出错，自动生成新日期:" + date);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 如果转不了，返回空
     */
    public static Date dateStr2DateDefaultNull(String dateStr) {
        if(dateStr==null){
            return null;
        }
        Date date = null;
        try {
            if (dateStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(dateStr);
            } else if (dateStr.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(dateStr);
            } else {
                LOG.error(dateStr + "不能转成日期");
            }
        } catch (Exception e) {
            LOG.error(dateStr + "不能转成日期", e);
        }
        return date;
    }
    
    /**
     * 字符串转double，转不了默认0.0
     * @param s
     * @return
     */
    public static Double string2double(String s) {
        Double d = 0.0;       
        try {
            d = new Double(s);
        } catch (Exception e) {
            LOG.error("could not parse {} to double, generate quantity 0.0", s);
        }
        return d;
    }

    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 验证手机号格式
     * @param phone
     * @return boolean
     */
    public static boolean checkPhone(String phone){
        if(StringUtil.isBlank(phone)){
            return false;
        }
        Pattern pattern = null;
        Matcher matcher = null;
        boolean flag = false;
        // 验证手机号
        pattern = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");
        matcher = pattern.matcher(phone);
        flag = matcher.matches();
        return flag;
    }

}
