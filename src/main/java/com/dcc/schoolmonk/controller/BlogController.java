package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.BlogDto;
import com.dcc.schoolmonk.service.BlogService;
import com.dcc.schoolmonk.vo.BlogMstVo;
import com.dcc.schoolmonk.vo.NewsArticlesVo;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/blog")
public class BlogController{
    
       @Autowired
       private BlogService blogService;

       @RequestMapping(method = RequestMethod.POST, value = "/addblog")
	     public ResponseEntity<ApiResponse> addblog(@RequestBody BlogMstVo blogMstVo,HttpServletRequest request) {
		   return blogService.addnewblog( blogMstVo);
        }
        
       @RequestMapping(method = RequestMethod.POST, value = "/editblog")
       public ResponseEntity<ApiResponse> editblog(@RequestBody BlogMstVo blogMstVo,HttpServletRequest request) {
	  	 return blogService.editblog( blogMstVo);
       }

       @RequestMapping(method = RequestMethod.POST, value = "/fetchallblog")
       public ResponseEntity<ApiResponse> FetchAllblog(@RequestBody BlogDto blogDto) {
		   return blogService.FetchAllBlog( blogDto);
       }
       
      @GetMapping("/findBlogById/{id}")
   	  public ResponseEntity<ApiResponse> findBlogById(@PathVariable(value="id") long id){
   		return blogService.findBlogById(id);
   	  }
      
     @GetMapping("/updateBlogStatusByid")
  	 public ResponseEntity<ApiResponse> updateBlogStatusById(@RequestParam long id,@RequestParam boolean status){
  		return blogService.updateBlogStatusByid(id,status);
  	}
  	 
    @GetMapping("/findbySlug")
    public ResponseEntity<ApiResponse> findBlogBySlug(@RequestParam String slug){
  		return blogService.findblogBySlug(slug);
  	}   
}
