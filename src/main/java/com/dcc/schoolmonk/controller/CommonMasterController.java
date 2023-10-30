package com.dcc.schoolmonk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.SchoolmonkApp;
import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.NewsArticalDto;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.CommonMasterService;
import com.dcc.schoolmonk.service.MailService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.ClassMstVo;
import com.dcc.schoolmonk.vo.CountriesVo;
import com.dcc.schoolmonk.vo.DistrictMstVo;
import com.dcc.schoolmonk.vo.DropdownMasterVo;
import com.dcc.schoolmonk.vo.FaqMstVo;
import com.dcc.schoolmonk.vo.FeedbackVo;
import com.dcc.schoolmonk.vo.InfrastructureMstVo;
import com.dcc.schoolmonk.vo.SchoolAddVo;
import com.dcc.schoolmonk.vo.SchoolLevelMstVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolSearchInputVo;
import com.dcc.schoolmonk.vo.StateMstVo;
import com.dcc.schoolmonk.vo.StatesVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/commonMaster")
public class CommonMasterController {

	private static final Logger LOGGER = Logger.getLogger(CommonMasterController.class);
	
	@Autowired
	UserService userService;

	@Autowired
	CommonMasterService commonMasterService;

	@Autowired
	MailService mailService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getStateList")
	public ResponseEntity<ApiResponse> getStateList(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getStateList :: Entering getStateList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: save:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<StateMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getStateList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(400, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getStateList method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getDistrictListByState")
	public ResponseEntity<ApiResponse> getDistrictListByState(@RequestParam("stateName") String stateName) {
		// LOGGER.info("CommonMasterController :: getDistrictListByState :: Entering getDistrictListByState method");
		
		// final String requestTokenHeader = request.getHeader("Authorization");

		// String jwtToken = null;

		// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
		// 	jwtToken = requestTokenHeader.substring(7);
		// }

		// UserVo userVo = userService.getUserDetails(request, jwtToken);

		// LOGGER.info("SchoolUserController:: save:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<DistrictMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getDistrictListByState(stateName);

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}
	   
        
		LOGGER.info("CommonMasterController :: Exiting getDistrictListByState method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveInfra")
	public ResponseEntity<ApiResponse> saveInfra(HttpServletRequest request, @RequestBody List<InfrastructureMstVo> voList) {
		LOGGER.info("CommonMasterController :: saveInfra: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("CommonMasterController:: saveInfra:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		try {
			List<InfrastructureMstVo> voListResp = commonMasterService.saveInfra(voList);
			
			if (voListResp != null) {
				apiResponse = new ApiResponse(200, "Success", "Data saved successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		}catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: saveInfra:: Exiting");
		
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getInfraList")
	public ResponseEntity<ApiResponse> getInfraList(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getInfraList :: Entering getStateList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: save:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<InfrastructureMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getInfraList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getInfraList method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveClass")
	public ResponseEntity<ApiResponse> saveClass(HttpServletRequest request, @RequestBody List<ClassMstVo> voList) {
		LOGGER.info("CommonMasterController :: saveClass: Entering saveClass method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("CommonMasterController:: saveClass:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		try {
			List<ClassMstVo> voListResp = commonMasterService.saveClass(voList);
			
			if (voListResp != null) {
				apiResponse = new ApiResponse(200, "Success", "Data saved successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		}catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: saveClass:: Exiting");
		
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getClassList")
	public ResponseEntity<ApiResponse> getClassList(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getClassList :: Entering getClassList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getClassList:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<ClassMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getClassList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getClassList method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getClassFromList")
	public ResponseEntity<ApiResponse> getClassFromList(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getClassFromList :: Entering getClassFromList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getClassList:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<ClassMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getClassFromList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getClassFromList method");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getClassUptoList")
	public ResponseEntity<ApiResponse> getClassUptoList(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getClassUptoList :: Entering getClassUptoList method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getClassList:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<ClassMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getClassUptoList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getClassUptoList method");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getClassStreams")
	public ResponseEntity<ApiResponse> getClassStreams(HttpServletRequest request, @RequestParam("className") String className) {
		LOGGER.info("CommonMasterController :: getClassStreams :: Entering getClassStreams method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getClassList:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getClassStreams(className);

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getClassStreams method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getBoards")
	public ResponseEntity<ApiResponse> getBoards() {
		LOGGER.info("CommonMasterController :: getBoards :: Entering getBoards method");
		
		// final String requestTokenHeader = request.getHeader("Authorization");

		// String jwtToken = null;

		// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
		// 	jwtToken = requestTokenHeader.substring(7);
		// }

		// UserVo userVo = userService.getUserDetails(request, jwtToken);

		// LOGGER.info("SchoolUserController:: getBoards:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getBoards();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getBoards method");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMediums")
	public ResponseEntity<ApiResponse> getMediums() {
		// LOGGER.info("CommonMasterController :: getMediums :: Entering getBoards method");
		
		// final String requestTokenHeader = request.getHeader("Authorization");

		// String jwtToken = null;

		// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
		// 	jwtToken = requestTokenHeader.substring(7);
		// }

		// UserVo userVo = userService.getUserDetails(request, jwtToken);

		// LOGGER.info("SchoolUserController:: getMediums:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getMediums();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getMediums method");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSchoolTypes")
	public ResponseEntity<ApiResponse> getSchoolTypes() {
		// LOGGER.info("CommonMasterController :: getSchoolTypes :: Entering getBoards method");
		
		// final String requestTokenHeader = request.getHeader("Authorization");

		// String jwtToken = null;

		// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
		// 	jwtToken = requestTokenHeader.substring(7);
		// }

		// UserVo userVo = userService.getUserDetails(request, jwtToken);

		// LOGGER.info("SchoolUserController:: getSchoolTypes:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getSchoolTypes();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getSchoolTypes method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getDDValuesByFieldName")
	public ResponseEntity<ApiResponse> getDDValuesByFieldName(HttpServletRequest request, @RequestParam("fieldName") String fieldName) {
		LOGGER.info("CommonMasterController :: getDDValuesByFieldName :: Entering getDDValuesByFieldName method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getSchoolTypes:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getDDValuesByFieldName(fieldName);

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getDDValuesByFieldName method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getBoardInSearch")
	public ResponseEntity<ApiResponse> getBoardsMedium(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getBoardInSearch :: Entering getBoards method");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<DropdownMasterVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getBoardInSearch();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getBoards method");
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMediumInSearch")
	public ResponseEntity<ApiResponse> getMediumInSearch(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getBoardInSearch :: Entering getBoards method");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getMediums();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getBoards method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSchoolLevels")
	public ResponseEntity<ApiResponse> getSchoolLevels(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: saveInfra: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("CommonMasterController:: saveInfra:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		try {
			List<SchoolLevelMstVo> voListResp = commonMasterService.getSchoolLevels();
			
			apiResponse = new ApiResponse(200, "Success", "Data saved successfully", voListResp, voListResp.size());
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		}catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: saveInfra:: Exiting");
		
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSchoolLevelsInSearch")
	public ResponseEntity<ApiResponse> getSchoolLevelsInSearch(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getSchoolLevelsInSearch: Entering getSchoolLevelsInSearch method");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		try {
			List<SchoolLevelMstVo> voListResp = commonMasterService.getSchoolLevels();
			
			apiResponse = new ApiResponse(200, "data_found", "data_found", voListResp, voListResp.size());
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		}catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: getSchoolLevelsInSearch:: Exiting");
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSchoolTypesInSearch")
	public ResponseEntity<ApiResponse> getSchoolTypesInSearch(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getSchoolTypesInSearch :: Entering getSchoolTypesInSearch method");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<String> listOfEntries;
		try {
			listOfEntries = commonMasterService.getSchoolTypes();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getSchoolTypesInSearch method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getDistrictListByStateMulti")
	public ResponseEntity<ApiResponse> getDistrictListByStateMulti(@RequestBody SchoolSearchInputVo inputVo,
			HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getDistrictListByStateMulti :: Entering getDistrictListByStateMulti method");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<DistrictMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getDistrictListByStateMulti(inputVo.getState());

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getDistrictListByState method");

		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getStateListInSearch")
	public ResponseEntity<ApiResponse> getStateListInSearch(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getStateListInSearch :: Entering getStateListInSearch method");
		
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<StateMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getStateList();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getStateList method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllCountries")
	public ResponseEntity<ApiResponse> getAllCountries(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getAllCountries :: Entering getAllCountries method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("CommonMasterController:: getAllCountries:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<CountriesVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getAllCountries();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getAllCountries method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getStatesByCountries")
	public ResponseEntity<ApiResponse> getStatesByCountries(@RequestParam(value="countryCode") String countryCode, HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getAllCountries :: Entering getAllCountries method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("CommonMasterController:: getAllCountries:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<StatesVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getStatesByCountries(userVo, countryCode);

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getAllCountries method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolDtl")
	public ResponseEntity<ApiResponse> saveSchoolDtl(HttpServletRequest request, @RequestBody SchoolMstVo schoolMsdVo) {
		LOGGER.info("CommonMasterController :: saveSchoolDtl: Entering saveSchoolDtl method");
	    
		return commonMasterService.saveSchoolDtl(schoolMsdVo);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveFeedback")
	public ResponseEntity<ApiResponse> saveFeedback(HttpServletRequest request, @RequestBody FeedbackVo feedbackVo) {
		LOGGER.info("CommonMasterController :: saveFeedback: Entering saveFeedback method");


		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		
		try {
			FeedbackVo voResult = commonMasterService.saveFeedback(feedbackVo);
			
			if (voResult != null) {
				apiResponse = new ApiResponse(200, "Success", "Data saved successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		}catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: saveSchoolDtl:: Exiting");
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getFeedBack")
	public ResponseEntity<ApiResponse> getFeedBack(@RequestBody FeedbackVo feedbackVo, HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getFeedBack :: Entering getFeedBack method");
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getFeedBack:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 

			if (userVo != null) {
				return commonMasterService.getFeedBack(feedbackVo);

			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateFeedbackStatus")
	public ResponseEntity<ApiResponse> updateFeedbackStatus(HttpServletRequest request, @RequestBody FeedbackVo vo) {
		LOGGER.info("CommonMasterController :: updateSchoolStatus: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("CommonMasterController:: updateSchoolStatus:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return commonMasterService.updateFeedbackStatus(vo, userVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("CommonMasterController :: updateFeedbackStatus:: Exiting");

		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getFaqs")
	public ResponseEntity<ApiResponse> getFaqs(HttpServletRequest request) {
		LOGGER.info("CommonMasterController :: getFeedBack :: Entering getFeedBack method");
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null; 
		List<FaqMstVo> listOfEntries;
		try {
			listOfEntries = commonMasterService.getFaqs();

			if (listOfEntries != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("CommonMasterController :: Exiting getFeedBack method");
		return responseEntity;
	}
	@PostMapping("/getallfaqs")
	public ResponseEntity<ApiResponse> getallfaqs(){
		return commonMasterService. getallfaqs();
		
	}
	@PostMapping("/updateFaqsid")
	public ResponseEntity<ApiResponse> updateFaqsid(@RequestBody FaqMstVo faqMstVo){
		return commonMasterService.updateFaqsByid(faqMstVo);
	
	}
	@GetMapping("/deleteByid/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable(value="id") long id){
		return commonMasterService.deleteByid(id);
   }
   
   @GetMapping("/fetchAllBlogCategory")	
   	public ResponseEntity<ApiResponse> fetchAllBlogCategory(){
		return commonMasterService.fetchallCategory();
   }
}
