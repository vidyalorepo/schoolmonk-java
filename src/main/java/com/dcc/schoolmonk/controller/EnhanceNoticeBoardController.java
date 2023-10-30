package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.dcc.schoolmonk.service.EnhanceNoticeBoardService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/noticeboard")
public class EnhanceNoticeBoardController {

	private static final Logger LOGGER = Logger.getLogger(EnhanceNoticeBoardController.class);
	
	@Autowired
	UserService userService;

	@Autowired
	EnhanceNoticeBoardService noticeBoardService;
	
	@PostMapping("/saveNotice")
	public ResponseEntity<ApiResponse> saveNotice(@RequestBody EnhanceNoticeBoardVo inputVo, HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: saveNotice :: entering :: "+inputVo);
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return noticeBoardService.saveNotice(inputVo, userVo.getUserId());
	}

	@PostMapping("/getPublicNoticeDetails")
	public ResponseEntity<ApiResponse> getPublicNoticeDetails(@RequestBody EnhanceNoticeBoardVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getPublicNoticeDetails :: entering :: ");
		return noticeBoardService.getNoticeDetails(inputVo);
	}
	
	@PostMapping("/getNoticeDetails")
	public ResponseEntity<ApiResponse> getNoticeDetails(@RequestBody EnhanceNoticeBoardVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getNoticeDetails :: entering :: "+inputVo);
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		//UserVo userVo = userService.getUserDetails(request, jwtToken);

		return noticeBoardService.getNoticeDetails(inputVo);
	}
	
	@PostMapping("/getPublicNoticeList")
	public ResponseEntity<ApiResponse> getPublicNoticeList(@RequestBody EnhanceNoticeBoardVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getPublicNoticeList :: entering :: "+inputVo.getNoticeType());
		return noticeBoardService.getNoticeList("Public");
	}
	
	@PostMapping("/getNoticeList")
	public ResponseEntity<ApiResponse> getNoticeList(@RequestBody EnhanceNoticeBoardVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getNoticeList :: entering :: "+inputVo.getNoticeType());
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		//UserVo userVo = userService.getUserDetails(request, jwtToken);

		return noticeBoardService.getNoticeList("Private");
	}
	
	@GetMapping("/getAllNoticeList")
	public ResponseEntity<ApiResponse> getAllNoticeList(HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getAllNoticeList :: entering :: ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		
		return noticeBoardService.getAllNoticeList(userVo);
	}
	
	@GetMapping("/getNoticeDetailsById")
	public ResponseEntity<ApiResponse> getNoticeDetailsById(@RequestParam(value = "noticeId") String noticeId, HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getNoticeDetailsById :: entering :: ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		
		if(null != userVo) {
			return noticeBoardService.getNoticeDetailsById(noticeId);
		}else {
			ApiResponse apiResponse = new ApiResponse(401, "erroe", "Un-Authorized", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/getNoticeBySchoolId")
	public ResponseEntity<ApiResponse> getNoticeListBySchoolId(@RequestBody EnhanceNoticeBoardVo inputVo, @RequestParam(value = "schoolId") Long schoolId, HttpServletRequest request){
		LOGGER.info(" EnhanceNoticeBoardController :: getNoticeDetailsById :: Entering etNoticeListBySchoolId method :: ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("UnsafeActController:: searchByInput:: UserVo" + userVo);
		ResponseEntity<ApiResponse> responseEntity = null;

		ApiResponse apiResponse = null;
		if(null != userVo) {
			return noticeBoardService.getNoticeListBySchoolId(inputVo,schoolId);
		}else {
			apiResponse = new ApiResponse(401, "ERROR", "");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info("EnhanceNnoticeBoardController :: getNoticeListBySchoolId:" + "Exiting getNoticeListBySchoolId method");

		return responseEntity;
	}
}
