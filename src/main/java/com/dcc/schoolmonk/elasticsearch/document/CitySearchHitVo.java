package com.dcc.schoolmonk.elasticsearch.document;

import java.util.List;

public class CitySearchHitVo {
    private Long noOfHits;
    private List<CityAutocompleteDocument> listOfCities;

    public CitySearchHitVo(Long noOfHits, List<CityAutocompleteDocument> listOfCities) {
        this.noOfHits = noOfHits;
        this.listOfCities = listOfCities;
    }

    public CitySearchHitVo() {
    }

    public Long getNoOfHits() {
        return noOfHits;
    }

    public void setNoOfHits(Long noOfHits) {
        this.noOfHits = noOfHits;
    }

    public List<CityAutocompleteDocument> getListOfCities() {
        return listOfCities;
    }

    public void setListOfCities(List<CityAutocompleteDocument> listOfCities) {
        this.listOfCities = listOfCities;
    }
}
