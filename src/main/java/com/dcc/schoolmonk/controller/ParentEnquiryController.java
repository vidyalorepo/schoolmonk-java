package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.ParentEnquiryDto;
import com.dcc.schoolmonk.service.ParentEnquiryService;
import com.dcc.schoolmonk.vo.ParentEnquiryVo;

import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/parentenquiry")
public class ParentEnquiryController {

   @Autowired
   ParentEnquiryService parentEnquiryService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> saveParentEnquiry(@RequestBody ParentEnquiryVo parentEnquiryVo,HttpServletRequest request) {
		return parentEnquiryService.save(parentEnquiryVo);
	}

	@RequestMapping(method = RequestMethod.POST , value = "getallenquiry")
	public ResponseEntity<ApiResponse> getallParentEnquiry(@RequestBody ParentEnquiryDto parentEnquiryDto,HttpServletRequest request) {
		return parentEnquiryService.getallParentEnquiry(parentEnquiryDto);
	}

	@RequestMapping(method = RequestMethod.GET , value = "deletebyid/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id,HttpServletRequest request) {
		return parentEnquiryService.deleteById(id);
	}

	
	@RequestMapping(method = RequestMethod.GET , value = "findbyid/{id}")
	public ResponseEntity<ApiResponse> findById(@PathVariable("id") Long id,HttpServletRequest request) {
		return parentEnquiryService.findByid(id);
	}

}
