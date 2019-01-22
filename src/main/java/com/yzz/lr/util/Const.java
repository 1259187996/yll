package com.yzz.lr.util;

/**
 * Created by Administrator on 2018/11/27.
 */
public class Const {


    public static final String CHARSET = "UTF-8";                                               //默认字符集
    public static final String LANGUAGE = "zh_CN";                                              //默认语言
    public static final String SMS_TEMPLATE_CODE = "SMS_133964406";
    public static final String MESSAGE_TYPE_REGISTER = "register";								//注册时发送验证码
    public static final String MESSAGE_TYPE_LOGIN = "login";									//重新登录时发送验证码
    public static final String QRCODE_BUCKET_NAME = "uploadall";							    //存放二维码的bucket
    public static final String QRCODE_PREFIX = "weixin_qrcode";							        //存放二维码的文件夹
    public static final String OSS_URL_PREFIX = "http://upload.autoport.com.cn/";				//OSS链接前戳
    public static final String USER_SESSION = "USER_SESSION";				                    //userSession常量
    public static final String MD5_DEFAULT_SALT = "status";                                     //MD5加密默认盐值


}
