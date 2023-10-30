package com.dcc.schoolmonk.service;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dcc.schoolmonk.common.ApiResponse;

import com.dcc.schoolmonk.dao.SchoolCityMstDao;
import com.dcc.schoolmonk.vo.SchoolCityMstVo;


@Service
public class LocationListService {
	 private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	 private SchoolCityMstDao SchoolCityMstDao;


		public LocationListService(com.dcc.schoolmonk.dao.SchoolCityMstDao schoolCityMstDao) {
		super();
		SchoolCityMstDao = schoolCityMstDao;
	}


      public ResponseEntity<ApiResponse> getLocationlist() {
  		ResponseEntity<ApiResponse> responseEntity = null;
  		try {
			 List<SchoolCityMstVo> listofLocationListVo=SchoolCityMstDao.findAllLocation();
		       responseEntity = new ResponseEntity<ApiResponse>(
	                    new ApiResponse(200, "success", "LocationList Fetch Successful.", listofLocationListVo),
	                    HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	            
	        }
			

	return responseEntity;
	}
	

}
