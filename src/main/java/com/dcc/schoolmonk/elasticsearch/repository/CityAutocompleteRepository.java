package com.dcc.schoolmonk.elasticsearch.repository;

import com.dcc.schoolmonk.elasticsearch.document.CityAutocompleteDocument;
import com.dcc.schoolmonk.elasticsearch.document.SchoolAutocompleteDocument;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
//extends ElasticsearchRepository<CityAutocompleteDocument, String>
public interface CityAutocompleteRepository extends ElasticsearchRepository<CityAutocompleteDocument, String> {

}
