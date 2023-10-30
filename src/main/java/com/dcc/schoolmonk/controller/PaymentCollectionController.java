package com.dcc.schoolmonk.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.athena.model.Row;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.PaymentCollectionService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.PaymentCollVo;
import com.dcc.schoolmonk.vo.PaymentCollectionVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolPaymentDetailsVo;
import com.dcc.schoolmonk.vo.UserVo;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/paymentCollectionController")
public class PaymentCollectionController {
	
	private static final Logger LOGGER = Logger.getLogger(PaymentCollectionController.class);
	
	@Autowired
	UserService userService;

	@Autowired
	PaymentCollectionService paymentCollectionService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/paymentCollectionSum")
	public ResponseEntity<ApiResponse> getPaymentCollectionSum(HttpServletRequest request) {
		LOGGER.info("paymentCollection :: paymentCollectionSum :: Entering paymentCollectionSum method");

		try {
			return paymentCollectionService.getPaymentCollectionSum();
		} catch (SchoolmonkAppApplicationException e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(HttpStatus.UNAUTHORIZED);
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/schoolPaymentCollection")
	public ResponseEntity<ApiResponse> getSchoolByInput(@RequestBody PaymentCollVo vo,
			HttpServletRequest request) {
		LOGGER.info("paymentCollectionController :: schoolPaymentCollection :: Entering schoolPaymentCollection method");
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
			return paymentCollectionService.getSchoolPaymentCollection(vo);
		} else {
			apiResponse = new ApiResponse(401, "ERROR", "");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("SchoolUserController :: getSchoolByInput:" + "Exiting getSchoolByInput method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/schoolPaymentDetails")
	public ResponseEntity<ApiResponse> getSchoolPaymentDetails(@RequestBody SchoolPaymentDetailsVo vo,
			HttpServletRequest request) {
		LOGGER.info("paymentCollectionController :: schoolPaymentDetails :: Entering schoolPaymentDetails method");
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
			return paymentCollectionService.getSchoolPaymentDetails(vo);
		} else {
			apiResponse = new ApiResponse(401, "ERROR", "");
			responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("SchoolUserController :: getSchoolByInput:" + "Exiting getSchoolByInput method");

		return responseEntity;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/schoolPaymentDetailsReport")
	public Object FirFarReport(
			@RequestParam(name = "schoolName") String schoolName,
			@RequestParam(name = "studentName", required = false) String studentName,
			@RequestParam(name = "schoolId") String schoolId,
			@RequestParam(name = "payment", required = false) String payment, 
			@RequestParam(name = "academicYear", required = false) String academicYear,
			@RequestParam(name = "admissionForClass", required = false) String admissionForClass,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("schoolPaymentDetailsReport:---------> Start");
		
		String FILE_NAME = schoolName+".xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("SchoolPayment");
		int rowNum = 0;

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + FILE_NAME);

		OutputStream out = null;

		
		List<SchoolPaymentDetailsVo> listOfEntries = paymentCollectionService.getschoolPaymentDeatailsReport(schoolId, studentName, payment, academicYear, admissionForClass);
		LOGGER.info("listOfEntries:-------------------------->" + listOfEntries);
		XSSFRow row = sheet.createRow(rowNum++);

		Cell cell0 = row.createCell(0);
		cell0.setCellValue("Student Name");

		Cell cell1 = row.createCell(1);
		cell1.setCellValue("Academic Year");

		Cell cell2 = row.createCell(2);
		cell2.setCellValue("Class");

		Cell cell3 = row.createCell(3);
		cell3.setCellValue("Payment On");
		
		Cell cell4 = row.createCell(4);
		cell4.setCellValue("Payment");

		for (SchoolPaymentDetailsVo vo : listOfEntries) {
			row = sheet.createRow(rowNum++);

			cell0 = row.createCell(0);
			cell0.setCellValue(vo.getStudentName());

			cell1 = row.createCell(1);
			cell1.setCellValue(vo.getAcademicYear());

			cell2 = row.createCell(2);
			cell2.setCellValue(vo.getAdmissionForClass());
		
			cell3 = row.createCell(3);
			cell3.setCellValue(vo.getPaymentOn());			
			
			cell4 = row.createCell(4);
			cell4.setCellValue(vo.getPayment());

		}

		try {

			out = response.getOutputStream();
			workbook.write(out);
			workbook.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("schoolPaymentDetailsReport:------------> Exiting");

		return "Success";
	}
	
}
