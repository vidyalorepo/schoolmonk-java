package com.dcc.schoolmonk.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.AttachmentService;
import com.dcc.schoolmonk.service.DropdownService;
import com.dcc.schoolmonk.service.HistoryService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.CodeValueVo;
import com.dcc.schoolmonk.vo.DropdownMasterVo;
import com.dcc.schoolmonk.vo.FileResponseVo;
import com.dcc.schoolmonk.vo.HistoryVo;
import com.dcc.schoolmonk.vo.SingleVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/fe")
public class FEController {

	private static final Logger LOGGER = Logger.getLogger(FEController.class);

	@Autowired
	DropdownService dropdownService;

	@Autowired
	UserService userService;

	@Autowired
	HistoryService historyService;

	@Autowired
	AttachmentService attachmentService;

	@RequestMapping(method = RequestMethod.GET, value = "/dropdownlist")
	public ResponseEntity<HashSet<CodeValueVo>> getDropdownList(@RequestParam(value = "formCode") String formCode,
			@RequestParam(value = "fieldName") String fieldName, @RequestParam(value = "parent") String parent) {
		LOGGER.info("FEController:getDropdownList:" + "Entering");
		LOGGER.info("FEController:getDropdownList: Form Code: " + formCode + " :: Field Name:" + fieldName
				+ " :: Parent:" + parent);

		ResponseEntity<HashSet<CodeValueVo>> responseEntity = null;

		Iterable<DropdownMasterVo> entries = dropdownService.getAllEntries();

		HashSet<CodeValueVo> codeValueList = new LinkedHashSet<CodeValueVo>();
		CodeValueVo codeValue = null;
		for (DropdownMasterVo dropdownVo : entries) {

			if (formCode.trim().equals(dropdownVo.getFormCode()) && fieldName.trim().equals(dropdownVo.getFieldName())
					&& parent.trim().equals(dropdownVo.getParent())) {

				codeValue = new CodeValueVo();
				codeValue.setCode(dropdownVo.getListId());
				codeValue.setValue(dropdownVo.getListValue());

				codeValueList.add(codeValue);
			}
		}

		LOGGER.info("FEController:getDropdownList:" + "Exiting");

		if (codeValueList.size() > 0) {

			responseEntity = new ResponseEntity<HashSet<CodeValueVo>>(codeValueList, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<HashSet<CodeValueVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	public ResponseEntity<List<HistoryVo>> getHistoryByTxId(@RequestParam("txId") long txId,
			@RequestParam("formCode") long formCode) {

		LOGGER.info("FEController:: getHistory:" + "Entering");

		ResponseEntity<List<HistoryVo>> responseEntity = null;

		List<HistoryVo> histories = historyService.getHistoryByTx(txId, formCode);
		if (histories != null) {

			responseEntity = new ResponseEntity<List<HistoryVo>>(histories, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<HistoryVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/attachmentRecord")
	public ResponseEntity<List<AttachmentVo>> listFileByModule(@RequestParam("formCode") String formCode,
			@RequestParam("txId") String txId) {
		LOGGER.info("CESController:: listFileByModule:" + "Entering attachment methods");

		List<AttachmentVo> attachments = attachmentService.getAttachementsByTx(formCode, txId);
		ResponseEntity<List<AttachmentVo>> responseEntity = new ResponseEntity<List<AttachmentVo>>(attachments,
				HttpStatus.OK);

		LOGGER.info("CESController:: listFileByModule:" + "Exiting attachment methods");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/attachment", consumes = { "multipart/form-data" })
	public AttachmentVo attachfile(@RequestParam("uploadFile") MultipartFile file,
			@RequestParam("formCode") String formCode, @RequestParam("txId") long txId, HttpServletRequest request) {
		LOGGER.info("FEController :: attachfile: Entering");

		// UserVo userVo = (UserVo)request.getSession().getAttribute("USER_VO");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("FEController :: attachfile:: UserVo:: " + userVo);
//
		LOGGER.info("FEController :: attachfile: userId:: " + userVo.getUserId());
		LOGGER.info("FEController :: attachfile() :: original file size :: " + file.getSize());
//		AttachmentVo attachmentVo = attachmentService.upload(file, formCode, txId, userVo.getUserKey());
		AttachmentVo attachmentVo = attachmentService.upload(file, formCode, txId, userVo.getUserId());

		return attachmentVo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/attachment")
	public ResponseEntity<byte[]> downloadfile(@RequestParam("fileId") long fileId) {
		LOGGER.info("FEController:: downloadfile:: Entering attachfile methods");

		AttachmentVo attachmentVo = attachmentService.getAttachmentById(fileId);
		String s3FileName = attachmentVo.getFilePath().substring(attachmentVo.getFilePath().lastIndexOf("/") + 1);
		LOGGER.info("FEController:: downloadfile:: s3FileName " + s3FileName);
		String originalfilename = attachmentVo.getFileName();

		byte[] bytes = attachmentService.download(s3FileName);

		/*
		 * String encodedFileName = null; try { encodedFileName =
		 * URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		 * LOGGER.info("CESController:: downloadfile:: " + filename); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace();
		 * 
		 * encodedFileName = filename; }
		 */

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		// httpHeaders.setContentDispositionFormData("attachment", encodedFileName);
		httpHeaders.setContentDispositionFormData("attachment", originalfilename);

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/attachment")
	public ResponseEntity<ApiResponse> deletefile(@RequestParam("fileId") Long fileId) {
		LOGGER.info("CESController:: deletefile:" + "Entering deletefile methods");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			attachmentService.delete(fileId);

			apiResponse = new ApiResponse(200, "File Deleted Successfully", "true");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();

			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checkpermission/{appId}")
	public ResponseEntity<SingleVo> checkPermission(@PathVariable(value = "appId") long appId,
			HttpServletRequest request) {
		LOGGER.info("FEController:getEntryById:" + "Entering getEntryById method: ID:" + appId);

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		String roleName = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		SingleVo singleVo = new SingleVo();

//		if (appId == GlobalConstants.MEAL_MENU_MASTER_FORM_CODE) {
//			roleName = null; //GlobalConstants.ROLE_NAME_OFFICE_ADMIN;
//
//			String userPermission = userService.checkPermission(jwtToken, roleName);
//			singleVo.setPermission(userPermission);
//		}

		return new ResponseEntity<SingleVo>(singleVo, HttpStatus.OK);

	}


	@RequestMapping(method = RequestMethod.POST, value = "/upload", consumes = { "multipart/form-data" })
	public void multipartupload(@RequestParam("abc") MultipartFile file1, HttpServletRequest request) {
		LOGGER.info("FEController :: attachfile: Entering");

		// UserVo userVo = (UserVo)request.getSession().getAttribute("USER_VO");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("FEController :: attachfile:: UserVo:: " + userVo);

		LOGGER.info("FEController :: attachfile: userId:: " + userVo.getUserId());
		LOGGER.info("FEController :: attachfile() :: original file size :: " + file1.getSize());
//		AttachmentVo attachmentVo = attachmentService.upload(file, formCode, txId, userVo.getUserKey());
		attachmentService.upload1(file1);

//		return attachmentVo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/multipleattachment", consumes = { "multipart/form-data" })
	public List<AttachmentVo> multipleAttachFile(@RequestParam("uploadFile") List<MultipartFile> file,
			@RequestParam("formCode") String formCode, @RequestParam("txId") long txId, HttpServletRequest request) {
		LOGGER.info("FEController :: attachfile: Entering");

		// UserVo userVo = (UserVo)request.getSession().getAttribute("USER_VO");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("FEController :: attachfile:: UserVo:: " + userVo);
//
		LOGGER.info("FEController :: attachfile: userId:: " + userVo.getUserId());
		LOGGER.info("FEController :: attachfile() :: original file size :: " + file.size());
//		AttachmentVo attachmentVo = attachmentService.upload(file, formCode, txId, userVo.getUserKey());
		List<AttachmentVo> attachmentVo = new ArrayList<>();
		
		for(MultipartFile res : file) {
			AttachmentVo temp = new AttachmentVo();
			temp = attachmentService.upload(res, formCode, txId, userVo.getUserId());
			attachmentVo.add(temp);
		}
		 

		return attachmentVo;
	}

	//====================== SD =======================
	@RequestMapping(method = RequestMethod.POST, value = "/uploadSingleFileInFolder", consumes = { "multipart/form-data" })
	public FileResponseVo uploadSingleFileInFolder(@RequestParam("file") MultipartFile file,
			@RequestParam("formCode") String formCode, @RequestParam("docType") String docType, @RequestParam("txId") long txId, 
			@RequestParam(value = "fileId", required=false) Long fileId,
			@RequestParam(value = "schoolId", required=false) Long schoolId,
			@RequestParam(value = "profileStep", required=false) String profileStep,
			HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

//		String fileName = "";
		String fileDownloadUri = "";
		FileResponseVo res = new FileResponseVo();
		if (null != userVo) {
			
			res = attachmentService.storeFile(file, formCode, txId, userVo.getUserId(), docType, fileId, schoolId, profileStep);
			
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/fe/downloadMedia/" + res.getFileName()).toUriString();
			String encodedUrl = "";
			try {
				encodedUrl = URLDecoder.decode(fileDownloadUri, StandardCharsets.UTF_8.toString());
				res.setRespMsg("success");
				res.setFileName(file.getOriginalFilename());
				res.setFilePath(fileDownloadUri);
				res.setFileId(res.getFileId());
				res.setSchoolId(schoolId);
				res.setProfileStepSet(res.getProfileStepSet());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		} else {
			new FileResponseVo("error : user details not found.");
		}
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadMultipleFileInFolder", consumes = { "multipart/form-data" })
	public List<FileResponseVo> uploadMultipleFileInFolder(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("formCode") String formCode, @RequestParam("docType") String docType, @RequestParam("txId") long txId,
			HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

//		String fileName = "";
		
		FileResponseVo fileRespose = new FileResponseVo();
		
		String fileDownloadUri = "";
//		Map<String, String> res = new HashedMap<String, String>();
		List<FileResponseVo> res = new ArrayList<>();
		if (null != userVo) {
			for(MultipartFile file: files) {
				
				fileRespose = attachmentService.storeFile(file, formCode, txId, userVo.getUserId(), docType, null, null, null);
				
				fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/fe/downloadMedia/" + fileRespose.getFileName()).toUriString();
				String encodedUrl = "";
				
				try {
					encodedUrl = URLDecoder.decode(fileDownloadUri, StandardCharsets.UTF_8.toString());
//					res.put("respMsg", "success");
//					res.put("fileName", file.getOriginalFilename());
//					res.put("filePath", fileDownloadUri);
//					
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
			 
		} else {
			res.add(0, new FileResponseVo("error : user details not found."));
		}
		
		return res;
	}
	
	@GetMapping("/downloadMedia/{fileName}")
	public ResponseEntity<Resource> downloadMedia(@PathVariable("fileName") String fileName,
			HttpServletRequest request) {

		// Load file as Resource
		Resource resource = attachmentService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}