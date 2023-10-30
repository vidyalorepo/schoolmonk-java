package com.dcc.schoolmonk.vo;

public class UserTypeVo {
    private Long typeCount;
    private String userType;

    public Long getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Long typeCount) {
        this.typeCount = typeCount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserTypeVo(Long typeCount, String userType) {
        this.typeCount = typeCount;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserTypeVo [typeCount=" + typeCount + ", userType=" + userType + "]";
    }

}
