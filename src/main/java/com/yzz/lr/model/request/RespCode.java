package com.yzz.lr.model.request;

/**
 * 返回码枚举类
 * @author zhizhuang.yang
 * @date 2017年9月8日
 * @version 1.0.0
 * @description 返回码枚举类
 */
public enum RespCode {

    SUCCESS("200", "成功"),

    REFRESH_CACHE_SUCCESS("300","缓存刷新失败"),

    DATA_NOT_EXIST("404","请求对象不存在"),

    DATA_EXIST("400","请求数据重复"),

    SYSTEM_EXCEPTION("500", "系统异常"),

    QUERY_PARAM_ERROR("499", "查询参数有误"),

    PHONE_NUMBER_ERROR("498", "手机号格式有误"),

    LOGIN_USER_NOT_EXIST("501", "账号不存在"),

    LOGIN_PASSWORD_ERROR("502", "账号密码不正确"),

    LOGIN_ERROR_TIMES_MANY("503", "密码错误次数过多"),

    USER_ACCOUNT_LOCKED("504", "账户被锁定"),

    USER_ACCOUNT_UNUSED("505","账户被禁用"),

    USER_ACCOUNT_OVER("506","账户已过期"),

    USER_ACCOUNT_UN_AUTH("507","用户没有相关权限"),

    REGISTER_USER_EXIST("510", "注册账号已存在"),

    REGISTER_USER_NOT_EXIST("511", "该账号尚未注册"),

    SMS_CODE_ERROR("513", "验证码错误"),

    MESSAGE_SEND_FAIL("512", "短信发送失败"),

    USER_UN_LOGIN("514", "用户尚未登录"),

    USER_LOGOUT_ERROR("515","用户注销失败"),

    CODE_RECEIVE_TIME_OVER("600","验证码已过期"),
    SEND_MESSAGE_COUNT_ERROR("601","超过短信发送次数"),
    SEND_MESSAGE_RECEIVE_TIME("602","小于短信发送最小间隔"),

    QRCODE_CREATE_ERROR("700","二维码生成异常"),
    QRCODE_UPLOAD_OSS_ERROR("701","二维码上传OSS异常"),

    PAGE_HAS_TRANSED("800","页面已经跳转过"),

    PAGE_NUMBER_ERROR("900","分页参数错误"),

    CALL_WEIXIN_API_ERROR("1001","调用微信API异常"),

    PARAMS_ERROR("1101","请求参数有误"),

    BALANCE_NOT_ENOUTH("1201","积分不足"),


    ;

    /**
     * @param returnCode
     * @param codeDesc
     */
    private RespCode(String returnCode, String codeDesc) {
        this.returnCode = returnCode;
        this.codeDesc = codeDesc;
    }

    /** 返回码 */
    private String returnCode;

    /** 返回码说明 */
    private String codeDesc;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}
