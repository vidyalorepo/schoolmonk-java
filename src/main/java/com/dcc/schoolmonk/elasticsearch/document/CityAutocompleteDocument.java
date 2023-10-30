package com.dcc.schoolmonk.elasticsearch.document;

import javax.persistence.Id;

import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition.Key;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "cityautocompleteindex")
@Setting(settingPath = "static/elasticsearch/settings/school-index-setting.json")
@Mapping(mappingPath = "static/elasticsearch/mappings/city-autocomplete-index-mapping.json")
public class CityAutocompleteDocument {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Search_As_You_Type)
    private String city;
    @Field(type = FieldType.Keyword)
    private String country;

    @Field(type = FieldType.Auto)
    private LocationVo location;

    public CityAutocompleteDocument(String id, String city, String country, LocationVo location) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.location = location;
    }

    public CityAutocompleteDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
    }

}
