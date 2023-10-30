package com.dcc.schoolmonk.elasticsearch.document;

import java.util.List;

public class SchoolSearchHitVo {
    private Long noOfHits;
    private List<SchoolMstDocument> listOfSchools;
    private List<SchoolAutocompleteDocument> listOfSchoolsNgrammed;

    public SchoolSearchHitVo() {
    }

    public SchoolSearchHitVo(Long noOfHits, List<SchoolMstDocument> listOfSchools,
            List<SchoolAutocompleteDocument> listOfSchoolsNgrammed) {
        this.noOfHits = noOfHits;
        this.listOfSchools = listOfSchools;
        this.listOfSchoolsNgrammed = listOfSchoolsNgrammed;
    }

    public Long getNoOfHits() {
        return noOfHits;
    }

    public void setNoOfHits(Long noOfHits) {
        this.noOfHits = noOfHits;
    }

    public List<SchoolMstDocument> getListOfSchools() {
        return listOfSchools;
    }

    public List<SchoolAutocompleteDocument> getListOfSchoolsNgrammed() {
        return listOfSchoolsNgrammed;
    }

    public void setListOfSchoolsNgrammed(List<SchoolAutocompleteDocument> listOfSchoolsNgrammed) {
        this.listOfSchoolsNgrammed = listOfSchoolsNgrammed;
    }

    public void setListOfSchools(List<SchoolMstDocument> listOfSchools) {
        this.listOfSchools = listOfSchools;
    }
}
