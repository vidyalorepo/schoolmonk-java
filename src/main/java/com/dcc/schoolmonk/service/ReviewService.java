package com.dcc.schoolmonk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolReviewDao;
import com.dcc.schoolmonk.dto.SchoolReviewDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolReviewVo;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.utils.GlobalConstants;
import com.dcc.schoolmonk.vo.SchoolMstVo;

@Service


public class ReviewService {
    
    private static final Logger LOGGER = LogManager.getLogger(ReviewService.class);

	@Autowired
	private SchoolReviewDao schoolreviewmstdao;
	
	@Autowired
	private SchoolMstDao schoolMstDao;
	
//	public ReviewService(SchoolReviewMstDao schoolreviewmstdao) {
//		super();
//		this.schoolreviewmstdao = schoolreviewmstdao;
//	}

	public ResponseEntity<ApiResponse> AddReview(SchoolReviewVo schoolreviewmstvo) {

		try {
			SchoolReviewVo art = schoolreviewmstdao.save(schoolreviewmstvo);
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "success", "Review Saved Successful.", art),
					HttpStatus.CREATED);
		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(500, "error", "Review Saved Failed.", schoolreviewmstvo),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<SchoolReviewVo> getAllReviews() {
		List<SchoolReviewVo> entry = schoolreviewmstdao.findAll();
		return entry;
	}

	public ResponseEntity<ApiResponse> getallreviews(SchoolReviewDto schoolReviewDto) {
		ResponseEntity<ApiResponse> responseEntity = null;
		int noOfRecords = 0;
		String whereClause = " where 1";
		String limitStr = "";
		String orderByStr = "";
		try {
			if (null == schoolReviewDto.getPage() && null == schoolReviewDto.getSize()) {
				// do nothing
			} else {
				Integer size = (null != schoolReviewDto.getSize()) ? schoolReviewDto.getSize()
						: GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
				Integer page = (null != schoolReviewDto.getPage() && schoolReviewDto.getPage() > 1)
						? (schoolReviewDto.getPage() - 1) * size
						: 0;
				limitStr = " limit " + size + " offset " + page;
				LOGGER.info("limitStr..." + limitStr);
			}
			if (null != schoolReviewDto.getSortBy() && !schoolReviewDto.getSortBy().trim().isEmpty()) {
				orderByStr = " order by " + schoolReviewDto.getSortBy().trim() + " " + schoolReviewDto.getSortBy();
			} else {
				orderByStr = " order by m.id DESC";
			}
       

			if (null != schoolReviewDto.getReviewerName() && !schoolReviewDto.getReviewerName().trim().isEmpty()) {
				LOGGER.info("schoolReviewDto.getReviewerName() ------------ " + schoolReviewDto.getReviewerName());
				whereClause += " and m.reviewer_name like '%" + schoolReviewDto.getReviewerName().trim() + "%'";
			}
			if (null != schoolReviewDto.getSchoolName() && !schoolReviewDto.getSchoolName().trim().isEmpty()) {
                LOGGER.info("schoolReviewDto.getSchoolName() ------------ " + schoolReviewDto.getSchoolName());
                whereClause += " and s.School_Name like '%" + schoolReviewDto.getSchoolName().toString().trim() + "%' ";
            }
			if (schoolReviewDto.getIsApproved() != null && !schoolReviewDto.getIsApproved().toString().isEmpty()) {
				LOGGER.info("schoolReviewDto.getIsApproved() ------------ " + schoolReviewDto.getIsApproved());
				Boolean isApproved = Boolean.parseBoolean(schoolReviewDto.getIsApproved().toString());
				whereClause += " and m.is_Approved = " + isApproved + " ";
			}
			if (schoolReviewDto.getSchoolid() != null && !schoolReviewDto.getSchoolid().toString().isEmpty()) {
				LOGGER.info("schoolReviewDto.getSchoolid() ------------ " + schoolReviewDto.getSchoolid());
				whereClause += " and m.is_Approved = true and m.school_id = " + schoolReviewDto.getSchoolid() + " ";
			}
			LOGGER.info("whereClause ------------ " + whereClause );
			LOGGER.info("limitStr ------------ " + orderByStr + limitStr);
			String abc =  (orderByStr + limitStr);
			LOGGER.info("abc ------------ " + abc);
			
			
			List<SchoolReviewVo> schoolReviewMstVo = schoolreviewmstdao.getallreviews(whereClause, abc);
//			LOGGER.info("schoolReviewMstVo ------------ " + schoolReviewMstVo.get(0) );
			noOfRecords = schoolreviewmstdao.getReviewcount(whereClause);
			responseEntity = new ResponseEntity<ApiResponse>(
					new ApiResponse(200, "success", "Review  Successful.", schoolReviewMstVo, noOfRecords),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	public ResponseEntity<ApiResponse> updateStatusByid(long id, boolean status) {
		ResponseEntity<ApiResponse> responseEntity = null;
		SchoolReviewVo artical = null;
        
        try {
            artical = schoolreviewmstdao.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("review With Id %s Not Found", id)));
          
        	  schoolreviewmstdao.updateStatusById(status,id);
        	  responseEntity = new ResponseEntity<ApiResponse>(
                      new ApiResponse(200, "success", "review Status Updated", artical),
                      HttpStatus.OK);    
             
		  

    } catch (Exception e) {
        e.printStackTrace();
        responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return responseEntity;
    
	}

	public ResponseEntity<ApiResponse> getReviewsById(Long id) {
		ResponseEntity<ApiResponse> responseEntity = null;
		List<SchoolReviewVo> entry = null;
		SchoolMstVo school = null;
		try {
		  school = schoolMstDao.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("school With Id %s Not Found", id)));	
		  entry = schoolreviewmstdao.findReviewListByid(id);
      	  responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "review list shown", entry),
                    HttpStatus.OK);    
		} catch (Exception e) {
			e.printStackTrace();
	        responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

}
