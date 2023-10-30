package com.dcc.schoolmonk.elasticsearch.document;

import java.util.List;

public class SchoolSearchInputVo extends SearchInputVo {
    private String boardName;
    private String mediumName;
    private String schoolType;

    public SchoolSearchInputVo(int page, int size, String sortColumn, String sortOrder, String searchTerm,
            List<String> fields, LocationVo location, String distance, FeesVo fees, String boardName,
            String mediumName) {
        super(page, size, sortColumn, sortOrder, searchTerm, fields, location, distance, fees);
        this.boardName = boardName;
        this.mediumName = mediumName;
    }

    public SchoolSearchInputVo(String searchTerm, List<String> fields, LocationVo location, String distance,
            FeesVo fees, String boardName, String mediumName) {
        super(searchTerm, fields, location, distance, fees);
        this.boardName = boardName;
        this.mediumName = mediumName;
    }

    public SchoolSearchInputVo(String boardName, String mediumName) {
        this.boardName = boardName;
        this.mediumName = mediumName;
    }

    public SchoolSearchInputVo() {
        super();
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    
}
