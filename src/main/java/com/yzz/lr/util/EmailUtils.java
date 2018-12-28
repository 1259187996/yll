package com.yzz.lr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 邮箱服务
 */
public class EmailUtils {

	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	private static String hostName = "110"; // 主邮箱的名称，如：QQ客服

	private static String hostEmail = "110@codeflagai.com"; // 发送邮件的邮箱（主邮箱）地址，如：inigo_liu@163.com

	private static String userName = "110@codeflagai.com"; // 主邮箱用户名，如：inigo_liu

	private static String password = "ABc123456"; // 主邮箱密码,如：123456

	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private static final String ALIDM_SMTP_HOST = "smtp.mxhichina.com";

	private static final int ALIDM_SMTP_PORT = 80;//或80

	public static void sendPoliceMail(final String subject,final String content,final String emailList) {
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				// 配置发送邮件的环境属性
				final Properties props = new Properties();
				// 表示SMTP发送邮件，需要进行身份验证
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.host", ALIDM_SMTP_HOST);
				props.put("mail.smtp.port", ALIDM_SMTP_PORT);
				// 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
				 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				 props.put("mail.smtp.socketFactory.port", "465");
				 props.put("mail.smtp.port", "465");
				// 发件人的账号
				props.put("mail.user", userName);
				// 访问SMTP服务时需要提供的密码
				props.put("mail.password", password);
				// 构建授权信息，用于进行SMTP进行身份验证
				Authenticator authenticator = new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// 用户名、密码
						String userName = props.getProperty("mail.user");
						String password = props.getProperty("mail.password");
						return new PasswordAuthentication(userName, password);
					}
				};
				// 使用环境属性和授权信息，创建邮件会话
				Session mailSession = Session.getInstance(props, authenticator);
//        mailSession.setDebug(true);
				// 创建邮件消息
				MimeMessage message = new MimeMessage(mailSession);
				try {
					// 设置发件人
					InternetAddress from = new InternetAddress(hostEmail);
					message.setFrom(from);
					Address[] a = new Address[1];
					a[0] = new InternetAddress(hostName);
					message.setReplyTo(a);
					// 设置收件人
					if (emailList.indexOf(",") > -1) {
						String[] emails = emailList.split(",");
						InternetAddress[] tos = new InternetAddress[emails.length];
						for (int i = 0; i < emails.length; i++) {
							tos[i] = new InternetAddress(emails[i]);
						}
						message.setRecipients(MimeMessage.RecipientType.TO, tos);
					} else {
						InternetAddress to = new InternetAddress(emailList);
						message.setRecipient(MimeMessage.RecipientType.TO, to);
					}
					// 设置邮件标题
					message.setSubject(subject);
					// 设置邮件的内容体
					message.setContent(content, "text/html;charset=UTF-8");
					// 发送邮件
					Transport.send(message);

					logger.info("发送成功:" + emailList);
				} catch (MessagingException e) {
					e.printStackTrace();
					String err = e.getMessage();
					// 在这里处理message内容， 格式是固定的
					System.out.println(err);
				}
			}
		});
	}

	public static void main(String[] args) {
		sendPoliceMail("测试邮件","内容","1259187996@qq.com");
	}
}
