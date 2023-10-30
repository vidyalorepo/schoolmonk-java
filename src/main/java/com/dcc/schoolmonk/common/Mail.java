package com.dcc.schoolmonk.common;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;

public class Mail {

	private static final Logger logger = Logger.getLogger(Mail.class);

	public static void sendMail(String from, String pass, String to, List<String> cc, String subject, String body,
			String it_team, String note) {

		Properties props = System.getProperties();
		// String host = "smtp.gmail.com";
		String host = ExtPropertyReader.host;
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));

//			InternetAddress[] toAddress = new InternetAddress[to.length()];
//			// To get the array of addresses
//			for (int i = 0; i < to.length(); i++) {
//				toAddress[i] = new InternetAddress(to);
//			}
//			for (int i = 0; i < toAddress.length; i++) {
//				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//			}

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // changed by Arijit Sarkar
			// message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			if (null != cc && cc.size() > 0) {
				for (String strCC : cc) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(strCC));
				}
			}

			// Custom body Begin
			StringBuffer sbf = new StringBuffer();

			sbf.append("<br>" + body);
			sbf.append("<br>");
			// sbf.append("\n"+url_pass+token+url_trail+"\n");
			// System.out.println("http://localhost:8080/users/tokenVerification?tokenid="+token
			// );
			sbf.append("<br>Regards,");
			sbf.append("<br>");
			sbf.append(it_team);
			sbf.append("<br>");
//			sbf.append(note);
			// body = body + intentNameF;
			//// Custom body End

			message.setSubject(subject);

			message.setContent(sbf.toString(), "text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("Email sent successfully.");
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void sendMailWithAttachment(String from, String pass, String to, List<String> cc, String subject,
			String body, String it_team, String note, String attachFileName) {

		Properties props = System.getProperties();
		// String host = "smtp.gmail.com";
		String host = ExtPropertyReader.host;
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));

//			InternetAddress[] toAddress = new InternetAddress[to.length()];
//			// To get the array of addresses
//			for (int i = 0; i < to.length(); i++) {
//				toAddress[i] = new InternetAddress(to);
//			}
//			for (int i = 0; i < toAddress.length; i++) {
//				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//			}

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // changed by Arijit Sarkar
			// message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			if (cc.size() > 0) {
				for (String strCC : cc) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(strCC));
				}
			}

			message.setSubject(subject);

			// Custom body Begin
			StringBuffer sbf = new StringBuffer();

			sbf.append("<br>" + body);
			sbf.append("<br>");
			// sbf.append("\n"+url_pass+token+url_trail+"\n");
			// System.out.println("http://localhost:8080/users/tokenVerification?tokenid="+token
			// );
			sbf.append("<br>Regards,");
			sbf.append("<br>");
			sbf.append(it_team);
			sbf.append("<br>");
			sbf.append(note);
			// body = body + intentNameF;
			//// Custom body End

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(subject);

			// attachment
			MimeBodyPart messageAttachment = new MimeBodyPart();
			String filename = attachFileName;
			DataSource source = new FileDataSource(filename);
			messageAttachment.setDataHandler(new DataHandler(source));
			messageAttachment.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(messageAttachment);

			// message.setContent(sbf.toString(), "text/html");
			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("Email sent successfully.");
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	public static void sendMailWithAttachment1(String from, String pass, List<String> to, List<String> cc, String subject,
			String body, String it_team, String note, String attachFileName) {

		Properties props = System.getProperties();
		// String host = "smtp.gmail.com";
		String host = ExtPropertyReader.host;
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));

//			InternetAddress[] toAddress = new InternetAddress[to.length()];
//			// To get the array of addresses
//			for (int i = 0; i < to.length(); i++) {
//				toAddress[i] = new InternetAddress(to);
//			}
//			for (int i = 0; i < toAddress.length; i++) {
//				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//			}

			if (to.size() > 0) {
				for (String strTO : to) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(strTO));
				}
			}
			
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // changed by Arijit Sarkar
			// message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			if (cc.size() > 0) {
				for (String strCC : cc) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(strCC));
				}
			}

			message.setSubject(subject);

			// Custom body Begin
			StringBuffer sbf = new StringBuffer();

			sbf.append("<br>" + body);
			sbf.append("<br>");
			// sbf.append("\n"+url_pass+token+url_trail+"\n");
			// System.out.println("http://localhost:8080/users/tokenVerification?tokenid="+token
			// );
			sbf.append("<br>Regards,");
			sbf.append("<br>");
			sbf.append(it_team);
			sbf.append("<br>");
			sbf.append(note);
			// body = body + intentNameF;
			//// Custom body End

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(subject);

			// attachment
			MimeBodyPart messageAttachment = new MimeBodyPart();
			String filename = attachFileName;
			DataSource source = new FileDataSource(filename);
			messageAttachment.setDataHandler(new DataHandler(source));
			messageAttachment.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(messageAttachment);

			// message.setContent(sbf.toString(), "text/html");
			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("Email sent successfully.");
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

}
