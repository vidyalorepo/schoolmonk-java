package com.dcc.schoolmonk.vo;

import java.util.Date;

public class IssueSearchInputVo {
    private String issuerName;
    private String issuerType;
    private String issuerEmail;
    private String issueState;
    private String ticketId;
    private Date createdOn;
    private Integer page;
    private Integer size;
    private String orderByColumn;

    public String getIssueState() {
        return issueState;
    }

    public IssueSearchInputVo(String issuerName, String issuerType, String issuerEmail, String issueState,
            String ticketId, Date createdOn, Integer page, Integer size, String orderByColumn, String orderBy,
            String sort) {
        this.issuerName = issuerName;
        this.issuerType = issuerType;
        this.issuerEmail = issuerEmail;
        this.issueState = issueState;
        this.ticketId = ticketId;
        this.createdOn = createdOn;
        this.page = page;
        this.size = size;
        this.orderByColumn = orderByColumn;
        this.orderBy = orderBy;
        this.sort = sort;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    private String orderBy;
    private String sort;

    public IssueSearchInputVo() {
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuerEmail() {
        return issuerEmail;
    }

    public void setIssuerEmail(String issuerEmail) {
        this.issuerEmail = issuerEmail;
    }

    public String getIssuerType() {
        return issuerType;
    }

    public void setIssuerType(String issuerType) {
        this.issuerType = issuerType;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "IssueSearchInputVo [createdOn=" + createdOn + ", issueState=" + issueState + ", issuerEmail="
                + issuerEmail + ", issuerName=" + issuerName + ", issuerType=" + issuerType + ", orderBy=" + orderBy
                + ", orderByColumn=" + orderByColumn + ", page=" + page + ", size=" + size + ", sort=" + sort
                + ", ticketId=" + ticketId + "]";
    }

}
