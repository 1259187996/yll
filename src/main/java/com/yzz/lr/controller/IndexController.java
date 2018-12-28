package com.yzz.lr.controller;

import com.yzz.lr.mapper.SystemConfigMapper;
import com.yzz.lr.model.SystemConfig;
import com.yzz.lr.model.request.RespCode;
import com.yzz.lr.model.request.RespModel;
import com.yzz.lr.util.AesException;
import com.yzz.lr.util.StringUtil;
import com.yzz.lr.util.SystemConfigUtil;
import com.yzz.lr.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/27.
 */
@Controller
public class IndexController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @PostMapping("/error")
    public RespModel error(HttpServletRequest request) {
        RespModel respModel = null;
        if (request.getAttribute("jsonResponse") != null) {
            respModel = (RespModel) request.getAttribute("jsonResponse");
        } else {
            this.failed(respModel, RespCode.SYSTEM_EXCEPTION);
        }
        return respModel;
    }

    @RequestMapping("/test")
    @ResponseBody
    public RespModel test(HttpServletRequest request) {
        RespModel respModel = new RespModel();
        this.success(respModel);
        return respModel;
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, ModelMap modelMap) {
        this.success(modelMap);
        return new ModelAndView("index",modelMap);
    }


    @RequestMapping("/wx/verify_wx_token")
    @ResponseBody
    public String verifyWXToken(HttpServletRequest request) throws AesException {
        String msgSignature = request.getParameter("signature");
        if(StringUtil.isBlank(msgSignature)){
            return "签名验证失败";
        }
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WeixinUtil.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }

    @RequestMapping("/refreshCache")
    @ResponseBody
    public RespModel refreshCache(HttpServletRequest request) throws AesException {
        RespModel respModel = new RespModel();
        logger.info("开始刷新缓存");
        try {
            Map<String,Object> params = new HashMap<>();
            List<SystemConfig> list = systemConfigMapper.select(params);
            SystemConfigUtil.refresh(list);
            this.success(respModel);
        }catch (Exception e){
            logger.info("刷新缓存失败:"+e.getMessage());
            this.failed(respModel,RespCode.REFRESH_CACHE_SUCCESS);
        }finally {
            logger.info("刷新缓存成功："+respModel.toString());
        }

        return respModel;
    }

}
