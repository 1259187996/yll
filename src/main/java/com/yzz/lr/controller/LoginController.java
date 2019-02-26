package com.yzz.lr.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzz.lr.model.request.RespModel;
import com.yzz.lr.service.LoginService;
import com.yzz.lr.util.StatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2019/2/25.
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @RequestMapping
    public RespModel login(HttpServletRequest request, @RequestParam JSONObject param){
        RespModel respModel = new RespModel();
        try {

            loginService.login(param);


        }catch (StatusException e){
            logger.error(e.getError_message());
            this.failed(respModel,e.getError_message());
        }catch (Exception e){

        }


        return respModel;
    }


}
