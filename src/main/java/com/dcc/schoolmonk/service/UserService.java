package com.dcc.schoolmonk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.Password;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.config.JwtTokenUtil;
import com.dcc.schoolmonk.dao.PasswordResetDao;
import com.dcc.schoolmonk.dao.RoleBaseAccessDao;
import com.dcc.schoolmonk.dao.RoleMstDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.StudentDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.dao.UserRoleMappingDao;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.vo.LoginDto;
import com.dcc.schoolmonk.vo.ResetPasswordVo;
import com.dcc.schoolmonk.vo.RoleBaseAccessVo;
import com.dcc.schoolmonk.vo.SendsmsRecipientsVO;
import com.dcc.schoolmonk.vo.SendsmsVo;
import com.dcc.schoolmonk.vo.SignUpDto;
import com.dcc.schoolmonk.vo.SubcriptionMstVo;
import com.dcc.schoolmonk.vo.UserRoleMappingVo;
import com.dcc.schoolmonk.vo.UserVo;

//git
@Service
public class UserService {

	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	PasswordResetDao passwordResetDao;

	@Autowired
	RoleBaseAccessDao roleBaseAccessDao;

	@Autowired
	MailService mailService;

	@Autowired
	UserRoleMappingDao userRoleMappingDao;

	@Autowired
	RoleMstDao roleMstDao;

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	StudentDao studentDao;

	@Autowired
	SendsmsService sendsmsService;

