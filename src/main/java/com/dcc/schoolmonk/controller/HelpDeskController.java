package com.dcc.schoolmonk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dcc.schoolmonk.SchoolmonkApp;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.service.AttachmentService;
import com.dcc.schoolmonk.service.HelpDeskService;
import com.dcc.schoolmonk.service.MailService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.FileResponseVo;
import com.dcc.schoolmonk.vo.HelpDeskDtlVo;
import com.dcc.schoolmonk.vo.IssueSearchInputVo;
import com.dcc.schoolmonk.vo.TicketDetailsResponseVo;
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
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {

    private static final Logger LOGGER = Logger.getLogger(HelpDeskController.class);

    @Autowired
    HelpDeskService helpDeskService;

    @Autowired
    UserService userService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
	MailService mailService;

    @RequestMapping(method = RequestMethod.POST, value = "/raiseTicket")
    public ResponseEntity<ApiResponse> raiseTicket(@RequestBody HelpDeskDtlVo helpDeskDtlVo,
            HttpServletRequest request) {
        LOGGER.info("HelpDeskController :: raiseTicket :: Entering raiseTicket method");

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserVo user = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            jwtToken = requestTokenHeader.split(" ")[1];
            user = userService.getUserDetails(request, jwtToken);
            helpDeskDtlVo.setIssuerId(user.getUserId());
            helpDeskDtlVo.setIssuerName(user.getFirstName());
//            helpDeskDtlVo.setIssuerLastName(user.getLastName());
            helpDeskDtlVo.setIssuerEmail(user.getEmail());
        }

        ResponseEntity<ApiResponse> responseEntity = null;
        ApiResponse apiResponse = null;
        TicketDetailsResponseVo responseVo = new TicketDetailsResponseVo();
        try {
            helpDeskDtlVo.setIssueState("Pending");
            HelpDeskDtlVo result = helpDeskService.raiseTicket(helpDeskDtlVo);
            responseVo.setIssueId(result.getIssueId());
            responseVo.setTicketId(result.getTicketId());
            //**********************Mail Service********************************************/
			//  String subject = SchoolmonkApp.ExtPropertyReader.helpDaskIssueSubject;
			//  String mainBody = SchoolmonkApp.ExtPropertyReader.helpDaskMailBody;
            //  mailService.sendMail(helpDeskDtlVo.getIssuerEmail(), subject, mainBody, null);
            apiResponse = new ApiResponse(201, "ticket_raised", "ticket_raised", responseVo, 1);
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        LOGGER.info("HelpDeskController :: raiseTicket :: raiseTicket method :: Exiting");
        return responseEntity;
    }

    // admin side listing of Issues

    @RequestMapping(method = RequestMethod.GET, value = "/getIssueDetails/{id}")
    public ResponseEntity<ApiResponse> getIssueDetails(@PathVariable(value = "id") String issueId,
            HttpServletRequest request) {
        LOGGER.info("HelpDeskController :: getIssueDetails :: getIssueDetails method :: Entering ");

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserVo user = null;
        String userType = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            jwtToken = requestTokenHeader.split(" ")[1];
            user = userService.getUserDetails(request, jwtToken);
            userType = user.getUserType();
        }

        ResponseEntity<ApiResponse> responseEntity = null;
        ApiResponse apiResponse = null;

        if (user != null && userType.toLowerCase().equals("admin_user")) {
            try {
                HelpDeskDtlVo result = helpDeskService.getIssueDetailsById(Long.parseLong(issueId));
                if (result != null) {

                    apiResponse = new ApiResponse(200, "issue_details_found", "issue_details_found", result, 1);
                    responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
                } else {

                    apiResponse = new ApiResponse(404, "issue_details_not_found", "issue_details_not_found", null, 0);
                    responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            apiResponse = new ApiResponse(401, "unautorized", "unauthorized");
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
        }
        LOGGER.info("HelpDeskController :: getIssueDetails :: getIssueDetails method :: Exiting");
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getIssues")
    public ResponseEntity<ApiResponse> getUsersByCustomSearch(@RequestBody IssueSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return helpDeskService.getIssuesByCustomSearch(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(500, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateIssueState")
    public ResponseEntity<ApiResponse> updateIssueStatus(@RequestBody HelpDeskDtlVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("HelpDeskController:: updateIssueStatus:: updateIssueStatus method:: Entering");

        String jwtTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserVo user = null;
        String userType = null;
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
            return helpDeskService.updateIssueStatus(inputVo, user);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    // attachment handling
    @RequestMapping(method = RequestMethod.POST, value = "/uploadFiles", consumes = { "multipart/form-data" })
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

            fileRespose = helpDeskService.storeFile(file, formCode, txId, userId, docType, null);

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
