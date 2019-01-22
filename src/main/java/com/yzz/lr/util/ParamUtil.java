package com.yzz.lr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtil {

	
	/**
	 * 验证手机号格式
	 * @param phone
	 * @return boolean
	 */
	public static boolean checkPhone(String phone){
		Pattern pattern = null;  
        Matcher matcher = null;  
        boolean flag = false;   
        // 验证手机号  
        pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); 
        matcher = pattern.matcher(phone);  
        flag = matcher.matches();   
        return flag;  
	}
	
}
