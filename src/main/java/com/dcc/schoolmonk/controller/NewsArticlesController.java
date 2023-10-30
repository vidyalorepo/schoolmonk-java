package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.NewsArticalDto;
import com.dcc.schoolmonk.service.NewsArticlesService;
import com.dcc.schoolmonk.vo.NewsArticlesVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/newsarticlescontroller")
public class NewsArticlesController {
	private static final Logger LOGGER = Logger.getLogger(NewsArticlesController.class);
	
	@Autowired
	NewsArticlesService newsarticlesservice;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addnewsartical")
	public ResponseEntity<ApiResponse> addNewsArtiacl(@RequestBody NewsArticlesVo newsarticlesvo,HttpServletRequest request) {
		return newsarticlesservice.addnewsarticle( newsarticlesvo);
	}

	@PostMapping("/getallnewsartical")
	public ResponseEntity<ApiResponse> getallnews(@RequestBody NewsArticalDto newsArticalDto){
	    LOGGER.info("/getallnewsartical()::Called........");
		return newsarticlesservice.getallNewsArtical(newsArticalDto);
	}

	@GetMapping("/deleteByid/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable(value="id") long id){
		return newsarticlesservice.deleteByid(id);
	}

	@PostMapping("/updateByid/{id}")
	public ResponseEntity<ApiResponse> updateById(@PathVariable(value="id") long id,@RequestBody NewsArticalDto newsArticalDto){
		return newsarticlesservice.updateByid(id,newsArticalDto);
	}

	@GetMapping("/updateStatusByid")
	public ResponseEntity<ApiResponse> updateStatusById(@RequestParam long id,@RequestParam boolean status){
		return newsarticlesservice.updateStatusByid(id,status);
	}

	@GetMapping("/findNewsArticalById/{id}")
	public ResponseEntity<ApiResponse> findNewsArticalById(@PathVariable(value="id") long id){
		return newsarticlesservice.findNewsArticalById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findNewsArticalBySlug/{slug}")
    public ResponseEntity<ApiResponse> findNewsArticalBySlug(@PathVariable(value="slug") String slug){
	  return newsarticlesservice.findNewsArticalBySlug(slug);
  }
}
