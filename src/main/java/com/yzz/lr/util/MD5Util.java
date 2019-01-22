package com.yzz.lr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chengfeng.lu on 2016/11/14.
 */
public class MD5Util {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Util.class.getName()
                    + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }



    /**
     * 功能：加盐版的MD5.返回格式为MD5(密码+{盐值})
     * @param str
     * @param salt
     * @return
     */
    public static String getMD5StringWithSalt(String str, String salt) {
        if (str == null) {
            throw new IllegalArgumentException("password不能为null");
        }
        if (StringUtil.isBlank(salt)) {
            throw new IllegalArgumentException("salt不能为空");
        }
        if ((salt.toString().lastIndexOf("{") != -1)
                || (salt.toString().lastIndexOf("}") != -1)) {
            throw new IllegalArgumentException("salt中不能包含 { 或者 }");
        }
        return getMD5String(str + "{" + salt.toString() + "}");
    }

    /**
     * 进行MD5加密，默认加盐值
     * @param str
     * @return
     */
    public static String MD5(String str){
        return getMD5StringWithSalt(str, Const.MD5_DEFAULT_SALT);
    }

    /**
     * 得到一个字符串的MD5值
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        return getMD5String(str.getBytes());
    }

    private static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
