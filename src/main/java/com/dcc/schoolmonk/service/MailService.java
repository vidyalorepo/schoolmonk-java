package com.dcc.schoolmonk.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;
import com.dcc.schoolmonk.common.Mail;

@Service
public class MailService {

	private static final Logger logger = Logger.getLogger(MailService.class);

	public void sendMail(String email, String token) {

		logger.info("User name for sending mail*****" + ExtPropertyReader.userName);

//		Mail.sendMail(ExtPropertyReader.userName, ExtPropertyReader.password, email, "", ExtPropertyReader.subject,
//				ExtPropertyReader.body, ExtPropertyReader.itTeam, ExtPropertyReader.note);

		// Don't delete the following code,this is used for SES
		/*
		 * try { SES.sendFromSES(ExtPropertyReader.userName, ExtPropertyReader.password,
		 * email, ExtPropertyReader.subject, ExtPropertyReader.body,
		 * ExtPropertyReader.itTeam, ExtPropertyReader.note, token,
		 * ExtPropertyReader.url_pass, ExtPropertyReader.url_trail); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	/*
	 * public void sendMail(String emailTo, String taskName, String token, String
	 * subject, String body, String emailCC) {
	 * 
	 * logger.info("MailService:: sendMail:: User name: " +
	 * ExtPropertyReader.userName);
	 * 
	 * // logger.info("Subject:" + ExtPropertyReader.subject); //
	 * logger.info("Body:" + ExtPropertyReader.body);
	 * 
	 * logger.info("MailService:: sendMail:: Subject: " + subject);
	 * logger.info("MailService:: sendMail:: Body: " + body);
	 * 
	 * try { Mail.sendMail(ExtPropertyReader.userName, ExtPropertyReader.password,
	 * emailTo, emailCC, subject, body, ExtPropertyReader.itTeamAdmin,
	 * ExtPropertyReader.note); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("MailService:: sendMail:: Exiting.");
	 * 
	 * // Don't delete the following code,this is used for SES
	 * 
	 * try { SES.sendFromSES(ExtPropertyReader.userName, ExtPropertyReader.password,
	 * email, ExtPropertyReader.subject, ExtPropertyReader.body,
	 * ExtPropertyReader.itTeam, ExtPropertyReader.note, token,
	 * ExtPropertyReader.url_pass, ExtPropertyReader.url_trail); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	public void sendMail(String emailTo, String subject, String body, List<String> emailCC) {

		logger.info("MailService:: sendMail:: User name: " + ExtPropertyReader.userName);

//		logger.info("Subject:" + ExtPropertyReader.subject);
//		logger.info("Body:" + ExtPropertyReader.body);

		logger.info("MailService:: sendMail:: Subject: " + subject);
		logger.info("MailService:: sendMail:: Body: " + body);

		try {
			Mail.sendMail(ExtPropertyReader.userName, ExtPropertyReader.password, emailTo, emailCC, subject, body,
					ExtPropertyReader.itTeam, ExtPropertyReader.note);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("MailService:: sendMail:: Exiting.");

		// Don't delete the following code,this is used for SES
		/*
		 * try { SES.sendFromSES(ExtPropertyReader.userName, ExtPropertyReader.password,
		 * email, ExtPropertyReader.subject, ExtPropertyReader.body,
		 * ExtPropertyReader.itTeam, ExtPropertyReader.note, token,
		 * ExtPropertyReader.url_pass, ExtPropertyReader.url_trail); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public void sendMailWithAttachment(String emailTo, String subject, String body, List<String> emailCC,
			String attachFileName) {

		logger.info("MailService:: sendMail:: User name: " + ExtPropertyReader.userName);

//		logger.info("Subject:" + ExtPropertyReader.subject);
//		logger.info("Body:" + ExtPropertyReader.body);

		logger.info("MailService:: sendMail:: Subject: " + subject);
		logger.info("MailService:: sendMail:: Body: " + body);

		try {
			Mail.sendMailWithAttachment(ExtPropertyReader.userName, ExtPropertyReader.password, emailTo, emailCC,
					subject, body, null, null, attachFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("MailService:: sendMail:: Exiting.");

		// Don't delete the following code,this is used for SES
		/*
		 * try { SES.sendFromSES(ExtPropertyReader.userName, ExtPropertyReader.password,
		 * email, ExtPropertyReader.subject, ExtPropertyReader.body,
		 * ExtPropertyReader.itTeam, ExtPropertyReader.note, token,
		 * ExtPropertyReader.url_pass, ExtPropertyReader.url_trail); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

}
