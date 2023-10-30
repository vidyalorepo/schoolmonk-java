package com.dcc.schoolmonk.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.HeaderTagMstDao;
import com.dcc.schoolmonk.dto.HeaderTagDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.vo.HeaderTagMstVo;

@Service
public class HeaderTagService {
    private static final Logger LOGGER = LogManager.getLogger(HeaderTagService.class);
    private HeaderTagMstDao headertagmstdao;

    public HeaderTagService(HeaderTagMstDao headertagmstdao) {
        super();
        this.headertagmstdao = headertagmstdao;
    }

    public ResponseEntity<ApiResponse> addheaderTag(HeaderTagMstVo headertagmstvo) {
        try {

            if (headertagmstvo.getIsPublish() == false) {
                HeaderTagMstVo art = headertagmstdao.save(headertagmstvo);
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(200, "success", "HeaderTag Saved Successful.", art), HttpStatus.CREATED);
            } else {
                headertagmstdao.updateStatus();
                HeaderTagMstVo art = headertagmstdao.save(headertagmstvo);
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(200, "success", "HeaderTag Saved Successful.", art), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(500, "error", "HeaderTag Saved Failed.", headertagmstvo),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    public ResponseEntity<ApiResponse> getallHeaderTag(HeaderTagDto headerTagDto) {
        ResponseEntity<ApiResponse> responseEntity = null;
        try {
            Sort sort = Sort.by("id").descending();

            Pageable pageable = PageRequest.of(headerTagDto.getPage() - 1, headerTagDto.getSize(), sort);

            Page<HeaderTagMstVo> headerTagsPage = headertagmstdao.findAll(pageable);

            List<HeaderTagMstVo> Headertags = headerTagsPage.getContent();

            int total = headertagmstdao.countTotal();
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "HeaderTag Fetch Successful.", Headertags, total),
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
        HeaderTagMstVo artical = null;

        try {
            artical = headertagmstdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("Header tag With Id %s Not Found", id)));
            if (status == true) {
                headertagmstdao.updateStatus();
                headertagmstdao.updateStatusById(status, id);
                responseEntity = new ResponseEntity<ApiResponse>(
                        new ApiResponse(200, "success", "HeaderTag Status Updated", artical),
                        HttpStatus.OK);

            }

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    public ResponseEntity<ApiResponse> fetchByTag() {

        ResponseEntity<ApiResponse> responseEntity = null;
        HeaderTagMstVo artical = null;

        try {
            artical = headertagmstdao.fetchById();
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "Tags Fetch Success", artical),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
