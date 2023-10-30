package com.dcc.schoolmonk.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.LocationListService;
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/locationlistcontroller")
public class LocationListController {
	private static final Logger LOGGER = Logger.getLogger(LocationListController.class);
	
	@Autowired
	LocationListService locationlistservice;
	
	@GetMapping("/getalllocationlist")
	public ResponseEntity<ApiResponse> getlocationlist(){
		return locationlistservice.getLocationlist();
}
}
