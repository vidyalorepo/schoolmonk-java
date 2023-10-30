package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.service.SubscriptionService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.SubcriptionMstVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("subscription")
public class SubscriptionController {
	
	private static final Logger LOGGER = Logger.getLogger(SubscriptionController.class);
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSubscriptionList")
	public ResponseEntity<ApiResponse> getSubscriptionList(HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: getSubscriptionList :: Entering getSubscriptionList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: getSubscriptionList:: UserVo" + userVo);
		
		return subscriptionService.getSubscriptionList(userVo);

	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/saveSubscription")
	public ResponseEntity<ApiResponse> saveSubscription(@RequestBody SubcriptionMstVo subcriptionMstVo ,HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: saveSubscription :: Entering saveSubscription method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: saveSubscription:: UserVo" + userVo);
		
		return subscriptionService.saveSubscription(userVo, subcriptionMstVo);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/upgradeSubscription")
	public ResponseEntity<ApiResponse> upgradeSubscription(@RequestBody SubcriptionMstVo subcriptionMstVo ,HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: upgradeSubscription :: Entering upgradeSubscription method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: upgradeSubscription:: UserVo" + userVo);
		
		return subscriptionService.upgradeSubscription(userVo, subcriptionMstVo);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSubscriptionFeaturedList")
	public ResponseEntity<ApiResponse> getSubscriptionFeaturedList(HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: getSubscriptionFeaturedList :: Entering getSubscriptionFeaturedList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: getSubscriptionFeaturedList:: UserVo" + userVo);
		
		return subscriptionService.getSubscriptionFeaturedList(userVo);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getUserSubscriptionLog")
	public ResponseEntity<ApiResponse> getUserSubscriptionLog(@RequestParam(value="userId") long userId, HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: getUserSubscriptionLog :: Entering getUserSubscriptionLog method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: getUserSubscriptionLog:: UserVo" + userVo);
		
		return subscriptionService.getUserSubscriptionLog(userVo, userId);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllFeaturedList")
	public ResponseEntity<ApiResponse> getAllFeaturedList(HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: getAllFeaturedList :: Entering getAllFeaturedList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: getAllFeaturedList:: UserVo" + userVo);
		
		return subscriptionService.getAllFeaturedList(userVo);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSubscriptionNotification")
	public ResponseEntity<ApiResponse> getSubscriptionNotification(@RequestParam(value="userId") long userId,
			@RequestParam(value="currentDate") String currentDate, HttpServletRequest request) {
		LOGGER.info("SubscriptionController :: getSubscriptionNotification :: Entering getSubscriptionNotification method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SubscriptionController:: getSubscriptionNotification:: UserVo" + userVo);
		
		return subscriptionService.getSubscriptionNotification(userId, currentDate);

	}

}
