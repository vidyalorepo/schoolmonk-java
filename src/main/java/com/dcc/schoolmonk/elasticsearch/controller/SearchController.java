package com.dcc.schoolmonk.elasticsearch.controller;

import javax.servlet.http.HttpServletRequest;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.elasticsearch.document.PostFilterSearchRequestVo;
import com.dcc.schoolmonk.elasticsearch.document.SchoolSearchInputVo;
import com.dcc.schoolmonk.elasticsearch.document.SearchInputVo;
import com.dcc.schoolmonk.elasticsearch.service.SearchService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/advanced-search")
public class SearchController {

    private static final Logger LOGGER = Logger.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    
    @RequestMapping(method = RequestMethod.POST, value = "/searchByKeyword")
    public ResponseEntity<ApiResponse> searchSchoolsByKeyword(@RequestBody SchoolSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return searchService.searchByKeyword(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getRelatedResults")
    public ResponseEntity<ApiResponse> relatedResults(@RequestBody SchoolSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return searchService.returnRelatedSearches(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/autocomplete")
    public ResponseEntity<ApiResponse> searchSchoolsAutocomplete(@RequestBody SchoolSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return searchService.createAutocompletionEngine("schoolautocompleteindex", inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/city-autocomplete")
    public ResponseEntity<ApiResponse> searchCityAutocomplete(@RequestBody SchoolSearchInputVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return searchService.createAutocompletionEngineForCity("cityautocompleteindex", inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filter")
    public ResponseEntity<ApiResponse> filterResults(@RequestBody PostFilterSearchRequestVo inputVo,
            HttpServletRequest request) {
        LOGGER.info("SchoolUserController :: getUsersBySearch :: Entering getUsersByCustomSearch method");

        try {
            return searchService.filterSearchResults(inputVo);
        } catch (Exception e) {
            e.getStackTrace();
        }

        LOGGER.info("SchoolUserController :: getUserBySearch:" + "Exiting getUserBySearch method");
        ApiResponse apiResponse = new ApiResponse(401, "ERROR", "");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    } 

}
