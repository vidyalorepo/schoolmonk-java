package com.dcc.schoolmonk.elasticsearch.repository;

import com.dcc.schoolmonk.elasticsearch.document.SchoolMstDocument;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends ElasticsearchRepository<SchoolMstDocument, String>{

   @Query("{\"bool\": {\"should\": [{\"multi_match\": {\"query\": \"?0\",\"fields\": [\"school_name\",\"school_name.metaphone\",\"school_name.soundex\",\"school_address\", \"school_address.metaphone\"]}}]}}")
   public SearchHits<SearchHit<SchoolMstDocument>> searchByKeyword(String searchTerm);
}
