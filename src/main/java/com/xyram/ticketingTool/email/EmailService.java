package com.xyram.ticketingTool.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.xyram.ticketingTool.service.FileService;


@Service
public class EmailService {
	
	@Autowired
	FileService fileService;
	
	private TemplateEngine templateEngine;

	private final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	public String senderEmail = "support@telelinks.com";
	public String password = "SupportMail@0";

	public Map<Object,Object> sendMail(Map<Object,Object> mailDetails) {
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		
		templateResolver.setPrefix("/templates/emails/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Map<Object,Object> response = new HashMap<Object,Object>();
		
		logger.info(".sendMail()");
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.zoho.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug.auth", "true");
        props.setProperty( "mail.pop3.socketFactory.fallback", "false");

		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, password);
			}
		});
		MimeMessage message = null;
	
		try {

			Context context = new Context();
			context.setVariable("message", mailDetails.get("message"));
			
			String process = templateEngine.process("email", context);
			message = new MimeMessage(session);
			
		    message.setFrom(new InternetAddress("support@telelinks.com"));
          
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailDetails.containsKey("toEmail") ? mailDetails.get("toEmail").toString() : null));
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setSubject(mailDetails.containsKey("subject") ? mailDetails.get("subject").toString() : null);
			helper.setText(process, true);
			ClassPathResource resource = new ClassPathResource("/static/images/pictureLogo.png");
			helper.addInline("pictureLogo", resource);
			Transport.send(message);
			logger.info("message sent successfully");
			response.put("status", HttpStatus.OK);
			response.put("message", "Mail Sent Successfully to: " + mailDetails.get("toEmail"));
			return response;
		} catch (MessagingException e) {
			logger.error("Could not send Email to: " + mailDetails.get("toEmail"));
			response.put("status", HttpStatus.METHOD_FAILURE);
			response.put("message", "Could not send Email to: " + mailDetails.get("toEmail"));
			response.put("Exception", e);
			return response;
		}

	}

//	public static void main(String[] args) {
//		EmailService emailService = new EmailService();
//		String sub = "Account Created";
//		String msg = "Dear Name,\r\n" + "\r\n" + "Your account created successfully!\r\n" + "\r\n"
//				+ "Below are account details:-\r\n" + "\r\n" + "Email:- \r\n" + "Password:-\r\n" + "\r\n" + "\r\n"
//				+ "Thanks and Regards,\r\n" + "Xyram Software Solutions Pvt Ltd.";
//		emailService.sendMail("chandinisundar0317@gmail.com", sub, msg);
//	}

}