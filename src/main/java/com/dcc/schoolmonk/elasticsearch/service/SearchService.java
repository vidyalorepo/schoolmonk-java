package com.dcc.schoolmonk.elasticsearch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.DistrictMstDao;
import com.dcc.schoolmonk.dao.SchoolAdmissionDtlDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.StateMstDao;
import com.dcc.schoolmonk.elasticsearch.document.CityAutocompleteDocument;
import com.dcc.schoolmonk.elasticsearch.document.CitySearchHitVo;
import com.dcc.schoolmonk.elasticsearch.document.PostFilterSearchRequestVo;
import com.dcc.schoolmonk.elasticsearch.document.SchoolAutocompleteDocument;
import com.dcc.schoolmonk.elasticsearch.document.SchoolMstDocument;
import com.dcc.schoolmonk.elasticsearch.document.SchoolSearchHitVo;
import com.dcc.schoolmonk.elasticsearch.document.SchoolSearchInputVo;
import com.dcc.schoolmonk.elasticsearch.repository.SchoolRepository;
import com.dcc.schoolmonk.elasticsearch.util.SearchUtil;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {
    private final Logger LOGGER = Logger.getLogger(SearchService.class);
    private final ObjectMapper MAPPER = new ObjectMapper();

   @Autowired
   SchoolRepository schoolRepository;

    @Autowired
    SchoolMstDao schoolMstDao;

    @Autowired
    StateMstDao stateMstDao;

    @Autowired
    DistrictMstDao districtMstDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    SchoolAdmissionDtlDao schoolAdmissionDtlDao;

   private final RestHighLevelClient client;

   public SearchService(RestHighLevelClient client) {
       this.client = client;
   }

    public ResponseEntity<ApiResponse> searchByKeyword(SchoolSearchInputVo inputVo) {
        LOGGER.info("SchoolMstService:: getUserByCustomSearch:: Entering getUserByCustomSearch method:: ");

        ApiResponse apiResponse = null;
        List<SchoolMstVo> schools = new ArrayList<>();
        List<SchoolMstDocument> schoolHits = new ArrayList<>();
        SearchRequest request = SearchUtil.buildSearchRequest("schoolmstindex", inputVo);
        SchoolMstDocument topHit = null;
        if (request == null) {
            LOGGER.error("cannot build search request");
            apiResponse = new ApiResponse(404, "not found", "not found", null, 0);
        }
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            SearchHit[] hits = response.getHits().getHits();
            Long noOfData = response.getHits().getTotalHits().value;
            for (SearchHit hit : hits) {
                schoolHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolMstDocument.class));
                schools.add(
                        schoolMstDao.findById(Long.parseLong(hit.getId())).orElse(null));

            }

            Map<String, List<String>> responseMap = processAggregations(response);
            PostFilterSearchRequestVo dropdownFilterInfo = extractDropdownFilterInfo(responseMap);

            List<SchoolMstVo> similarSchools = getSimilarSchools("schoolmstindex", schoolHits);

            // Added By Kousik for details data
            List<SchoolMstVo> searchSchoolsRes = new ArrayList<>();
            for (SchoolMstVo vo : schools) {
                // vo = getSchoolInfo(vo);
                LOGGER.info("searchSchoolsRes"+vo);
                searchSchoolsRes.add(getSchoolInfo(vo));
            }

            List<SchoolMstVo> similarSchoolsRes = new ArrayList<>();
            for (SchoolMstVo vo : similarSchools) {
                // vo = getSchoolInfo(vo);
                similarSchoolsRes.add(getSchoolInfo(vo));
            }

            apiResponse = new ApiResponse(200, "success", "success", searchSchoolsRes, dropdownFilterInfo,
                    similarSchoolsRes,
                    noOfData.intValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> returnRelatedSearches(SchoolSearchInputVo inputVo) {
        ApiResponse apiResponse = null;
        List<SchoolMstDocument> schoolHits = new ArrayList<>();

        try {
            SearchRequest searchRequest = SearchUtil.buildSearchRequest("schoolmstindex", inputVo);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = searchResponse.getHits().getHits();
            List<SchoolMstDocument> redundantHits = new ArrayList<>();
            for (SearchHit hit : hits) {
                redundantHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolMstDocument.class));
            }
            SchoolMstDocument leastImportantHit = redundantHits.get(redundantHits.size() - 1);
            String[] likeTexts = { inputVo.getSearchTerm(), inputVo.getBoardName(), inputVo.getMediumName() };
            String[] fields = { "school_name", "school_medium", "school_board" };
            SearchRequest request = SearchUtil.buildMltRequest("schoolmstindex", leastImportantHit, fields, likeTexts);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] relatedResults = response.getHits().getHits();
            System.out.println(response.toString());
            for (SearchHit hit : relatedResults) {
                schoolHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolMstDocument.class));

            }
            Long noOfData = response.getHits().getTotalHits().value;
            apiResponse = new ApiResponse(200, "success", "success", schoolHits,
                    noOfData.intValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    private PostFilterSearchRequestVo extractDropdownFilterInfo(
            Map<String, List<String>> responseMap) {
        if (responseMap == null)
            return null;

        Set<String> boards = new HashSet<>();
        Set<String> mediums = new HashSet<>();
        Set<String> cities = new HashSet<>();
        Set<String> schoolTypes = new HashSet<>();
        Set<String> states = new HashSet<>();
        Set<Double> minFees = new TreeSet();
        Set<Double> maxFees = new TreeSet();

        for (String key : responseMap.keySet()) {
            if (key.toLowerCase().equals("city.keyword")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        cities.add(keyword.toUpperCase());
                }
            }
            if (key.toLowerCase().equals("school_board.keyword")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        boards.add(keyword.toUpperCase());
                }
            }
            if (key.toLowerCase().equals("school_medium.keyword")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        mediums.add(keyword.trim().toUpperCase());
                }
            }
            if (key.toLowerCase().equals("state_name.keyword")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        states.add(keyword.toUpperCase());
                }
            }
            if (key.toLowerCase().equals("school_type.keyword")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        schoolTypes.add(keyword.toUpperCase());
                }
            }
            if (key.toLowerCase().equals("min_fees")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        minFees.add(Double.parseDouble(keyword));
                }

            }
            if (key.toLowerCase().equals("max_fees")) {
                for (String value : responseMap.get(key)) {
                    for (String keyword : value.split(","))
                        maxFees.add(Double.parseDouble(keyword));
                }
            }

        }

        PostFilterSearchRequestVo dropdownRequestVo = new PostFilterSearchRequestVo(boards, mediums, schoolTypes,
                states, cities, null, null, maxFees, minFees);
        return dropdownRequestVo;

    }

    private List<SchoolMstVo> getSimilarSchools(String indexName, List<SchoolMstDocument> schoolResults) {

        if (schoolResults.size() == 0)
            return new ArrayList<>();
        List<SchoolMstVo> similarSchools = new ArrayList<>();
        List<SchoolMstDocument> schoolHits = new ArrayList<>();
        SchoolMstDocument topHit = schoolResults.get(0);
        try {
            String[] mltFields = { "city", "school_medium", "school_board", "school_address" };
            String[] likeTexts = { "" };
            SearchRequest similarResultRequest = SearchUtil.buildMltRequest(indexName, topHit, mltFields, likeTexts);
            SearchResponse response = client.search(similarResultRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {

                schoolHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolMstDocument.class));
                similarSchools.add(
                        schoolMstDao.findById(Long.parseLong(hit.getId())).orElse(null));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return similarSchools;
    }

    public ResponseEntity<ApiResponse> createAutocompletionEngine(String indexName, SchoolSearchInputVo inputVo) {
        ApiResponse apiResponse = null;
        List<SchoolAutocompleteDocument> schools = new ArrayList<>();
        SearchRequest request = SearchUtil.buildAutocompleteSearchRequest(indexName, inputVo);
        SchoolSearchHitVo esReponse = getEsResponse(request);
        if (request == null) {
            LOGGER.error("cannot build search request");
            apiResponse = new ApiResponse(404, "not found", "not found", null, 0);
        }

        schools = esReponse.getListOfSchoolsNgrammed();

        apiResponse = new ApiResponse(200, "success", "success", schools, esReponse.getNoOfHits().intValue());

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> createAutocompletionEngineForCity(String indexName,
            SchoolSearchInputVo inputVo) {
        ApiResponse apiResponse = null;
        List<CityAutocompleteDocument> cities = new ArrayList<>();
        SearchRequest request = SearchUtil.buildAutocompleteSearchRequestCity(indexName, inputVo);
        CitySearchHitVo esReponse = getEsResponseForCity(request);
        if (request == null) {
            LOGGER.error("cannot build search request");
            apiResponse = new ApiResponse(404, "not found", "not found", null, 0);
        }

        cities = esReponse.getListOfCities();

        apiResponse = new ApiResponse(200, "success", "success", cities, esReponse.getNoOfHits().intValue());

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> filterSearchResults(PostFilterSearchRequestVo inputVo) {
        ApiResponse apiResponse = null;
        List<SchoolMstVo> schools = new ArrayList<>();
        List<SchoolMstDocument> schoolHits = new ArrayList<>();
        SearchRequest request = SearchUtil.getFilteredResults("schoolmstindex", inputVo);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            SearchHit[] hits = response.getHits().getHits();
            Long noOfData = response.getHits().getTotalHits().value;
            List<Long> relevantIds = new ArrayList<>();
            AttachmentVo attachmentVo=null;

            for (SearchHit hit : hits) {
                schoolHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolMstDocument.class));

                SchoolMstVo school = schoolMstDao.findById(Long.parseLong(hit.getId())).orElse(null); // TODO: need
                attachmentVo=attachmentDao.findByTxIdAndFormCodeSingle(school.getId(), "school_media");
                school.setSchoolLogoPath(((attachmentVo == null)? "" : attachmentVo.getFilePath()));                                                                                      // improvement
                school.setStateName(stateMstDao.findById(school.getState()).get().getStateName());
                schools.add(school);

            }
            List<SchoolMstVo> similarSchools = getSimilarSchools("schoolmstindex", schoolHits);
            apiResponse = new ApiResponse(200, "success", "success", schools, null, similarSchools,
                    noOfData.intValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    private SchoolSearchHitVo getEsResponse(SearchRequest request) {

        List<SchoolAutocompleteDocument> schoolHits = null;
        SchoolSearchHitVo result = new SchoolSearchHitVo();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            SearchHit[] hits = response.getHits().getHits();
            schoolHits = new ArrayList<SchoolAutocompleteDocument>(hits.length);
            Long noOfData = response.getHits().getTotalHits().value;

            for (SearchHit hit : hits) {
                schoolHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), SchoolAutocompleteDocument.class));

            }

            System.out.println(schoolHits);
            result.setNoOfHits(noOfData);
            result.setListOfSchoolsNgrammed(schoolHits);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private CitySearchHitVo getEsResponseForCity(SearchRequest request) {

        List<CityAutocompleteDocument> cityHits = null;
        CitySearchHitVo result = new CitySearchHitVo();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            SearchHit[] hits = response.getHits().getHits();
            cityHits = new ArrayList<CityAutocompleteDocument>(hits.length);
            Long noOfData = response.getHits().getTotalHits().value;

            for (SearchHit hit : hits) {
                cityHits.add(
                        MAPPER.readValue(hit.getSourceAsString(), CityAutocompleteDocument.class));

            }

            result.setNoOfHits(noOfData);
            result.setListOfCities(cityHits);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, List<String>> processAggregations(SearchResponse response) {

        Map<String, List<String>> responseMap = new HashMap<>();
        System.out.println(response);
        try {
            Map<String, Aggregation> keys = response.getAggregations().getAsMap();
            if (response.getAggregations() != null) {
                for (Aggregation agg : response.getAggregations().asList()) {

                    String key = agg.getName();
                    List<String> aggValues = new ArrayList<String>();
                    Terms terms = response.getAggregations().get(key);
                    for (Terms.Bucket bucket : terms.getBuckets()) {
                        String extLabel = bucket.getKey().toString();
                        aggValues.add(extLabel);
                    }
                    responseMap.put(key, aggValues);

                }
            }
            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    } 

    // ========================= private method area ====================

    public SchoolMstVo getSchoolInfo(SchoolMstVo entry) {
        // also get attachment files
        List<AttachmentVo> docList = attachmentDao.findByTxId(entry.getId());
        entry.setDocList(docList);

        for (AttachmentVo doc : docList) {
            if (doc.getDocType().equals("school_logo")) {
                entry.setSchoolLogoPath(doc.getFilePath());
            }
            if (doc.getDocType().equals("school_banner")) {
                entry.setSchoolBannerPath(doc.getFilePath());
            }
            if (doc.getDocType().equals("school_Brochure")) {
                entry.setSchoolBrochurePath(doc.getFilePath());
            }
        }

        /*
         * if(null != entry.getAdmissionStartDate() && null !=
         * entry.getAdmissionEndDate()){
         * boolean res = CommmonFunction.isDateBetween(entry.getAdmissionStartDate(),
         * entry.getAdmissionEndDate());
         * entry.setAdmissionOpen(res);
         * }
         */
        // get max start and end date from admission dtl
        String getAdmissionDates = schoolAdmissionDtlDao.getAdmissionDates(entry.getId());
        if (null != getAdmissionDates && !getAdmissionDates.contains("null")) {
            String[] dates = getAdmissionDates.split(",");
            String startDate = dates[0];
            String endDate = dates[1];

            if (null != startDate && !startDate.equals("null") && null != endDate && !endDate.equals("null")) {
                boolean res = CommmonFunction.isDateBetween(startDate, endDate);
                entry.setAdmissionOpen(res);
            }
        }

        /*
         * List<String> boards = entry.getSchoolBoardClassDtlVo().stream()
         * .map(SchoolBoardClassDtlVo::getBoard).distinct()
         * .collect(Collectors.toList());
         * 
         * List<String> mediums = entry.getSchoolBoardClassDtlVo().stream()
         * .map(SchoolBoardClassDtlVo::getMedium).distinct()
         * .collect(Collectors.toList());
         * 
         * entry.setBoard((null != boards && boards.size()>0) ? String.join(",", boards)
         * : "");
         * entry.setMedium((null != mediums && mediums.size()>0) ? String.join(",",
         * mediums) : "");
         */
        entry.setDistrictName((null != entry.getDistrict()) ? districtMstDao.getName(entry.getDistrict()) : "");
        entry.setStateName((null != entry.getState()) ? stateMstDao.getName(entry.getState()) : "");

        return entry;
    }

}
