package com.dcc.schoolmonk.dto;

public class SaveAdsOrderDto {
      
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String zone;
    private int qty;
    private Boolean isAgree;
    private String duration;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
 
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public Boolean getIsAgree() {
        return isAgree;
    }
    public void setIsAgree(Boolean isAgree) {
        this.isAgree = isAgree;
    }
    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    
}
