package com.yzz.lr.model.request;

import java.io.Serializable;

/**
 * 返回基础类
 * @author zhizhuang.yang
 * @date 2017年9月8日
 * @version 1.0.0
 * @description 返回基础类
 */
public class RespModel<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String respCode;

    /**
     * 返回码描述
     */
    private String respDesc;

    /**
     * 返回信息
     */
    private T respData;

    /**
     * 分页信息
     */
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    public String toString() {
        return "RespModel{respCode='" + this.respCode + '\'' + ", respDesc='" + this.respDesc + '\'' + ", respData=" + this.respData + ", page=" + this.page + '}';
    }
}
