package com.dcc.schoolmonk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;
import com.dcc.schoolmonk.vo.CodeValueVo;
import com.dcc.schoolmonk.vo.PaymentCollVo;
import com.dcc.schoolmonk.vo.PaymentCollectionVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolPaymentDetailsVo;

@Service
public class PaymentCollectionService {
	
	private static final Logger LOGGER = Logger.getLogger(PaymentCollectionService.class);
	
	@Autowired
	SchoolStudentMappingDao schoolStudentMappingDao;
	
	public ResponseEntity<ApiResponse> getPaymentCollectionSum() {
		ApiResponse apiResponse = null;
		try {
			List<String> entry = schoolStudentMappingDao.getPaymentCollectionSum();
			LOGGER.info("entry------------>"+entry);
			List<CodeValueVo> response = new ArrayList<>();
//			for (String var : entry) {
//				LOGGER.info("var------------>"+var);
//				String[] res = var.split(",");
//				CodeValueVo codeValueVo = new CodeValueVo();
//				codeValueVo.setCode(res[1]);
//				codeValueVo.setValue(res[0]);
//				response.add(codeValueVo);
//
//			}

			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", response); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
	}
	
	public ResponseEntity<ApiResponse> getSchoolPaymentCollection(PaymentCollVo vo) {
		LOGGER.info("SchoolMstService:: getSchoolByInput:: Entering getSchoolByInput method:: ");
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == vo.getPage() && null == vo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != vo.getSize()) ? vo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != vo.getPage() && vo.getPage() > 1) ? (vo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != vo.getOrderByColName() && !vo.getOrderByColName().trim().isEmpty() &&
				null != vo.getOrderBy() && !vo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + vo.getOrderByColName().trim() + " " + vo.getOrderBy();
		} else {
			orderByStr = " order by School_Name ASC";
		}

		if (null != vo.getAcademicYear() && !vo.getAcademicYear().trim().isEmpty()) {
			LOGGER.info("vo.getAcademicYear() ------------ " + vo.getAcademicYear());
			whereClause += " and Academic_Year like '%" + vo.getSchoolName().trim() + "%'";
		}
		if (null != vo.getContactPhone() && !vo.getContactPhone().trim().isEmpty()) {
			LOGGER.info("vo.getContactPhone() ------------ " + vo.getContactPhone());
			whereClause += " and Contact_Phone like '%" + vo.getContactPhone().trim() + "%'";
		}
		if (null != vo.getContactEmail() && !vo.getContactEmail().trim().isEmpty()) {
			LOGGER.info("vo.getContactEmail() ------------ " + vo.getContactEmail());
			whereClause += " and SCHOOL_MST.Contact_Email like '%" + vo.getContactEmail().trim() + "%'";
		}
		if (null != vo.getSchoolName() && !vo.getSchoolName().trim().isEmpty()) {
			LOGGER.info("vo.getSchoolName() ------------ " + vo.getSchoolName());
			whereClause += " and School_Name like '%" + vo.getSchoolName().trim() + "%'";
		}
		if (null != vo.getFiscalYear() && !vo.getFiscalYear().trim().isEmpty()) {
			LOGGER.info("vo.getFiscalYear() ------------ " + vo.getFiscalYear());
			whereClause += " and CONCAT(YEAR(payment_on),'-', YEAR(payment_on)+1) = " + "'"+ vo.getFiscalYear()+ "'" + " AND MONTH(payment_on) >= 2" ;
		}
		
		LOGGER.info("whereClause ------------ " + whereClause	);
		
