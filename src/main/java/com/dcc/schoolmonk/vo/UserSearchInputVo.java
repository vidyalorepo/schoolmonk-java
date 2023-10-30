package com.dcc.schoolmonk.vo;

public class UserSearchInputVo {
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userType;
    private Integer page;
    private Integer size;
    private String orderByColumn;
    private String orderBy;
    private String sort;

    public String getUserName() {
        return userName;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getSort() {
        return sort;
    }

    public UserSearchInputVo(String userName, String userEmail, String userPhone, String userType, Integer page,
            Integer size, String orderByColumn, String orderBy, String sort) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userType = userType;
        this.page = page;
        this.size = size;
        this.orderByColumn = orderByColumn;
        this.orderBy = orderBy;
        this.sort = sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "UserSearchInputVo [orderBy=" + orderBy + ", orderByColumn=" + orderByColumn + ", page=" + page
                + ", size=" + size + ", sort=" + sort + ", userEmail=" + userEmail + ", userName=" + userName
                + ", userPhone=" + userPhone + ", userType=" + userType + "]";
    }

}
