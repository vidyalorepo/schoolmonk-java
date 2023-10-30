package com.dcc.schoolmonk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.DashboardService;
import com.dcc.schoolmonk.service.SchoolUserService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	private static final Logger LOGGER = Logger.getLogger(DashboardController.class);

	@Autowired
	UserService userService;

	@Autowired
	DashboardService dashboardService;

	// Akash

	@Autowired
	SchoolUserService schoolUserService;

	@RequestMapping(method = RequestMethod.GET, value = "/activeSchoolCount")
	public ResponseEntity<ApiResponse> getActiveSchoolCount(HttpServletRequest request) {
		LOGGER.info("DashboardController :: getActiveSchoolCount :: Entering getActiveSchoolCount method");

		try {
			return schoolUserService.getActiveSchoolCount();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

	// @RequestMapping(method = RequestMethod.GET, value =
	// "/getSchoolCountByStatus/{status}")
	// public ResponseEntity<ApiResponse> getCountByStatus(@PathVariable(value =
	// "status") String status,
	// HttpServletRequest request) {
	// LOGGER.info("DashboardController :: getCountByStatus:" + "Entering
	// getCountByStatus method: status:" + status);
	//
	// final String requestTokenHeader = request.getHeader("Authorization");
	//
	// String jwtToken = null;
	//
	// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	// jwtToken = requestTokenHeader.substring(7);
	// }
	//
	// UserVo userVo = userService.getUserDetails(request, jwtToken);
	//
	// if(userVo != null) {
	// try {
	// return schoolUserService.getSchoolCoutByStatusNew(status);
	//
	// } catch (SchoolmonkAppApplicationException e) {
	// e.getStackTrace();
	// return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
	// }
	// }
	// return null;
	//
	// }

	// @RequestMapping(method = RequestMethod.GET, value =
	// "/getSchoolCountByStatus")
	// public ResponseEntity<ApiResponse> getCountByStatus(HttpServletRequest
	// request, @RequestParam("status") String status) {
	//
	// LOGGER.info("DashboardController :: getCountByStatus:" + "Entering
	// getCountByStatus method: status:" + status);
	//
	// final String requestTokenHeader = request.getHeader("Authorization");
	//
	// String jwtToken = null;
	//
	// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
	// jwtToken = requestTokenHeader.substring(7);
	// }
	//
	// UserVo userVo = userService.getUserDetails(request, jwtToken);
	// if(userVo != null) {
	// try {
	// return schoolUserService.getSchoolCoutByStatusNew(status);
	//
	// } catch (SchoolmonkAppApplicationException e) {
	// e.getStackTrace();
	// return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
	// }
	// }
	// return null;
	// }

	@RequestMapping(method = RequestMethod.POST, value = "/getSchoolCountByStatus")
	public ResponseEntity<ApiResponse> getCountByStatus(@RequestBody SchoolMstVo schoolMstVo,
			HttpServletRequest request) {
		LOGGER.info("DashboardController :: getCountByStatus:" + "Entering getCountByStatus method: status:"
				+ schoolMstVo.getSchoolStatus());

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		if (userVo != null) {
			try {
				return schoolUserService.getSchoolCoutByStatusNew(schoolMstVo.getSchoolStatus());

			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
				return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
			}
		}
		return null;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/schoolTypeCount")
	public ResponseEntity<ApiResponse> getSchoolTypeCount(HttpServletRequest request) {
		LOGGER.info("DashboardController :: getSchoolTypeCount :: Entering getSchoolTypeCount method");

		try {
			return dashboardService.getSchoolTypeCount();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/schoolBoardCount")
	public ResponseEntity<ApiResponse> getSchoolBoardCount(HttpServletRequest request) {
		LOGGER.info("DashboardController :: schoolBoardCount :: Entering schoolBoardCount method");

		try {
			return dashboardService.getSchoolBoardCount();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/schoolLevelCount")
	public ResponseEntity<ApiResponse> getSchoolLevelCount(HttpServletRequest request) {
		LOGGER.info("DashboardController :: getSchoolLevelCount :: Entering getSchoolLevelCount method");

		try {
			return dashboardService.getSchoolLevelCount();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/activeUsersCount")
	public ResponseEntity<ApiResponse> userCountByType(HttpServletRequest request) {
		LOGGER.info("DashboardController :: userCountByType :: Entering userCountByType method");

		try {
			return dashboardService.userCountByType();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/issueCount")
	public ResponseEntity<ApiResponse> getIssueCount(HttpServletRequest request) {
		LOGGER.info("DashboardController :: issueCount :: Entering getIssueCount method");

		try {
			return dashboardService.getIssueCount();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}

}
