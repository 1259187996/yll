package com.yzz.lr.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzz.lr.model.request.RespModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/2/25.
 */
@Service("loginService")
public class LoginServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    public RespModel login(JSONObject param){
        RespModel respModel = new RespModel();
        String userName = param.getString("userName");
        String password = param.getString("password");
        return respModel;
    }


}
