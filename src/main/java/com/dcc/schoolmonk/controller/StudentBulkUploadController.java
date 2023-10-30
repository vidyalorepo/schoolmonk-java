package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.StudentBulkUploadService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/studentBulkController")
public class StudentBulkUploadController {

    @Autowired
    UserService userService;

    @Autowired
    StudentBulkUploadService studentBulkUploadService;

    @RequestMapping(method = RequestMethod.POST, value = "/uploadStudents")
    public ResponseEntity<ApiResponse> uploadStudents(@RequestParam("uploadFile") MultipartFile excelInput,
            HttpServletRequest request) {

        ResponseEntity<ApiResponse> responseEntity = null;
        ApiResponse apiResponse = null;

        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }

        UserVo userVo = userService.getUserDetails(request, jwtToken);

        if (userVo != null) {
            return studentBulkUploadService.uploadStudents(excelInput, userVo);
        } else {
            apiResponse = new ApiResponse(401, "ERROR", "User not found.", "");
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }
}