		List<String> entry = schoolStudentMappingDao.getSchoolPayCollectionProc(whereClause, limitStr);
		List<PaymentCollVo> resultSet = new ArrayList<>();
		LOGGER.info("entry ------------ " + entry);
		for (String str : entry) {
			LOGGER.info("each_dataa ------------ " + str);
			PaymentCollVo eachVo = new PaymentCollVo();
			String[] eachdata = str.split(",");
			eachVo.setId(Long.valueOf(eachdata[0]));
			eachVo.setSchoolId(eachdata[0]);
			eachVo.setContactEmail(eachdata[1]);
			eachVo.setSchoolPrincipalName(eachdata[2]);
			eachVo.setSchoolName(eachdata[3]);
			eachVo.setContactPhone(eachdata[4]);
			eachVo.setPayment(eachdata[5]);
			
			resultSet.add(eachVo);
		}
//		int totalNo = schoolMstDao.getTotalCountByInput(whereClause, "SCHOOL_MST").intValue();
		LOGGER.info("SchoolPaymentCollectionService =:: getSchoolPaymentCollection:: Exiting getSchoolPaymentCollection method:: ");
		apiResponse = new ApiResponse(200, "success", "success", resultSet, resultSet.size());
		LOGGER.info("SchoolPaymentCollectionService::searchByInput::Exiting...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ApiResponse> getSchoolPaymentDetails(SchoolPaymentDetailsVo vo) {
		LOGGER.info("PaymentCollectionService:: getSchoolPaymentDetails:: Entering getSchoolPaymentDetails method:: ");
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == vo.getPage() && null == vo.getSize()) {
			// do nothing
		} else {
			Integer size = (null != vo.getSize()) ? vo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Integer page = (null != vo.getPage() && vo.getPage() > 1) ? (vo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != vo.getOrderByColName() && !vo.getOrderByColName().trim().isEmpty() &&
				null != vo.getOrderBy() && !vo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + vo.getOrderByColName().trim() + " " + vo.getOrderBy();
		} else {
			orderByStr = " order by School_Name ASC";
		}

		if (null != vo.getSchoolId() && !vo.getSchoolId().trim().isEmpty()) {
			LOGGER.info("vo.getSchoolId() ------------ " + vo.getSchoolId());
			whereClause += " AND SCHOOL_STUDENT_MAPPING.School_Id = " + vo.getSchoolId().trim();
		}
		if(null != vo.getFiscalYear() && !vo.getFiscalYear().isEmpty()) {
			LOGGER.info("vo.getFiscalYear() ------------ " + vo.getFiscalYear());
			whereClause += " AND (concat(YEAR(payment_on), '-',YEAR(payment_on)+1) = '"+vo.getFiscalYear()+"' or  "
					+ "concat(YEAR(payment_on) - 1, '-',YEAR(payment_on)) = '"+ vo.getFiscalYear().trim() +"') AND MONTH(payment_on) >= 2";	
		}
		if (null != vo.getStudentId() && !vo.getStudentId().trim().isEmpty()) {
			LOGGER.info("vo.getStudentId() ------------ " + vo.getStudentId());
			whereClause += " AND SCHOOL_STUDENT_MAPPING.Student_Id = " + vo.getStudentId().trim();
		}
		if (null != vo.getAdmissionForClass() && !vo.getAdmissionForClass().trim().isEmpty()) {
			LOGGER.info("vo.getAdmissionForClass() ------------ " + vo.getAdmissionForClass());
			whereClause += " AND SCHOOL_STUDENT_MAPPING.Admission_For_Class = '" + vo.getAdmissionForClass().trim() +"'";
		}
		
		if (null != vo.getPaymentStartDate() && null != vo.getPaymentEndDate()) {
			LOGGER.info("vo.getPaymentStartDate() + vo.getPaymentEndDate() ------------ " + vo.getPaymentStartDate() +" "+ vo.getPaymentEndDate());
			whereClause += " AND DATE(SCHOOL_STUDENT_MAPPING.payment_on) between DATE('"+vo.getPaymentStartDate()+"') AND DATE('"+vo.getPaymentEndDate()+"')";
		}
		
		LOGGER.info("whereClause ------------ " + whereClause	);
		
		List<String> entry = schoolStudentMappingDao.getSchoolPayDetailsProc(whereClause, limitStr);
		List<SchoolPaymentDetailsVo> resultSet = new ArrayList<>();
		LOGGER.info("entry ------------ " + entry);
		for (String str : entry) {
			LOGGER.info("each_dataa str ------------> " + str);
			SchoolPaymentDetailsVo eachVo = new SchoolPaymentDetailsVo();
			String[] eachdata = str.split(",");
			LOGGER.info("each_dataa ------------> " + eachdata);
//			eachVo.setId(Long.valueOf(eachdata[0]));
			eachVo.setSchoolId(eachdata[0]);
			eachVo.setStudentId(eachdata[1]);
			eachVo.setAcademicYear(eachdata[2]);
			eachVo.setAdmissionForClass(eachdata[3]);
			eachVo.setStudentName(eachdata[4]);
			eachVo.setPaymentOn(eachdata[5]);
			eachVo.setPayment(eachdata[6]);
			eachVo.setActualGst(eachdata[7]);
			eachVo.setActualConvenieceFee(eachdata[8]);
			
			resultSet.add(eachVo);
		}
		LOGGER.info("SchoolPaymentCollectionService =:: getSchoolPaymentDetails:: Exiting getSchoolPaymentDetails method:: ");
		apiResponse = new ApiResponse(200, "success", "success", resultSet, resultSet.size());
		LOGGER.info("SchoolPaymentCollectionService:-------:getSchoolPaymentDetails:--------:Exiting...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	public List<SchoolPaymentDetailsVo> getschoolPaymentDeatailsReport(String schoolId, String studentName, String payment, String academicYear, String admissionForClass) {
		LOGGER.info("SchoolPaymentCollectionService:--------: getschoolPaymentDeatailsReport :-----: Entering ");		
		String whereClause = "";
		ApiResponse apiResponse = null;
		if (null != schoolId && !schoolId.trim().isEmpty()) {
			LOGGER.info("schoolId ------------> " + schoolId);
			whereClause += " and SCHOOL_STUDENT_MAPPING.School_Id like '%" + schoolId.trim() + "%'";
		}
		if (null != studentName && !studentName.trim().isEmpty()) {
			LOGGER.info("studentName --------------> " + studentName);
			whereClause += " and STUDENT_MST.Student_name like '%" + studentName.trim() + "%'";
		}
		if (null != academicYear && !academicYear.trim().isEmpty()) {
			LOGGER.info("academicYear ------------> " + academicYear);
			whereClause += " and Academic_Year like '%" + academicYear.trim() + "%'";
		}
		if (null != admissionForClass && !admissionForClass.trim().isEmpty()) {
			LOGGER.info("admissionForClass ------------> " + admissionForClass);
			whereClause += " and SCHOOL_STUDENT_MAPPING.Admission_For_Class like '%" + admissionForClass.trim() + "%'";
		}
		
		LOGGER.info("whereClause ------------ " + whereClause	);
		List<String> entry = schoolStudentMappingDao.getSchoolPayDetailsProc(whereClause, "");
		List<SchoolPaymentDetailsVo> resultSet = new ArrayList<>();
		for (String str : entry) {
			LOGGER.info("each_dataa str ------------> " + str);
			SchoolPaymentDetailsVo eachVo = new SchoolPaymentDetailsVo();
			String[] eachdata = str.split(",");
			eachVo.setId(Long.valueOf(eachdata[0]));
			eachVo.setSchoolId(eachdata[0]);
			eachVo.setStudentId(eachdata[1]);
			eachVo.setAcademicYear(eachdata[2]);
			eachVo.setAdmissionForClass(eachdata[3]);
			eachVo.setStudentName(eachdata[4]);
		    LOGGER.info("setPaymentOn ------------> " + eachdata[5]);
			eachVo.setPaymentOn(eachdata[5]);
			eachVo.setPayment(eachdata[6]);			
			resultSet.add(eachVo);
		}
		LOGGER.info("resultSet ------------> " + resultSet);		
		return resultSet;
	}

}
