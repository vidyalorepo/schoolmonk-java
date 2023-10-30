package com.dcc.schoolmonk.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.Password;
import com.dcc.schoolmonk.service.MailService;
import com.dcc.schoolmonk.service.SchoolUserService;
import com.dcc.schoolmonk.service.SendsmsService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.LoginDto;
import com.dcc.schoolmonk.vo.ResetPasswordVo;
import com.dcc.schoolmonk.vo.RoleBaseAccessVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SendsmsVo;
import com.dcc.schoolmonk.vo.SignUpDto;
import com.dcc.schoolmonk.vo.UserFeVo;
import com.dcc.schoolmonk.vo.UserVo;

import io.jsonwebtoken.SignatureException;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.POST, RequestMethod.GET })

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	MailService mailService;

	@Autowired
	SchoolUserService schoolUserService;
	
	@Autowired
	SendsmsService sendsmsService;

	public static Set<UserFeVo> allUsers = new HashSet<UserFeVo>();

	// static {
	//
	// Iterable<UserVo> userVo = userService.getAllUser();
	//
	// if (userVo != null) {
	//
	// for (UserVo user : userVo) {
	// UserFeVo feVo = new UserFeVo();
	//
	// System.out.println("Name:: -->" +user.getFirstName());
	// feVo.setFullName(user.getFirstName() +" " + user.getLastName());
	// feVo.setEmpNameEmail(user.getFirstName() +" " + user.getLastName() +" (" +
	// user.getEmail() +"");
	// feVo.setEmployeeId(user.getEmployeeId());
	// feVo.setEmailId(user.getEmail());
	//
	// }
	//
	// }
	// }

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody SignUpDto signUpDto) {
		return userService.register(signUpDto);
	}

	@PostMapping("/validate")
	public ResponseEntity<ApiResponse> validateOTP(@RequestBody SignUpDto signUpDto) {
		return userService.validateOTP(signUpDto);
	}

	@PostMapping("/signup")
	public ApiResponse signUp(@RequestBody SignUpDto signUpDto) {
		return userService.signUp(signUpDto);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto, HttpSession session) {

		LOGGER.info("UserController:: login:: Entering");

		ResponseEntity<ApiResponse> responseEntity = userService.login(loginDto, session);
		return responseEntity;
	}
	
	@PostMapping("/subscription")
	public ApiResponse getsubscription(@RequestParam String User_id){

		ApiResponse apiResponse=userService.findsubscription(User_id);
		return apiResponse;
	}

	// For Role Base Access
	// @GetMapping("/rolebaseaccess/{userkey}")
	// public ResponseEntity<RoleBaseAccessVo> roleBaseAccess(@PathVariable(value =
	// "userkey") long userKey,
	// HttpServletRequest request) {

	@GetMapping("/rolebaseaccess")
	public ResponseEntity<RoleBaseAccessVo> roleBaseAccess(HttpServletRequest request) {

		LOGGER.info("UserController:: roleBaseAccess:: Entering");

		RoleBaseAccessVo roleBaseAccessVo = null;
		ResponseEntity<RoleBaseAccessVo> responseEntity = null;

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		try {
			roleBaseAccessVo = userService.roleBaseAccess(userVo.getUserId());

			responseEntity = new ResponseEntity<RoleBaseAccessVo>(roleBaseAccessVo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseEntity;
	}

	// @PostMapping("/sendResetPasswordMailLink")
	@RequestMapping(value = "/sendResetPasswordMailLink", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> sendResetPasswordMailLink(@RequestBody ResetPasswordVo resetPasswordVo,
			HttpServletRequest request) {

		return userService.sendResetPasswordLink(resetPasswordVo);
	}

	// Process to Validate The token
	@RequestMapping(value = "/validateToken", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> validateToken(@RequestParam(value = "token") String token,
			HttpServletRequest request) {

		LOGGER.info("UserController:: validateToken:: Entering");
		ApiResponse apiResponse = null;

		// Find the user associated with the reset token
		// Optional<UserVo> userVo = userService.findUserByResetToken(token);
		UserVo userVo = userService.findUserByResetToken(token).orElse(null);
		// This should always be non-null but we check just in case
		if (null != userVo && userVo.getOtpExpiryTime() >= System.currentTimeMillis()) {

			// UserVo resetUser = userVo.get();

			apiResponse = new ApiResponse(200, "success", "Token Successfully Validate", "");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

		} else {
			apiResponse = new ApiResponse(400, "errorMessage", "Sorry the link/token has been expired", "");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}

		// return responseEntity;
	}

	// Process reset password form
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordVo resetPasswordVo,
			HttpServletRequest request) {

		LOGGER.info("UserController:: resetPassword:: Entering");
		LOGGER.info("UserController:: resetPassword:: ResetPasswordToken:: " + resetPasswordVo.getToken());
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		Optional<UserVo> userVo = null;
		int result = 0;
		// Find the user associated with the reset token
		userVo = userService.findUserByResetToken(resetPasswordVo.getToken().trim());

		// This should always be non-null but we check just in case
		if (userVo.isPresent()) {

			UserVo resetUser = userVo.get();

			// Set new password

			try {
				LOGGER.info("UserController:: resetPassword:: Entering Try Block");

				LOGGER.info("UserController:: resetPassword:: Before calling the salted hash");
				String encryptedPassword = Password.getSaltedHash(resetPasswordVo.getPassword());

				LOGGER.info("UserController:: resetPassword:: after calling the salted hash");
				resetUser.setPassword(encryptedPassword);
				// Set the reset token to null so it cannot be used again
				resetUser.setResetToken(null);
				LOGGER.info("UserController:: resetPassword:: Token Set To Null");
				// Save user
				result = userService.updatePassword(resetUser);

				LOGGER.info("UserController:: resetPassword:: Exiting");
				if (result == 1) {
					apiResponse = new ApiResponse(200, "success", "Password Reset Successfully", "");
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
				} else {
					apiResponse = new ApiResponse(400, "success", "Password Didn't Set! Please Try Again", "");
					return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception exp) {
				// exp.printStackTrace();

				LOGGER.info("UserController:: resetPassword:: Catch Part :: If try block fails shows this logger ");
				apiResponse = new ApiResponse(400, "error", "The resert password link/token hasbeen expired", "");
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}

		} else {
			LOGGER.info("UserController:: resetPassword:: else Part :: If user not found shows this logger ");
			apiResponse = new ApiResponse(400, "error", "No User Found", "");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}

		// return responseEntity;
	}

	public ResponseEntity<Iterable<UserFeVo>> getAllEmployees(HttpServletRequest request) {

		LOGGER.info("UserController:: getAllEmployees:: Entering:: getAllEmployees");

		Iterable<UserVo> userVo = userService.getAllUser();

		if (allUsers == null || allUsers.size() == 0) {

			for (UserVo user : userVo) {

				UserFeVo feVo = new UserFeVo();

				feVo.setFullName(user.getFirstName() + " " + user.getLastName());
				feVo.setUserKey(user.getUserId());
				;

				allUsers.add(feVo);

			}
		}

		ResponseEntity<Iterable<UserFeVo>> responseEntity = new ResponseEntity<Iterable<UserFeVo>>(allUsers,
				HttpStatus.OK);

		LOGGER.info("UserController::getAllEmployees:" + "Employee Size::" + allUsers.size());

		return responseEntity;
	}

	@PostMapping("/updatePwd")
	public ResponseEntity<ApiResponse> updatePwd(@RequestBody SignUpDto signUpDto) {
		return userService.updatePwd(signUpDto);
	}

	@GetMapping("/validate")
	public ResponseEntity<String> validate(HttpServletRequest request) {

		LOGGER.info("UserController:: validate:: Entering");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		try {
			if (null != userVo) {
				return new ResponseEntity<String>("valid", HttpStatus.OK);
			}
		} catch (SignatureException sig) {
			LOGGER.info(sig.getMessage());
			return new ResponseEntity<String>("invalid", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// e.printStackTrace();
			return new ResponseEntity<String>("invalid", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>("invalid", HttpStatus.UNAUTHORIZED);
	}

	// Added By Akash
	@PostMapping("/activateUser")
	public ResponseEntity<ApiResponse> activateUser(@RequestBody SchoolMstVo schoolMstVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return schoolUserService.activateUser(schoolMstVo, userVo.getUserId());
	}

	@PostMapping("/registerAdminUser")
	public ResponseEntity<ApiResponse> registerAdminUser(@RequestBody UserVo vo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);
		return userService.createAdminUser(vo, userVo.getUserId());
	}
	@GetMapping("/updateUserStatus")
	public ResponseEntity<ApiResponse> updateUserStatus(@RequestParam long User_id ,@RequestParam Boolean status, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		return userService.updateUserStatusrByuserId(User_id,status);
	}
	//added by sukdeb
	@PostMapping("/sendsms")
	public String sendsms(@RequestBody SendsmsVo sendsmsvo){
       
		System.out.println(sendsmsvo);
		String response=sendsmsService.consumeAPI(sendsmsvo);
		
		return response;
      
	}

}
