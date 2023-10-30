package com.dcc.schoolmonk.dto;

public class BlogDto {
    private Integer size;
    private Integer page;
    private String sortBy;
    private String title;
    private String status;
    private String sortDir;
    private String blogCategory;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSortDir() {
        return sortDir;
    }
    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBlogCategory() {
        return blogCategory;
    }
    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }    
}
