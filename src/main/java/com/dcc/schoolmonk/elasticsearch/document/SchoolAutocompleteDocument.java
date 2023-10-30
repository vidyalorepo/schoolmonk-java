package com.dcc.schoolmonk.elasticsearch.document;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "schoolautocompleteindex")
@Setting(settingPath = "static/elasticsearch/settings/school-index-setting.json")
@Mapping(mappingPath = "static/elasticsearch/mappings/school-autocomplete-index-mapping.json")
public class SchoolAutocompleteDocument {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Search_As_You_Type)
    private String school_name;
    @Field(type = FieldType.Keyword)
    private String school_name_slug;

    public SchoolAutocompleteDocument() {
    }

    public SchoolAutocompleteDocument(Long id, String school_name, String school_name_slug) {
        this.id = id;
        this.school_name = school_name;
        this.school_name_slug = school_name_slug;
    }

    public String getSchool_name_slug() {
        return school_name_slug;
    }

    public void setSchool_name_slug(String school_name_slug) {
        this.school_name_slug = school_name_slug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
}
