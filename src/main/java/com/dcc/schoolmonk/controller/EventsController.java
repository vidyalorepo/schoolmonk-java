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
import com.dcc.schoolmonk.service.EventsService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;
import com.dcc.schoolmonk.vo.EventsVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/events")
public class EventsController {
private static final Logger LOGGER = Logger.getLogger(EventsController.class);
	
	@Autowired
	UserService userService;

	@Autowired
	EventsService eventsService;
	
	@PostMapping("/saveEvent")
	public ResponseEntity<ApiResponse> saveEvent(@RequestBody EventsVo inputVo, HttpServletRequest request) {
		LOGGER.info(" EventsController :: saveEvent :: entering :: "+inputVo);
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return eventsService.saveEvent(inputVo, userVo.getUserId());
	}
	
	@PostMapping("/getPublicEventDetails")
	public ResponseEntity<ApiResponse> getPublicEventDetails(@RequestBody EventsVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getPublicEventDetails :: entering :: ");
		return eventsService.getEventDetails(inputVo);
	}

	@PostMapping("/getEventDetails")
	public ResponseEntity<ApiResponse> getEventDetails(@RequestBody EventsVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EventsController :: getNoticeDetails :: entering :: "+inputVo);
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		//UserVo userVo = userService.getUserDetails(request, jwtToken);

		return eventsService.getEventDetails(inputVo);
	}
	
	
	@PostMapping("/getEventList")
	public ResponseEntity<ApiResponse> getEventList(@RequestBody EventsVo inputVo, 
			HttpServletRequest request) {
		LOGGER.info(" EventsController :: getEventList :: entering :: "+inputVo.getEventType());
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		//UserVo userVo = userService.getUserDetails(request, jwtToken);

		return eventsService.getEventList("Private");
	}
	
	@GetMapping("/getAllEventsList")
	public ResponseEntity<ApiResponse> getAllEventsList(HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getAllEventsList :: entering :: ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		
		return eventsService.getAllEventsList(userVo);
	}
	
	@GetMapping("/getEventsDetailsById")
	public ResponseEntity<ApiResponse> getEventDetailsById(@RequestParam(value = "eventId") String eventId, HttpServletRequest request) {
		LOGGER.info(" EnhanceNoticeBoardController :: getNoticeDetailsById :: entering :: ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		
		if(null != userVo) {
			return eventsService.getEventDetailsById(eventId);
		}else {
			ApiResponse apiResponse = new ApiResponse(401, "erroe", "Un-Authorized", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		
	}
}
