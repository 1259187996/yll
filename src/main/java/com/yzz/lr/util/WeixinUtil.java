package com.yzz.lr.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/11/29.
 * 微信交互工具
 */
public class WeixinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * 验证Token
     * @param msgSignature 签名串，对应URL参数的signature
     * @param timeStamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     *
     * @return 是否为安全签名
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public static boolean verifyUrl(String msgSignature, String timeStamp, String nonce)
            throws AesException {
        String signature = SHA1.getSHA1(WeixinConfig.TOKEN, timeStamp, nonce);
        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }
        return true;
    }


    /**
     * 或者AccessToken
     * @param code  授权后返回的code
     * @return
     * <pre>
     *     {
                "access_token":"ACCESS_TOKEN",
                "expires_in":7200,
                "refresh_token":"REFRESH_TOKEN",
                "openid":"OPENID",
                "scope":"SCOPE"
            }
     * </pre>
     * @throws Exception
     */
    public static JSONObject getAccessToken(String code)throws HttpException {
        JSONObject returnJson = new JSONObject();
        String param = "appid="+WeixinConfig.APP_ID+"&secret="+WeixinConfig.APP_SECRET+"&code="+code+"&grant_type=authorization_code";
        String result = HttpRequest.httpGet(WeixinConfig.GET_ACCESS_TOKEN_URL,param,Const.CHARSET);
        logger.info("请求微信access_token结果："+result);
        if(!result.isEmpty()){
            returnJson = JSON.parseObject(result);
        }
        return returnJson;
    }

    /**
     * 或者AccessToken
     * @param accessToken  网页授权接口调用凭证
     * @param openId       用户的唯一标识
     * @return
     * <pre>
     *     {
                "openid":" OPENID",
                "nickname": NICKNAME,
                "sex":"1",
                "province":"PROVINCE"
                "city":"CITY",
                "country":"COUNTRY",
                "headimgurl":    "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
                "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
            }
     * </pre>
     * @throws Exception
     */
//    public static WeixinUserInfo getWeixinUserInfo(String accessToken,String openId)throws Exception{
//        WeixinUserInfo userInfo = new WeixinUserInfo();
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("access_token",accessToken));
//        params.add(new BasicNameValuePair("openid",openId));
//        params.add(new BasicNameValuePair("lang",Const.LANGUAGE));
//        String result = String.valueOf(HttpsUtil.sendGet(WeixinConfig.GET_USER_INFO_URL,params));
//        logger.info("请求微信user_info结果："+result);
//        if(!result.isEmpty()){
//            userInfo = JSON.parseObject(result,new TypeReference<WeixinUserInfo>(){});
//        }
//        return userInfo;
//    }


}