	public ResponseEntity<ApiResponse> register(SignUpDto vo) {
		UserVo user = new UserVo();
		ApiResponse apiResponse = null;
		
		List<UserVo> userDB = userDao.checkUserActive(vo.getEmail(),vo.getPhone());
		
		try {
			//Changed by Mouli
			if(userDB.isEmpty() ){
				String otp = String.valueOf(UniqueIDGenerator.OTP(6));
				LOGGER.info("otp ------------- " + otp);
				user.setEmail(vo.getEmail());
				user.setCountryCode(vo.getCountryCode());
				user.setOtp(otp);
				user.setPhone(vo.getPhone());
				user.setIsActive(false);
				// set username and dadid for JWT token, these two must be same, otherwise not
				// working
				/*
				 * String id = vo.getEmail().split("@")[0] + otp;
				 * user.setDadId(id);
				 * user.setUsername(id);
				 */
				user.setOtpExpiryTime(System.currentTimeMillis() + 480000);
				userDao.save(user);

				// *****send mail with OTP************
				/*StringBuilder mailBody = new StringBuilder();
				String subject = ExtPropertyReader.registerInitSubject;
				String mainBody = ExtPropertyReader.registerInitBody;
				mailBody.append(mainBody.replace("<otp>", otp));
				mailService.sendMail(vo.getEmail(), subject, mailBody.toString(), null);*/
				
				// *****Send Mail with submit otp page url************
				    StringBuilder mailBody = new StringBuilder();
					String subject = ExtPropertyReader.submitOtpSubject;
					String submitOtpUrl=ExtPropertyReader.submitOtpUrl;
					String mainBody = ExtPropertyReader.submitOtpBody;
					mailBody.append(mainBody.replace("<enterotpurl>", submitOtpUrl));
					mailService.sendMail(vo.getEmail(), subject, mailBody.toString(), null);
				//********************************************************************* */	

				//*************Send Otp in Mobile*********************
					String phoneString="91"+vo.getPhone();
					sendotpMobile(phoneString,vo.getEmail(),otp);
				//************************************************************ */	

				apiResponse = new ApiResponse(200, "registration initialized", "registration initialized", user); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}
			else {	
				apiResponse = new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.",
						user); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (DataIntegrityViolationException div) {
			LOGGER.error("DataIntegrityViolationException ---------- " + div.getMessage());
			user.setOtp(null);
			apiResponse = new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.",
					user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "registration not initialized", "registration init un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	public ResponseEntity<ApiResponse> validateOTP(SignUpDto vo) {
		UserVo user = new UserVo();
		ApiResponse apiResponse = null;
		try {
			// get user information by email
			UserVo userDB = userDao.findByEmail(vo.getEmail());
			if (vo.getOtp().equals(userDB.getOtp())) {
//				userDB.setOtp(null);
				userDao.save(userDB);
				apiResponse = new ApiResponse(200, "OTP_VALID", "validation successful", userDB); // Added

				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(406, "OTP_NOT_VALID", "Invalid OTP", user); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "registration not initialized", "validation un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
    public void sendotpMobile(String phone ,String Email,String otp){
					SendsmsVo smsvo=new SendsmsVo();
					smsvo.setFlow_id("63295392d4b7dd606f07b7b2");
					smsvo.setSender("VDYALO");
					SendsmsRecipientsVO sendsmsRecvo=new SendsmsRecipientsVO();
					sendsmsRecvo.setFclass1(otp);
					sendsmsRecvo.setTclass("Vi");
					sendsmsRecvo.setMobiles(phone);
					sendsmsRecvo.setSession("2023-2024");
					sendsmsRecvo.setUrl("vidyalo.com");
					List<SendsmsRecipientsVO> recipients = new ArrayList<SendsmsRecipientsVO>();
					recipients.add(0, sendsmsRecvo);
					smsvo.setRecipients(recipients);
		String response=sendsmsService.consumeAPI(smsvo);
		LOGGER.info("OTP SEND RESPONSE STATUS : " + response);
	}
	public ApiResponse signUp(SignUpDto signUpDto) {
		UserVo user = userDao.findByEmail(signUpDto.getEmail());
		try {

			// validateSignUp(signUpDto);
			// can use Bcrypt
			BeanUtils.copyProperties(user, signUpDto);

			String base64passworddecrypt = new String(signUpDto.getPassword());
			String encryptedPassword = Password.getSaltedHash(base64passworddecrypt);
			user.setPassword(encryptedPassword);
			user.setIsActive(true);
			user.setOtp(null);
			userDao.save(user);

			// get roleId for student in DB
			String roleId = roleMstDao.getRoleIdByName("STUDENT_USER");
			if (null != roleId) {
				// save a data in UserRoleMappingVo
				UserRoleMappingVo userRoleMappingVo = new UserRoleMappingVo();
				userRoleMappingVo.setRoleId(Long.parseLong(roleId));
				userRoleMappingVo.setUserId(user.getUserId());

				// Added for Subscription
				if (signUpDto.getSubscriptionId() != null) {
					SubcriptionMstVo vo = new SubcriptionMstVo();
					vo.setId(signUpDto.getSubscriptionId());
					userRoleMappingVo.setSubcriptionMstVo(vo);
				} else {
					userRoleMappingVo.setSubcriptionMstVo(null);
				}

				userRoleMappingDao.save(userRoleMappingVo);
				LOGGER.info("DATA SAVED in UserRoleMappingVo : " + userRoleMappingVo);

				// By new design, student is not a user anymore, they are child of user data
				/*
				 * StudentMstVo vo = new StudentMstVo();
				 * vo.setCreatedBy(user.getUserId());
				 * vo.setUpdatedBy(user.getUserId());
				 * vo.setStudentUser(user);
				 * vo.setStudentName(user.getFirstName() + " " + user.getLastName());
				 * vo.setDateOfBirth(user.getDateOfBirth());
				 * StudentMstVo studentSaved = studentDao.save(vo);
				 * LOGGER.info("StudentMstVo saved from signUp--------------- " + studentSaved);
				 */
			}

			return new ApiResponse(200, "success", "Registration complete", user);

		} catch (DataIntegrityViolationException div) {
			LOGGER.error("DataIntegrityViolationException ---------- " + div.getMessage());
			return new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.", user);
			/*
			 * apiResponse = new ApiResponse(406, "duplicate_entry",
			 * "User already exist with this email or phone number.", user); // Added
			 * return new ResponseEntity<ApiResponse>(apiResponse,
			 * HttpStatus.NOT_ACCEPTABLE);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(400, "failure", "UserId/Password Mismatch", user);
		}
	}

	// public ResponseEntity<UserVo> login(LoginDto loginDto, HttpSession session) {
	public ResponseEntity<ApiResponse> login(LoginDto loginDto, HttpSession session) {

		LOGGER.info("Login::Entering");

		ApiResponse apiResponse = null; // Added
		boolean checkPassword = false;
		// UserVo user = userDao.findByUsername(loginDto.getUsername());
		String email = "";
		String phone = "";
		if (null != loginDto.getUsername() && loginDto.getUsername().contains("@")) {
			email = loginDto.getUsername();
		} else {
			phone = loginDto.getUsername();
		}
		UserVo user = userDao.findByIsActiveAndEmailOrPhone(true, email, phone);

		if (user == null) {
			apiResponse = new ApiResponse(401, "user_not_exist", "User doesn't exist in System", null); // Added
			// return new ResponseEntity<UserVo>(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}

		// String passwordfromui = new
		// String(Base64.decodeBase64(loginDto.getPassword()));
		// LOGGER.info("Login::Password::" + passwordfromui);

		try {

			checkPassword = Password.check(loginDto.getPassword(), user.getPassword());

		} catch (Exception e) {
			// LOGGER.info("Login::Password check expeption::" + e);
			e.printStackTrace();
		}

		if (!checkPassword) {
			// return new ResponseEntity<UserVo>(HttpStatus.UNAUTHORIZED);
			apiResponse = new ApiResponse(401, "Wrong_UserName_Password", "User ID and/or Password Invalid", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}

		// GET DATA FROM USER_ROLE_MAPPING
		user.setUserType(userDao.getRoleOfUser(user.getUserId()));
		LOGGER.info("Login::UserVo set in session::" + user);
		if (null != user.getUserType() && user.getUserType().equals("STUDENT_USER")) {
			/*
			 * StudentMstVo vo = new StudentMstVo();
			 * vo.setCreatedBy(user.getUserId());
			 * vo.setUpdatedBy(user.getUserId());
			 * vo.setStudentUser(user);
			 * StudentMstVo studentSaved = studentDao.save(vo);
			 * LOGGER.info("StudentMstVo saved from LOGIN--------------- "+studentSaved);
			 */

			// user.setStudentId(studentDao.getStudentIdByUserId(user.getUserId()));
		}

		final String token = jwtTokenUtil.generateToken(user);

		LOGGER.info("UserService::token::token --> " + token);
		user.setJwtToken(token);

		session.setAttribute("USER_VO", user);

		LOGGER.info("UserService::Login::Exiting");

		// return new ResponseEntity<UserVo>(user, HttpStatus.OK);
		apiResponse = new ApiResponse(200, "Login_Success", "Login Successful", user);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ApiResponse findsubscription(String user_id){

		ApiResponse apiResponse = null;
	     UserRoleMappingVo usersub=new UserRoleMappingVo();
		 usersub=userRoleMappingDao.findsubscriptionid(user_id);
		

		 return new ApiResponse(200, "Fetch  Successful", "Subscription Fetch Successful", usersub);
		
	}

	public UserVo findById(Long id) {

		LOGGER.info("UserService::findById::Entering :: Id ::" + id);

		UserVo userVo = (userDao.findById(id)).get();

		LOGGER.info("UserService::getUserByUsername::Exiting::");

		return userVo;
	}

	public String getEmployeeName(long id) {

		UserVo userVo = (userDao.findById(id)).get();
		return userVo.getFirstName() + " " + userVo.getLastName();

	}

	public UserVo getUserDetails(HttpServletRequest request, String jwtToken) {
		LOGGER.info("UserService:: getUserDetails:: Entering ");

		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_VO");

		LOGGER.info("UserService:: getUserDetails:: UserVo:: " + userVo);

		if (userVo == null) {
			// userVo = userDao.findByJwtToken(jwtToken);
			String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			// userVo = userDao.findByUsername(userName);
			userVo = userDao.findByIsActiveAndEmail(true, userName);
		}

		LOGGER.info("UserService:: getUserDetails:: Exiting:: UserVo" + userVo);
		return userVo;
	}

	public String checkPermission(String jwtToken, String roleName) {
		LOGGER.info("UserService:: checkPermission:: Entering ");

		String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);

		String returnVal = userDao.checkPermission(userName, roleName);

		return returnVal;
	}

	public UserVo findUserByEmail(String email) {

		LOGGER.info("findUserByEmail::UserVo email::" + email);
		UserVo user = userDao.findByIsActiveAndEmail(true, email);

		return user;
	}

	// public ApiResponse update(UserVo userVo) {
	//
	// userDao.save(userVo);
	//
	// return new ApiResponse(200, "success", "Login Successful", userVo);
	// }

	public Optional<UserVo> findUserByResetToken(String token) {

		Optional<UserVo> userVo = userDao.findByResetToken(token);

		return userVo;
	}

	// User Role
	// public List<String> checkAdmin(long userKey) {
	//
	// List<String> adminCheck = userDao.getAllAdmin(userKey);
	//
	// return adminCheck;
	// }

	public UserVo update(UserVo resetUser) {
		LOGGER.info("UserService:: Update :: Entering");

		UserVo user = null;

		user = userDao.save(resetUser);

		LOGGER.info("UserService:: Update :: Exiting");

		// apiResponse = new ApiResponse(200, "success", "Password Reset Successfully ",
		// user);
		// return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		return user;
	}

	public int updatePassword(UserVo resetUser) {
		LOGGER.info("UserService:: updatePassword :: Entering");

		int result = 0;

		result = userDao.updatePassword(resetUser.getUserId(), resetUser.getPassword());

		LOGGER.info("UserService:: updatePassword :: Exiting");

		return result;

	}

	public RoleBaseAccessVo roleBaseAccess(long userKey) {
		LOGGER.info("UserService:: getRoleName :: Entering");

		RoleBaseAccessVo roleBaseAccessVo = null;
		try {
			roleBaseAccessVo = roleBaseAccessDao.getRoleBaseAccess(userKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("UserService:: getRoleName :: Exiting");

		return roleBaseAccessVo;
	}

	public UserVo findByUserId(long userId) {
		LOGGER.info("UserService:: findByUserId :: Entering");

		UserVo userVo = userDao.findByUserId(userId);

		return userVo;
	}

	public Iterable<UserVo> getAllUser() {
		LOGGER.info("UserService:: getAllUser :: Entering");

		Iterable<UserVo> userVo = userDao.findAll();

		return userVo;
	}

	public ResponseEntity<ApiResponse> updatePwd(SignUpDto vo) {
		ApiResponse apiResponse = null;
		try {
			// get user information by email
			UserVo userDB = userDao.findByEmail(vo.getEmail());
			if (vo.getOtp().equals(userDB.getOtp())) {
				String base64passworddecrypt = new String(vo.getPassword());
				String encryptedPassword = Password.getSaltedHash(base64passworddecrypt);
				userDao.updatePassword(userDB.getUserId(), encryptedPassword);
				apiResponse = new ApiResponse(200, "update_pwd_success", "Password set Successful", userDB);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(406, "OTP_NOT_VALID", "OTP not validated", null); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(400, "error", "Password set error", null);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	@Transactional
	public ResponseEntity<ApiResponse> createAdminUser(UserVo vo, Long createdByUserId) {
		ApiResponse apiResponse = null;
		UserVo user = new UserVo();
		// vo.setCreatedBy(createdByUserId);
		try {
			// Save in User table
			user.setFirstName(vo.getFirstName());
			user.setLastName(vo.getLastName());
			user.setEmail(vo.getEmail());
			user.setPhoneCountryCode(vo.getPhoneCountryCode());
			user.setPhone(vo.getPhone());
			String otp = null;
			UUID uuid = null;
			UserVo userSaved = userDao.save(user);
			String roleId = roleMstDao.getRoleIdByName("ADMIN_USER");
			if (null != roleId) {
					// save a data in UserRoleMappingVo
					UserRoleMappingVo userRoleMappingVo = new UserRoleMappingVo();
					userRoleMappingVo.setRoleId(Long.parseLong(roleId));
					userRoleMappingVo.setUserId(user.getUserId());
					// userRoleMappingVo.setSchoolId(schoolSaved.getId());
					userRoleMappingDao.save(userRoleMappingVo);
					LOGGER.info("DATA SAVED in UserRoleMappingVo : " + userRoleMappingVo);
				}
			LOGGER.info("Admin User Saved --------------- " + userSaved);

					// Generate random 36-character string token for reset password
					userSaved.setResetToken(UUID.randomUUID().toString());

					// Save token to database
					userSaved.setOtpExpiryTime(System.currentTimeMillis() + 480000);
					update(userSaved);
	
					// Email message
					String emailTo = userSaved.getEmail();
	
					StringBuilder mainResetPasswordBody = new StringBuilder();
	
					String subject = ExtPropertyReader.forgotPasswordSubject;
					String mainBody = ExtPropertyReader.forgotPasswordBody;
	
					String appUrl = ExtPropertyReader.forgotPasswordUrl;
					String resetPasswordUrl = appUrl + userSaved.getResetToken();
	
					mainResetPasswordBody.append(mainBody.replace("<FirstName>", userSaved.getFirstName())
							.replace("<LastName>", userSaved.getLastName())
							.replace("<ResetPassword>", resetPasswordUrl));
	
					StringBuilder passwordResetLinkBody = new StringBuilder();

			 mailService.sendMail(emailTo, subject, mainResetPasswordBody.toString(), null);

			apiResponse = new ApiResponse(200, "registration initialized", "registration initialized", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (DataIntegrityViolationException div) {
			LOGGER.error("DataIntegrityViolationException ---------- " + div.getMessage());
			user.setOtp(null);
			apiResponse = new ApiResponse(406, "duplicate_entry", "User already exist with this email or phone number.",
					user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "registration not initialized", "registration init un-successful", user); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> sendResetPasswordLink(ResetPasswordVo resetPasswordVo) {
		ApiResponse apiResponse = null;
		UserVo userVo = findUserByEmail(resetPasswordVo.getEmail());

		if (userVo != null) {

			try {
				// Generate random 36-character string token for reset password
				userVo.setResetToken(UUID.randomUUID().toString());

				// Save token to database
				userVo.setOtpExpiryTime(System.currentTimeMillis() + 480000);
				update(userVo);

				// Email message
				String emailTo = userVo.getEmail();

				StringBuilder mainResetPasswordBody = new StringBuilder();

				String subject = ExtPropertyReader.forgotPasswordSubject;
				String mainBody = ExtPropertyReader.forgotPasswordBody;

				String appUrl = ExtPropertyReader.forgotPasswordUrl;
				String resetPasswordUrl = appUrl + userVo.getResetToken();

				mainResetPasswordBody.append(mainBody.replace("<FirstName>", userVo.getFirstName())
						.replace("<LastName>", userVo.getLastName())
						.replace("<ResetPassword>", resetPasswordUrl));
				// String resetPasswordLinkName = ExtPropertyReader.forgotPasswordLinkName;
				// String resetPasswordUrl = ExtPropertyReader.forgotPasswordUrl;

				// String appUrl = "http://localhost:4200/#/auth/forget?token=" +
				// userVo.getResetToken();

				StringBuilder passwordResetLinkBody = new StringBuilder();
				// passwordResetLinkBody.append("<br><a href=\"" + resetPasswordUrl + "\">Reset
				// Password</a><br><br>");
				// passwordResetLinkBody.append("<br><a href=\"" + resetPasswordUrl + "\">Reset
				// Password</a><br><br>");

				// passwordResetLinkBody
				// .append(resetPasswordUrl + "<a href=\"" + appUrl + "\">Reset
				// Password</a><br><br>");

				// mainResetPasswordBody.append(passwordResetLinkBody.toString());
				// Send Mail
				mailService.sendMail(emailTo, subject, mainResetPasswordBody.toString(), null);

				apiResponse = new ApiResponse(200, "Success", "Reset Password Link Send Successfully", ""); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			apiResponse = new ApiResponse(400, "Error", "Employee does not exist", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		apiResponse = new ApiResponse(500, "error", "Internal Server Error", null);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    public ResponseEntity<ApiResponse> updateUserStatusrByuserId(long user_id, Boolean status) {
		ResponseEntity<ApiResponse> responseEntity = null;
             UserVo uservo=userDao.findById(user_id).orElseThrow(()-> new ResourceNotFound(String.format("User not found with id : %s", user_id)));

			try {
				userDao.updateuserStatus(user_id,status);
             uservo=userDao.findById(user_id).orElseThrow(()-> new ResourceNotFound(String.format("User not found with id : %s", user_id)));
				responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "User  Status Updated", uservo),
                    HttpStatus.OK);    
                
			} catch (Exception e) {
			
				e.printStackTrace();
				responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			 }


        return responseEntity;
    }
}
