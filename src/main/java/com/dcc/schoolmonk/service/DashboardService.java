package com.dcc.schoolmonk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.BlogDao;
import com.dcc.schoolmonk.dao.FeedBackDao;
import com.dcc.schoolmonk.dao.NewsArticlesDao;
import com.dcc.schoolmonk.dao.ParentEnquiryDao;
import com.dcc.schoolmonk.dao.SchoolLevelDtlDao;
import com.dcc.schoolmonk.dao.SchoolLevelMstDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolReviewDao;
import com.dcc.schoolmonk.dao.TestimonialDao;
import com.dcc.schoolmonk.vo.CodeValueVo;
import com.dcc.schoolmonk.vo.IssueStateVo;
import com.dcc.schoolmonk.vo.TestimonialVo;
import com.dcc.schoolmonk.vo.UserTypeVo;

@Service
public class DashboardService {

	private static final Logger LOGGER = Logger.getLogger(DashboardService.class);

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	CommonMasterService commonMasterService;

	@Autowired
	SchoolLevelMstDao schoolLevelMstDao;

	@Autowired
	SchoolLevelDtlDao schoolLevelDtlDao;
	
	@Autowired
	TestimonialDao testimonialDao;
	
	@Autowired
	FeedBackDao feedBackDao;
	
	@Autowired
	NewsArticlesDao newsArticlesDao;
	
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	SchoolReviewDao schoolReviewDao;
	
	@Autowired
	ParentEnquiryDao parentEnquiryDao;

	public ResponseEntity<ApiResponse> getSchoolTypeCount() {
		ApiResponse apiResponse = null;

		try {
			List<String> entry = schoolMstDao.getSchoolTypeCount();

			List<CodeValueVo> response = new ArrayList<>();
			for (String var : entry) {

				String[] res = var.split(",");
				CodeValueVo codeValueVo = new CodeValueVo();
				codeValueVo.setCode(res[1]);
				codeValueVo.setValue(res[0]);
				response.add(codeValueVo);

			}

			apiResponse = new ApiResponse(200, "data_found", "data_found successfully", response); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(404, "data_not_found", "data_not_found", null); // Added
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ApiResponse> getSchoolBoardCount() {
		ApiResponse apiResponse = null;

		List<String> listOfEntries;

		listOfEntries = commonMasterService.getBoards();

		List<CodeValueVo> response = new ArrayList<>();

		for (String boardName : listOfEntries) {
			LOGGER.info(boardName);

			Long schoolBoardCount = schoolMstDao.getSchoolBoardCount(boardName);

			LOGGER.info(schoolBoardCount);
			CodeValueVo codeValueVo = new CodeValueVo();
			if (schoolBoardCount != 0) {
				codeValueVo.setCode(boardName);
				codeValueVo.setValue(schoolBoardCount.toString());
				response.add(codeValueVo);
			}

		}

		apiResponse = new ApiResponse(200, "data_found", "data_found successfully", response);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> userCountByType() {
		ApiResponse apiResponse = null;

		List<UserTypeVo> listOfEntries;

		listOfEntries = commonMasterService.getActiveUsersTypes();

		apiResponse = new ApiResponse(200, "data_found", "data_found successfully",
				listOfEntries);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getSchoolLevelCount() {
		ApiResponse apiResponse = null;

		List<String> listOfEntries;

		listOfEntries = schoolLevelMstDao.getSchoolLevels();

		List<CodeValueVo> response = new ArrayList<>();

		for (String levelName : listOfEntries) {
			LOGGER.info(levelName);

			Long schoolLevelCount = schoolLevelDtlDao.getSchoolLevelCount(levelName);

			LOGGER.info(schoolLevelCount);
			CodeValueVo codeValueVo = new CodeValueVo();
			if (schoolLevelCount != 0) {
				codeValueVo.setCode(levelName);
				codeValueVo.setValue(schoolLevelCount.toString());
				response.add(codeValueVo);
			}

		}

		apiResponse = new ApiResponse(200, "data_found", "data_found successfully", response);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getIssueCount() {
		ApiResponse apiResponse = null;

		List<IssueStateVo> listOfEntries;

		listOfEntries = commonMasterService.getIssueCount();

		apiResponse = new ApiResponse(200, "data_found", "data_found successfully",
				listOfEntries);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> gettestimonialCount() {
		
		int entries;
		
		entries = testimonialDao.getTestimonialCount();
		
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getFeedbackCount() {
		
		int entries;
		entries = feedBackDao.getFeedbackCount();
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getnewsCount() {
		
		int entries;
		entries = newsArticlesDao.getNewsCount();
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getBlogCount() {
		
		int entries;
		entries = blogDao.getBlogCount();
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getReviewCount() {
		
		int entries;
		entries = schoolReviewDao.getReviewCount();
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	public ResponseEntity<ApiResponse> getGuardianEnquiryCount() {
		
		int entries;
		entries = parentEnquiryDao.getGuardianEnquiryCount();
		ApiResponse apiResponse = new ApiResponse(200, "Success", "Data found successfully", entries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
