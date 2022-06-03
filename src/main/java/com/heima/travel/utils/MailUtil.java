package com.heima.travel.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

/**
 * 发送邮件工具类
		 */
public final class MailUtil {
	private MailUtil(){}
	/**
	 * 发送邮件
	 * 参数一:发送邮件给谁
	 * 参数二:发送邮件的内容
	 */
	public static void sendMail(String toEmail, String emailMsg) {
		//1_创建Java程序与163邮件服务器的连接对象
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.163.com");
		props.put("mail.smtp.auth", "true");
		//创建第三方邮箱登录时的认证对象
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("xx34029067@163.com", "RXANCQLJWDPQXKAK");
			}
		};
		Session session = Session.getInstance(props, auth);
		//2_创建一封邮件
		Message message = new MimeMessage(session);
		try {
			//设置邮箱发送的地址，即发送者
			message.setFrom(new InternetAddress("xx34029067@163.com"));
			//设置接收信息的邮箱地址，即接收者
			message.setRecipient(RecipientType.TO, new InternetAddress(toEmail));
			//设置邮箱的主题
			message.setSubject("用户激活");
			//设置邮箱的正文
			message.setContent(emailMsg, "text/html;charset=UTF-8");
			//3_发送邮件
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("邮件发送失败");
		}
	}
	/**
	 * 测试类
	 */
	public static void main(String[] args) throws Exception{
		String toEmail = "34029067@qq.com";
		String emailMsg = "测试一下";
		sendMail(toEmail,emailMsg);
		System.out.println("发送成功。。。");
	}
}








