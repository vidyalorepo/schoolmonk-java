package com.dcc.schoolmonk.elasticsearch.repository;

import com.dcc.schoolmonk.elasticsearch.document.SchoolAutocompleteDocument;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
//extends ElasticsearchRepository<SchoolAutocompleteDocument, Long>
public interface SchoolAutocompleteRepository extends ElasticsearchRepository<SchoolAutocompleteDocument, Long> {

}
