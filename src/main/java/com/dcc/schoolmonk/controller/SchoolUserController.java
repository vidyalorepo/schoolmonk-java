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

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.SchoolUserService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.AdmissionStatusMstVo;
import com.dcc.schoolmonk.vo.CodeValueVo;
import com.dcc.schoolmonk.vo.SchoolAcademicDtlVo;
import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import com.dcc.schoolmonk.vo.SchoolInfraDtlVo;
import com.dcc.schoolmonk.vo.SchoolLevelDtlVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolSearchInputVo;
import com.dcc.schoolmonk.vo.SchoolStreamDtlVo;
import com.dcc.schoolmonk.vo.SchoolStreamSubjectDtlVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.SchoolTimingDtlVo;
import com.dcc.schoolmonk.vo.UserSearchInputVo;
import com.dcc.schoolmonk.vo.UserVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/schooluser")
public class SchoolUserController {

	private static final Logger LOGGER = Logger.getLogger(SchoolUserController.class);

	@Autowired
	UserService userService;

	@Autowired
	SchoolUserService schoolUserService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public ResponseEntity<List<SchoolMstVo>> getAllEntries(HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getAllEntries :: Entering getAllEntries method");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		ResponseEntity<List<SchoolMstVo>> responseEntity = null;

		if (userVo != null) {

			List<SchoolMstVo> listOfEntries;
			try {
				listOfEntries = schoolUserService.getAllEntries();

				if (listOfEntries != null) {
					responseEntity = new ResponseEntity<List<SchoolMstVo>>(listOfEntries, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<List<SchoolMstVo>>(HttpStatus.NOT_FOUND);
				}
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
			}

		} else {
			responseEntity = new ResponseEntity<List<SchoolMstVo>>(HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("SchoolUserController :: getAllEntries:" + "Exiting getAllEntries method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/school/{id}")
	public ResponseEntity<SchoolMstVo> getEntryById(@PathVariable(value = "id") String id,
			HttpServletRequest request) {
		LOGGER.info("schooluser :: getEntryById: Entering getEntryById method: ID:" + id);

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		SchoolMstVo schoolMstVo = null;
		ResponseEntity<SchoolMstVo> responseEntity = null;

		try {
			schoolMstVo = schoolUserService.getEntryById(Long.parseLong(id));

			if (schoolMstVo != null) {
				responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException e) {
			responseEntity = new ResponseEntity<SchoolMstVo>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("schooluser :: getEntryById:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/school")
	public ResponseEntity<ApiResponse> getEntryById(@RequestBody SchoolAdmissionDtlVo vo,
			@RequestParam(value = "schoolId") Long schoolId, HttpServletRequest request) {
		LOGGER.info("schooluser :: getEntryById: Entering getEntryById method: ID:" + schoolId);

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		// SchoolMstVo schoolMstVo = null;
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			// schoolMstVo = schoolUserService.getEntryBySchoolId(vo, id);

			if (userVo != null) {
				return schoolUserService.getEntryBySchoolId(vo, schoolId);
				// responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				// responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo,
				// HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException e) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("schooluser :: getEntryById:: Exiting");
		return responseEntity;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody SchoolMstVo schoolMstVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);
		// UserVo userVo = new UserVo();
		// userVo.setUserId(19L);

		return schoolUserService.register(schoolMstVo, userVo.getUserId());
	}

	@GetMapping("/getByToken/{token}")
	public ResponseEntity<ApiResponse> getByToken(@PathVariable(value = "token") String token,
			HttpServletRequest request) {
		/*
		 * final String requestTokenHeader = request.getHeader("Authorization");
		 * 
		 * String jwtToken = null;
		 * 
		 * if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
		 * jwtToken = requestTokenHeader.substring(7); }
		 * 
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 */

		return schoolUserService.getByToken(token);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getschoolbyuser/{userId}")
	public ResponseEntity<SchoolMstVo> getEntryByUserId(@PathVariable(value = "userId") String userId,
			HttpServletRequest request) {
		LOGGER.info("schooluser :: getEntryById:" + "Entering getEntryById method: userId:" + userId);

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		SchoolMstVo schoolMstVo = null;
		ResponseEntity<SchoolMstVo> responseEntity = null;

		if (userVo != null) {
			try {
				schoolMstVo = schoolUserService.getEntryByUserId(Long.parseLong(userId));

				if (schoolMstVo != null) {
					responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.NOT_FOUND);
				}

			} catch (NumberFormatException e) {
				responseEntity = new ResponseEntity<SchoolMstVo>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LOGGER.info("schooluser :: getEntryById:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/schoolUpdate")
	public ResponseEntity<ApiResponse> editEntryById(HttpServletRequest request, @RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: editEntryById:" + "Entering editEntryById method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		LOGGER.info("SchoolUserController:: save:: UserVo" + userVo);

		schoolMstVo.setUpdatedBy(userVo.getUserId());
		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.editEntryById(schoolMstVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: editEntryById:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSchoolBySearch")
	public ResponseEntity<List<SchoolMstVo>> getSchoolBySearch(
			@RequestParam(name = "schoolName", required = false) String schoolName,
			@RequestParam(name = "board", required = false) String board,
			@RequestParam(name = "medium", required = false) String medium,
			@RequestParam(name = "p", required = false) Integer page,
			@RequestParam(name = "s", required = false) Integer size,
			@RequestParam(name = "o", required = false) String sort, HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getSchoolBySearch :: Entering getSchoolBySearch method");

		ResponseEntity<List<SchoolMstVo>> responseEntity = null;

		List<SchoolMstVo> listOfEntries;
		try {
			listOfEntries = schoolUserService.getSchoolBySearch(schoolName, board, medium, page, size, sort);

			if (listOfEntries != null && listOfEntries.size() > 0) {
				responseEntity = new ResponseEntity<List<SchoolMstVo>>(listOfEntries, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<List<SchoolMstVo>>(HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getSchoolBySearch:" + "Exiting getSchoolBySearch method");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAdmissionOpenSchool")
	public ResponseEntity<ApiResponse> getAdmissionOpenSchool(HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getSchoolBySearch :: Entering getAdmissionOpenSchool method");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		List<SchoolMstVo> listOfEntries;
		try {
			listOfEntries = schoolUserService.getAdmissionOpenSchool();

			if (listOfEntries != null && listOfEntries.size() > 0) {
				apiResponse = new ApiResponse(200, "school_found", "school_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "school_not_found", "school_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getSchoolBySearch:" + "Exiting getAdmissionOpenSchool method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getFeaturedSchool")
	public ResponseEntity<ApiResponse> getFeaturedSchool(HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getSchoolBySearch :: Entering getFeaturedSchool method");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		List<SchoolMstVo> listOfEntries;
		try {
			listOfEntries = schoolUserService.getFeaturedSchool();

			if (listOfEntries != null && listOfEntries.size() > 0) {
				apiResponse = new ApiResponse(200, "school_found", "school_found", listOfEntries, listOfEntries.size()); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "school_not_found", "school_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getSchoolBySearch:" + "Exiting getFeaturedSchool method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/getSchoolByInput")
	public ResponseEntity<ApiResponse> getSchoolByInput(@RequestBody SchoolMstVo schoolMstVo,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getSchoolByInput :: Entering getSchoolByInput method");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("UnsafeActController:: searchByInput:: UserVo" + userVo);
		ResponseEntity<ApiResponse> responseEntity = null;

		ApiResponse apiResponse = null;
		if (userVo != null) {
			return schoolUserService.getSchoolByInput(schoolMstVo);
		} else {
			apiResponse = new ApiResponse(401, "ERROR", "");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("SchoolUserController :: getSchoolByInput:" + "Exiting getSchoolByInput method");

		return responseEntity;

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Page<SchoolMstVo> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {

		Page<SchoolMstVo> resultPage = null;
		try {
			resultPage = schoolUserService.findPaginated(page, size);
			if (page > resultPage.getTotalPages()) {
				LOGGER.info("---------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultPage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/schoolBySearch/{id}")
	public ResponseEntity<SchoolMstVo> getEntryByIdWithoutJWT(@PathVariable(value = "id") String id) {
		LOGGER.info("schooluser :: getEntryByIdWithoutJWT:" + "Entering getEntryByIdWithoutJWT method: ID:" + id);

		SchoolMstVo schoolMstVo = null;
		ResponseEntity<SchoolMstVo> responseEntity = null;

		try {
			schoolMstVo = schoolUserService.getEntryById(Long.parseLong(id));

			if (schoolMstVo != null) {
				responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<SchoolMstVo>(schoolMstVo, HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException e) {
			responseEntity = new ResponseEntity<SchoolMstVo>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("schooluser :: getEntryByIdWithoutJWT:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolAdmissionDtl")
	public ResponseEntity<ApiResponse> saveSchoolAdmissionDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: saveAdmissionDtl: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		LOGGER.info("SchoolUserController:: save:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			schoolMstVo = schoolUserService.saveSchoolAdmissionDtl(schoolMstVo);

			if (schoolMstVo != null) {
				apiResponse = new ApiResponse(200, "Success", "School admission details updated successfully",
						schoolMstVo);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: editEntryById:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolInfraDtl")
	public ResponseEntity<ApiResponse> saveSchoolInfraDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: saveSchoolInfraDtl: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		return schoolUserService.saveSchoolInfraDtl(schoolMstVo);

		/*
		 * LOGGER.info("SchoolUserController:: saveSchoolInfraDtl:: UserVo" + userVo);
		 * schoolMstVo.setUpdatedBy(userVo.getUserId());
		 * 
		 * ResponseEntity<ApiResponse> responseEntity = null; ApiResponse apiResponse =
		 * null;
		 * 
		 * try { schoolMstVo = schoolUserService.saveSchoolInfraDtl(schoolMstVo);
		 * 
		 * if (schoolMstVo != null) { apiResponse = new ApiResponse(200, "Success",
		 * "School Infrastructure details updated successfully", schoolMstVo);
		 * responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		 * } else { apiResponse = new ApiResponse(401, "ERROR", ""); responseEntity =
		 * new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST); }
		 * }catch (Exception exp) { responseEntity = new
		 * ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		 * exp.printStackTrace(); }
		 * 
		 * LOGGER.info("SchoolUserController :: saveSchoolInfraDtl:: Exiting");
		 * 
		 * return responseEntity;
		 */
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/saveSchoolStudentEligibilityDtl") public ResponseEntity<ApiResponse>
	 * saveSchoolStudentEligibilityDtl(HttpServletRequest request, @RequestBody
	 * SchoolMstVo schoolMstVo) { LOGGER.
	 * info("SchoolUserController :: saveSchoolStudentEligibilityDtl: Entering saveAdmissionDtl method"
	 * );
	 * 
	 * final String requestTokenHeader = request.getHeader("Authorization"); String
	 * jwtToken = null; if (requestTokenHeader != null &&
	 * requestTokenHeader.startsWith("Bearer")) { jwtToken =
	 * requestTokenHeader.substring(7); } UserVo userVo =
	 * userService.getUserDetails(request, jwtToken);
	 * 
	 * LOGGER.info("SchoolUserController:: saveSchoolStudentEligibilityDtl:: UserVo"
	 * + userVo); schoolMstVo.setUpdatedBy(userVo.getUserId());
	 * 
	 * ResponseEntity<ApiResponse> responseEntity = null; ApiResponse apiResponse =
	 * null;
	 * 
	 * try { schoolMstVo =
	 * schoolUserService.saveSchoolStudentEligibilityDtl(schoolMstVo);
	 * 
	 * if (schoolMstVo != null) { apiResponse = new ApiResponse(200, "Success",
	 * "School Eligibility details updated successfully", schoolMstVo);
	 * responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	 * } else { apiResponse = new ApiResponse(401, "ERROR", ""); responseEntity =
	 * new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST); }
	 * }catch (Exception exp) { responseEntity = new
	 * ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
	 * exp.printStackTrace(); }
	 * 
	 * LOGGER.
	 * info("SchoolUserController :: saveSchoolStudentEligibilityDtl:: Exiting");
	 * 
	 * return responseEntity; }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolAcademicDtl")
	public ResponseEntity<ApiResponse> saveSchoolAcademicDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: saveSchoolAcademicDtl: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		LOGGER.info("SchoolUserController:: saveSchoolAcademicDtl:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		// ApiResponse apiResponse = null;

		try {
			responseEntity = schoolUserService.saveSchoolAcademicDtl(schoolMstVo);

		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: saveSchoolAcademicDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateSchoolAcademicDtl")
	public ResponseEntity<ApiResponse> updateSchoolAcademicDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: saveSchoolAcademicDtl: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		LOGGER.info("SchoolUserController:: saveSchoolAcademicDtl:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		// ApiResponse apiResponse = null;

		try {
			responseEntity = schoolUserService.updateSchoolAcademicDtl(schoolMstVo);

		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: saveSchoolAcademicDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAcademicdtlById/{id}")
	public ResponseEntity<ApiResponse> getAcademicDtlByid(@PathVariable(value = "id") String id) {

     return schoolUserService.getAcademicDtlBYId(Long.parseLong(id));
	}


	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolTimingDtl")
	public ResponseEntity<ApiResponse> saveSchoolTimingDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: saveSchoolTimingDtl: Entering saveAdmissionDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		GlobalConstants.adminEmail = userVo.getEmail();
		LOGGER.info("SchoolUserController:: saveSchoolTimingDtl:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		try {
			return schoolUserService.saveSchoolTimingDtl(schoolMstVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: saveSchoolTimingDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSchoolTimingDtl")
	public ResponseEntity<ApiResponse> deleteSchoolTimingDtl(HttpServletRequest request,
			@RequestBody SchoolTimingDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSchoolTimingDtl: Entering deleteSchoolTimingDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.deleteSchoolTimingDtl(vo);

			if (res > 0) {
				apiResponse = new ApiResponse(200, "Success", "data deleted successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolTimingDtl:: Exiting");

		return responseEntity;
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/deleteSchoolStudentEligibilityDtl") public ResponseEntity<ApiResponse>
	 * deleteSchoolStudentEligibilityDtl(HttpServletRequest request, @RequestBody
	 * SchoolStudentEligibilityDtlVo vo) { LOGGER.
	 * info("SchoolUserController :: deleteSchoolStudentEligibilityDtl: Entering ");
	 * 
	 * final String requestTokenHeader = request.getHeader("Authorization"); String
	 * jwtToken = null; if (requestTokenHeader != null &&
	 * requestTokenHeader.startsWith("Bearer")) { jwtToken =
	 * requestTokenHeader.substring(7); } UserVo userVo =
	 * userService.getUserDetails(request, jwtToken);
	 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
	 * userVo);
	 * 
	 * ResponseEntity<ApiResponse> responseEntity = null; ApiResponse apiResponse =
	 * null;
	 * 
	 * try { int res = schoolUserService.deleteSchoolStudentEligibilityDtl(vo);
	 * 
	 * if (res > 0) { apiResponse = new ApiResponse(200, "Success",
	 * "data deleted successfully", null); responseEntity = new
	 * ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK); } else { apiResponse
	 * = new ApiResponse(401, "ERROR", ""); responseEntity = new
	 * ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST); } }catch
	 * (Exception exp) { responseEntity = new
	 * ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
	 * exp.printStackTrace(); }
	 * 
	 * LOGGER.
	 * info("SchoolUserController :: deleteSchoolStudentEligibilityDtl:: Exiting");
	 * 
	 * return responseEntity; }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSchoolAcademicDtl")
	public ResponseEntity<ApiResponse> deleteSchoolAcademicDtl(HttpServletRequest request,
			@RequestBody SchoolAcademicDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSchoolAcademicDtl: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.deleteSchoolAcademicDtl(vo);

			if (res > 0) {
				apiResponse = new ApiResponse(200, "Success", "data deleted successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolAcademicDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSchoolBoardClassDtl")
	public ResponseEntity<ApiResponse> deleteSchoolBoardClassDtl(HttpServletRequest request,
			@RequestBody SchoolLevelDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSchoolBoardClassDtl: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.deleteSchoolBoardClassDtl(vo);

			if (res > 0) {
				apiResponse = new ApiResponse(200, "Success", "data deleted successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolBoardClassDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/checkSchoolBoardDetail")
	public ResponseEntity<ApiResponse> checkSchoolBoardDetail(HttpServletRequest request,
			@RequestBody SchoolLevelDtlVo vo) {
		LOGGER.info("SchoolUserController :: checkSchoolBoardDetail: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.checkSchoolBoardDetail(vo);

			if (res == 1) {
				apiResponse = new ApiResponse(200, "data_exists", "Eligibility, timing, Admission data exists", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}
			if (res == 2) {
				apiResponse = new ApiResponse(200, "data_not_exists", "No details data exists", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolBoardClassDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSchoolAdmissionDtl")
	public ResponseEntity<ApiResponse> deleteSchoolAdmissionDtl(HttpServletRequest request,
			@RequestBody SchoolAdmissionDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSchoolAdmissionDtl: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.deleteSchoolAdmissionDtl(vo);

			if (res > 0) {
				apiResponse = new ApiResponse(200, "Success", "data deleted successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolAdmissionDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSchoolInfraDtl")
	public ResponseEntity<ApiResponse> deleteSchoolInfraDtl(HttpServletRequest request,
			@RequestBody SchoolInfraDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSchoolInfraDtl: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		/*
		 * UserVo userVo = userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			int res = schoolUserService.deleteSchoolInfraDtl(vo);

			if (res > 0) {
				apiResponse = new ApiResponse(200, "Success", "data deleted successfully", null);
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(401, "ERROR", "");
				responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: deleteSchoolInfraDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getClassListOfSchool")
	public ResponseEntity<ApiResponse> getClassListOfSchool(HttpServletRequest request,
			@RequestBody SchoolLevelDtlVo vo) {
		LOGGER.info("SchoolUserController :: getClassListOfSchool: Entering ");

		/*
		 * final String requestTokenHeader = request.getHeader("Authorization"); String
		 * jwtToken = null; if (requestTokenHeader != null &&
		 * requestTokenHeader.startsWith("Bearer")) { jwtToken =
		 * requestTokenHeader.substring(7); } UserVo userVo =
		 * userService.getUserDetails(request, jwtToken);
		 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
		 * userVo);
		 */

		return schoolUserService.getClassListOfSchool(vo);
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "/findEligibility")
	 * public ResponseEntity<ApiResponse> findEligibility(HttpServletRequest
	 * request, @RequestBody SchoolStudentEligibilityDtlVo vo) {
	 * LOGGER.info("SchoolUserController :: findEligibility: Entering ");
	 * 
	 * final String requestTokenHeader = request.getHeader("Authorization"); String
	 * jwtToken = null; if (requestTokenHeader != null &&
	 * requestTokenHeader.startsWith("Bearer")) { jwtToken =
	 * requestTokenHeader.substring(7); } UserVo userVo =
	 * userService.getUserDetails(request, jwtToken);
	 * LOGGER.info("SchoolUserController:: deleteSchoolTimingDtl:: UserVo" +
	 * userVo);
	 * 
	 * ResponseEntity<ApiResponse> responseEntity = null; ApiResponse apiResponse =
	 * null;
	 * 
	 * try { SchoolStudentEligibilityDtlVo res =
	 * schoolUserService.findEligibility(vo);
	 * 
	 * if (null !=res ) { apiResponse = new ApiResponse(200, "data_found",
	 * "data fetched successfully", res); responseEntity = new
	 * ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK); } else { apiResponse
	 * = new ApiResponse(404, "data_not_found", ""); responseEntity = new
	 * ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND); } }catch
	 * (Exception exp) { responseEntity = new
	 * ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
	 * exp.printStackTrace(); }
	 * 
	 * LOGGER.info("SchoolUserController :: findEligibility:: Exiting");
	 * 
	 * return responseEntity; }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/updateSchoolStatus")
	public ResponseEntity<ApiResponse> updateSchoolStatus(HttpServletRequest request, @RequestBody SchoolMstVo vo) {
		LOGGER.info("SchoolUserController :: updateSchoolStatus: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: updateSchoolStatus:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.updateSchoolStatus(vo, userVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: updateSchoolStatus:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getAppliedStudent")
	public ResponseEntity<ApiResponse> getAppliedStudent(@RequestBody SchoolStudentMappingVo vo,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getAppliedStudent :: Entering getAppliedStudent method");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return schoolUserService.getAppliedStudent(vo);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAdminDashBoard")
	public ResponseEntity<ApiResponse> getAdminDashBoard(HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getAdminDashBoard :: Entering getAdminDashBoard method");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return schoolUserService.getAdminDashBoard();

	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveSchoolAdmissionEligibilityDtl")
	public ResponseEntity<ApiResponse> saveSchoolAdmissionEligibilityDtl(HttpServletRequest request,
			@RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info(
				"SchoolUserController :: saveSchoolAdmissionEligibilityDtl: Entering saveSchoolAdmissionEligibilityDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: saveSchoolAdmissionEligibilityDtl:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		try {
			return schoolUserService.saveSchoolAdmissionEligibilityDtl(schoolMstVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: saveSchoolAdmissionEligibilityDtl:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getClassRange")
	public ResponseEntity<ApiResponse> getClassRange(HttpServletRequest request, @RequestBody SchoolMstVo schoolMstVo) {
		LOGGER.info("SchoolUserController :: getClassRange: Entering saveSchoolAdmissionEligibilityDtl method");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		LOGGER.info("SchoolUserController:: getClassRange:: UserVo" + userVo);
		schoolMstVo.setUpdatedBy(userVo.getUserId());

		ResponseEntity<ApiResponse> responseEntity = null;
		try {
			return schoolUserService.getClassRange(schoolMstVo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: getClassRange:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/publishBulk")
	public ResponseEntity<ApiResponse> updatePublishStatus(HttpServletRequest request,
			@RequestBody SchoolAdmissionDtlVo vo) {
		LOGGER.info("SchoolUserController :: updatePublishStatus: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: updatePublishStatus:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.updatePublishStatus(vo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}

		LOGGER.info("SchoolUserController :: updatePublishStatus:: Exiting");

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getClassList")
	public ResponseEntity<List<CodeValueVo>> getClassList(@RequestParam(name = "schoolID") Long schoolID,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getClassList :: Entering getClassList method");

		ResponseEntity<List<CodeValueVo>> responseEntity = null;

		List<CodeValueVo> listOfEntries;
		try {
			listOfEntries = schoolUserService.getClassList(schoolID);

			if (listOfEntries != null && listOfEntries.size() > 0) {
				responseEntity = new ResponseEntity<List<CodeValueVo>>(listOfEntries, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<List<CodeValueVo>>(HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getClassList:" + "Exiting getClassList method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteStream")
	public ResponseEntity<ApiResponse> deleteStream(HttpServletRequest request, @RequestBody SchoolStreamDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteStream: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: deleteStream:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.deleteStream(vo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}
		LOGGER.info("SchoolUserController :: deleteStream:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSubject")
	public ResponseEntity<ApiResponse> deleteSubject(HttpServletRequest request, @RequestBody SchoolStreamDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSubject: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: deleteSubject:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.deleteSubject(vo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}
		LOGGER.info("SchoolUserController :: deleteSubject:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteSubjectByType")
	public ResponseEntity<ApiResponse> deleteSubjectByType(HttpServletRequest request,
			@RequestBody SchoolStreamSubjectDtlVo vo) {
		LOGGER.info("SchoolUserController :: deleteSubjectByType: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: deleteSubjectByType:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.deleteSubjectByType(vo);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}
		LOGGER.info("SchoolUserController :: deleteSubjectByType:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admissionFindById")
	public ResponseEntity<ApiResponse> admissionFindById(HttpServletRequest request,
			@RequestParam(name = "id") Long id) {
		LOGGER.info("SchoolUserController :: admissionFindById: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: admissionFindById:: UserVo" + userVo);

		ResponseEntity<ApiResponse> responseEntity = null;

		try {
			return schoolUserService.admissionFindById(id);
		} catch (Exception exp) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			exp.printStackTrace();
		}
		LOGGER.info("SchoolUserController :: admissionFindById:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllAdmissionStatus")
	public ResponseEntity<List<AdmissionStatusMstVo>> getAllEntriesAdmissionStatus(HttpServletRequest request) {
		LOGGER.info(
				"SchoolUserController :: getAllEntriesAdmissionStatus :: Entering getAllEntriesAdmissionStatus method");

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		ResponseEntity<List<AdmissionStatusMstVo>> responseEntity = null;

		if (userVo != null) {

			List<AdmissionStatusMstVo> listOfEntries;
			try {
				listOfEntries = schoolUserService.getAllEntriesAdmissionStatus();

				if (listOfEntries != null) {
					responseEntity = new ResponseEntity<List<AdmissionStatusMstVo>>(listOfEntries, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<List<AdmissionStatusMstVo>>(HttpStatus.NOT_FOUND);
				}
			} catch (SchoolmonkAppApplicationException e) {
				e.getStackTrace();
			}

		} else {
			responseEntity = new ResponseEntity<List<AdmissionStatusMstVo>>(HttpStatus.UNAUTHORIZED);
		}
		LOGGER.info("SchoolUserController :: getAllEntriesAdmissionStatus:"
				+ "Exiting getAllEntriesAdmissionStatus method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllStreamList")
	public ResponseEntity<ApiResponse> getClassListOfSchool(HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getClassListOfSchool: Entering ");

		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("SchoolUserController:: getClassListOfSchool:: UserVo" + userVo);

		return schoolUserService.getClassListOfSchool();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getSchoolByCustomSearch")
	public ResponseEntity<ApiResponse> getSchoolByCustomSearch(@RequestBody SchoolSearchInputVo inputVo,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getSchoolBySearch :: Entering getSchoolBySearch method");

		try {
			return schoolUserService.getSchoolByCustomSearch(inputVo);
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getSchoolBySearch:" + "Exiting getSchoolBySearch method");
		ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getUsersByCustomSearch")
	public ResponseEntity<ApiResponse> getUsersByCustomSearch(@RequestBody UserSearchInputVo inputVo,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

		try {
			return schoolUserService.getUsersByCustomSearch(inputVo);
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
		ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
	public ResponseEntity<UserVo> getUserById(@PathVariable(value = "id") String id, HttpServletRequest request) {
		LOGGER.info("schooluser :: getUserById: Entering getUserById method: ID:" + id);

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		UserVo user = null;
		ResponseEntity<UserVo> responseEntity = null;

		try {
			user = schoolUserService.getUserByUserId(Long.parseLong(id));

			if (user != null) {
				responseEntity = new ResponseEntity<UserVo>(user, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<UserVo>(user, HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException e) {
			responseEntity = new ResponseEntity<UserVo>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<UserVo>(user, HttpStatus.NOT_FOUND);
			e.printStackTrace();
		}

		LOGGER.info("schooluser :: getUserById:: Exiting");
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findBySlug/{slug}")
	public ResponseEntity<ApiResponse> findBySlug(@PathVariable(value = "slug") String slug,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: findBySlug :: Entering findBySlug method");

		try {
			return schoolUserService.findBySlug(slug);
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: findBySlug: Exiting findBySlug method");
		ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/saveRating")
	public ResponseEntity<ApiResponse> saveRating(@RequestBody SchoolMstVo schoolMstVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);
		// UserVo userVo = new UserVo();
		// userVo.setUserId(19L);

		return schoolUserService.saveRating(schoolMstVo, userVo.getUserId());
	}

	@PostMapping("/saveFavorite")
	public ResponseEntity<ApiResponse> saveFavorite(@RequestBody SchoolMstVo schoolMstVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return schoolUserService.saveFavorite(schoolMstVo);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllPublishedNotice")
	public ResponseEntity<ApiResponse> getAllPublishedNotice() {
		LOGGER.info("SchoolUserController :: getClassListOfSchool: Entering ");

		return schoolUserService.getAllPublishedNotice();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getPublishedNoticeDtl")
	public ResponseEntity<ApiResponse> getPublishedNoticeDtl(@RequestParam(name = "noticeId") Long noticeId) {
		LOGGER.info("SchoolUserController :: getClassListOfSchool: Entering ");

		return schoolUserService.getPublishedNoticeDtl(noticeId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findFavourieSchools")
	public ResponseEntity<ApiResponse> findFavourieSchools(@RequestParam(value = "userId") String userId,
			HttpServletRequest request) {
		LOGGER.info("SchoolUserController :: findFavourieSchools :: Entering findFavourieSchools method");

		try {
			return schoolUserService.findFavourieSchools(userId);
		} catch (Exception e) {
			e.getStackTrace();
		}

		LOGGER.info("SchoolUserController :: findFavourieSchools: Exiting findFavourieSchools method");
		ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/removeFavourieSchools")
	public ResponseEntity<ApiResponse> removeFavourieSchools(@RequestParam(name = "schoolId") String schoolId,
			@RequestParam(name = "userId") String userId) {
		LOGGER.info("SchoolUserController :: removeFavourieSchools: Entering ");

		return schoolUserService.removeFavourieSchools(schoolId, userId);
	}

}