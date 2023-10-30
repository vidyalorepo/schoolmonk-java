package com.dcc.schoolmonk.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.dao.BlogDao;
import com.dcc.schoolmonk.dto.BlogDto;
import com.dcc.schoolmonk.exception.ResourceNotFound;
import com.dcc.schoolmonk.utils.GlobalConstants;
import com.dcc.schoolmonk.vo.BlogMstVo;
import com.dcc.schoolmonk.vo.NewsArticlesVo;

@Service
public class BlogService {
    private static final Logger LOGGER = LogManager.getLogger(BlogService.class);
     @Autowired
     private BlogDao blogDao;

    public ResponseEntity<ApiResponse> addnewblog(BlogMstVo blogMstVo) {
        ResponseEntity<ApiResponse> responseEntity = null;
        try {
            String slugNAme= CommmonFunction.slugNameGeneration(blogMstVo.getTitle().trim());
            blogMstVo.setSlug(slugNAme);
            BlogMstVo blogMstVoSave=blogDao.save(blogMstVo);
            responseEntity= new ResponseEntity<ApiResponse>(
                new ApiResponse(200, "success", "New Blog Saved Successful.", blogMstVoSave, 1), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
       return responseEntity;
    }

    public ResponseEntity<ApiResponse> editblog(BlogMstVo blogMstVo) {
        ResponseEntity<ApiResponse> responseEntity = null;
        try {
            BlogMstVo blog=blogDao.findById(blogMstVo.getId()).orElseThrow(()->new ResourceNotFound(String.format("BlogMST With Id: %s not found", blogMstVo.getId())));
            blog.setBlogDetails(blogMstVo.getBlogDetails());
            blog.setTitle(blogMstVo.getTitle());
            blog.setDescription(blogMstVo.getDescription());
            blog.setStatus(blogMstVo.isStatus());
            blog.setBlogCategory(blogMstVo.getBlogCategory());
            blog=blogDao.save(blog);
            responseEntity= new ResponseEntity<ApiResponse>(
                new ApiResponse(200, "success", "Blog updated Successful.", blog, 1), HttpStatus.CREATED);
        } catch (Exception e) {
        e.printStackTrace();
        responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    public ResponseEntity<ApiResponse> FetchAllBlog(BlogDto blogDto) {
        ResponseEntity<ApiResponse> responseEntity = null;
        int noOfRecords = 0;
        String whereClause = " where 1";
        String limitStr = "";
        String orderByStr = "";
                try {
            if (null == blogDto.getPage() && null == blogDto.getSize()) {
                // do nothing
            } else {
                Integer size = (null != blogDto.getSize()) ? blogDto.getSize()
                        : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
                Integer page = (null != blogDto.getPage() && blogDto.getPage() > 1)
                        ? (blogDto.getPage() - 1) * size
                        : 0;
                limitStr = " limit " + size + " offset " + page;
                LOGGER.info("limitStr..." + limitStr);
            }
            if (null != blogDto.getSortBy()
                    && !blogDto.getSortBy().trim().isEmpty()) {
                orderByStr = " order by " + blogDto.getSortBy().trim() + " "
                        + blogDto.getSortDir();
            } else {
                orderByStr = " order by id DESC";
            }
            if (null != blogDto.getTitle()
                    && !blogDto.getTitle().trim().isEmpty()) {
                LOGGER.info("blogDto.getTitle() ------------ " + blogDto.getTitle());
                whereClause += " and title like '%" + blogDto.getTitle().trim() + "%'";
            }
            if (null != blogDto.getStatus()
                    && !blogDto.getStatus().trim().isEmpty()) {
                LOGGER.info("blogDto.getStatus() ------------ " + blogDto.getStatus());
                whereClause += " and status = " + Boolean.parseBoolean(blogDto.getStatus()) + " ";
            }
            if (null != blogDto.getBlogCategory() && !blogDto.getBlogCategory().trim().isEmpty()) {
                LOGGER.info("blogDto.getBlogCategory() ------------ " + blogDto.getBlogCategory());
                whereClause += " and blog_category like '%" + blogDto.getBlogCategory().trim() + "%'";
            }
            LOGGER.info("whereClause ------------ " + whereClause);
            List<BlogMstVo> newsArticlesVo = blogDao.getAllBlog(whereClause, limitStr, orderByStr);
            noOfRecords = blogDao.getAllBlogCount(whereClause);
            responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "All Blog Fetch Successful.", newsArticlesVo, noOfRecords),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return responseEntity;
    }

	public ResponseEntity<ApiResponse> findBlogById(long id) {
		ResponseEntity<ApiResponse> responseEntity = null;
        BlogMstVo artical = null;

        try {
            artical = blogDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("Blog With Id %s Not Found", id)));

                    responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "Blog Fetch Success", artical),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
	}

	public ResponseEntity<ApiResponse> updateBlogStatusByid(long id, boolean status) {
		ResponseEntity<ApiResponse> responseEntity = null;
        BlogMstVo artical = null;

        try {
            artical = blogDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("Blog With Id %s Not Found", id)));

            int res= blogDao.updateStatus(id,status);
            if (res>0) {
                artical = blogDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(String.format("Blog With Id %s Not Found", id)));
                 responseEntity = new ResponseEntity<ApiResponse>(
                    new ApiResponse(200, "success", "Blog Status Updated", artical),
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

    public ResponseEntity<ApiResponse> findblogBySlug(String slug) {
        ResponseEntity<ApiResponse> responseEntity = null;
        BlogMstVo blog = null;
        try {
          blog= blogDao.findbySlug(slug);
          responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(200, "success", "Blog find Success.", blog),HttpStatus.OK);   
        } catch (Exception e) {
        e.printStackTrace();
        responseEntity = new ResponseEntity<ApiResponse>(new ApiResponse(500, "error", "INTERNAL SERVER ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
     


     
}
