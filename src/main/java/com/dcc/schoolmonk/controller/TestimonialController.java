package com.dcc.schoolmonk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.TestimonialService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.FileResponseVo;
import com.dcc.schoolmonk.vo.HelpDeskDtlVo;
import com.dcc.schoolmonk.vo.TestimonialDtlVo;
import com.dcc.schoolmonk.vo.TestimonialPublishDto;
import com.dcc.schoolmonk.vo.TestimonialSearchInputVo;
import com.dcc.schoolmonk.vo.TestimonialVo;
import com.dcc.schoolmonk.vo.UserVo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
        RequestMethod.POST, RequestMethod.GET })

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {
    private static final Logger LOGGER = Logger.getLogger(TestimonialController.class);

    @Autowired
    UserService userService;

    @Autowired
    TestimonialService testimonialService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/getAllTestimonials")
    public ResponseEntity<ApiResponse> getAllTestimonials() {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return testimonialService.getAllTestimonials();
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(500, "ERROR", "Server Error");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<ApiResponse> addTestimonial(@RequestBody TestimonialVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("Testimonial Controller:: add:: addTestimonial method:: Entering");

        String jwtTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserVo user = null;
        String userType = "";
        if (jwtTokenHeader != null && jwtTokenHeader.startsWith("Bearer")) {
            jwtToken = jwtTokenHeader.split(" ")[1];
            user = userService.getUserDetails(request, jwtToken);
            userType = user.getUserType();
        }

        ApiResponse apiResponse = null;
        ResponseEntity<ApiResponse> responseEntity = null;
        if (!userType.equals("ADMIN_USER")) {
            apiResponse = new ApiResponse(401, "unauthorized", "unauthorized");
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }

        try {
            TestimonialVo testimonial = testimonialService.addTestimonial(inputVo);
            apiResponse = new ApiResponse(200, "success", "success", testimonial);
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/gettestimonials")
    public ResponseEntity<ApiResponse> getTestimonialsByCustomSearch(@RequestBody TestimonialSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return testimonialService.getTestimonialsByCustomSearch(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(500, "ERROR", "Server Error");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/togglePublish")
    public ResponseEntity<ApiResponse> toggleTestimonialById(@RequestBody TestimonialPublishDto inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return testimonialService.toggleTestimonialByPublish(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse("ERROR", 500, "Server Error");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gettestimonials/{id}")
    public TestimonialVo getTestimonialById(@PathVariable Long id) {

        try {
            return testimonialService.getTestimonialById(id);
        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload", consumes = { "multipart/form-data" })
    public List<FileResponseVo> uploadMultipleFileInFolder(@RequestParam("files") List<MultipartFile> files,
            @RequestParam("formCode") String formCode, @RequestParam("docType") String docType,
            @RequestParam("txId") long txId,
            HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserVo user = null;
        Long userId = (long) -10; // negative user id for non logged in user

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            jwtToken = requestTokenHeader.split(" ")[1];
            user = userService.getUserDetails(request, jwtToken);
            userId = user.getUserId();
        }
        FileResponseVo fileRespose = new FileResponseVo();
        String fileDownloadUri = "";
        List<FileResponseVo> res = new ArrayList<>();

        for (MultipartFile file : files) {

            fileRespose = testimonialService.storeFile(file, formCode, txId, userId, docType, null);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/fe/downloadMedia/" + fileRespose.getFileName()).toUriString();
            String encodedUrl = "";

            try {
                encodedUrl = URLDecoder.decode(fileDownloadUri, StandardCharsets.UTF_8.toString());
                FileResponseVo tempFileRes = new FileResponseVo();
                tempFileRes.setRespMsg("success");
                tempFileRes.setFileName(file.getOriginalFilename());
                tempFileRes.setFilePath(fileDownloadUri);
                tempFileRes.setFileId(fileRespose.getFileId());
                res.add(tempFileRes);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return res;
    }
}
