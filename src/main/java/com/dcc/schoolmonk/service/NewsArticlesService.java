package com.dcc.schoolmonk.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.dao.NewsArticlesDao;
import com.dcc.schoolmonk.dto.NewsArticalDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.utils.GlobalConstants;
import com.dcc.schoolmonk.vo.NewsArticlesVo;

@Service
public class NewsArticlesService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private NewsArticlesDao newsarticlesdao;


 
    public NewsArticlesService(NewsArticlesDao newsarticlesdao) {
        this.newsarticlesdao = newsarticlesdao;
    }

    public ResponseEntity<ApiResponse> addnewsarticle(NewsArticlesVo newsarticlesvo) {
        try {
           String slugNAme= CommmonFunction.slugNameGeneration(newsarticlesvo.getSubject());
           newsarticlesvo.setNewsSlug(slugNAme);
            NewsArticlesVo artical = newsarticlesdao.save(newsarticlesvo);
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Saved Successful.", artical, 1), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("News Artical Save" + e);
            e.getMessage();
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(500, "success", "News Artical Saved Failed.", newsarticlesvo),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(500, "error", "News Artical Saved Failed.", newsarticlesvo),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> getallNewsArtical(NewsArticalDto newsArticalDto) {
        ResponseEntity<ApiResponse> responseEntity = null;
        int noOfRecords = 0;
        String whereClause = " where 1";
        String limitStr = "";
        String orderByStr = "";
        try {
            if (null == newsArticalDto.getPage() && null == newsArticalDto.getSize()) {
                // do nothing
            } else {
                Integer size = (null != newsArticalDto.getSize()) ? newsArticalDto.getSize()
                        : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
                Integer page = (null != newsArticalDto.getPage() && newsArticalDto.getPage() > 1)
                        ? (newsArticalDto.getPage() - 1) * size
                        : 0;
                limitStr = " limit " + size + " offset " + page;
                LOGGER.info("limitStr..." + limitStr);
            }
            if (null != newsArticalDto.getSortBy()
                    && !newsArticalDto.getSortBy().trim().isEmpty()) {
                orderByStr = " order by " + newsArticalDto.getSortBy().trim() + " "
                        + newsArticalDto.getSortDir();
            } else {
                orderByStr = " order by id DESC";
            }
            if (null != newsArticalDto.getSubject()
                    && !newsArticalDto.getSubject().trim().isEmpty()) {
                LOGGER.info("newsArticalDto.getSubject() ------------ " + newsArticalDto.getSubject());
                whereClause += " and subject like '%" + newsArticalDto.getSubject().trim() + "%'";
            }
            if (null != newsArticalDto.getNewsdate() && !newsArticalDto.getNewsdate().trim().isEmpty()) {
                LOGGER.info("newsArticalDto.getNewsdate() ------------ " + newsArticalDto.getNewsdate());
                whereClause += " and date( news_date) like'%" + newsArticalDto.getNewsdate().toString() + "%' ";
            }
            if (null != newsArticalDto.getStatus()
                    && !newsArticalDto.getStatus().trim().isEmpty()) {
                LOGGER.info("newsArticalDto.getStatus() ------------ " + newsArticalDto.getStatus());
                whereClause += " and status = " + Boolean.parseBoolean(newsArticalDto.getStatus()) + " ";
            }
            LOGGER.info("whereClause ------------ " + whereClause);
            List<NewsArticlesVo> newsArticlesVo = newsarticlesdao.getNewsArticallist(whereClause, limitStr, orderByStr);
            noOfRecords = newsarticlesdao.getcountNewsArtical(whereClause);
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Fetch Successful.", newsArticlesVo, noOfRecords),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    public ResponseEntity<ApiResponse> deleteByid(long id) {
        ResponseEntity<ApiResponse> responseEntity = null;

        try {
            newsarticlesdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("News Artical With Id %s Not Found", id)));
            newsarticlesdao.deleteById(id);
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Deleted Success"),
                    HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    public ResponseEntity<ApiResponse> updateByid(long id, NewsArticalDto newsArticalDto) {
        ResponseEntity<ApiResponse> responseEntity = null;
        NewsArticlesVo artical = null;

        try {
            artical = newsarticlesdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("News Artical With Id %s Not Found", id)));

            artical.setStatus(Boolean.parseBoolean(newsArticalDto.getStatus()));
            artical.setSubject(newsArticalDto.getSubject());
            artical.setNoticeBody(newsArticalDto.getNoticeBody());
            NewsArticlesVo updArticlesVo = newsarticlesdao.save(artical);
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Update Success", updArticlesVo),
                    HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<ApiResponse> findNewsArticalById(long id) {
        ResponseEntity<ApiResponse> responseEntity = null;
        NewsArticlesVo artical = null;

        try {
            artical = newsarticlesdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("News Artical With Id %s Not Found", id)));


                    responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Fetch Success", artical),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<ApiResponse> updateStatusByid(long id,boolean status) {
        ResponseEntity<ApiResponse> responseEntity = null;
        NewsArticlesVo artical = null;

        try {
            artical = newsarticlesdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("News Artical With Id %s Not Found", id)));

            int res= newsarticlesdao.updateStatus(id,status);
            if (res>0) {
                artical = newsarticlesdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("News Artical With Id %s Not Found", id)));
                 responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Status Updated", artical),
                    HttpStatus.OK);    
            }else{
                 responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(400, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.BAD_REQUEST);
            }

         

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

	public ResponseEntity<ApiResponse> findNewsArticalBySlug(String slug) {
		ResponseEntity<ApiResponse> responseEntity = null;
        NewsArticlesVo artical = null;
        try {
            artical = newsarticlesdao.findByslug(slug);
            if (artical==null) {
            	responseEntity = new ResponseEntity<ApiResponse>(
                        new ApiResponse(400, "not found", "News Artical not found with slug", slug),
                        HttpStatus.BAD_REQUEST);
            }else {         
            	responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "News Artical Fetch Success", artical),
                    HttpStatus.OK);
            }
              
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
		return responseEntity;
	}


}
