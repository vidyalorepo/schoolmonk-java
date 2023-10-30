package com.dcc.schoolmonk.elasticsearch.document;

import java.util.List;
import java.util.Set;

public class PostFilterSearchRequestVo extends PageRequestVo {
    private Set<String> board;
    private Set<String> medium;
    private Set<String> schoolType;
    private Set<String> state;
    private Set<String> city;
    private String searchTerm;
    private List<String> fields;
    private Set<Double> maxFees;
    private Set<Double> minFees;

    public PostFilterSearchRequestVo(int page, int size, String sortColumn, String sortOrder, Set<String> board,
            Set<String> medium, Set<String> schoolType, Set<String> state, Set<String> city, String searchTerm,
            List<String> fields, Set<Double> maxFees, Set<Double> minFees) {
        super(page, size, sortColumn, sortOrder);
        this.board = board;
        this.medium = medium;
        this.schoolType = schoolType;
        this.state = state;
        this.city = city;
        this.searchTerm = searchTerm;
        this.fields = fields;
        this.maxFees = maxFees;
        this.minFees = minFees;
    }

    public PostFilterSearchRequestVo(Set<String> board, Set<String> medium, Set<String> schoolType, Set<String> state,
            Set<String> city, String searchTerm, List<String> fields, Set<Double> maxFees, Set<Double> minFees) {
        this.board = board;
        this.medium = medium;
        this.schoolType = schoolType;
        this.state = state;
        this.city = city;
        this.searchTerm = searchTerm;
        this.fields = fields;
        this.maxFees = maxFees;
        this.minFees = minFees;
    }

    public PostFilterSearchRequestVo() {
        super();
    }

    public Set<String> getBoard() {
        return board;
    }

    public void setBoard(Set<String> board) {
        this.board = board;
    }

    public Set<String> getMedium() {
        return medium;
    }

    public Set<Double> getMaxFees() {
        return maxFees;
    }

    public void setMaxFees(Set<Double> maxFees) {
        this.maxFees = maxFees;
    }

    public Set<Double> getMinFees() {
        return minFees;
    }

    public void setMinFees(Set<Double> minFees) {
        this.minFees = minFees;
    }

    public void setMedium(Set<String> medium) {
        this.medium = medium;
    }

    public Set<String> getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Set<String> schoolType) {
        this.schoolType = schoolType;
    }

    public Set<String> getState() {
        return state;
    }

    public void setState(Set<String> state) {
        this.state = state;
    }

    public Set<String> getCity() {
        return city;
    }

    public void setCity(Set<String> city) {
        this.city = city;
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

}
