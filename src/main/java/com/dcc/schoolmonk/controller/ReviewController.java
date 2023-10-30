package com.dcc.schoolmonk.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.dcc.schoolmonk.dto.SchoolReviewDto;
import com.dcc.schoolmonk.exception.SchoolmonkAppApplicationException;
import com.dcc.schoolmonk.service.ReviewService;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.SchoolReviewVo;
import com.dcc.schoolmonk.vo.StudentMstVo;
import com.dcc.schoolmonk.vo.UserVo;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/reviewcontroller")        
public class ReviewController {
    
    private static final Logger LOGGER = Logger.getLogger(ReviewController.class);
	@Autowired
	ReviewService reviewservice;

	@RequestMapping(method = RequestMethod.POST, value = "/addreview")
	public ResponseEntity<ApiResponse> addReview(@RequestBody SchoolReviewVo schoolreviewmstvo) {
		return reviewservice.AddReview(schoolreviewmstvo);

	}
	
    @RequestMapping(method = RequestMethod.POST, value = "/getallreview")
	public ResponseEntity<ApiResponse> getallReview(@RequestBody SchoolReviewDto schoolReviewDto) {

		LOGGER.info("/getallreview()::Called........");
		return reviewservice.getallreviews(schoolReviewDto);

	}
	
	@GetMapping("/updateStatusByid")
	public ResponseEntity<ApiResponse> updateStatusById(@RequestParam long id, @RequestParam boolean status){
	return reviewservice.updateStatusByid(id,status);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getallreviews/{id}")
	public ResponseEntity<ApiResponse> getReviewsList(@PathVariable Long id) {
		return  reviewservice.getReviewsById(id);

	}

}
