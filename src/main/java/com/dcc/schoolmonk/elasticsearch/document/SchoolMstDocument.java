package com.dcc.schoolmonk.elasticsearch.document;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "schoolmstindex")
@Setting(settingPath = "static/elasticsearch/settings/school-index-setting.json")
@Mapping(mappingPath = "static/elasticsearch/mappings/school-index-mapping.json")
public class SchoolMstDocument {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Text, name = "school_name")
    private String school_name;
    @Field(type = FieldType.Text, name = "school_address")
    private String school_address;
    @Field(type = FieldType.Text, name = "school_board")
    private String school_board;
    @Field(type = FieldType.Text, name = "school_medium")
    private String school_medium;
    @Field(type = FieldType.Text, name = "city")
    private String city;
    @Field(type = FieldType.Text, name = "state_name")
    private String state_name;
    @Field(type = FieldType.Text, name = "school_type")
    private String school_type;
    @Field(type = FieldType.Double, name = "max_fees")
    private Double max_fees;
    @Field(type = FieldType.Double, name = "min_fees")
    private Double min_fees;
    @Field(type = FieldType.Auto)
    private LocationVo location;

    public SchoolMstDocument() {
    }

    public SchoolMstDocument(Long id, String school_name, String school_address, String school_board,
            String school_medium, String city, String state_name, String school_type, Double max_fees, Double min_fees,
            LocationVo location) {
        this.id = id;
        this.school_name = school_name;
        this.school_address = school_address;
        this.school_board = school_board;
        this.school_medium = school_medium;
        this.city = city;
        this.state_name = state_name;
        this.school_type = school_type;
        this.max_fees = max_fees;
        this.min_fees = min_fees;
        this.location = location;
    }

    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
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

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_board() {
        return school_board;
    }

    public void setSchool_board(String school_board) {
        this.school_board = school_board;
    }

    public String getSchool_medium() {
        return school_medium;
    }

    public void setSchool_medium(String school_medium) {
        this.school_medium = school_medium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public Double getMax_fees() {
        return max_fees;
    }

    public void setMax_fees(Double max_fees) {
        this.max_fees = max_fees;
    }

    public Double getMin_fees() {
        return min_fees;
    }

    public void setMin_fees(Double min_fees) {
        this.min_fees = min_fees;
    }

}
