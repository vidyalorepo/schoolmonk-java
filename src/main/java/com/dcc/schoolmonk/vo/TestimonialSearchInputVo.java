package com.dcc.schoolmonk.vo;

public class TestimonialSearchInputVo {
    private String name;
    private String designation;
    private String institution;
    private Integer page;
    private Integer size;
    private String orderByColumn;
    private String sort;

    public TestimonialSearchInputVo(String name, String designation, String institution, Integer page, Integer size,
            String orderByColumn, String sort) {
        this.name = name;
        this.designation = designation;
        this.institution = institution;
        this.page = page;
        this.size = size;
        this.orderByColumn = orderByColumn;
        this.sort = sort;
    }

    public TestimonialSearchInputVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "TestimonialSearchInputVo [designation=" + designation + ", institution=" + institution + ", name="
                + name + ", orderByColumn=" + orderByColumn + ", page=" + page + ", size=" + size + ", sort=" + sort
                + "]";
    }
}
