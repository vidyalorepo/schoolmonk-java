package com.dcc.schoolmonk.dto;

public class ParentEnquiryDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private Integer size;
    private Integer page;
    private String sortBy;
    private String sortDir;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public String getSortBy() {
        return sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public String getSortDir() {
        return sortDir;
    }
    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
    

    
}
