package com.dcc.schoolmonk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
 
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.AdsDto;
import com.dcc.schoolmonk.dto.SaveAdsOrderDto;
import com.dcc.schoolmonk.service.AdsPlaneService;
import com.dcc.schoolmonk.service.AttachmentService;
import com.dcc.schoolmonk.vo.AdsOrderDetailsVo;

import com.dcc.schoolmonk.vo.AdszoneMstVo;
import com.dcc.schoolmonk.vo.FileResponseVo;

import kong.unirest.HttpStatus;

 

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {

		RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE })

 

@RestController

@RequestMapping("/adsvertisement")

public class AdsPlaneController {

	private static final Logger LOGGER = Logger.getLogger(AdsPlaneController.class);

    @Autowired

    AdsPlaneService adsPlaneService;
    
    @Autowired
	AttachmentService attachmentService;


    @RequestMapping(method = RequestMethod.POST, value = "/savezone")

	public ResponseEntity<ApiResponse> saveAds(@RequestBody AdszoneMstVo adszoneMstVo,HttpServletRequest request) {

		return adsPlaneService.SaveZone(adszoneMstVo);
    }	

    @RequestMapping(method = RequestMethod.POST, value = "/saveorder")

	public ResponseEntity<ApiResponse> saveOrder(@RequestBody SaveAdsOrderDto saveAdsOrderDto) {

		return adsPlaneService.Saveorder(saveAdsOrderDto);	
    }

   @GetMapping("/getallads")

	public ResponseEntity<ApiResponse> getallAds( AdsOrderDetailsVo adsOrderDetailsVo ) {

	return adsPlaneService.getAllads(adsOrderDetailsVo);
}
   
   @GetMapping("/fetchByAdd/{id}")
   public ResponseEntity<ApiResponse> fetchById(@PathVariable(value="id") long id) {
		return adsPlaneService.fetchById(id);
}
   
   @RequestMapping(method = RequestMethod.POST, value = "/uploadSingleFileInFolder", consumes = { "multipart/form-data" })
	public FileResponseVo uploadSingleFileInFolder(@RequestParam("file") MultipartFile file,
			@RequestParam("formCode") String formCode, @RequestParam("docType") String docType, @RequestParam("txId") long txId, 
			@RequestParam(value = "fileId", required=false) Long fileId,
			@RequestParam(value = "adsId", required=true) Long adsId,
			@RequestParam(value = "adsUrl") String adsUrl,
			HttpServletRequest request) {



//		String fileName = "";
		String fileDownloadUri = "";
		FileResponseVo res = new FileResponseVo();
			res = attachmentService.storeAdvertisementFile(file, formCode, txId, 0, docType, fileId, adsId, adsUrl);
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/fe/downloadMedia/" + res.getFileName()).toUriString();
			String encodedUrl = "";
			try {
				encodedUrl = URLDecoder.decode(fileDownloadUri, StandardCharsets.UTF_8.toString());
				res.setRespMsg("success");
				res.setFileName(file.getOriginalFilename());
				res.setFilePath(fileDownloadUri);
				res.setFileId(res.getFileId());
				res.setAdsId(adsId);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		return res;
	}

   @RequestMapping(method = RequestMethod.GET, value = "/attachment")
	public ResponseEntity<ApiResponse> deletefile(@RequestParam("fileId") Long fileId) {
		LOGGER.info("CESController:: deletefile:" + "Entering deletefile methods");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			attachmentService.delete(fileId);
			apiResponse = new ApiResponse(200, "File Deleted Successfully", "true",1);
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse,null, HttpStatus.OK);
		} catch (Exception e) {
			
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
   }
   
   @RequestMapping(method = RequestMethod.POST, value = "/getallorder")
	public ResponseEntity<ApiResponse> getallOrder(@RequestBody AdsDto adsDto) {

		LOGGER.info("/getallorder()::Called........");
		return adsPlaneService.getallOrderDetails(adsDto);

	}

	@RequestMapping(method = RequestMethod.GET,value = "/updatestatus")
	public ResponseEntity<ApiResponse> updateAdsStatus(@RequestParam("adsId") Long adsId,@RequestParam("status") Boolean status,@RequestParam("duration") String duration) {

		LOGGER.info("adsvertisement::Called........");
		return adsPlaneService.updateAdsStatus(adsId,status,duration);

	}

	@RequestMapping(method = RequestMethod.POST,value = "/fetchattachmentbyzone")
	public ResponseEntity<ApiResponse> FetchAdsAttatchmentBYZoneId(@RequestBody List<String> zoneName) {

		LOGGER.info("adsvertisement::FetchAdsAttachmentBYZoneId() Called........");
		return adsPlaneService.FetchAdsAttachmentBYZoneId(zoneName);

	}

}

