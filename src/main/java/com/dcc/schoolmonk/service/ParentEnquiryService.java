package com.dcc.schoolmonk.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.ParentEnquiryDao;
import com.dcc.schoolmonk.dto.ParentEnquiryDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.utils.GlobalConstants;
import com.dcc.schoolmonk.vo.NewsArticlesVo;
import com.dcc.schoolmonk.vo.ParentEnquiryVo;

@Service
public class ParentEnquiryService {

        private static final Logger LOGGER = LogManager.getLogger(ParentEnquiryService.class);

    @Autowired
    ParentEnquiryDao parentEnquiryDao;

    public ResponseEntity<ApiResponse> save(ParentEnquiryVo parentEnquiryVo) {
        ResponseEntity<ApiResponse> responseEntity = null;

        try {
            ParentEnquiryVo parentEnquiryVoSave = parentEnquiryDao.save(parentEnquiryVo);
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "Parent enquery has been saved.", parentEnquiryVoSave),
                    HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<ApiResponse> getallParentEnquiry(ParentEnquiryDto parentEnquiryDto) {
            ResponseEntity<ApiResponse> responseEntity = null;
            int noOfRecords = 0;
            String whereClause = " where 1";
            String limitStr = "";
            String orderByStr = "";
            try {
                if (null == parentEnquiryDto.getPage() && null == parentEnquiryDto.getSize()) {
                // do nothing
            } else {
                Integer size = (null != parentEnquiryDto.getSize()) ? parentEnquiryDto.getSize()
                        : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
                Integer page = (null != parentEnquiryDto.getPage() && parentEnquiryDto.getPage() > 1)
                        ? (parentEnquiryDto.getPage() - 1) * size
                        : 0;
                limitStr = " limit " + size + " offset " + page;
                LOGGER.info("limitStr..." + limitStr);
            }
            if (null != parentEnquiryDto.getSortBy()
                    && !parentEnquiryDto.getSortBy().trim().isEmpty()) {
                orderByStr = " order by " + parentEnquiryDto.getSortBy().trim() + " "
                        + parentEnquiryDto.getSortDir();
            } else {
                orderByStr = " order by id DESC";
            }
            if (null != parentEnquiryDto.getName()
                    && !parentEnquiryDto.getName().trim().isEmpty()) {
                LOGGER.info("parentEnquiryDto.getName() ------------ " + parentEnquiryDto.getName());
                whereClause += " and (first_name like '%"+ parentEnquiryDto.getName().trim()+"%' or last_name like '%" + parentEnquiryDto.getName().trim() + "%' )";
            }
            if (null != parentEnquiryDto.getEmail() && !parentEnquiryDto.getEmail().trim().isEmpty()) {
                LOGGER.info("parentEnquiryDto.getEmail() ------------ " + parentEnquiryDto.getEmail());
                whereClause += " and email = '" + parentEnquiryDto.getEmail().trim() + "' ";
            }
            if (null != parentEnquiryDto.getPhone()
                    && !parentEnquiryDto.getPhone().trim().isEmpty()) {
                LOGGER.info("parentEnquiryDto.getPhone() ------------ " + parentEnquiryDto.getPhone());
                whereClause += " and phone = '" + parentEnquiryDto.getPhone().trim()  + "'";
            }
           if (null != parentEnquiryDto.getCity()
                    && !parentEnquiryDto.getCity().trim().isEmpty()) {
                LOGGER.info("parentEnquiryDto.getCity() ------------ " + parentEnquiryDto.getCity());
                whereClause += " and city like'%" + parentEnquiryDto.getCity().trim()  + "%'";
            }
            LOGGER.info("whereClause ------------ " + whereClause);

            List<ParentEnquiryVo> parentEnquiryVo = parentEnquiryDao.getallEnquiry(whereClause, limitStr, orderByStr);
            noOfRecords = parentEnquiryDao.getcountenquiry(whereClause);

            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "parent enquiry fetch successful.", parentEnquiryVo,noOfRecords),
                    HttpStatus.OK);
                
            } catch (Exception e) {
                e.printStackTrace();
                responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

        return responseEntity;
    }

    public ResponseEntity<ApiResponse> deleteById(Long id) {
        ResponseEntity<ApiResponse> responseEntity = null;
        ParentEnquiryVo parentEnquiryVo= parentEnquiryDao.findById(id).orElseThrow(()-> new ResourceNotFound(String.format("PARENT_ENQUIRY_MST witd id : %s not found", id)));
        try {
            if (parentEnquiryVo != null) {
                
                 parentEnquiryDao.deleteById(id);
            }
        responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "parent enquiry deleted success By ID."),
                    HttpStatus.OK);
        } catch (Exception e) {
              e.printStackTrace();
                responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<ApiResponse> findByid(Long id) {
        ResponseEntity<ApiResponse> responseEntity = null;
        ParentEnquiryVo parentEnquiryVo= parentEnquiryDao.findById(id).orElseThrow(()-> new ResourceNotFound(String.format("PARENT_ENQUIRY_MST witd id : %s not found", id)));
        try {
        responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "parent enquiry Fetch success.",parentEnquiryVo),
                    HttpStatus.OK);
        } catch (Exception e) {
          e.printStackTrace();
          responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
