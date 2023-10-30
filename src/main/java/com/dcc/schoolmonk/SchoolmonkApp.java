package com.dcc.schoolmonk;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.dcc.schoolmonk.common.FileStorageProperties;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SchoolmonkApp {

	private static final Logger logger = Logger.getLogger(SchoolmonkApp.class);

	@Bean
	ExtPropertyReader extPropertyReader() {
		return new ExtPropertyReader();
	}

	@Bean
	FileStorageProperties fileStorageProperties() {
		return new FileStorageProperties();
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SchoolmonkApp.class, args);
		ExtPropertyReader extPropertyReader = context.getBean(ExtPropertyReader.class);
		extPropertyReader.findPath();
		extPropertyReader.getKeys();
		extPropertyReader.getEmailProperties();
		logger.error("<<---------------------Service started------------------------->>");
	}

	public static class ExtPropertyReader {

		public static String EXTERNAL_PATH = null;
		public static String bucketName = null;
		public static String workflowBaseUrl = null;
		public static String accessKey = null;
		public static String secretKey = null;
		private static String oS = null;
		private static String[] oSnameVersion = null;
		private static String osnameOnly = null;

		public static String userName = null;
		public static String password = null;
		public static String host = null;

		public static String forgotPasswordSubject = null;
		public static String forgotPasswordBody = null;
		public static String forgotPasswordLinkName = null;
		public static String forgotPasswordUrl = null;

		public static String registerInitSubject = null;
		public static String registerInitBody = null;

		public static String updatePwdSubject = null;
		public static String updatePwdBody = null;
		
		public static String emailToDcc = null;
		public static String newSchoolAddSubject = null;
		public static String newSchoolAddBody = null;

		public static String bucket = null;

		public static String itTeam = null;
		public static String note = null;

		public static String submitOtpSubject=null;
		public static String submitOtpBody=null;
		public static String submitOtpUrl=null;

		// public static String helpDaskIssueSubject=null;
		// public static String helpDaskMailBody=null;

		private String findPath() {

			if (oS == null) {

				oS = System.getProperty("os.name");

				System.out.println("Print Opertaing System:::" + oS);

				oSnameVersion = oS.toString().split("\\s+");
				osnameOnly = oSnameVersion[0].trim();

				if (osnameOnly.equals("Windows")) {

					EXTERNAL_PATH = "c:/path/";

				} else {
					EXTERNAL_PATH = "/opt/path/";
				}
			}
			return EXTERNAL_PATH;

		}

		private void getKeys() {

			FileReader filereader = null;
			try {
				filereader = new FileReader(EXTERNAL_PATH + "SchoolmonkApp.properties");
				Properties prop = new Properties();
				prop.load(filereader);

				bucketName = prop.getProperty("aws_bucket"); // THIS BUCKET IS NOT IN USE NOW --- PS
				workflowBaseUrl = prop.getProperty("camunda_base_url");

				// accessKey = Optional.ofNullable(System.getenv("aws_access_key_id"))
				// .orElseThrow(() -> new Exception("AccessKey is not set in the environment"));
				// secretKey = Optional.ofNullable(System.getenv("aws_secret_access_key"))
				// .orElseThrow(() -> new Exception("SecretKey is not set in the environment"));

				// logger.info("accessKey--" + accessKey);
				// logger.info("secretAccessKey--" + secretKey);

			} catch (Exception e) {
				logger.info("RiskRater :: ExtPropertyReader :: getKeys :: Error : " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					filereader.close();
				} catch (IOException e) {
					logger.info("RiskRater :: ExtPropertyReader :: getKeys :: Error closing filereader \n : "
							+ e.getMessage());
				}
			}

		}

		private void getEmailProperties() {

			FileReader filereader = null;
			try {
				filereader = new FileReader(EXTERNAL_PATH + "SchoolmonkApp.properties");

				Properties prop = new Properties();
				prop.load(filereader);

				userName = prop.getProperty("User_Name");
				password = prop.getProperty("Password");
				host = prop.getProperty("Host");
				itTeam = prop.getProperty("IT_Team_Admin");
				note = prop.getProperty("Note");

				forgotPasswordSubject = prop.getProperty("reset_password_subject");
				forgotPasswordBody = prop.getProperty("reset_password_body");
				// forgotPasswordLinkName = prop.getProperty("reset_password_link_name");
				forgotPasswordUrl = prop.getProperty("Forgot_Password_Url");

				registerInitSubject = prop.getProperty("register_init_subject");
				registerInitBody = prop.getProperty("register_init_body");
                
				//**************************submit otp***********************/
				submitOtpSubject=prop.getProperty("submit_otp_subject");
				submitOtpBody=prop.getProperty("submit_otp_body");
				submitOtpUrl=prop.getProperty("submit_otp_url");
				//*************************************************************/
				updatePwdSubject = prop.getProperty("update_pwd_subject");
				updatePwdBody = prop.getProperty("update_pwd_body");
				
				//Added by kousik --New school add notification to dcc email
				emailToDcc = prop.getProperty("email_to_dcc");
				newSchoolAddSubject = prop.getProperty("new_school_add_subject");
				newSchoolAddBody = prop.getProperty("new_school_add_body");
				//*******************************Help Dask Issue*********************************/
				// helpDaskIssueSubject=prop.getProperty("user_issue_subject");
				// helpDaskMailBody=prop.getProperty("user_issue_body");


				logger.info("schoolmonkApp :: ExtPropertyReader :: getEmailProperties :: User_Name--" + userName);
				logger.info("schoolmonkApp :: ExtPropertyReader :: updatePwdSubject::" + updatePwdSubject
						+ " :: updatePwdBody--" + updatePwdBody);
			} catch (Exception e) {
				logger.info("schoolmonkApp :: ExtPropertyReader :: getKeys :: Error : " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					filereader.close();
				} catch (IOException e) {
					logger.info("schoolmonkApp :: ExtPropertyReader :: getKeys :: Error closing filereader \n : "
							+ e.getMessage());
				}
			}

		}

	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
