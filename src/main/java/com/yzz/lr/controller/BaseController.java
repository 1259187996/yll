package com.yzz.lr.controller;

import com.yzz.lr.model.request.Page;
import com.yzz.lr.model.request.RespCode;
import com.yzz.lr.model.request.RespModel;
import com.yzz.lr.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);


	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}

	/**
	 * 成功方法,直接返回数据
	 * @param respModel
	 */
	protected <T>void success(RespModel<T> respModel) {
		respModel.setRespCode(RespCode.SUCCESS.getReturnCode());
		respModel.setRespDesc(RespCode.SUCCESS.getCodeDesc());
	}

	/**
	 * 成功方法,直接返回数据
	 * @param modelMap
	 */
	protected void success(ModelMap modelMap) {
		modelMap.put("respCode",RespCode.SUCCESS.getReturnCode());
		modelMap.put("respDesc",RespCode.SUCCESS.getCodeDesc());
	}

	/**
	 * 成功方法,直接返回数据（带分页）
	 * @param respModel
	 * @param page
	 */
	protected <T>void success(RespModel<T> respModel, Page page) {
		respModel.setRespCode(RespCode.SUCCESS.getReturnCode());
		respModel.setRespDesc(RespCode.SUCCESS.getCodeDesc());
		respModel.setPage(page);
	}

	/**
	 * 成功方法,直接返回数据
	 * @param response
	 * @param respModel
	 */
//	protected <T>void writeResponse(HttpServletResponse response, RespModel<T> respModel) {
//		response.setContentType(Const.JSON_CONTENT_TYPE);
//		try {
//			String returnStr = JsonUtils.entityToJSON(respModel).toString();
//			logger.info(returnStr);
//			response.getWriter().write(returnStr);
//		} catch (IOException e) {
//			logger.error(e);
//		}
//	}

	/**
	 * 失败方法，赋予respModel失败值
	 * 整体异常，无法判断时
	 * @param respModel
	 * @param error_message
	 */
	protected void failed(RespModel respModel, String error_message) {
		respModel.setRespCode(RespCode.SYSTEM_EXCEPTION.getReturnCode());
		respModel.setRespDesc(RespCode.SYSTEM_EXCEPTION.getCodeDesc()+","+error_message);
	}

	/**
	 * 失败方法，赋予respModel失败值
	 * 知道具体异常情况时
	 * @param respModel
	 * @param respCode
	 */
	protected void failed(RespModel respModel, RespCode respCode,String error_message) {
		respModel.setRespCode(respCode.getReturnCode());
		if(!StringUtil.isBlank(error_message)){
			respModel.setRespDesc(respCode.getCodeDesc()+error_message);
		}else{
			respModel.setRespDesc(respCode.getCodeDesc());
		}

	}

	protected void failed(RespModel respModel, RespCode respCode) {
		respModel.setRespCode(respCode.getReturnCode());
		respModel.setRespDesc(respCode.getCodeDesc());
	}
}
