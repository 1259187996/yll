package com.yzz.lr.util;


import com.yzz.lr.model.request.RespCode;

import java.io.Serializable;

/**
 * Created by zhizhuang.yang on 2017/9/12.
 */
public class StatusException extends Exception implements Serializable{

    private static final long serialVersionUID = 1L;

    private RespCode respCode = RespCode.SYSTEM_EXCEPTION;
    private String error_message = "";

    public StatusException(String error_message){
        super(error_message);
        this.respCode.setCodeDesc(RespCode.SYSTEM_EXCEPTION.getCodeDesc()+":"+error_message);
    }

    public StatusException(RespCode respCode){
        super(respCode.getCodeDesc());
        this.respCode = respCode;
    }

    public StatusException(RespCode respCode,String error_message){
        super(respCode.getCodeDesc()+error_message);
        this.respCode = respCode;
        this.error_message = error_message;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public RespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }
}
