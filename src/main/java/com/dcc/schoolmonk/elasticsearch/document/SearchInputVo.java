package com.dcc.schoolmonk.elasticsearch.document;

import java.util.List;

public class SearchInputVo extends PageRequestVo {

    private String searchTerm;
    private List<String> fields;
    private LocationVo location;
    private String distance;
    private FeesVo fees;

    public SearchInputVo(int page, int size, String sortColumn, String sortOrder, String searchTerm,
            List<String> fields, LocationVo location, String distance, FeesVo fees) {
        super(page, size, sortColumn, sortOrder);
        this.searchTerm = searchTerm;
        this.fields = fields;
        this.location = location;
        this.distance = distance;
        this.fees = fees;
    }

    public SearchInputVo(String searchTerm, List<String> fields, LocationVo location, String distance, FeesVo fees) {
        this.searchTerm = searchTerm;
        this.fields = fields;
        this.location = location;
        this.distance = distance;
        this.fees = fees;
    }

    public SearchInputVo() {
        super();
    }

    public FeesVo getFees() {
        return fees;
    }

    public void setFees(FeesVo fees) {
        this.fees = fees;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
    }
}
