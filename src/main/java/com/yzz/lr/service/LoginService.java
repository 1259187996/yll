package com.yzz.lr.service;

import com.alibaba.fastjson.JSONObject;
import com.yzz.lr.model.request.RespModel;
import com.yzz.lr.util.StatusException;

/**
 * Created by Administrator on 2019/2/25.
 */
public interface LoginService {

    RespModel login(JSONObject param)throws StatusException;

}
