package com.dcc.schoolmonk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.SchoolBulkUploadService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/schoolMstBulkController")

public class SchoolMstBulkController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	SchoolBulkUploadService schoolBulkUploadService;    // Class Name    -- Ref Var
	
	private static final Logger LOGGER = Logger.getLogger(SchoolMstBulkController.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadSchools")
	public ResponseEntity<ApiResponse> uploadDrivers(@RequestParam("uploadFile") MultipartFile excelInput,
			HttpServletRequest request) {
		LOGGER.info("SchoolMstBulkController:: uploadSchools() called......");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);
		
		if (userVo != null) {
			return schoolBulkUploadService.uploadSchools(excelInput, userVo);
		}
		else {
			apiResponse = new ApiResponse(401, "ERROR", "User not found.", "");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		return responseEntity ;
	}
    
	@RequestMapping(method = RequestMethod.POST, value = "/checkduplicateSchool")
	public Object CheckDuplicateSchool(@RequestParam("uploadFile") MultipartFile excelInput,HttpServletResponse response) {
		LOGGER.info("SchoolMstBulkController::CheckDuplicateSchool() called......");
		return  schoolBulkUploadService.CheckDuplicateSchool(excelInput,response);
	}
}
