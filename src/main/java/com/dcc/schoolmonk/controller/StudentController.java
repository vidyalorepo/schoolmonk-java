package com.dcc.schoolmonk.controller;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.StudentService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.StudentMstVo;
import com.dcc.schoolmonk.vo.UserVo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/studentcontroller")
public class StudentController {

	private static final Logger LOGGER = Logger.getLogger(SchoolUserController.class);

	@Autowired
	UserService userService;

	@Autowired
	StudentService studentService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public ResponseEntity<List<StudentMstVo>> getAllEntries(HttpServletRequest request) {
		LOGGER.info("studentcontroller :: getAllEntries :: Entering getAllEntries method");

		// final String requestTokenHeader = request.getHeader("Authorization");
		//
		// String jwtToken = null;
		//
		// if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
		// jwtToken = requestTokenHeader.substring(7);
		// }
		//
		// UserVo userVo = userService.getUserDetails(request, jwtToken);

		ResponseEntity<List<StudentMstVo>> responseEntity = null;

		// if (userVo != null) {

		List<StudentMstVo> listOfEntries;
		try {
			listOfEntries = studentService.getAllEntries();

			if (listOfEntries != null) {
				responseEntity = new ResponseEntity<List<StudentMstVo>>(listOfEntries, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<List<StudentMstVo>>(HttpStatus.NOT_FOUND);
			}
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
		}

		// } else {
		// responseEntity = new
		// ResponseEntity<List<StudentMstVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		// }
		LOGGER.info("studentcontroller :: getAllEntries:" + "Exiting getAllEntries method");

		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/student/{id}")
	public ResponseEntity<StudentMstVo> getEntryById(@PathVariable(value = "id") String id,
			HttpServletRequest request) {
		LOGGER.info("studentcontroller :: getEntryById:" + "Entering getEntryById method: ID:" + id);

		StudentMstVo studentMstVo = null;
		ResponseEntity<StudentMstVo> responseEntity = null;

		try {
			studentMstVo = studentService.getEntryById(Long.parseLong(id));

			if (studentMstVo != null) {
				responseEntity = new ResponseEntity<StudentMstVo>(studentMstVo, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<StudentMstVo>(studentMstVo, HttpStatus.NOT_FOUND);
			}

		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
			responseEntity = new ResponseEntity<StudentMstVo>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("studentcontroller :: getEntryById:: Exiting");
		return responseEntity;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> saveStudent(@RequestBody StudentMstVo studentMstVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.saveStudent(studentMstVo, userVo.getUserId());
	}

	@RequestMapping(value = "/student/get", method = RequestMethod.GET/*
																		 * , produces = "application/json", params = {
																		 * "page", "size" }
																		 */)
	public Page<StudentMstVo> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {

		Page<StudentMstVo> resultPage = null;
		try {
			resultPage = studentService.findPaginated(page, size);
			if (page > resultPage.getTotalPages()) {
				LOGGER.info("---------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultPage;
	}

	@PostMapping("/applyInitiate")
	public ResponseEntity<ApiResponse> applyInitiate(@RequestBody SchoolStudentMappingVo vo,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.applyInitiate(vo, userVo.getUserId());
	}

	@RequestMapping(value = "/listforSibling", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> listforSibling(@RequestParam("schoolId") Long schoolId,@RequestParam("createdBy") Long createdBy,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.listOfSiblingBySchoolId(schoolId,createdBy);
	}
	
	@RequestMapping(value = "/sortlistedByStudent", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> sortlistedByStudent(@RequestParam("studentId") Long studentId,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.sortlistedByStudent(studentId);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteSibling")
	public ResponseEntity<ApiResponse> deleteSibling(@RequestParam("id") Long id) {
		LOGGER.info("studentcontroller:: deleteSibling:" + "Entering deleteSibling methods");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			studentService.deleteSibling(id);

			apiResponse = new ApiResponse(200, "File Deleted Successfully", "true");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}



	@PostMapping("/shortlist")
	public ResponseEntity<ApiResponse> updateShortlisted(@RequestBody List<Long> ids, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.updateShortlisted(ids, userVo.getUserId());
	}

	@PostMapping("/previewStudentApplication")
	public ResponseEntity<ApiResponse> previewStudentApplication(@RequestBody SchoolStudentMappingVo vo,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		ApiResponse apiResponse = null;
		try {
			SchoolStudentMappingVo res = studentService.getSchoolStudentMappingVoById(vo.getRegistrationToken());

			if (res != null) {
				apiResponse = new ApiResponse(200, "data_found", "data_found", res, 1); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} else {
				apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		apiResponse = new ApiResponse(200, "data_not_found", "data_not_found", null, 0); // Added
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/saveStreamSubject")
	public ResponseEntity<ApiResponse> saveStreamSubject(@RequestBody SchoolStudentMappingVo schoolStudentMappingVo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}

		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.saveStreamSubject(schoolStudentMappingVo, userVo.getUserId());
	}

	@RequestMapping(value = "/getStreamSubjectData", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getStreamSubjectData(@RequestParam("studentId") Long studentId,
			@RequestParam("schoolId") Long schoolId,
			@RequestParam("admissionClass") String admissionClass,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.getStreamSubjectData(studentId, admissionClass, schoolId);
	}
	
	@PostMapping("/updateStudentStatus")
	public ResponseEntity<ApiResponse> updateStudentStatus(@RequestBody SchoolStudentMappingVo vo, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.updateStudentStatus(vo, userVo);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAddress")
	public ResponseEntity<ApiResponse> deleteAddress(@RequestParam("id") Long id) {
		LOGGER.info("studentcontroller:: deleteAddress:" + "Entering deleteAddress methods");

		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;

		try {
			studentService.deleteAddress(id);

			apiResponse = new ApiResponse(200, "Address Deleted Successfully", "true");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<ApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@PostMapping("/checkForApply")
	public ResponseEntity<ApiResponse> checkForApply(@RequestBody SchoolAdmissionDtlVo vo,
			HttpServletRequest request) {
		
		return studentService.checkForApply(vo);
	}
	
	@PostMapping("/studentCreate")
	public ResponseEntity<ApiResponse> studentCreate(@RequestBody StudentMstVo vo,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);
		LOGGER.info("userVo ------> "+userVo);
				
		return studentService.createStudent(vo, userVo.getUserId());
	}
	
	@RequestMapping(value = "/getStudentApplicant", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getStudentApplicants(@RequestParam("studentUserId") Long studentUserId, HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.getStudentApplicants(studentUserId, userVo);
	}
	
	@RequestMapping(value = "/getStudentByParentId", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getStudentByParentId(@RequestParam("studentId") Long studentId,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.getStudentByParentId(studentId);
	}
	
	@RequestMapping(value = "/getStudentSchoolMapping", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getStudentSchoolMapping(@RequestParam("studentId") Long studentId,
			@RequestParam("schoolId") Long schoolId,
			@RequestParam("board") String board,
			@RequestParam("classStd") String classStd,
			HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		}
		UserVo userVo = userService.getUserDetails(request, jwtToken);

		return studentService.getStudentSchoolMapping(studentId, schoolId, board, classStd);
	}
}
