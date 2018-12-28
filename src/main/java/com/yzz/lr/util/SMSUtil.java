package com.yzz.lr.util;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送辅助类
 */
public class SMSUtil {

	private static final String AccessId = "LTAITlUo7arfdYfg";
	private static final String AccessKey = "p6BfLeRHi2hSd8SrmVp5ciMneIFdBG";
	private static final String MNSEndpoint = "https://1226576686731530.mns.cn-hangzhou.aliyuncs.com/";
	private static final String Topic = "sms.topic-cn-hangzhou";
	private static final String SMSTemplateCode = "SMS_62350515";//注册验证
	private static final String SMSTemplateCode2 = "SMS_62350517";//登录提醒
	private static final String SMS_140520242 = "SMS_140520242";//通知客服人员
	private static final String SignName = "信号旗智能科技";
	private static final String ProductName = "易豹科技";
	private static final String SMSTemplateParamKey = ":%s。";// 亲,您的账号重置后密码是

	public static void sendPwd(String phone, String pwd, String template) {
		String text = String.format(SMSTemplateParamKey, pwd);
		sendSms(text, phone, template);
	}

	public static boolean sendLoginCode(String phone,String code){
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", phone);
		params.put("code", code);
		params.put("product", ProductName);
		return post(params,SMSTemplateCode2);
	}

	public static boolean sendRegisterCode(String phone,String code){
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", phone);
		params.put("code", code);
		params.put("product", ProductName);
		return post(params,SMSTemplateCode);
	}

	public static boolean sendSms(String text, String mobile, String template) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("text", text);
		params.put("mobile", mobile);
		return post(params, template);
	}

	public static boolean sendNotice(String name, String phone, String mobile, String template) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("phone", phone);
		params.put("mobile", mobile);
		return post(params, template);
	}

	public static String sendEmail(String phone, String email1, String email2) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", phone);
		params.put("email1", email1);
		params.put("email2", email2);
		return postUtil(params,SMS_140520242);
	}

	private static boolean post(Map<String, String> params, String template) {
		boolean flag = true;
		CloudAccount account = new CloudAccount(AccessId, AccessKey, MNSEndpoint);
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(Topic);
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("消息内容:");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(SignName);
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();

		if (SMSTemplateCode.equals(template)) {
			batchSmsAttributes.setTemplateCode(template);
			smsReceiverParams.setParam("code", params.get("code"));
			smsReceiverParams.setParam("product", params.get("product"));
		}else if (SMSTemplateCode2.equals(template)) {
			batchSmsAttributes.setTemplateCode(template);
			smsReceiverParams.setParam("code", params.get("code"));
			smsReceiverParams.setParam("product", params.get("product"));
		} else {
			// 3.2 设置发送短信使用的模板（SMSTempateCode）
			batchSmsAttributes.setTemplateCode(SMSTemplateCode);
			// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
			smsReceiverParams.setParam("pwd", params.get("text"));
		}
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(params.get("mobile"), smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		String messageId = "";
		String messageMd5 = "";
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			messageId = ret.getMessageId();
			messageMd5 = ret.getMessageBodyMD5();
			System.out.println("messageId:" + messageId + ",messageMd5:" + messageMd5);
			client.close();
//			return "messageId:" + messageId + ",messageMd5:" + messageMd5;
		} catch (ServiceException se) {
			flag = false;
			se.printStackTrace();
//			return "errorCode:" + se.getErrorCode() + ",ｒequestId,:" + se.getRequestId() + ",message:"
//					+ se.getMessage();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		client.close();
//		return "messageId:" + messageId + ",messageMd5:" + messageMd5;
		return flag;
	}

	private static String postUtil(Map<String, String> params,String sms) {
		CloudAccount account = new CloudAccount(AccessId, AccessKey, MNSEndpoint);
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(Topic);
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("消息内容:");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(SignName);
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();

		batchSmsAttributes.setTemplateCode(sms);
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (!StringUtil.isBlank(key) && !StringUtil.isBlank(value)) {
				smsReceiverParams.setParam(key, value);
			}
		}
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(params.get("mobile"), smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		String messageId = "";
		String messageMd5 = "";
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			messageId = ret.getMessageId();
			messageMd5 = ret.getMessageBodyMD5();
			System.out.println("messageId:" + messageId + ",messageMd5:" + messageMd5);
			client.close();
			return "messageId:" + messageId + ",messageMd5:" + messageMd5;
		} catch (ServiceException se) {
			se.printStackTrace();
			return "errorCode:" + se.getErrorCode() + ",ｒequestId,:" + se.getRequestId() + ",message:"
					+ se.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
		return "messageId:" + messageId + ",messageMd5:" + messageMd5;
	}

	public static void main(String[] args) {
		String name = "王测试";
		String phone = "15801356021";
		// String adminPhone =
		// PropertiesConfigUtils.getProperty("application-test",
		// "notice.phone");
		// if(!"".equals(adminPhone)) {
		// String[] phones = adminPhone.split(",");
		// for(String p : phones) {
		// if(!"".equals(p)) {
		// System.out.println("测试："+p);
		// 短信发送
//		String str = "有新用户通过邮件方式，发送邮件到邮件，请与销售沟通，尽快确认邮箱用户信息，并新增用户（操作流程），该邮箱号：xxxx.com。";
		SMSUtil.sendRegisterCode ("15951884250","123456");
		// }
		// }
		// }
//		SMSUtil.sendEmail(phone, "111","222");
	}
}
