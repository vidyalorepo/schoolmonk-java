package com.dcc.schoolmonk.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.dao.EnhanceNoticeBoardDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class EnhanceNoticeBoardService {

	private static final Logger LOGGER = Logger.getLogger(EnhanceNoticeBoardService.class);

	@Autowired
	SchoolMstDao schoolMstDao;

	@Autowired
	EnhanceNoticeBoardDao noticeBoardDao;
	
	
	public ResponseEntity<ApiResponse> saveNotice(EnhanceNoticeBoardVo vo, Long userId) {
		ApiResponse apiResponse = null;
		EnhanceNoticeBoardVo dataSavedVo = new EnhanceNoticeBoardVo();		
		try {
			if(null == vo.getId()){
				UUID uuid = UUID.randomUUID();
				LOGGER.info("Notice uuid --------------- " + uuid.toString());
				vo.setNoticeId(uuid.toString());
				vo.setCreatedBy(userId);
				vo.setCreatedOn(new Date());
				vo.setUpdatedOn(new Date());
				dataSavedVo = vo;
			} else {
				EnhanceNoticeBoardVo getData = noticeBoardDao.findById(vo.getId()).get();
				getData.setId(vo.getId());
				getData.setCreatedBy(userId);
				getData.setUpdatedBy(userId);
				getData.setUpdatedOn(new Date());
				getData.setNoticeContent(vo.getNoticeContent());
				getData.setNoticeType(vo.getNoticeType());
				getData.setStartsOn(vo.getStartsOn());
				getData.setExpiresOn(vo.getExpiresOn());
				dataSavedVo = getData;
			}
			EnhanceNoticeBoardVo dataSaved = noticeBoardDao.save(dataSavedVo);			
			LOGGER.info("Notice saved --------------- " + dataSaved);
			
			//We have to do something here for notification : TBD

			apiResponse = new ApiResponse(200, "success", "Notice Saved Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Notice Save un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<ApiResponse> getNoticeDetails(EnhanceNoticeBoardVo vo) {
		LOGGER.info("getNoticeList :: noticeType :: "+vo.getNoticeType());
		ApiResponse apiResponse = null;
				
		try {
			EnhanceNoticeBoardVo dataSaved = noticeBoardDao.findByNoticeId(vo.getNoticeId());			
			LOGGER.info("getNoticeDetails fetched --------------- " + dataSaved);

			apiResponse = new ApiResponse(200, "success", "Notice fetched Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Notice fetch un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<ApiResponse> getNoticeList(String noticeType) {
		LOGGER.info("getNoticeList :: noticeType :: "+noticeType);
		ApiResponse apiResponse = null;
				
		try {
			List<EnhanceNoticeBoardVo> data = noticeBoardDao.findByNoticeType(noticeType);			
			LOGGER.info("Notice list --------------- " + data);

			apiResponse = new ApiResponse(200, "success", "Notice fetched Successfully", data); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "erroe", "Notice Save un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public ResponseEntity<ApiResponse> getAllNoticeList(UserVo userVo) {
		LOGGER.info("getAllNoticeList :: getAllNoticeList :: ");
		ApiResponse apiResponse = null;
		if(null != userVo) {
			try {
				List<EnhanceNoticeBoardVo> data = (List<EnhanceNoticeBoardVo>) noticeBoardDao.findAll();			
				LOGGER.info("Notice list --------------- " + data);

				apiResponse = new ApiResponse(200, "success", "Notice fetched Successfully", data); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new ApiResponse(406, "erroe", "Notice Save un-successful", null); 
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
			}
		}else {
			apiResponse = new ApiResponse(401, "erroe", "Un-Authorized", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	public ResponseEntity<ApiResponse> getNoticeDetailsById(String noticeId) {
		LOGGER.info("getNoticeList :: noticeType :: "+noticeId);
		ApiResponse apiResponse = null;
				
		try {
			EnhanceNoticeBoardVo dataSaved = noticeBoardDao.findByNoticeId(noticeId);			
			LOGGER.info("getNoticeDetails fetched --------------- " + dataSaved);

			apiResponse = new ApiResponse(200, "success", "Notice fetched Successfully", dataSaved); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(406, "error", "Notice fetch un-successful", null); 
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

public ResponseEntity<ApiResponse> getNoticeListBySchoolId(EnhanceNoticeBoardVo inputVo, Long schoolId) {
		
		LOGGER.info("EnhanceNoticeBoardVo:: getNoticeListBySchoolId :: Entering getNoticeListBySchoolId method:: ");
		ApiResponse apiResponse = null;
		String whereClause = "";
		String limitStr = "";
		String orderByStr = "";
		if (null == inputVo.getPage() && null == inputVo.getSize()) {
			// do nothing
		} else {
			Long size = (null != inputVo.getSize()) ? inputVo.getSize() : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
			Long page = (null != inputVo.getPage() && inputVo.getPage() > 1) ? (inputVo.getPage() - 1) * size : 0;
			limitStr = " limit " + size + " offset " + page;
			LOGGER.info("limitStr..." + limitStr);
		}
		if (null != inputVo.getOrderByColName() && !inputVo.getOrderByColName().trim().isEmpty() &&
				null != inputVo.getOrderBy() && !inputVo.getOrderBy().trim().isEmpty()) {
			orderByStr = " order by " + inputVo.getOrderByColName().trim() + " " + inputVo.getOrderBy();
		} else {
			orderByStr = " order by ID ASC";
		}
		LOGGER.info("orderByStr ------------ " + orderByStr);
		
		if (null != inputVo.getNoticeContent() && !inputVo.getNoticeContent().trim().isEmpty()) {
			LOGGER.info("inputVo.getNoticeContent() ------------ " + inputVo.getNoticeContent());
			whereClause += " and notice_content like '%" + inputVo.getNoticeContent().trim() + "%'";
		}
		
		if (null != inputVo.getNoticeSubCat() && !inputVo.getNoticeSubCat().trim().isEmpty()) {
			LOGGER.info("inputVo.getNoticeSubCat() ------------ " + inputVo.getNoticeSubCat());
			whereClause += " and notice_sub_cat like '%" + inputVo.getNoticeSubCat().trim() + "%'";
		}
		
		if (null != inputVo.getNoticeType() && !inputVo.getNoticeType().trim().isEmpty()) {
			LOGGER.info("inputVo.getNoticeType() ------------ " + inputVo.getNoticeType());
			whereClause += " and notice_type like '%" + inputVo.getNoticeType().trim() + "%'";
		}
		
		List<EnhanceNoticeBoardVo> data = noticeBoardDao.findAllNoticeInfoBySchoolId(whereClause, limitStr, orderByStr);
//		for (EnhanceNoticeBoardVo voNew : data) {
//			voNew = (EnhanceNoticeBoardVo) getNoticeDetailsBySchoolId(schoolId);
//		}
		int totalNo = noticeBoardDao.getTotalCountByInput(whereClause, "NOTICE_BOARD").intValue();
		LOGGER.info("EnhanceNoticeBoardService:: getNoticeListBySchoolId:: Exiting getNoticeListBySchoolId method:: ");
		apiResponse = new ApiResponse(200, "success", "Notice List fetched Successfully", data, totalNo);
		LOGGER.info("NearMisssReportService::findAllBySchoolId::Exiting...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		
	}
	
	
}
